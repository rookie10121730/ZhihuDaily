package com.seven.hzxubowen.zhihudaily.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.seven.hzxubowen.zhihudaily.R;
import com.seven.hzxubowen.zhihudaily.Util.OnFragmentInteractionListener;
import com.seven.hzxubowen.zhihudaily.Util.OnLoadMoreListener;
import com.seven.hzxubowen.zhihudaily.Util.BaseRequest;
import com.seven.hzxubowen.zhihudaily.adapter.ComplexRecyclerViewAdapter;
import com.seven.hzxubowen.zhihudaily.adapter.MyViewPagerAdapter;
import com.seven.hzxubowen.zhihudaily.bean.ApiURL;
import com.seven.hzxubowen.zhihudaily.bean.Story;
import com.seven.hzxubowen.zhihudaily.net.MyRequestQueue;
import com.seven.hzxubowen.zhihudaily.widget.TopStoryView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment{

    //当前页面的控件，RecyclerView和SwipeRefreshLayout
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private RequestQueue mQueue;

    //Fragment与Activity交互Listener
    private OnFragmentInteractionListener mListener;

    //ViewPager和RecyclerView的数据源
    private ArrayList<Object> mNewsList;
    private ArrayList<TopStoryView> mTopViewList;

    //分别用于存放News和Top的Id
    private ArrayList<String> mNewsIdList;
    private ArrayList<String> mTopIdList;

    private RecyclerView.LayoutManager mLayoutManager;
    private ImageLoader imageLoader;

    //适配器
    private ComplexRecyclerViewAdapter adapter;
    private MyViewPagerAdapter myViewPagerAdapter;


    //标记当前新闻的日期
    private int currentDate;
    //用于辨别是下拉刷新还是上拉加载
    private boolean isNeedRefresh = false;

    //0代表初次加载，1代表下拉刷新，2代表上滑加载
    private int requestType = 0;

    public HomeFragment() {

    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsList = new ArrayList<>();
        mTopIdList = new ArrayList<>();
        mNewsIdList = new ArrayList<>();
        mTopViewList = new ArrayList<>();

        //获取全局的RequestQueue和ImageLoader
        mQueue = MyRequestQueue.getSingleton(getActivity()).getRequestQueue();
        imageLoader = MyRequestQueue.getSingleton(getActivity()).getImageLoader();
        showLoadingFragment("loading");
        getHomeResponse(ApiURL.ZHIHU_LATEST_URL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fr_home, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.rv_test);
        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);
        myViewPagerAdapter = new MyViewPagerAdapter(mTopViewList);
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.sr_home);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                requestType = 1;
                Log.e("requestType", "" + requestType);
                isNeedRefresh = true;
                refresh();
            }
        });

        //为RecyclerView初始化一个adapter
        adapter = new ComplexRecyclerViewAdapter(mNewsList, imageLoader, myViewPagerAdapter, mRecyclerView, mSwipeRefreshLayout,
                new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        requestType = 2;
                        loadMore();
                    }
                });

        //添加点击事件
        adapter.setItemClickListener(new ComplexRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                mListener.onFragmentInteraction(((Story)mNewsList.get(position-1)).getStoryId(), mNewsIdList);
            }
        });
        mRecyclerView.setAdapter(adapter);
        return v;
    }

    private void refresh(){
        synchronized (this){
            if(isNeedRefresh){
                int size = mNewsList.size();
                resetData();
                adapter.notifyItemRangeRemoved(0, size + 1);
                myViewPagerAdapter.notifyDataSetChanged();
                Log.e("xbw2", "News:" + mNewsList.size() + " Top:" + mTopViewList.size());
                if(!mSwipeRefreshLayout.isRefreshing()){
                    mSwipeRefreshLayout.setRefreshing(true);
                }
                getHomeResponse(ApiURL.ZHIHU_LATEST_URL);
                isNeedRefresh = false;
            }
        }
    }

    private void loadMore(){
        synchronized (this){
            mNewsList.add(null);
            adapter.notifyItemInserted(mNewsList.size());
            //Load more data for reyclerview
            getHomeResponse(ApiURL.ZHIHU_PAST_BASE + (currentDate));
        }
    }


    public void resHomeJsonResponse(String homeReponse){
        try{
            JSONObject jsonObject = new JSONObject(homeReponse);
            String date = jsonObject.getString("date");
            currentDate = Integer.parseInt(date);
            mNewsList.add(date);
            JSONArray storyJsonArray = jsonObject.getJSONArray("stories");

            for(int i=0; i<storyJsonArray.length(); i++){
                JSONObject jo = storyJsonArray.getJSONObject(i);
                JSONArray ja = jo.getJSONArray("images");
                //判断story是否有无图新闻
                Story story = new Story(jo.getString("title"), (String)ja.get(0), jo.getString("id"));
                mNewsList.add(story);
                mNewsIdList.add(jo.getString("id"));
            }
            if(jsonObject.has("top_stories")){
                JSONArray topJsonArray = jsonObject.getJSONArray("top_stories");
                for(int i=0; i<topJsonArray.length(); i++){
                    JSONObject jo = (JSONObject) topJsonArray.get(i);
                    TopStoryView topStoryView = new TopStoryView(getActivity());
                    topStoryView.setText(jo.getString("title"));
                    topStoryView.setImageResource(jo.getString("image"), imageLoader);
                    mTopIdList.add(jo.getString("id"));
                    mTopViewList.add(topStoryView);
                }
            }
            setClickListenerForTop();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }


    private void getHomeResponse(String url){
        BaseRequest baseRequest = new BaseRequest(mQueue, response);
        baseRequest.getPastNews(url);
    }

    BaseRequest.StringResponse response = new BaseRequest.StringResponse() {
        @Override
        public void responseSucc(String response) {
            switch (requestType){
                case 0:
                    dismissLoadingFragment();
                    resHomeJsonResponse(response);
                    myViewPagerAdapter.notifyDataSetChanged();
                    adapter.notifyItemRangeChanged(1, mNewsList.size());
                    break;
                case 1:

                    resHomeJsonResponse(response);
                    myViewPagerAdapter.notifyDataSetChanged();
                    adapter.notifyItemRangeChanged(1, mNewsList.size());
                    if(mSwipeRefreshLayout.isRefreshing()){
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    break;
                case 2:
                    if(mNewsList.size() > 0 && mNewsList.get(mNewsList.size()-1) == null){
                        mNewsList.remove(mNewsList.size() - 1);
                        adapter.notifyItemRemoved(mNewsList.size() + 1);
                    }
                    resHomeJsonResponse(response);
                    adapter.notifyItemRangeChanged(0, mNewsList.size()+1);
                    adapter.setLoaded();
                    break;
                default:
                    dismissLoadingFragment();
                    resHomeJsonResponse(response);
                    break;
            }
        }

        @Override
        public void responseFail(VolleyError error){
            if(mSwipeRefreshLayout.isRefreshing()){
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }
    };

    //为Top添加点击事件
    private void setClickListenerForTop(){
        for(int i=0; i<mTopViewList.size(); i++){
            final int tmp = i;
            mTopViewList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onFragmentInteraction(mTopIdList.get(tmp), mTopIdList);
                }
            });
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    //防止Activity销毁后，mListener仍然保留对Activity的引用，造成内存泄漏
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //下拉刷新时清空数据源
    private void resetData(){
        mNewsList.clear();
        mTopViewList.clear();
        mTopIdList.clear();
        mNewsIdList.clear();
    }
}
