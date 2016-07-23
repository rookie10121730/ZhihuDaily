package com.seven.hzxubowen.zhihudaily.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.android.volley.toolbox.ImageLoader;
import com.seven.hzxubowen.zhihudaily.R;

/**
 * Created by hzxubowen on 2016/7/23.
 */
public class TitleHeaderView extends FrameLayout {

    private MyNetWorkImageView myNetWorkImageView;


    public TitleHeaderView(Context context){
        super(context);
        LayoutInflater.from(context).inflate(R.layout.title_header_view, this, true);
        myNetWorkImageView = (MyNetWorkImageView) findViewById(R.id.title_image);
    }

    public TitleHeaderView(Context context, AttributeSet attr){
        super(context, attr);
        LayoutInflater.from(context).inflate(R.layout.title_header_view, this, true);
        myNetWorkImageView = (MyNetWorkImageView) findViewById(R.id.title_image);
    }

    /*设置图片接口*/
    public void setImageUrl(String url, ImageLoader imageLoader){
        myNetWorkImageView.setImageUrl(url, imageLoader);
    }

}
