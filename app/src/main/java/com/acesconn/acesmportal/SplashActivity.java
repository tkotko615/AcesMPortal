package com.acesconn.acesmportal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener{
    ImageView imageView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = this;

        //取消ActionBar
        getSupportActionBar().hide();
        //取消狀態欄
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        imageView = (ImageView)findViewById(R.id.img_splash);

        //imageView 設定動畫元件(透明度調整)
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash_anim);
        animation.setFillEnabled(true);
        animation.setFillAfter(true);
        animation.setAnimationListener(this);
        imageView.setAnimation(animation);

        /*另一寫法
        另起線程停3秒再切主畫面
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent().setClass(SplashActivity.this, MainActivity.class));
            }
        }, 3000);  //停三秒
        */
    }

    /*實作 Animation.AnimationListener 的三種方法*/
    @Override
    public void onAnimationStart(Animation animation) {}

    @Override
    public void onAnimationEnd(Animation animation) {
        startActivity(new Intent(context,MainActivity.class));
        finish();  //關閉過場畫面
    }

    @Override
    public void onAnimationRepeat(Animation animation) {}
}
