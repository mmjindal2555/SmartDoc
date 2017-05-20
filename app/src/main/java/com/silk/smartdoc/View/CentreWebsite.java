package com.silk.smartdoc.View;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebSettings.PluginState;

import com.silk.smartdoc.R;

public class CentreWebsite extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_centre_website);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setBackgroundColor(getResources().getColor(R.color.primarycolor));
        //toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        WebView webView = (WebView) findViewById(R.id.centerWebsite);
        String centerName = getIntent().getStringExtra("centerName");
        String centerUrl = getIntent().getStringExtra("centerUrl");
        //toolbar.setTitle(centerName);
        getWindow().setStatusBarColor(getResources().getColor(R.color.statusbarcolor));

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setPluginState(PluginState.ON);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setSupportMultipleWindows(false);
        webView.getSettings().setSupportZoom(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        final Activity activity = this;

        webView.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView view, int progress) {
                activity.setProgress(progress * 100);
                if(progress == 100) {
                    setProgressBarIndeterminateVisibility(false);
                    setProgressBarVisibility(false);
                }
            }
        });

        webView.loadUrl("http://"+centerUrl);

        //setSupportActionBar(toolbar);


    }



}
