package com.myapp.googleplay.ui.fragment;

import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.myapp.googleplay.R;
import com.myapp.googleplay.global.GooglePlayApplication;
import com.myapp.googleplay.http.HttpHelper;
import com.myapp.googleplay.http.Url;
import com.myapp.googleplay.lib.randomlayout.StellarMap;
import com.myapp.googleplay.util.ColorUtil;
import com.myapp.googleplay.util.CommonUtils;
import com.myapp.googleplay.util.JsonUtil;
import com.myapp.googleplay.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by haoshul on 2016/6/21.
 */
public class RecommendFragment extends BaseFragment {
    protected StellarMap stellarMap;


    @Override
    protected View getSuccessView() {
        stellarMap = new StellarMap(getActivity());
        int padding = (int) CommonUtils.getDimens(R.dimen.dp_15);
        stellarMap.setInnerPadding(padding,padding,padding,padding);
        return stellarMap;
    }

    @Override
    protected Object requestData() {
        String result = HttpHelper.get(Url.RECOMMEND);

        final ArrayList<String> list = (ArrayList<String>) JsonUtil.parseJsonToList(result,new TypeToken<List<String>>(){}.getType());


        if(list != null){
            CommonUtils.runOnUIThread(new Runnable() {
                @Override
                public void run() {
//                    Toast.makeText(getActivity(), list.toString(), Toast.LENGTH_SHORT).show();
                    stellarMap.setAdapter(new StellarMapAdapter(list));
                    stellarMap.setGroup(0,true);//第一次显示的数据，缩放动画
                    stellarMap.setRegularity(14,15);//x，y显示密度

                }
            });
        }
        return list;
    }

    class StellarMapAdapter implements StellarMap.Adapter{
        ArrayList<String> list = null;
        public StellarMapAdapter(ArrayList<String> list){
            this.list = list;
        }

        /**
         * 返回多少组数据
         * @return
         */
        @Override
        public int getGroupCount() {
            return 3;
        }

        /**
         * 每组多少数据
         * @param group
         * @return
         */
        @Override
        public int getCount(int group) {
            return 11;
        }

        /**
         * 暂时无用
         * @param group
         * @param degree
         * @return
         */
        @Override
        public int getNextGroupOnPan(int group, float degree) {
            return 0;
        }

        /**
         * 缩放动画完成后，下一组加载哪组数据
         * @param group
         * @param isZoomIn
         * @return
         */
        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            return (group+1)%getGroupCount();
        }

        /**
         *返回view对象
         * @param group   第几组
         * @param position   第几位
         * @param convertView
         * @return
         */
        @Override
        public View getView(int group, int position, View convertView) {
            final TextView textView = new TextView(getActivity());
            int listposition = group*getCount(group) + position;
            textView.setText(list.get(listposition));
            Random random = new Random();
            textView.setTextSize(14+random.nextInt(8));
            textView.setTextColor(ColorUtil.randomColor());
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showToast(GooglePlayApplication.getContext(),textView.getText().toString());
                }
            });
            return textView;
        }
    }
}
