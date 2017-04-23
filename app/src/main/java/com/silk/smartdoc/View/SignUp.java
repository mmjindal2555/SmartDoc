package com.silk.smartdoc.View;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.silk.smartdoc.R;

public class SignUp extends AppCompatActivity {

    TextView sdlogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        sdlogo = (TextView)findViewById(R.id.sdLogo);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(),"font/Satisfy-Regular.ttf");
        sdlogo.setTypeface(myCustomFont);
        getWindow().setStatusBarColor(getResources().getColor(R.color.statusbarcolor));
    }
}
