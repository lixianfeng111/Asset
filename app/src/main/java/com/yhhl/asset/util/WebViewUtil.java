
package com.yhhl.asset.util;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class WebViewUtil {
    private static WebView webView2;

    public static void initWebView(WebView webView, ProgressBar progressBar, String url) {
        /*********WebView的基本设置***********/

        webView2=webView;
        webView2.getSettings().setTextSize(WebSettings.TextSize.NORMAL);
        webView2.getSettings().setAllowFileAccess(true);

//        webView.getSettings().setPluginsEnabled(true); // 是否开启插件支持
        webView2.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //-> 按规则重新布局
        webView2.getSettings().setNeedInitialFocus(true); //-> 是否需要获取焦点
        webView2.getSettings().setGeolocationEnabled(false);// -> 设置开启定位功能
        webView2.getSettings().setBlockNetworkLoads(false); //-> 是否从网络获取资源

        webView2.getSettings().setLoadWithOverviewMode(true);//网页自适应问题
        webView2.getSettings().setUseWideViewPort(true); //解决网页自适应问题
        webView2.getSettings().setDomStorageEnabled(false);//DOM Storage
        webView2.getSettings().setJavaScriptEnabled(true);  //设置支持js
        webView2.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        // 支持缩放(适配到当前屏幕)
        webView2.getSettings().setSupportZoom(true);

        // 将图片调整到合适的大小
        // 设置可以被显示的屏幕控制
        webView2.getSettings().setDisplayZoomControls(true);
        webView2.getSettings().setDefaultTextEncodingName("UTF-8");
        webView2.getSettings().setDomStorageEnabled(true);
        webView2.setWebViewClient(new WebViewClient());
        webView2.loadUrl(url);
        webView2.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    progressBar.setProgress(newProgress);
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setProgress(newProgress);
                }
            }

        });

    }

    public static void clearWebView(){
        if (webView2 != null) {
            webView2.stopLoading();
            webView2.clearHistory();
            webView2.clearCache(true);
            webView2.pauseTimers();
            webView2.destroy();
            webView2 = null;
        }
    }
}