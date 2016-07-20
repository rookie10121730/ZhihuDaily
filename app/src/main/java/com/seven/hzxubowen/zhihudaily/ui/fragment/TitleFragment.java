package com.seven.hzxubowen.zhihudaily.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.seven.hzxubowen.zhihudaily.R;
import com.seven.hzxubowen.zhihudaily.Util.BaseRequest;
import com.seven.hzxubowen.zhihudaily.Util.LogicUtil;
import com.seven.hzxubowen.zhihudaily.adapter.NewsAdapter;
import com.seven.hzxubowen.zhihudaily.bean.ApiURL;
import com.seven.hzxubowen.zhihudaily.bean.Story;
import com.seven.hzxubowen.zhihudaily.net.MyRequestQueue;
import com.seven.hzxubowen.zhihudaily.widget.MyNetWorkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by hzxubowen on 2016/7/19.
 */
public class TitleFragment extends BaseFragment {

    public static final String TITLE = "param1";
    public static final String ID = "param2";
    public String mTitle;
    public String mId;

    private RequestQueue mQueue;
    private BaseRequest mBaseRequest;
    private NewsAdapter mNewsAdapter;

    private ArrayList<Story> mStoryList;
    private String mUrl;

    private ListView mListView;
    private MyNetWorkImageView mTitleImage;
    private TextView mTitleEditor;

    public TitleFragment(){

    }


    public static TitleFragment newInstance(String param1, String param2){
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, param1);
        bundle.putString(ID, param2);
        TitleFragment titleFragment = new TitleFragment();
        titleFragment.setArguments(bundle);
        return titleFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            mTitle = getArguments().getString(TITLE);
            mId = getArguments().getString(ID);
        }

        mQueue = MyRequestQueue.getSingleton(getActivity()).getRequestQueue();
        showLoadingFragment("loading");
        mBaseRequest = new BaseRequest(mQueue, stringResponse);
        mBaseRequest.getPastNews(ApiURL.ZHIHU_TITLE_DAILY + mId);

        mStoryList = new ArrayList<>();

        mNewsAdapter = new NewsAdapter(getActivity(), mStoryList, MyRequestQueue.getSingleton(getActivity()).getImageLoader());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fr_title, container, false);

        mListView = (ListView) v.findViewById(R.id.title_list);
        mTitleImage = (MyNetWorkImageView) v.findViewById(R.id.title_image);
        //mTitleEditor = (TextView) v.findViewById(R.id.title_text);
        mListView.setAdapter(mNewsAdapter);
        LogicUtil.setListViewHeight(mListView);
        return v;

    }

    BaseRequest.StringResponse stringResponse = new BaseRequest.StringResponse() {
        @Override
        public void responseSucc(String response) {
            dismissLoadingFragment();
            getTitleResponse(response);
            mNewsAdapter.notifyDataSetChanged();
            LogicUtil.setListViewHeight(mListView);
        }
    };

    public void getTitleResponse(String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            mUrl = jsonObject.getString("background");
            mTitleImage.setImageUrl(mUrl, MyRequestQueue.getSingleton(getActivity()).getImageLoader());
            JSONArray storyJsonArray = jsonObject.getJSONArray("stories");
            for(int i=0; i<storyJsonArray.length(); i++){
                JSONObject jo = storyJsonArray.getJSONObject(i);
                //判断story是否有无图新闻
                Story story;
                if(!jo.has("images")){
                    Log.e("No Imgage", "true");
                    story = new Story(jo.getString("title"), null,
                            jo.getString("id"));
                }else{
                    story = new Story(jo.getString("title"), (String)jo.getJSONArray("images").get(0),
                            jo.getString("id"));
                }
                mStoryList.add(story);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
