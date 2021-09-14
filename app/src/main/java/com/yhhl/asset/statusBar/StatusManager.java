package com.yhhl.asset.statusBar;

import android.app.Activity;

import com.yhhl.asset.R;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

/**
 * Created by PengSong on 18/11/15.
 */

public class StatusManager {


    private StatusManager(){
    }
    private static StatusManager instance;
    public static StatusManager getInstance(){
        if (instance == null){
            instance = new StatusManager();
        }
        return instance;
    }


    public void initStatusTheme(Activity activity){
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(activity,true);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(activity);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(activity, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(activity, ContextCompat.getColor(activity, R.color.white));
        }
    }

    /**
     * 默认的状态栏
     * @param activity
     */
    public void loadDefaultStatusTheme(Activity activity){
        customStatusTheme(activity,R.color.white,true);
    }

    /**
     * 自定义的状态栏主题
     * @param activity
     * @param colorId
     * @param isDark
     */
    public void customStatusTheme(Activity activity, @ColorRes int colorId, boolean isDark){
        StatusBarUtil.setStatusBarColor(activity,ContextCompat.getColor(activity,colorId));
        StatusBarUtil.setStatusBarDarkTheme(activity,isDark);
    }

    /**
     * 沉浸式的状态栏
     * @param activity
     * @param isDark  状态栏的字体颜色
     */
    public void immersionStatusBar(Activity activity, boolean isDark){
        StatusBarUtil.setRootViewFitsSystemWindows(activity,false);
        StatusBarUtil.setStatusBarDarkTheme(activity,isDark);
        StatusBarUtil.setStatusBarColor(activity,ContextCompat.getColor(activity,R.color.transparent));
    }

}
