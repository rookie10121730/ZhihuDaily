package com.seven.hzxubowen.zhihudaily.Util;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by hzxubowen on 2016/7/12.
 */
public class BaseRequest {
    private RequestQueue mQueue;
    private StringResponse mResponse;

    public interface StringResponse{
        void responseSucc(String response);
    }

    public BaseRequest(RequestQueue queue, StringResponse Response){
        mQueue = queue;
        mResponse = Response;
    }

    public void getPastNews(String url){
        StringRequest sq = new StringRequest(url,
                 new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        mResponse.responseSucc(response);
                    }},
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){

                    }
                });
        mQueue.add(sq);
        }
}