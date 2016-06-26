package com.myapp.googleplay.ui.adapter;

import com.myapp.googleplay.bean.CategoryInfo;
import com.myapp.googleplay.ui.holder.BaseHolder;
import com.myapp.googleplay.ui.holder.CategoryInfoHolder;
import com.myapp.googleplay.ui.holder.CategoryTitleHolder;

import java.util.ArrayList;

/**
 * Created by haoshul on 2016/6/23.
 */
public class CategoryAdapter extends BasicAdapter<Object> {
    ArrayList<Object> list = new ArrayList<>();
    public CategoryAdapter(ArrayList<Object> list) {
        super(list);
        this.list = list;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    private final int ITEM_TITLE = 0;
    private final int ITEM_INFO = 1;


    @Override
    public int getItemViewType(int position) {
        Object object = list.get(position);
        if (object instanceof String){
            return ITEM_TITLE;
        }else if(object instanceof CategoryInfo){
            return ITEM_INFO;
        }
        return super.getItemViewType(position);
    }

    @Override
    public BaseHolder<Object> getHolder(int position) {
        int itemViewType = getItemViewType(position);
        switch (itemViewType){
            case ITEM_TITLE:
                return new CategoryTitleHolder();
            case ITEM_INFO:
                return new CategoryInfoHolder();
        }
        return null;
    }
}
