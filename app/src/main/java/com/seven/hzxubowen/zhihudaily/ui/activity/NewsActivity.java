package com.seven.hzxubowen.zhihudaily.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

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

    private ActionBar mActionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fr_content);

        Intent intent = getIntent();
        if(intent != null){
            mNewsList = intent.getStringArrayListExtra(NEWS_LIST);
            mNewsId = intent.getStringExtra(NEWS_ID);
        }
        mRequestQueue = MyRequestQueue.getSingleton(this).getRequestQueue();
        mImageLoader = MyRequestQueue.getSingleton(this).getImageLoader();
        mViewPager = (ViewPager) findViewById(R.id.content_view_pager);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("测试代码", "onPageScrolled滑动中" + position);
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("测试代码", "onPageSelected选中了" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    //正在滑动   pager处于正在拖拽中

                    Log.d("测试代码", "onPageScrollStateChanged=======正在滑动" + "SCROLL_STATE_DRAGGING");

                } else if (state == ViewPager.SCROLL_STATE_SETTLING) {
                    //pager正在自动沉降，相当于松手后，pager恢复到一个完整pager的过程
                    Log.d("测试代码", "onPageScrollStateChanged=======自动沉降" + "SCROLL_STATE_SETTLING");

                } else if (state == ViewPager.SCROLL_STATE_IDLE) {
                    //空闲状态  pager处于空闲状态
                    Log.d("测试代码", "onPageScrollStateChanged=======空闲状态" + "SCROLL_STATE_IDLE");
                }
            }
        });


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
