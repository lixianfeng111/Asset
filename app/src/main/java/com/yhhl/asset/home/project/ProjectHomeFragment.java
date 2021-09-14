package com.yhhl.asset.home.project;

import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.yhhl.asset.R;
import com.yhhl.asset.base.BaseFragment;
import com.yhhl.asset.base.BasePresenter;
import com.yhhl.asset.event.DismissEvent;
import com.yhhl.asset.home.webview.WebViewUrl;
import com.yhhl.asset.util.WebViewUtil;
import org.greenrobot.eventbus.EventBus;
import butterknife.BindView;
import butterknife.OnClick;

public class ProjectHomeFragment extends BaseFragment {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.probar)
    ProgressBar probar;
//    private String url="http://114.251.113.1:34668/citybase/explorer/index.html?pmtsUrl=http" +
//            "%3A%2F%2F114.251.113.1%3A34668%2FFreedoMetaSvc%2Fservice%2Fmeta%2Fpmts%2F1.1.0%" +
//            "2FPMTSCapabilities.json%3Ftoken%3DqwbZJfCq7Ls-ijOLj2nAtISMfSQUhuu4v_YZi7_vosI46Pg" +
//            "TEgZlxw7L83qTVeYr7IPE96EQtE31yoQM4SonskvHMabbnv519dhwGkvJ1i1aZJWiV2Opc6yENFVdmlZivXZ" +
//            "6c2k5es7uZc4WxHroQFt4kotZ7giem0q0kVBpNe5_nH4XoKjyrwwS-D68oAE7XDYPgm5I4SvhqjtvEgpu1N4r" +
//            "qdNzC50ZLmGrT2wnoQAf56VAZ1KyMpAFaZ8uyiT37S_4GwmtZt7ya8vRiLJ5xg%3D%3D%26id%3Dd829db9c" +
//            "-5a6f-4541-89aa-c33dcfa4f8a1";
    @Override
    public void initView() {
        title.setText("项目首页");
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        WebViewUtil.initWebView(webView,probar, WebViewUrl.WebView_URL);
    }

    @Override
    public int initLayoutId() {
        return R.layout.fragment_project_home;
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
                EventBus.getDefault().post(new DismissEvent(new ProjectHomeFragment()));
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
            WebViewUtil.clearWebView();
        }
    }
}
