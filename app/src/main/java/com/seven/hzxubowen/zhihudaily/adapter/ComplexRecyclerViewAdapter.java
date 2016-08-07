package com.seven.hzxubowen.zhihudaily.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.seven.hzxubowen.zhihudaily.R;
import com.seven.hzxubowen.zhihudaily.Util.LogicUtil;
import com.seven.hzxubowen.zhihudaily.Util.OnLoadMoreListener;
import com.seven.hzxubowen.zhihudaily.bean.Story;
import com.seven.hzxubowen.zhihudaily.widget.CircleIndicator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by hzxubowen on 2016/7/13.
 */
public class ComplexRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Object> mNewsList;
    private ImageLoader mImageLoader;
    private MyViewPagerAdapter adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private final int DATE = 0, NEWS = 1, TOP = 2, LOAD = 3;

    public void setLoaded() {
        isLoading = false;
    }

    private boolean isLoading;
    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;


    public interface OnItemClickListener{
        void onItemClick(View itemView, int position);
    }

    private OnItemClickListener mListener;


    public ComplexRecyclerViewAdapter(List<Object> newsList, ImageLoader imageLoader, MyViewPagerAdapter viewPagerAdapter, RecyclerView mRecyclerView, SwipeRefreshLayout swipeRefreshLayout
            , final OnLoadMoreListener mOnLoadMoreListener){
        mNewsList = newsList;
        mImageLoader = imageLoader;
        adapter = viewPagerAdapter;
        mSwipeRefreshLayout = swipeRefreshLayout;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //防止下拉刷新时，因为ITEM被移除时 触发下拉加载
                if(dy <= 0) return;
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                Log.e("xbw", "total:" + totalItemCount + " last:" + lastVisibleItem);

                if(!isLoading && lastVisibleItem + visibleThreshold >= totalItemCount){
                    if(mOnLoadMoreListener != null){
                        mOnLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position){
        if(position == 0){
            return TOP;
        }
        if(mNewsList.get(position - 1) instanceof String){
            return DATE;
        }else if(mNewsList.get(position - 1) instanceof Story){
            return NEWS;
        }else{
            return LOAD;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        Log.e("xbw","ViewType: " + viewType);
        switch (viewType){
            case DATE:
                View v1 = inflater.inflate(R.layout.item_recycler_date, parent, false);
                viewHolder = new ViewHolderForDate(v1);
                break;
            case NEWS:
                View v2 = inflater.inflate(R.layout.item_recycle_story, parent, false);
                viewHolder = new ViewHolderForStory(v2);
                break;
            case TOP:
                View v3 = inflater.inflate(R.layout.item_recycler_top, parent, false);
                viewHolder = new ViewHolderForTop(v3);
                break;
            case LOAD:
                View v4 = inflater.inflate(R.layout.item_recycler_loading, parent, false);
                viewHolder = new ViewHolderForLoading(v4);
                break;
            default:
                return null;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position){

        if(position == 0){
            ViewHolderForTop vh3 = (ViewHolderForTop) viewHolder;
            return;
        }

        switch(getItemViewType(position)){
            case DATE:
                ViewHolderForDate vh1 = (ViewHolderForDate) viewHolder;
                //vh1.getDateText().setText((String)mNewsList.get(position - 1));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String str = sdf.format(new Date());
                if(str.equals(mNewsList.get(position - 1))){
                    vh1.getDateText().setText("今日热闻");
                }else{
                    vh1.getDateText().setText(LogicUtil.getWeekByDateStr((String)mNewsList.get(position - 1)));
                }

                break;
            case NEWS:
                ViewHolderForStory vh2 = (ViewHolderForStory) viewHolder;
                vh2.getNewsImage().setImageUrl(((Story)mNewsList.get(position - 1)).getImageUrl(), mImageLoader);
                vh2.getNewsTitle().setText(((Story)mNewsList.get(position - 1)).getDailyTitle());
                break;
            case LOAD:
                ViewHolderForLoading vh4 = (ViewHolderForLoading) viewHolder;
                vh4.getmProgressBar().setIndeterminate(false);
                break;
        }
    }

    @Override
    public int getItemCount(){
        return mNewsList.size() + 1;
    }

    public void setItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    //四种对应不同布局的不同的ViewHolder
    public class ViewHolderForDate extends RecyclerView.ViewHolder {
        private TextView dateText;


        public ViewHolderForDate(View v){
            super(v);
            dateText = (TextView) v.findViewById(R.id.story_date);
        }

        public TextView getDateText() {
            return dateText;
        }
    }

    public class ViewHolderForLoading extends RecyclerView.ViewHolder {

        private ProgressBar mProgressBar;


        public ProgressBar getmProgressBar() {
            return mProgressBar;
        }

        public ViewHolderForLoading(View v){
            super(v);
            mProgressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }

    public class ViewHolderForStory extends RecyclerView.ViewHolder{
        private TextView newsTitle;
        private NetworkImageView newsImage;
        private TextView newsId;


        public ViewHolderForStory(View v){
            super(v);
            newsTitle = (TextView) v.findViewById(R.id.story_title);
            newsImage = (NetworkImageView) v.findViewById(R.id.story_image);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        mListener.onItemClick(v, getLayoutPosition());
                    }
                }
            });
        }
        public TextView getNewsTitle() {
            return newsTitle;
        }
        public NetworkImageView getNewsImage() {
            return newsImage;
        }

    }
    public class ViewHolderForTop extends RecyclerView.ViewHolder {

        private ViewPager viewPager;

        private CircleIndicator circleIndicator;

        public ViewPager getViewPager() {
            return viewPager;
        }


        public ViewHolderForTop(View v){
            super(v);
            viewPager = (ViewPager)v.findViewById(R.id.top);
            getViewPager().setAdapter(adapter);
            getViewPager().setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_MOVE:
                            mSwipeRefreshLayout.setEnabled(false);
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            mSwipeRefreshLayout.setEnabled(true);
                            break;
                    }
                    return false;
                }
            });
            circleIndicator = (CircleIndicator) v.findViewById(R.id.indicator);
            circleIndicator.setViewPager(viewPager);
        }
    }

}