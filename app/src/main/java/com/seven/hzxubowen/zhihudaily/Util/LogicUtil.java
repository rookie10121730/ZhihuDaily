package com.seven.hzxubowen.zhihudaily.Util;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.seven.hzxubowen.zhihudaily.bean.ApiURL;
import com.seven.hzxubowen.zhihudaily.bean.Story;
import com.seven.hzxubowen.zhihudaily.bean.TopStory;
import com.seven.hzxubowen.zhihudaily.widget.ContentView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

/**
 * Created by hzxubowen on 2016/6/14.
 */
public class LogicUtil {

    //在srollView中嵌套listview时，会出现listview高度显示不正确的情况，此时需要自己手动去测量listview的长度
    public static void setListViewHeight(ListView listView){
        if(listView.getAdapter() == null)
            return;

        int totalHeight = 0;
        for(int i=0; i<listView.getAdapter().getCount(); i++){
            View listItem = listView.getAdapter().getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = totalHeight + (listView.getDividerHeight() * (listView.getAdapter().getCount() - 1));

        listView.setLayoutParams(layoutParams);
    }


    //解析主题日报中的数据
    //注意，story中可能出现无图的现象，要单独处理
    public static void resTitleResponse(String homeReponse, List<Story> storyList){
        try{
            JSONObject jsonObject = new JSONObject(homeReponse);
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
                storyList.add(story);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    public static void getContentJsonResponse(String id, final ContentView view, RequestQueue queue, final ImageLoader imageLoader){
        String url = ApiURL.contentUrl + id;

        JsonObjectRequest jsonRequest = new JsonObjectRequest(url,
                new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try{
                        String title, imageUrl;
                        if(response.has("image") && response.has("title")){
                            title = response.getString("title");
                            imageUrl = response.getString("image");
                            view.setImageResource(imageUrl, imageLoader);
                            view.setText(title);
                        }else{
                            view.getmImgView().setVisibility(View.GONE);
                            view.getmTextView().setVisibility(View.GONE);
                        }
                        String body = response.getString("body");
                        view.setWebView(body);

                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){

                }
            });

        queue.add(jsonRequest);
    }

    //计算日期是星期几，并作简单处理
    public static String getWeekByDateStr(String strDate)
    {
        int year = Integer.parseInt(strDate.substring(0, 4));
        int month = Integer.parseInt(strDate.substring(4, 6));
        int day = Integer.parseInt(strDate.substring(6, 8));

        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.DAY_OF_MONTH, day);

        String week = "";
        int weekIndex = c.get(Calendar.DAY_OF_WEEK);

        switch (weekIndex)
        {
            case 1:
                week = "星期天";
                break;
            case 2:
                week = "星期一";
                break;
            case 3:
                week = "星期二";
                break;
            case 4:
                week = "星期三";
                break;
            case 5:
                week = "星期四";
                break;
            case 6:
                week = "星期五";
                break;
            case 7:
                week = "星期六";
                break;
        }
        return month+"月" + day +"日 " + week;
    }


    //px转换成dp
    public static int px2dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }


}
