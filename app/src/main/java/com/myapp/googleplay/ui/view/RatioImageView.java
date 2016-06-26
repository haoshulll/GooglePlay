package com.myapp.googleplay.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.myapp.googleplay.global.GooglePlayApplication;

/**
 * Created by haoshul on 2016/6/23.
 */
public class RatioImageView extends ImageView{
    private float ratio;

    public RatioImageView(Context context) {
        super(context);
    }

    public RatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        String namespace = "http://schemas.android.com/apk/res-auto";
        ratio = attrs.getAttributeFloatValue(namespace,"riv_ratio",0f);
        Log.i(GooglePlayApplication.TAG, "RatioImageView: "+ratio);

    }

    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setRatio(float ratio){
        this.ratio = ratio;
    }

    /**
     * MeasureSpec:测量规则，包含size,mode(测量模式)
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     *  size:具体大小
     *  mode:测量模式有三种：AT_MOST：对应 wrap_content
     *                    EXACTLY:对应具体数值，match_parent
     *                    ENSPECIFIED:未确定值
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        if (ratio != 0){
            float height = width/ratio;
            heightMeasureSpec = MeasureSpec.makeMeasureSpec((int)height,MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
