package com.example.mydemoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;

public class WebViewActivity extends AppCompatActivity {


    private final String url = "https://www.chefkoch.de/";
    private WebView mWebView;
    private Button mDownload;
    private ImageView cookBook;
    private TextView label1; // Label: "myChefkochBook"


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        // Initialisieren xml
        init();

        // Laden chefkoch.de-Homepage
        mDownload.setVisibility(View.INVISIBLE);
        mWebView.clearCache(true);
        mWebView.loadUrl(url);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                url = mWebView.getUrl();

                if (url.contains("https://www.chefkoch.de/rezepte/")) {
                    mDownload.setVisibility(View.VISIBLE);
                    //Toast.makeText(MainActivity.this, "Url: " + url, Toast.LENGTH_LONG).show();
                } else {
                    mDownload.setVisibility(View.INVISIBLE);
                }
                super.onPageFinished(view, url);
            }
        });


        mDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDownload.setVisibility(View.INVISIBLE);
                mWebView.setVisibility(View.INVISIBLE);
                cookBook.setVisibility(View.VISIBLE);
                label1.setVisibility(View.VISIBLE);

                String currentUrl = mWebView.getUrl();
                new JSoupChefkoch(WebViewActivity.this).execute(currentUrl);


            }
        });

    }


/*
 *  Methodenteil
 * */

    // Initialisieren
    private void init() {
        mWebView = (WebView) findViewById(R.id.webView);
        mDownload = (Button) findViewById(R.id.btn_Download);
        //labelDownload = (TextView) findViewById(R.id.label_2);
        cookBook = (ImageView) findViewById(R.id.iv_FoodImg);
        label1 = (TextView) findViewById(R.id.label_1);

        cookBook.setVisibility(View.INVISIBLE);
        label1.setVisibility(View.INVISIBLE);

    }

    // Navigation Webview
    @Override
    public void onBackPressed() {

        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }
}