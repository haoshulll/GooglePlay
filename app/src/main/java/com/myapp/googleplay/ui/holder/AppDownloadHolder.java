package com.myapp.googleplay.ui.holder;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.myapp.googleplay.R;
import com.myapp.googleplay.bean.AppInfo;
import com.myapp.googleplay.global.GooglePlayApplication;
import com.myapp.googleplay.manager.DownLoadManager;
import com.myapp.googleplay.manager.DownloadInfo;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by haoshul on 2016/6/26.
 */
public class AppDownloadHolder extends BaseHolder<AppInfo> implements DownLoadManager.DownLoadObserver,View.OnClickListener{

    @InjectView(R.id.pb_progress)
    ProgressBar pbProgress;
    @InjectView(R.id.btn_download)
    TextView btnDownload;
    public AppInfo appInfo;

    @Override
    protected View initView() {
        View view = View.inflate(GooglePlayApplication.getContext(), R.layout.layout_detail_app_download, null);
        ButterKnife.inject(this, view);
        btnDownload.setOnClickListener(this);
        DownLoadManager.getInstance().registerDownloadObserver(this);
        return view;
    }

    @Override
    public void bindView(AppInfo data) {
        this.appInfo = data;
        DownloadInfo downloadInfo = DownLoadManager.getInstance().getDownloadInfo(appInfo);
        if (downloadInfo != null) {
            updateDownloadState(downloadInfo);
        }
    }


    @Override
    public void onDownloadStateChange(DownloadInfo downloadInfo) {
        updateDownloadState(downloadInfo);
    }


    /**
     * 更新下载状态
     * @param downloadInfo
     */
    private void updateDownloadState(DownloadInfo downloadInfo) {
        if(appInfo == null || appInfo.getId() != downloadInfo.getId()){
            return;
        }

        switch (downloadInfo.getState()) {
            case DownLoadManager.STATE_FINISH:
                btnDownload.setText("安装");
                break;
            case DownLoadManager.STATE_PAUSE:
                float fraction = downloadInfo.getCurrentLength()*100f/downloadInfo.getSize();
                pbProgress.setProgress((int) fraction);
                btnDownload.setBackgroundResource(0);
                btnDownload.setText("继续下载");
                break;
            case DownLoadManager.STATE_ERROR:
                btnDownload.setText("出错，重新下载");
                break;
            case DownLoadManager.STATE_WAITTING:
                btnDownload.setText("等待中");
                break;

        }
    }

    @Override
    public void onDownloadProgressChange(DownloadInfo downloadInfo) {
        if(appInfo == null || appInfo.getId() != downloadInfo.getId()){
            return;
        }
         float fraction = downloadInfo.getCurrentLength()*100f/downloadInfo.getSize();
        pbProgress.setProgress((int) fraction);
        btnDownload.setBackgroundResource(0);
        btnDownload.setText((int) fraction+"%");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_download:
                DownloadInfo downloadInfo = DownLoadManager.getInstance().getDownloadInfo(appInfo);
                if (downloadInfo == null) {
                    // 说明需要下载
                    DownLoadManager.getInstance().downLoad(appInfo);
                } else {
                    if (downloadInfo.getState() == DownLoadManager.STATE_DOWNLOADING
                            || downloadInfo.getState() == DownLoadManager.STATE_WAITTING) {
                        // 暂停
                        DownLoadManager.getInstance().pause(appInfo);
                    } else if (downloadInfo.getState() == DownLoadManager.STATE_FINISH) {
                        // 安装
                        DownLoadManager.getInstance().installApk(appInfo);
                    } else {
                        // 下载或者重新下载
                        DownLoadManager.getInstance().downLoad(appInfo);
                    }
                }
                break;
        }
    }
}
