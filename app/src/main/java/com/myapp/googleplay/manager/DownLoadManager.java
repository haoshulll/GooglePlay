package com.myapp.googleplay.manager;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.SparseArray;

import com.myapp.googleplay.bean.AppInfo;
import com.myapp.googleplay.global.GooglePlayApplication;
import com.myapp.googleplay.http.HttpHelper;
import com.myapp.googleplay.http.Url;
import com.myapp.googleplay.util.CommonUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by haoshul on 2016/6/26.
 */
public class DownLoadManager {
    //下载目录
    public static String DOWNLOAD_DIR = Environment.getExternalStorageDirectory()
            +"/"+ GooglePlayApplication.getContext().getPackageName()+"/download";
    public static final int STATE_NONE = 0;//初始状态
    public static final int STATE_DOWNLOADING = 1;//下载状态
    public static final int STATE_PAUSE = 2;//暂停状态
    public static final int STATE_FINISH = 3;//下载完成状态
    public static final int STATE_ERROR = 4;//下载失败状态
    public static final int STATE_WAITTING = 5;//等待状态

    //用来记录维护所有的下载监听对象
    private ArrayList<DownLoadObserver> observers = new ArrayList<>();
    //用来存放所有下载任务的DownloadInfo数据
    private SparseArray<DownloadInfo> downloadInfoMap = new SparseArray<>();

    private static DownLoadManager mInstance = new DownLoadManager();
    public static DownLoadManager getInstance(){
        return mInstance;
    }

    private DownLoadManager(){
        //初始化下载目录
        File dir = new File(DOWNLOAD_DIR);
        if (!dir.exists()){
            dir.mkdirs();
        }
    }

    /**
     * 下载的方法
     */
    public void downLoad(AppInfo appInfo){
        //1.取出DownloadInfo,因为后续的操作都需要根据state赖进行
        DownloadInfo downloadInfo = downloadInfoMap.get(appInfo.getId());
        if (downloadInfo == null){
            //未下载，需要生成DownLoadInfo并存入map中
            downloadInfo = DownloadInfo.create(appInfo);
            downloadInfoMap.put(downloadInfo.getId(),downloadInfo);
        }
        //2.判断state是否能够进行下载：none，pause,error才能进行下载
        if(downloadInfo.getState()==STATE_NONE || downloadInfo.getState()==STATE_PAUSE
                || downloadInfo.getState()==STATE_ERROR){
            //创建下载任务,并放入downloadTaskMap中维护起来
            DownloadTask downloadTask = new DownloadTask(downloadInfo);
//            downloadTaskMap.put(downloadInfo.getId(), downloadTask);

            //此时当前downloadInfo更新为等待状态
            downloadInfo.setState(STATE_WAITTING);
            //通知状态更新
            notifyStateChange(downloadInfo);

            //将任务交给ThreadPoolManager执行
            ThreadPoolManager.getInstance().execute(downloadTask);
        }
    }


    class DownloadTask implements Runnable {
        private DownloadInfo downloadInfo;

        public DownloadTask(DownloadInfo downloadInfo) {
            super();
            this.downloadInfo = downloadInfo;
        }

        @Override
        public void run() {
            //3.一旦执行run方法，说明任务已经开始执行，需要将状态更新为下载中
            downloadInfo.setState(STATE_DOWNLOADING);
            notifyStateChange(downloadInfo);

            //4.开始下载，需要判断从头下载还是断点下载
            HttpHelper.HttpResult  httpResult = null;
            File file = new File(downloadInfo.getPath());
            if (!file.exists()||file.length()!= downloadInfo.getCurrentLength()){
                //需从头下载
                file.delete();
                downloadInfo.setCurrentLength(0);//重置

                String url = String.format(Url.APP_DOWNLOAD,downloadInfo.getDownloadUrl());
                httpResult = HttpHelper.download(url);
            }else{
                //需要断点下载
                String url = String.format(Url.APP_BREAK_DOWNLOAD,downloadInfo.getDownloadUrl(),downloadInfo.getCurrentLength());
                httpResult = HttpHelper.download(url);
            }

            //5.获取流对象，写入文件
            if (httpResult != null && httpResult.getInputStream()!= null){
                //请求数据成功,开始写入文件
                InputStream inputStream = httpResult.getInputStream();
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(file,true);
                    byte[] buffer = new byte[1024*8];
                    int len = -1;
                    while ((len = inputStream.read(buffer))!=-1 && downloadInfo.getState() == STATE_DOWNLOADING) {
                        fos.write(buffer, 0, len);

                        //更新currentLength
                        downloadInfo.setCurrentLength(downloadInfo.getCurrentLength() + len);
                        //通知进度更新
                        notifyProgressChange(downloadInfo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //下载失败
                    processErrorState(file);
                }finally {
                    httpResult.close();
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                //6.到这里的情况：a.下载完成  b.暂停
                if (file.length() == downloadInfo.getSize() && downloadInfo.getState() == STATE_DOWNLOADING){
                    //下载完成
                    downloadInfo.setState(STATE_FINISH);
                    notifyStateChange(downloadInfo);
                }else if (downloadInfo.getState() == STATE_PAUSE){
                    notifyStateChange(downloadInfo);
                }

            }else{
                //下载失败
                processErrorState(file);
            }
        }

        private void processErrorState(File file) {
            file.delete();
            downloadInfo.setCurrentLength(0);
            downloadInfo.setState(STATE_ERROR);
            notifyStateChange(downloadInfo);
        }
    }

    /**
     * DownloadInfo
     * @param appInfo
     * @return
     */
    public DownloadInfo getDownloadInfo(AppInfo appInfo){
        return downloadInfoMap.get(appInfo.getId());
    }

    /**
     * 暂停的方法
     */
    public void pause(AppInfo appInfo){
        DownloadInfo downloadInfo = getDownloadInfo(appInfo);
        if (downloadInfo != null){
            downloadInfo.setState(STATE_PAUSE);
            notifyStateChange(downloadInfo);
        }
    }

    /**
     * 安装APK的方法
     */
    public void installApk(AppInfo appInfo){
        DownloadInfo downloadInfo = getDownloadInfo(appInfo);
        if (downloadInfo!=null){
            //安装apk
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse("file://"+downloadInfo.getPath()),"application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            GooglePlayApplication.getContext().startActivity(intent);
        }
    }


    /**
     * 注册监听器对象
     * @param observer
     */
    public void registerDownloadObserver(DownLoadObserver observer){
        if (!observers.contains(observer)){
            observers.add(observer);
        }
    }
    /**
     * 注销监听器对象
     * @param observer
     */
    public void ungisterDownloadObserver(DownLoadObserver observer){
        if (observers.contains(observer)){
            observers.remove(observer);
        }
    }

    /**
     * 通知所有的监听器状态更新了
     * @param downloadInfo
     */
    private void notifyStateChange(final DownloadInfo downloadInfo){
        CommonUtils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                for (DownLoadObserver observer : observers) {
                    observer.onDownloadStateChange(downloadInfo);
                }
            }
        });

    }
    /**
     * 通知所有的监听器下载进度更新了
     * @param downloadInfo
     */
    private void notifyProgressChange(final DownloadInfo downloadInfo){
        CommonUtils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                for (DownLoadObserver observer : observers) {
                    observer.onDownloadProgressChange(downloadInfo);
                }
            }
        });

    }

    public interface DownLoadObserver{
        /**
         * 下载状态改变的回调
         */
        void onDownloadStateChange(DownloadInfo downloadInfo);

        /**
         * 下载进度改变的回调
         */
        void onDownloadProgressChange(DownloadInfo downloadInfo);
    }
}
