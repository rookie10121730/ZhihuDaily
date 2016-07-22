package com.seven.hzxubowen.zhihudaily.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.seven.hzxubowen.zhihudaily.R;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        EasySplashScreen config = new EasySplashScreen(SplashActivity.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(3000)
                .withBackgroundResource(android.R.color.holo_blue_light)
                .withFooterText("Copyright By Seven")
                .withBeforeLogoText("知乎日报")
                .withLogo(R.mipmap.logo)
                .withAfterLogoText("每天三次，每次七分钟");


        //set your own animations
        myCustomTextViewAnimation(config.getFooterTextView());

        //customize all TextViews
        Typeface font = Typeface.createFromAsset(getAssets(), "scyahei.ttf");
        config.getAfterLogoTextView().setTypeface(font);
        config.getFooterTextView().setTextColor(Color.WHITE);
        config.getFooterTextView().setTextSize(25);
        config.getFooterTextView().setTypeface(font);

        //create the view
        View easySplashScreenView = config.create();

        setContentView(easySplashScreenView);
    }

    private void myCustomTextViewAnimation(TextView tv){
        Animation animation=new TranslateAnimation(0,0,480,0);
        animation.setDuration(2000);
        tv.startAnimation(animation);
    }
}
