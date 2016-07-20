package com.seven.hzxubowen.zhihudaily.net;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.seven.hzxubowen.zhihudaily.cache.BitmapCache;

/**
 * Created by hzxubowen on 2016/7/19.
 */
public class MyRequestQueue {
    private static MyRequestQueue mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private Context mContext;

    private MyRequestQueue(Context context){
        mContext = context;
        mRequestQueue = getRequestQueue();
        mImageLoader = new ImageLoader(mRequestQueue, new BitmapCache());
    }

    public static synchronized MyRequestQueue getSingleton(Context context){
        if(mInstance == null){
            mInstance = new MyRequestQueue(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            //通过与Application 的Context绑定，使得mRequestQueue的生命周期与APP同步
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public ImageLoader getImageLoader(){
        return mImageLoader;
    }


}
