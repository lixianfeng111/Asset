package com.yhhl.asset.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebView;
import com.hss01248.dialog.ActivityStackManager;
import com.hss01248.dialog.StyledDialog;
import com.qweather.sdk.view.HeConfig;
import com.yhhl.asset.net.LogUtil;
import com.yhhl.asset.net.OkHttpUtil;
import com.yhhl.asset.util.GlideUtil;
import com.yhhl.asset.util.SpzUtils;

import org.litepal.LitePal;

//App类
public class App extends Application {

    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        SpzUtils.init(sContext);
        OkHttpUtil.init(sContext);
        LogUtil.init();
        LitePal.initialize(this);
        GlideUtil.initGlideHelper(this);
        // 修復WebView的多進程加載的bug
        initWebView();
        HeConfig.init("HE2108311136311383","bc5357306dd24874835baa4c164f635a");
        //切换至开发版服务
        HeConfig.switchToDevService();
        initDialogUtils();//初始化DialogUtils
    }

    private void initWebView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            String processName = getProcessName();
            WebView.setDataDirectorySuffix(processName);
        }
    }

    private void initDialogUtils(){
        //在Application的oncreate方法里:传入context
        StyledDialog.init(this);

//        //在activity生命周期callback中拿到顶层activity引用:
        registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                ActivityStackManager.getInstance().addActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                ActivityStackManager.getInstance().removeActivity(activity);
            }
        });
    }
}