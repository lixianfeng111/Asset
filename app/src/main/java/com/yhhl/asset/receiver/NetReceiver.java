package com.yhhl.asset.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.yhhl.asset.net.NetUtils;

/**
 * 用于实时监听app的网络状态
 */
public class NetReceiver extends BroadcastReceiver {
    //网络状态监听接口
    private NetStatuMonitor netStatuMonitor;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)){
            //获取网络状态的类型
            boolean isconnected = NetUtils.isconnected(context);
            if (netStatuMonitor!=null){
                netStatuMonitor.onNetChange(isconnected);
            }
        }
    }
    //网络状态类型改变的监听接口
    public interface  NetStatuMonitor{
        void onNetChange(boolean netstaus);
    }

    /**
     *@param netStatuMonitor
     * 设置网络状态监听接口
     */
    public void setNetStatuMonitor(NetStatuMonitor netStatuMonitor) {
        this.netStatuMonitor = netStatuMonitor;
    }
}
