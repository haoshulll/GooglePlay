package com.myapp.googleplay.ui.anim;

import android.animation.ValueAnimator;
import android.view.View;

/**
 * Created by haoshul on 2016/6/26.
 */
public class HeightAnimation {
    private ValueAnimator valueAnimator;

    public HeightAnimation(final View view,int startHeight,int endHeight) {
        valueAnimator = ValueAnimator.ofInt(startHeight,endHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                view.getLayoutParams().height = animatedValue;
                view.requestLayout();

                //将动画执行暴露给外界
                if(animatorUpdateListener != null){
                    animatorUpdateListener.onAnimationUpdate(animation);
                }

            }
        });
        valueAnimator.setDuration(350);
    }

    /**
     * 开启动画
     */
    public void start(){
        valueAnimator.start();
    }


    private OnAnimatorUpdateListener animatorUpdateListener;
    public void addOnAnimatorUpdateListener(OnAnimatorUpdateListener animatorUpdateListener){
        this.animatorUpdateListener = animatorUpdateListener;
    }

    public interface OnAnimatorUpdateListener{
        void onAnimationUpdate(ValueAnimator animation);
    }

}
