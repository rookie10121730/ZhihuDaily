package com.seven.hzxubowen.zhihudaily.adapter;

import android.content.Context;
import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.seven.hzxubowen.zhihudaily.R;
import com.seven.hzxubowen.zhihudaily.bean.Story;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzxubowen on 2016/6/21.
 */
public class NewsAdapter extends BaseAdapter {

    private ArrayList<Story> storyList;
    private LayoutInflater inflater;
    private ImageLoader imageLoader;

    public NewsAdapter(Context context, ArrayList<Story> storyList, ImageLoader imageLoader){
        inflater = LayoutInflater.from(context);
        this.storyList = storyList;
        this.imageLoader = imageLoader;
    }

    @Override
    public int getCount(){
        return storyList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.news_list_item, parent, false);
            viewHolder.storyTitle = (TextView) convertView.findViewById(R.id.story_title);
            viewHolder.storyImage = (NetworkImageView) convertView.findViewById(R.id.story_image);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.storyTitle.setText(storyList.get(position).getDailyTitle());
//        viewHolder.storyImage.setDefaultImageResId(R.drawable.loadingpicture);
//        viewHolder.storyImage.setErrorImageResId(R.drawable.loadingpicture);

        //判断是否有图，有图则加载，无图则隐藏该控件
        if(storyList.get(position).getImageUrl() != null){
            viewHolder.storyImage.setImageUrl(storyList.get(position).getImageUrl(), imageLoader);
        }else{
            viewHolder.storyImage.setVisibility(View.GONE);
            //ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            //        ViewGroup.LayoutParams.WRAP_CONTENT);
            //viewHolder.storyTitle.setLayoutParams(layoutParams);
        }
        return convertView;

    }

    @Override
    public Object getItem(int position){
        return storyList.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    class ViewHolder{
        TextView storyTitle;
        NetworkImageView storyImage;
    }


}
