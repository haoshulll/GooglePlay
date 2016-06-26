package com.myapp.googleplay.ui.holder;

import android.view.View;

/**
 * Created by haoshul on 2016/6/22.
 */
public abstract class BaseHolder<T> {
    protected View holderView;
    public BaseHolder(){
        //1.初始化View·    ·
        holderView = initView();
        //2.设置tag
        holderView.setTag(this);
    }

    protected abstract View initView();

    /**
     * 绑定数据到View的方法
     * @param data
     */
    public abstract void bindView(T data);

    public View getHolderView(){
        return holderView;
    }
}
