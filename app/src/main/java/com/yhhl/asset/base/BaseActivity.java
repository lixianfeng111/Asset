package com.yhhl.asset.base;

import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.yhhl.asset.receiver.NetReceiver;
import com.yhhl.asset.statusBar.StatusManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * baseActivity
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements NetReceiver.NetStatuMonitor {
    protected T miBasePresenter;
    private NetReceiver netBroadcastReceiver;
    private boolean netStatus;
//    private ErrorView errorView;
    protected ViewManager mViewManager;

    private BaseTitleBar mBaseTitleBar;
    private FrameLayout baseContent;
    private Unbinder bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        errorView = new ErrorView(this);
//        ((ViewGroup) getWindow().getDecorView()).addView(errorView);
        if (initLayoutId() != 0) {
            initVariable();
            setContentView(initLayoutId());
            bind = ButterKnife.bind(this);
            StatusManager.getInstance().initStatusTheme(this);
            StatusManager.getInstance().loadDefaultStatusTheme(this);
            miBasePresenter = initPresenter();
            initView();
            AppManager.getAppManager().addActivity(this);
            initData();
            initListener();
            //注册广播
            if (netBroadcastReceiver == null) {
                netBroadcastReceiver = new NetReceiver();
                IntentFilter filter = new IntentFilter();
                filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
                registerReceiver(netBroadcastReceiver, filter);
                //设置监听
                netBroadcastReceiver.setNetStatuMonitor(this);
            }

        } else {
            finish();
        }
    }


    public abstract void initView();

    public abstract void initListener();

    public abstract void initData();

    public abstract int initLayoutId();

    //初始化变量
    public abstract void initVariable();

    public abstract T initPresenter();


    @Override
    protected void onResume() {
        /**
         * 设置为竖屏 强制
         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
    }




    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    //销毁
    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (miBasePresenter != null) {
            miBasePresenter.onDestory();
        }
    }

    /**
     * ondestory中取消注册
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销广播
        if (netBroadcastReceiver!=null){
            unregisterReceiver(netBroadcastReceiver);
        }
        if (bind!=null){
            bind.unbind();
        }
    }

    @Override
    public void onNetChange(boolean netStatus) {
        this.netStatus = netStatus;
        isNetConnect();
    }

    /**
     * 监听网络状态做出相应改变
     */
    private void isNetConnect() {

    }


    //显示空布局
    public void showEmptyLayout() {

    }

    //加载错误的布局
    public void showErrorLayout() {

    }

    //显示正在加载布局
    public void showLoadingLayout() {

    }

    //显示内容
    public void showContent() {

    }


}
