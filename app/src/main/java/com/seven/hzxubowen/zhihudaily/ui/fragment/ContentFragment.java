package com.seven.hzxubowen.zhihudaily.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.seven.hzxubowen.zhihudaily.R;
import com.seven.hzxubowen.zhihudaily.adapter.ContentViewPagerAdapter;
import com.seven.hzxubowen.zhihudaily.cache.BitmapCache;
import com.seven.hzxubowen.zhihudaily.widget.ContentView;
import java.util.ArrayList;
import java.util.List;


public class ContentFragment extends Fragment {


    private static final String CURRENT_NEWS_ID = "param1";
    private static final String NEWS_ID_LIST = "param2";

    //第一个参数是首页响应，用与生成content ViewPager
    //第二个参数是首页点击的新闻的Id，用于指明现在应该呈现的新闻页面
    private String mCurrentNewsId;
    private ArrayList<String> mNewsIdList;
    private ContentViewPagerAdapter contentViewPagerAdapter;
    private ContentView mContentView;
    private List<ContentView> mViewList;
    private ViewPager mViewPager;
    private RequestQueue mQueue;
    private ImageLoader mImageLoader;

    public ContentFragment() {
        // Required empty public constructor
    }


    public static ContentFragment newInstance(String param1, ArrayList<String> param2) {
        ContentFragment fragment = new ContentFragment();
        Bundle args = new Bundle();
        args.putString(CURRENT_NEWS_ID, param1);
        args.putStringArrayList(NEWS_ID_LIST, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mCurrentNewsId = getArguments().getString(CURRENT_NEWS_ID);
            mNewsIdList = getArguments().getStringArrayList(NEWS_ID_LIST);
        }
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_content, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.content_view_pager);
        mImageLoader = new ImageLoader(mQueue, new BitmapCache());
        contentViewPagerAdapter = new ContentViewPagerAdapter(mViewList, mNewsIdList, mQueue, mImageLoader);
        mViewPager.setAdapter(contentViewPagerAdapter);

        for(int i=0; i<mNewsIdList.size(); i++){
            if(mCurrentNewsId.equals(mNewsIdList.get(i))){
                mViewPager.setCurrentItem(i);
            }
        }
        return view;
    }

    private void initData(){
        mViewList = new ArrayList<>();
        for(int i=0; i<mNewsIdList.size(); i++){
            mContentView = new ContentView(getActivity());
            mViewList.add(mContentView);
        }
        mQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
    }
}
