package com.seven.hzxubowen.zhihudaily.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.seven.hzxubowen.zhihudaily.R;

/**
 * Created by hzxubowen on 2016/6/26.
 */
public class ContentView extends FrameLayout {
    private MyNetWorkImageView mImgView = null;
    private TextView mTextView = null;
    private WebView mWebView = null;

    private Context mContext;


    public ContentView(Context context){
        super(context);
        LayoutInflater.from(context).inflate(R.layout.content_view, this, true);
        mContext = context;
        mImgView = (MyNetWorkImageView) findViewById(R.id.contentImage);
        mTextView = (TextView)findViewById(R.id.contentTitle);
        mWebView = (WebView) findViewById(R.id.contentWebView);
    }



    public ContentView(Context context, AttributeSet attrs){
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.top_story_view, this, true);
        mContext = context;
        mImgView = (MyNetWorkImageView) findViewById(R.id.contentImage);
        mTextView = (TextView)findViewById(R.id.contentTitle);
        mWebView = (WebView) findViewById(R.id.contentWebView);

    }

    /*设置图片接口*/
    public void setImageResource(String imageUrl, ImageLoader imageLoader){
        mImgView.setImageUrl(imageUrl, imageLoader);
    }

    /*设置文字接口*/
    public void setText(String str){
        mTextView.setText(str);
    }

    /*设置文字大小*/
    public void setTextSize(float size){
        mTextView.setTextSize(size);
    }

    /*WebView加载内容*/
    public void setWebView(String body){
        String html = "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" />" + body;
        mWebView.loadDataWithBaseURL("file:///android_asset/", html, "text/html","UTF-8", null);
    }

}
