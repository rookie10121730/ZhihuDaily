package com.seven.hzxubowen.zhihudaily.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.seven.hzxubowen.zhihudaily.R;
import com.seven.hzxubowen.zhihudaily.Util.BaseRequest;
import com.seven.hzxubowen.zhihudaily.Util.LogicUtil;
import com.seven.hzxubowen.zhihudaily.Util.OnFragmentInteractionListener;
import com.seven.hzxubowen.zhihudaily.adapter.NewsAdapter;
import com.seven.hzxubowen.zhihudaily.bean.ApiURL;
import com.seven.hzxubowen.zhihudaily.bean.Story;
import com.seven.hzxubowen.zhihudaily.net.MyRequestQueue;
import com.seven.hzxubowen.zhihudaily.widget.CircleNetworkImageView;
import com.seven.hzxubowen.zhihudaily.widget.MyNetWorkImageView;
import com.seven.hzxubowen.zhihudaily.widget.TitleHeaderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by hzxubowen on 2016/7/19.
 */
public class TitleFragment extends BaseFragment implements ListView.OnItemClickListener{

    public static final String TITLE = "param1";
    public static final String ID = "param2";
    public String mTitle;
    public String mId;
    private ArrayList<String> mIdList;

    private RequestQueue mQueue;
    private BaseRequest mBaseRequest;
    private NewsAdapter mNewsAdapter;

    private ArrayList<Story> mStoryList;
    private String mUrl;

    private ListView mListView;
    //用来当ListView的header的View
    private TitleHeaderView mTitleHeaderView;

    private LinearLayout mEditorLayout;


    private OnFragmentInteractionListener mListener;


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
        mIdList = new ArrayList<>();

        mNewsAdapter = new NewsAdapter(getActivity(), mStoryList, MyRequestQueue.getSingleton(getActivity()).getImageLoader());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fr_title, container, false);
        mListView = (ListView) v.findViewById(R.id.title_list);


        mTitleHeaderView = new TitleHeaderView(getActivity());
        mEditorLayout = (LinearLayout) mTitleHeaderView.findViewById(R.id.title_editor);
        //mTitleEditor = (TextView) v.findViewById(R.id.title_text);
        mListView.setAdapter(mNewsAdapter);
        mListView.setOnItemClickListener(this);
        mListView.addHeaderView(mTitleHeaderView);
        //LogicUtil.setListViewHeight(mListView);
        return v;

    }

    BaseRequest.StringResponse stringResponse = new BaseRequest.StringResponse() {
        @Override
        public void responseSucc(String response) {
            dismissLoadingFragment();
            getTitleResponse(response);
            mNewsAdapter.notifyDataSetChanged();
            //LogicUtil.setListViewHeight(mListView);
        }

        @Override
        public void responseFail(VolleyError error){

        }

    };

    public void getTitleResponse(String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            mUrl = jsonObject.getString("background");
            mTitleHeaderView.setImageUrl(mUrl, MyRequestQueue.getSingleton(getActivity()).getImageLoader());
            JSONArray storyJsonArray = jsonObject.getJSONArray("stories");
            for(int i=0; i<storyJsonArray.length(); i++){
                JSONObject jo = storyJsonArray.getJSONObject(i);
                //判断story是否有无图新闻
                Story story;
                if(!jo.has("images")){
                    story = new Story(jo.getString("title"), null,
                            jo.getString("id"));
                }else{
                    story = new Story(jo.getString("title"), (String)jo.getJSONArray("images").get(0),
                            jo.getString("id"));
                }
                mIdList.add(jo.getString("id"));
                mStoryList.add(story);
            }

            JSONArray editorJsonArray = jsonObject.getJSONArray("editors");
            for(int i=0; i<editorJsonArray.length(); i++){
                JSONObject jo = editorJsonArray.getJSONObject(i);
                CircleNetworkImageView civ = new CircleNetworkImageView(getActivity());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(100, 100);
                lp.setMargins(10, 10, 10, 10);
                lp.gravity = Gravity.CENTER_VERTICAL;
                civ.setLayoutParams(lp);
                civ.setImageUrl(jo.getString("avatar"), MyRequestQueue.getSingleton(getActivity()).getImageLoader());
                mEditorLayout.addView(civ);
            }

        }catch (JSONException e){
            e.printStackTrace();
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        if(position == 0){
            return;
        }else {
            mListener.onFragmentInteraction(mStoryList.get(position - 1).getStoryId(), mIdList);
        }

    }


}
