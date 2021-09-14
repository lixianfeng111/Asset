package com.yhhl.asset.home.homeactivity;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.yhhl.asset.R;
import com.yhhl.asset.statusBar.StatusManager;
import com.yhhl.asset.util.ToastUtil;
import com.yhhl.asset.util.WebViewUtil;

public class HomeActivity extends AppCompatActivity {

    private TextView title;
    private WebView webView;
    private ProgressBar probar;
//    private String url=;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        StatusManager.getInstance().initStatusTheme(this);
        StatusManager.getInstance().loadDefaultStatusTheme(this);
        initView();
        initWebView();
    }

    private void initView() {
        title = findViewById(R.id.title);
        webView = findViewById(R.id.webView);
        probar = findViewById(R.id.probar);
        title.setText("项目首页");
    }

    private void initWebView() {
        /*********WebView的基本设置***********/

        webView.getSettings().setTextSize(WebSettings.TextSize.NORMAL);
        webView.getSettings().setAllowFileAccess(true);

//        webView.getSettings().setPluginsEnabled(true); // 是否开启插件支持
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //-> 按规则重新布局
        webView.getSettings().setNeedInitialFocus(true); //-> 是否需要获取焦点
        webView.getSettings().setGeolocationEnabled(false);// -> 设置开启定位功能
        webView.getSettings().setBlockNetworkLoads(false); //-> 是否从网络获取资源


        webView.getSettings().setLoadWithOverviewMode(true);//网页自适应问题
        webView.getSettings().setUseWideViewPort(true); //解决网页自适应问题
        webView.getSettings().setDomStorageEnabled(false);//DOM Storage
        webView.getSettings().setJavaScriptEnabled(true);  //设置支持js
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        // 支持缩放(适配到当前屏幕)
        webView.getSettings().setSupportZoom(true);

        // 将图片调整到合适的大小
        // 设置可以被显示的屏幕控制
        webView.getSettings().setDisplayZoomControls(true);
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://114.251.113.1:9090/freedobim/#/?pk_project=1001A810000000009ZTS");
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    probar.setProgress(newProgress);
                    probar.setVisibility(View.GONE);
//                    if (probar!=null) {
//                        probar.setProgress(newProgress);
//                        probar.setVisibility(View.GONE);
//                    }

                } else {
//                    if (probar!=null){
//
//                    }
                    probar.setProgress(newProgress);
                }
            }

        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.stopLoading();
            webView.clearHistory();
            webView.clearCache(true);
            webView.pauseTimers();
            webView.destroy();
            webView = null;
        }
    }
}