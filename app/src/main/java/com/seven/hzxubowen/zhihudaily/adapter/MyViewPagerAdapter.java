package com.seven.hzxubowen.zhihudaily.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.seven.hzxubowen.zhihudaily.widget.TopStoryView;

import java.util.List;

/**
 * Created by hzxubowen on 2016/6/20.
 */
public class MyViewPagerAdapter extends PagerAdapter {

    private List<TopStoryView> viewList;

    public MyViewPagerAdapter(List<TopStoryView> viewList) {
        this.viewList = viewList;
    }



    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        container.removeView(viewList.get(position));

    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "title";
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewGroup parent = (ViewGroup) viewList.get(position).getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        container.addView(viewList.get(position));
        return viewList.get(position);
    }
}



