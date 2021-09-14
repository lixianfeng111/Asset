package com.yhhl.asset.home.decorate;

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
import com.yhhl.asset.util.WebViewUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class DecorateActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.probar)
    ProgressBar probar;
    private String url = "https://720yun.com/t/7fdjuzhy5f2?scene_id=23880714";
    @Override
    public void initView() {

        title.setText("装饰修饰");
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

//        WebViewUtil.initWebView(webView,probar,url);
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
        webView.loadUrl(url);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    probar.setProgress(newProgress);
                    probar.setVisibility(View.GONE);
                } else {
                    probar.setProgress(newProgress);
                }
            }

        });
        webView.loadUrl(url);
    }

    @Override
    public int initLayoutId() {
        return R.layout.activity_decorate;
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
//            WebViewUtil.clearWebView();
        }
    }
}