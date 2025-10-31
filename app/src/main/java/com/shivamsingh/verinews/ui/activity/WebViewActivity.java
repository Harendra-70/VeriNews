package com.shivamsingh.verinews.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.shivamsingh.verinews.R;

public class WebViewActivity extends AppCompatActivity {
    WebView webView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_web_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        webView=findViewById(R.id.webView);
        progressBar=findViewById(R.id.progressBar);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true); // enable local storage
        webView.getSettings().setUserAgentString(
                "Mozilla/5.0 (Linux; Android 10; SM-G975F) " + // Pretend to be an Android phone (Samsung Galaxy S10, Android 10)
                "AppleWebKit/537.36 (KHTML, like Gecko) " +    // Standard rendering engine info (used by Chrome, Safari)
                "Chrome/115.0.0.0 Mobile Safari/537.36"         // Pretend to be a modern version of Chrome Mobile
        );

        Intent intent=getIntent();
        String url=intent.getStringExtra("url");
        if (url != null) {
            String fixedUrl = url.replace("http://", "https://");

            webView.setWebViewClient(new WebViewClient(){

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    progressBar.setVisibility(View.VISIBLE); // Show progress bar
                    super.onPageStarted(view, url, favicon);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    progressBar.setVisibility(View.GONE); // Hide progress bar
                    super.onPageFinished(view, url);
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    view.loadUrl(request.getUrl().toString()); // Open links inside same WebView
                    return true; // Prevent opening in external browser
                }

            });
            webView.loadUrl(fixedUrl);
        } else {
            Log.e("NewsApp", "URL is null!");
        }

        getOnBackPressedDispatcher().addCallback(this,new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                  if(webView.canGoBack())
                      webView.goBack();
                  else
                      finish();
            }
        });


    }
}