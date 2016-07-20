package com.seven.hzxubowen.zhihudaily.Util;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.seven.hzxubowen.zhihudaily.bean.ApiURL;


/**
 * Created by hzxubowen on 2016/6/29.
 * 这个类用于初次获取latest新闻和下拉刷新再次获取latest新闻时使用
 */
public class LatestStringRequest {

    public interface LatestStringResponse{
        void responseSucc(String response);
    }

    private LatestStringResponse latestResponse;
    private RequestQueue queue;

    public LatestStringRequest(LatestStringResponse latestResponse, RequestQueue queue){
        this.latestResponse = latestResponse;
        this.queue = queue;
    }



    //获取首页响应
    public void getHomeStringResponse(){
        StringRequest stringRequest = new StringRequest(ApiURL.homeUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        latestResponse.responseSucc(response);
                    }
                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){

            }
        });
        queue.add(stringRequest);
    }


}
