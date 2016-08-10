package com.seven.hzxubowen.zhihudaily.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.seven.hzxubowen.zhihudaily.R;
import com.seven.hzxubowen.zhihudaily.Util.BaseRequest;
import com.seven.hzxubowen.zhihudaily.adapter.ContentViewPagerAdapter;
import com.seven.hzxubowen.zhihudaily.bean.ApiURL;
import com.seven.hzxubowen.zhihudaily.net.MyRequestQueue;
import com.seven.hzxubowen.zhihudaily.widget.ContentView;

import org.json.JSONObject;

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
    private int mCurrentPosition;

    private TextView mPraise;
    private TextView mComments;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fr_content);

        mPraise = (TextView) findViewById(R.id.tv_popularity);
        mComments = (TextView) findViewById(R.id.tv_comments);

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
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    //正在滑动   pager处于正在拖拽中
                } else if (state == ViewPager.SCROLL_STATE_SETTLING) {
                    //pager正在自动沉降，相当于松手后，pager恢复到一个完整pager的过程

                } else if (state == ViewPager.SCROLL_STATE_IDLE) {
                    //空闲状态  pager处于空闲状态
                    if(mViewPager.getCurrentItem() != mCurrentPosition){
                        mCurrentPosition = mViewPager.getCurrentItem();
                        mPraise.setText("...");
                        mComments.setText("...");
                        BaseRequest request = new BaseRequest(mRequestQueue, listener);
                        request.getPastNews(ApiURL.ZHIHU_NEWS_EXTRA + mNewsList.get(mCurrentPosition));
                    }
                }
            }
        });
        initData();
    }

    BaseRequest.StringResponse listener = new BaseRequest.StringResponse() {
        @Override
        public void responseSucc(String response) {
            try{
                JSONObject jo = new JSONObject(response);
                mPraise.setText(jo.getString("popularity"));
                mComments.setText(jo.getString("comments"));
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void responseFail(VolleyError error){

        }
    };


    private void initData(){
        BaseRequest request = new BaseRequest(mRequestQueue, listener);
        request.getPastNews(ApiURL.ZHIHU_NEWS_EXTRA + mNewsList.get(mCurrentPosition));
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
                mCurrentPosition = i;
            }
        }

    }


    public void goBack(View v){
        super.onBackPressed();
    }

    public void share(View v){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "http://codepath.com");
        startActivity(Intent.createChooser(shareIntent, "Share link using"));
    }

    public void waitForAdd(View v){
        Toast.makeText(this, "该功能待添加", Toast.LENGTH_SHORT).show();
    }

}
