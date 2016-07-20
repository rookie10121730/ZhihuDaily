package com.seven.hzxubowen.zhihudaily.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.seven.hzxubowen.zhihudaily.Util.LogicUtil;
import com.seven.hzxubowen.zhihudaily.widget.ContentView;
import com.seven.hzxubowen.zhihudaily.widget.TopStoryView;

import java.util.List;

/**
 * Created by hzxubowen on 2016/6/24.
 */
public class ContentViewPagerAdapter extends PagerAdapter {

    private List<ContentView> viewList;
    private String id;
    private RequestQueue queue;
    private ImageLoader imageLoader;
    private List<String> idList;

    public ContentViewPagerAdapter(List<ContentView> viewList, List<String> idList, RequestQueue queue, ImageLoader imageLoader) {
        this.viewList = viewList;
        this.idList = idList;
        this.queue = queue;
        this.imageLoader = imageLoader;
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
        container.addView(viewList.get(position));
        LogicUtil.getContentJsonResponse(idList.get(position), viewList.get(position), queue, imageLoader);
        return viewList.get(position);
    }
}
