package com.seven.hzxubowen.zhihudaily.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.seven.hzxubowen.zhihudaily.Util.LatestStringRequest;
import com.seven.hzxubowen.zhihudaily.Util.OnFragmentInteractionListener;
import com.seven.hzxubowen.zhihudaily.ui.fragment.ContentFragment;
import com.seven.hzxubowen.zhihudaily.ui.fragment.HomeFragment;
import com.seven.hzxubowen.zhihudaily.R;
import com.seven.hzxubowen.zhihudaily.ui.fragment.LoadingFragment;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {


    private RequestQueue mQueue;
    private FragmentManager mFragmentManager;
    private LatestStringRequest mLateStringRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ac_main);
        mQueue = Volley.newRequestQueue(this);
        mLateStringRequest = new LatestStringRequest(res, mQueue);
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().add(R.id.container,
                LoadingFragment.newInstance(), "loading").commit();
        mLateStringRequest.getHomeStringResponse();
    }

    private LatestStringRequest.LatestStringResponse res = new LatestStringRequest.LatestStringResponse(){
        @Override
        public void responseSucc(String response){
            mFragmentManager.beginTransaction().replace(R.id.container,
                    HomeFragment.newInstance(), "home").commit();
        }
    };

    //implements the interface
    public void onFragmentInteraction(String newsId, ArrayList<String> idList){
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(R.id.container, ContentFragment.newInstance(newsId, idList));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
