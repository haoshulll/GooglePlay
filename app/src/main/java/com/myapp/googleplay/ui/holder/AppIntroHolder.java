package com.myapp.googleplay.ui.holder;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.myapp.googleplay.R;
import com.myapp.googleplay.bean.AppInfo;
import com.myapp.googleplay.global.GooglePlayApplication;
import com.myapp.googleplay.ui.anim.HeightAnimation;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by haoshul on 2016/6/26.
 */
public class AppIntroHolder extends BaseHolder<AppInfo> implements View.OnClickListener {
    @InjectView(R.id.tv_des)
    TextView tvDes;
    @InjectView(R.id.tv_author)
    TextView tvAuthor;
    @InjectView(R.id.iv_intro_arrow)
    ImageView ivIntroArrow;

    private int minHeight,maxHeight;
    public ScrollView scrollView;

    @Override
    protected View initView() {
        View view = View.inflate(GooglePlayApplication.getContext(), R.layout.layout_detail_app_intro, null);
        ButterKnife.inject(this, view);
        view.setOnClickListener(this);
        return view;
    }

    @Override
    public void bindView(AppInfo data) {
        tvDes.setText(data.getDes());
        tvAuthor.setText(data.getAuthor());

        //1.先获取5行的高度
        tvDes.setMaxLines(5);
        tvDes.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                //监听器用完一般都需要移除掉
                tvDes.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                minHeight = tvDes.getHeight();//得到好行文本的高度

                //2.需要获得全部文本的高度
                tvDes.setMaxLines(Integer.MAX_VALUE);//显示全本文本
                tvDes.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        tvDes.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        maxHeight = tvDes.getHeight();//得到全部文本的高度

                        //3.需要让tv_des一开始只显示5行的高度
                        tvDes.getLayoutParams().height = minHeight;
                        tvDes.requestLayout();
                    }
                });
            }
        });
    }


    public void setScrollView(ScrollView scrollView){
        this.scrollView = scrollView;
    }

    private boolean isExtend = false;//是否展开
    @Override
    public void onClick(View v) {
        HeightAnimation heightAnimation = null;
        if (isExtend){
            heightAnimation = new HeightAnimation(tvDes,maxHeight,minHeight);
        }else{
            heightAnimation = new HeightAnimation(tvDes,minHeight,maxHeight);
        }
        heightAnimation.addOnAnimatorUpdateListener(new HeightAnimation.OnAnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scrollView.scrollBy(0,maxHeight-minHeight);
            }
        });

        heightAnimation.start();
        isExtend = !isExtend;
    }
}
