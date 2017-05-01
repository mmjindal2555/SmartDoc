package com.silk.smartdoc.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.silk.smartdoc.R;

import java.util.Timer;

public class SplashScreen extends AppCompatActivity {
    TextView wetv;
    TextView aretv;
    TextView smarttv;
    TextView doctv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        wetv = (TextView)findViewById(R.id.weText);
        aretv = (TextView)findViewById(R.id.areText);
        smarttv = (TextView)findViewById(R.id.smartText);
        doctv = (TextView)findViewById(R.id.docText);

        getWindow().setStatusBarColor(getResources().getColor(R.color.statusbarcolor));

        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "font/Satisfy-Regular.ttf");
        wetv.setTypeface(myCustomFont);
        aretv.setTypeface(myCustomFont);
        smarttv.setTypeface(myCustomFont);
        doctv.setTypeface(myCustomFont);

        Animation fadeInAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        wetv.startAnimation(fadeInAnim);
        aretv.startAnimation(fadeInAnim);
        smarttv.startAnimation(fadeInAnim);
        doctv.startAnimation(fadeInAnim);

        Intent intent = new Intent(SplashScreen.this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);


    }

}
