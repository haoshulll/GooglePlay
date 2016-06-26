package com.myapp.googleplay.ui.holder;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myapp.googleplay.R;
import com.myapp.googleplay.bean.AppInfo;
import com.myapp.googleplay.bean.SafeInfo;
import com.myapp.googleplay.global.GooglePlayApplication;
import com.myapp.googleplay.global.ImageLoaderOptions;
import com.myapp.googleplay.http.Url;
import com.myapp.googleplay.ui.anim.HeightAnimation;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AppSafeHolder extends BaseHolder<AppInfo> implements OnClickListener {
    @InjectView(R.id.iv_image1)
    ImageView ivImage1;
    @InjectView(R.id.iv_image2)
    ImageView ivImage2;
    @InjectView(R.id.iv_image3)
    ImageView ivImage3;
    @InjectView(R.id.iv_safe_arrow)
    ImageView ivSafeArrow;
    @InjectView(R.id.iv_des_image1)
    ImageView ivDesImage1;
    @InjectView(R.id.tv_des1)
    TextView tvDes1;
    @InjectView(R.id.iv_des_image2)
    ImageView ivDesImage2;
    @InjectView(R.id.tv_des2)
    TextView tvDes2;
    @InjectView(R.id.ll_safe2)
    LinearLayout llSafe2;
    @InjectView(R.id.iv_des_image3)
    ImageView ivDesImage3;
    @InjectView(R.id.tv_des3)
    TextView tvDes3;
    @InjectView(R.id.ll_safe3)
    LinearLayout llSafe3;
    @InjectView(R.id.ll_des)
    LinearLayout llDes;
    private int height;

    @Override
    public View initView() {
        View view = View.inflate(GooglePlayApplication.getContext(), R.layout.layout_detail_app_safe, null);
        ButterKnife.inject(this, view);

        view.setOnClickListener(this);

        return view;
    }

    @Override
    public void bindView(AppInfo appInfo) {
        ArrayList<SafeInfo> safeList = appInfo.getSafe();
        //显示第一个
        SafeInfo safe1 = safeList.get(0);
        ImageLoader.getInstance().displayImage(Url.IMAGE_PREFIX + safe1.getSafeUrl(), ivImage1, ImageLoaderOptions.options);
        ImageLoader.getInstance().displayImage(Url.IMAGE_PREFIX + safe1.getSafeDesUrl(), ivDesImage1, ImageLoaderOptions.options);
        tvDes1.setText(safe1.getSafeDes());

        //由于第2个和第3个可能木有，那么需要判断
        if (safeList.size() > 1) {
            SafeInfo safe2 = safeList.get(1);
            ImageLoader.getInstance().displayImage(Url.IMAGE_PREFIX + safe2.getSafeUrl(), ivImage2, ImageLoaderOptions.options);
            ImageLoader.getInstance().displayImage(Url.IMAGE_PREFIX + safe2.getSafeDesUrl(), ivDesImage2, ImageLoaderOptions.options);
            tvDes2.setText(safe2.getSafeDes());
        } else {
            //隐藏第2个
            llSafe2.setVisibility(View.GONE);
        }

        if (safeList.size() > 2) {
            SafeInfo safe3 = safeList.get(2);
            ImageLoader.getInstance().displayImage(Url.IMAGE_PREFIX + safe3.getSafeUrl(), ivImage3, ImageLoaderOptions.options);
            ImageLoader.getInstance().displayImage(Url.IMAGE_PREFIX + safe3.getSafeDesUrl(), ivDesImage3, ImageLoaderOptions.options);
            tvDes3.setText(safe3.getSafeDes());
        } else {
            //隐藏第3个
            llSafe3.setVisibility(View.GONE);
        }


        //1.计算ll_des的高度
        llDes.measure(0, 0);
        height = llDes.getMeasuredHeight();
        //2.通过将ll_des高度设置为0
        llDes.getLayoutParams().height = 0;
        llDes.requestLayout();

        //给整个view添加下滑平移动画
        //先将View移动到上面
        ViewHelper.setTranslationX(holderView,-holderView.getWidth());
        ViewPropertyAnimator.animate(holderView)
                .translationX(0)
                .setDuration(500)
                .setInterpolator(new OvershootInterpolator(5 ))
                .start();
    }

    private boolean isExtend = false;//是否展开
    private boolean isRunAnim = false;//是否正在执行动画

    @Override
    public void onClick(View v) {
        if (v == holderView) {

            HeightAnimation heightAnimation = null;
            if (isRunAnim){
                //如果正在执行动画
                return;
            }

            if (isExtend) {
                //执行收缩动画
                heightAnimation = new HeightAnimation(llDes, height,0);
            } else {
                //执行展开动画
                heightAnimation = new HeightAnimation(llDes,0, height);
            }

            heightAnimation.start();

            //更改标记
            isExtend = !isExtend;

            //让箭头旋转
//			ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv_safe_arrow,"rotation",180);
//			objectAnimator.setDuration(350);
//			objectAnimator.start();

            ViewPropertyAnimator.animate(ivSafeArrow).rotationBy(180).setDuration(350)
                    .setListener(new DesAnimListener())
                    .start();

        }
    }

    class DesAnimListener implements Animator.AnimatorListener{
        @Override
        public void onAnimationStart(Animator animator) {
            isRunAnim = true;
        }
        @Override
        public void onAnimationEnd(Animator animator) {
            isRunAnim = false;
        }
        @Override
        public void onAnimationCancel(Animator animator) {

        }
        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    }

}
