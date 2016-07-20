package com.seven.hzxubowen.zhihudaily.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.seven.hzxubowen.zhihudaily.R;
import com.seven.hzxubowen.zhihudaily.bean.TopStory;


/**
 * Created by hzxubowen on 2016/6/16.
 */
public class TopStoryView extends FrameLayout {

    private MyNetWorkImageView mImgView = null;
    private TextView mTextView = null;
    private Context mContext;


    public TopStoryView(Context context){
        super(context);
        LayoutInflater.from(context).inflate(R.layout.top_story_view, this, true);
        mContext = context;
        mImgView = (MyNetWorkImageView) findViewById(R.id.topImage);
        mTextView = (TextView)findViewById(R.id.topTitle);

    }



    public TopStoryView(Context context, AttributeSet attrs){
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.top_story_view, this, true);
        mContext = context;
        mImgView = (MyNetWorkImageView) findViewById(R.id.topImage);
        mTextView = (TextView)findViewById(R.id.topTitle);

    }

    /*设置图片接口*/
    public void setImageResource(String url, ImageLoader imageLoader){
        mImgView.setImageUrl(url, imageLoader);
    }

    /*设置文字接口*/
    public void setText(String str){
        mTextView.setText(str);
    }

    /*设置文字大小*/
    public void setTextSize(float size){
        mTextView.setTextSize(size);
    }


}
