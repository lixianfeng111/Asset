package com.yhhl.asset.home.checkmodel;

import butterknife.BindView;
import butterknife.OnClick;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.yhhl.asset.R;
import com.yhhl.asset.base.BaseActivity;
import com.yhhl.asset.base.BasePresenter;
import com.yhhl.asset.event.DismissEvent;
import com.yhhl.asset.home.project.ProjectHomeFragment;
import com.yhhl.asset.home.webview.WebViewUrl;
import com.yhhl.asset.util.SpzUtils;
import com.yhhl.asset.util.WebViewUtil;

import org.greenrobot.eventbus.EventBus;

public class SeeTheModelActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.probar)
    ProgressBar progressBar;
    private String url="http://114.251.113.1:9090/freedobim/#/app/modelPreview?modelId=fab59834-3c6b-41cf-88fb-5a16280c8491";
    private String url2="http://114.251.113.1:9090/freedobim/#/app/modelPreview?modelId=e8f5e11e-c360-4fbb-b584-86b045950cbe";
    private int see_the_model;

    @Override
    public void initView() {
        title.setText("设计交底");
    }

    @Override
    public void initListener() {
        Bundle bundle = getIntent().getExtras();
        see_the_model = bundle.getInt("see_the_model");
        initWebView();
    }

    @Override
    public void initData() {
//        String webView = SpzUtils.getString("webView");
//        WebViewUtil.initWebView(this.webView,probar, webView);
    }

    private  void initWebView() {
        /*********WebView的基本设置***********/

        webView=webView;
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
        if (see_the_model==0){
            webView.loadUrl(url);
        }else {
            webView.loadUrl(url2);
        }

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    if (progressBar!=null) {
                        progressBar.setProgress(newProgress);
                        progressBar.setVisibility(View.GONE);
                    }

                } else {
                    if (progressBar!=null){
                        progressBar.setProgress(newProgress);
                    }

                }
            }

        });

    }

    @Override
    public int initLayoutId() {
        return R.layout.activity_see_the_model;
    }

    @Override
    public void initVariable() {

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
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