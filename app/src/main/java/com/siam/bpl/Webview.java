package com.siam.bpl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class Webview extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    WebView web;
    ProgressBar progressBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private AdView BannerAds;
    @SuppressLint({"MissingInflatedId", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        web = findViewById(R.id.webview);
        progressBar = findViewById(R.id.progressBar);
        BannerAds = findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder().build();
        BannerAds.loadAd(adRequest);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.container);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        web.getSettings().getJavaScriptEnabled();
        web.getSettings().setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                progressBar.setVisibility(View.VISIBLE);
                web.setVisibility(View.GONE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                progressBar.setVisibility(View.GONE);
                web.setVisibility(View.VISIBLE);
            }
        });
        web.getSettings().getAllowContentAccess();
        web.getSettings().getBuiltInZoomControls();
        web.getSettings().getDefaultFontSize();
        web.loadUrl("https://fonts.google.com/");


        adsShow1();
    }

    private void adsShow1() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new InterstitialAds(Webview.this);
                adsShow2();
            }
        }, 60000);
    }
    private void adsShow2() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new InterstitialAds(Webview.this);
                adsShow1();
            }
        }, 60000);
    }

    @Override
    public void onRefresh() {
        web.reload();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }
}