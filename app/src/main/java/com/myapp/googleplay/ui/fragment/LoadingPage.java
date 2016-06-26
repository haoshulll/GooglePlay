package com.myapp.googleplay.ui.fragment;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.myapp.googleplay.R;
import com.myapp.googleplay.util.CommonUtils;

/**
 * Created by haoshul on 2016/6/21.
 */
public abstract class LoadingPage extends FrameLayout {
    public LoadingPage(Context context) {
        super(context);
        initLoadingPage();
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLoadingPage();
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLoadingPage();
    }

    enum PageState{
        STATE_LOADING,
        STATE_ERROR,
        STATE_SUCCESS
    }

    private PageState mState = PageState.STATE_LOADING;

    private View loadingView;
    private View errorView;
    private View successView;

    Button button;

    /**
     * 初始化loadingPage
     */
    private void initLoadingPage(){
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (loadingView == null){
            loadingView = View.inflate(getContext(), R.layout.page_loading,null);
        }
        addView(loadingView,layoutParams);

        if (errorView == null){
            errorView = View.inflate(getContext(),R.layout.page_error,null);

            button = (Button) errorView.findViewById(R.id.btn_reload);
            button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //显示进度条
                    mState = PageState.STATE_LOADING;
                    showPage();
                    //重加载数据
                    loadDataAndRefreshPage();
                }
            });
        }
        addView(errorView,layoutParams);

        if (successView == null){
            //通过抽象的方法来实现
            successView = createSuccessView();
        }
        if (successView != null){
            addView(successView,layoutParams);
        }else {
            throw new IllegalArgumentException("The method createSuccessView can not return null");
        }

        showPage();

        loadDataAndRefreshPage();
    }

    /**
     * 由于每个界面的successView都不一样，那么应该由每个界面自己去实现
     * @return
     */
    protected abstract View createSuccessView();

    /**
     * 根据当前状态mstate显示对应的View
     */
    private void showPage(){
        loadingView.setVisibility(View.INVISIBLE);
        successView.setVisibility(View.INVISIBLE);
        errorView.setVisibility(View.INVISIBLE);
        switch (mState){
            case STATE_LOADING:
                loadingView.setVisibility(View.VISIBLE);
                break;
            case STATE_SUCCESS:
                successView.setVisibility(View.VISIBLE);
                break;
            case STATE_ERROR:
                errorView.setVisibility(View.VISIBLE);
                break;
        }

    }

    /**
     * 加载数据并根据加载完成的数据刷新Page
     */
    public void loadDataAndRefreshPage(){
        new Thread(){
            @Override
            public void run() {

                SystemClock.sleep(500);

                //1.获取数据
                Object data = loadData();
                //2.检查是否为空
                mState = checkData(data);

                CommonUtils.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        //3.刷新Page
                        showPage();
                    }
                });
            }
        }.start();

    }

    private PageState checkData(Object data){
        return data == null? PageState.STATE_ERROR:PageState.STATE_SUCCESS;
    }

    /**
     * 由于只关心每个界面返回的数据是否为空，具体过程让每个界面去实现
     * @return
     */
    protected abstract Object loadData();
}
