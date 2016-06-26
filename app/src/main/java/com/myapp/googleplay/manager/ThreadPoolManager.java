package com.myapp.googleplay.manager;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by haoshul on 2016/6/26.
 */
public class ThreadPoolManager {
    private static ThreadPoolManager mInstance = new ThreadPoolManager();
    public static ThreadPoolManager getInstance(){
        return mInstance;
    }
    private ThreadPoolExecutor executor;
    private int corePoolSize;//核心线程池，表示能够同时执行的任务数量
    private int maximumPoolSize;//最大线程池，包含了corePoolSize的数量
    private int keepAliveTime = 1;//最大线程池中任务的存活时间
    private TimeUnit unit = TimeUnit.HOURS;
    private ThreadPoolManager(){
        //设定同时执行的任务数量：当前设备的可用cpu核心数*2+1
        corePoolSize = Runtime.getRuntime().availableProcessors()*2+1;
        maximumPoolSize = corePoolSize;//一般不用到，但是不要给0
        executor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                new LinkedBlockingQueue<Runnable>(),//缓存队列，一般不会限制上限
                Executors.defaultThreadFactory(),//使用默认
                new ThreadPoolExecutor.AbortPolicy()//取消策略，表示超出最大线程池的任务就拒绝管理了
        );
    }

    /**
     * 往线程池中添加任务
     * @param runnable
     */
    public void execute(Runnable runnable){
        if (runnable!=null){
            executor.execute(runnable);
        }
    }


    /**
     * 移除任务
     * @param runnable
     */
    public void remove(Runnable runnable) {
        if (runnable != null){
            executor.remove(runnable);
        }
    }
}
