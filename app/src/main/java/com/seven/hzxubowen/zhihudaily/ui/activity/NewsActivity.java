package com.seven.hzxubowen.zhihudaily.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.seven.hzxubowen.zhihudaily.R;
import com.seven.hzxubowen.zhihudaily.adapter.ContentViewPagerAdapter;
import com.seven.hzxubowen.zhihudaily.net.MyRequestQueue;
import com.seven.hzxubowen.zhihudaily.widget.ContentView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzxubowen on 2016/7/22.
 */
public class NewsActivity extends AppCompatActivity{
    //用于标记HomeActivity传入的Intent变量
    public static String NEWS_LIST = "newsList";
    public static String NEWS_ID = "newsId";
    private ArrayList<String> mNewsList;
    private String mNewsId;

    //全局RequestQueue和ImageLoader
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private ContentView mContentView;
    private List<ContentView> mViewList;
    private ViewPager mViewPager;
    private ContentViewPagerAdapter contentViewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if(intent != null){
            mNewsList = intent.getStringArrayListExtra(NEWS_LIST);
            mNewsId = intent.getStringExtra(NEWS_ID);
        }

        mRequestQueue = MyRequestQueue.getSingleton(this).getRequestQueue();
        mImageLoader = MyRequestQueue.getSingleton(this).getImageLoader();

        setContentView(R.layout.fr_content);
        mViewPager = (ViewPager) findViewById(R.id.content_view_pager);
        initData();

    }

    private void initData(){
        mViewList = new ArrayList<>();
        for(int i=0; i<mNewsList.size(); i++){
            mContentView = new ContentView(this);
            mViewList.add(mContentView);
        }

        contentViewPagerAdapter = new ContentViewPagerAdapter(mViewList, mNewsList, mRequestQueue, mImageLoader);
        mViewPager.setAdapter(contentViewPagerAdapter);

        for(int i=0; i<mNewsList.size(); i++){
            if(mNewsId.equals(mNewsList.get(i))){
                mViewPager.setCurrentItem(i);
            }
        }

    }




}
