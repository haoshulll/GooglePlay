package com.myapp.googleplay.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.myapp.googleplay.R;
import com.myapp.googleplay.global.GooglePlayApplication;
import com.myapp.googleplay.http.HttpHelper;
import com.myapp.googleplay.http.Url;
import com.myapp.googleplay.ui.view.FlowLayout;
import com.myapp.googleplay.util.ColorUtil;
import com.myapp.googleplay.util.CommonUtils;
import com.myapp.googleplay.util.DrawableUtil;
import com.myapp.googleplay.util.JsonUtil;
import com.myapp.googleplay.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haoshul on 2016/6/21.
 */
public class HotFragment extends BaseFragment {
    private FlowLayout flowLayout;
    private int textHpadding,textVpadding;
    private ScrollView scollview;
    private float radius;
    @Override
    protected View getSuccessView() {
        radius = CommonUtils.getDimens(R.dimen.dp_6);
        flowLayout = new FlowLayout(getActivity());
        textHpadding = (int) CommonUtils.getDimens(R.dimen.dp_12);
        textVpadding = (int) CommonUtils.getDimens(R.dimen.dp_6);
        scollview = new ScrollView(getActivity());
        int padding = (int) CommonUtils.getDimens(R.dimen.dp_15);
        //设置padding
        flowLayout.setPadding(padding,padding,padding,padding);
        //设置间距
        flowLayout.setHorizontalSpacing(padding);
        flowLayout.setVerticalSpacing(padding);
        scollview.addView(flowLayout);
        return scollview;
    }

    @Override
    protected Object requestData() {
        final String result = HttpHelper.get(Url.HOT);
        final ArrayList<String> list = (ArrayList<String>) JsonUtil.parseJsonToList(result,new TypeToken<List<String>>(){}.getType());

        if (list != null){
                CommonUtils.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        for (String str: list){
                            final TextView textView = new TextView(getActivity());
                            textView.setText(str);
                            textView.setGravity(Gravity.CENTER);
                            textView.setTextColor(Color.WHITE);
                            GradientDrawable pressed = DrawableUtil.generateDrawable(ColorUtil.randomColor(),radius);
                            GradientDrawable normal = DrawableUtil.generateDrawable(ColorUtil.randomColor(),radius);
                            textView.setBackgroundDrawable(DrawableUtil.generateSelector(normal,pressed));
                            textView.setTextSize(16);
                            textView.setPadding(textHpadding,textVpadding,textHpadding,textVpadding);
                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ToastUtil.showToast(GooglePlayApplication.getContext(),textView.getText().toString());
                                }
                            });
                            flowLayout.addView(textView);
                        }
                    }
                });

        }
        return list;
    }
}
