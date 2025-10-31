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
import com.shivamsingh.verinews.databinding.ActivityWebViewBinding;

public class WebViewActivity extends AppCompatActivity {
    private ActivityWebViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityWebViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setDomStorageEnabled(true); // enable local storage
        binding.webView.getSettings().setUserAgentString(
                "Mozilla/5.0 (Linux; Android 10; SM-G975F) " + // Pretend to be an Android phone (Samsung Galaxy S10, Android 10)
                        "AppleWebKit/537.36 (KHTML, like Gecko) " +    // Standard rendering engine info (used by Chrome, Safari)
                        "Chrome/115.0.0.0 Mobile Safari/537.36"         // Pretend to be a modern version of Chrome Mobile
        );

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        if (url != null) {
            String fixedUrl = url.replace("http://", "https://");

            binding.webView.setWebViewClient(new WebViewClient() {

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    binding.progressBar.setVisibility(View.VISIBLE); // Show progress bar
                    super.onPageStarted(view, url, favicon);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    binding.progressBar.setVisibility(View.GONE); // Hide progress bar
                    super.onPageFinished(view, url);
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    view.loadUrl(request.getUrl().toString()); // Open links inside same WebView
                    return true; // Prevent opening in external browser
                }

            });
            binding.webView.loadUrl(fixedUrl);
        } else {
            Log.e("NewsApp", "URL is null!");
        }

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (binding.webView.canGoBack())
                    binding.webView.goBack();
                else
                    finish();
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.webView.destroy();
        binding = null; // optional, to prevent leaks
    }
}