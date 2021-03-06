package com.seven.hzxubowen.zhihudaily.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.seven.hzxubowen.zhihudaily.R;
import com.seven.hzxubowen.zhihudaily.Util.GraphUtil;

public class SplashActivity extends AppCompatActivity {

    RelativeLayout sfv;
    ImageView img;
    ObjectAnimator tranOa;
    ObjectAnimator alphaOa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_splash);
        sfv = (RelativeLayout) findViewById(R.id.splash_footer);
        img = (ImageView) findViewById(R.id.splash_body);
        initAnimation();
    }

    AnimatorListenerAdapter adapter = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            if(animation == tranOa){
                img.setVisibility(View.VISIBLE);
                img.setImageBitmap(GraphUtil.decodeSampledBitmapFromResource(getResources(), R.drawable.splash, 540, 960));
                alphaOa.start();
            }else{
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };

    void initAnimation(){

        alphaOa = ObjectAnimator.ofFloat(img, "alpha", 0.0f, 1.0f);
        alphaOa.setDuration(2500);
        alphaOa.addListener(adapter);

        tranOa = ObjectAnimator.ofFloat(sfv, "translationY", 480, 0);
        tranOa.setDuration(1000);
        tranOa.addListener(adapter);
        tranOa.start();

    }

    @Override
    public void onBackPressed(){

    }

}
