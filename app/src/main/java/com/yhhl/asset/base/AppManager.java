package com.yhhl.asset.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Stack;


/**
 * 应用程序管理类
 *
 */
public class AppManager {
    private static Stack<WeakReference<Activity>> activityStack;
    private static AppManager instance;
    private PackageManager packageManager;
    private PackageInfo packageInfo;
    private Context context;

    //单例设计模式
    AppManager() {
        context = App.sContext;
        packageManager = context.getPackageManager();
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            //
        }
    }

    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    public int getVersionCode() {
        return packageInfo.versionCode;
    }

    public String getVersionName() {
        return packageInfo.versionName;
    }

    public String getPackageName() {
        return packageInfo.packageName;
    }


    /**
     * 添加Activity到栈
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        if (!activityStack.contains(activity)) {
            activityStack.add(new WeakReference<>(activity));
        }
    }


    public void finishActivity() {
        WeakReference<Activity> reference = activityStack.lastElement();
        Activity activity = reference.get();
        if (activity != null) {
            this.finishActivity(activity);
        }
    }

    public void finishActivity(Activity activity) {
        if (activity != null && activityStack != null) {

            for (Iterator<WeakReference<Activity>> iterator = activityStack.iterator(); iterator.hasNext(); ) {
                WeakReference<Activity> reference = iterator.next();
                Activity temp = reference.get();
                if (null == temp) {
                    iterator.remove();
                    continue;
                }

                if (activity == temp) {
                    iterator.remove();
                }
            }
            activity.finish();
        }
    }

    public void finishActivity(Class<?> cls) {
        if (activityStack != null) {
            for (Iterator<WeakReference<Activity>> iterator = activityStack.iterator(); iterator.hasNext(); ) {
                WeakReference<Activity> reference = iterator.next();
                Activity temp = reference.get();
                if (null == temp) {
                    iterator.remove();
                    continue;
                }
                if (temp.getClass().equals(cls)) {
                    iterator.remove();
                    temp.finish();
                }

            }

        }
    }

    public void finishTopActivity() {
        if (activityStack != null) {
            WeakReference<Activity> reference = activityStack.lastElement();
            Activity activity = reference.get();
            if (activity != null) {
                activityStack.remove(activityStack.size() - 1);
                activity.finish();
            }
        }
    }


    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (activityStack != null) {
            for (Iterator<WeakReference<Activity>> iterator = activityStack.iterator(); iterator.hasNext(); ) {
                WeakReference<Activity> reference = iterator.next();
                Activity activity = reference.get();
                iterator.remove();
                if (activity != null) {
                    activity.finish();
                }
            }

        }
    }

    /**
     * 应用程序退出
     */
    @SuppressLint("MissingPermission")
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            System.exit(0);
        }
    }

    public Activity getTopActivity() {
        WeakReference<Activity> activity = activityStack.lastElement();
        if (activity != null) {
            return activity.get();
        }
        return null;
    }

    public void AppRestart() {
        this.finishAllActivity();

        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent restartIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 200, restartIntent); // 重启应用

        android.os.Process.killProcess(android.os.Process.myPid());
    }

    //退出登录
    public static void LogOut(Activity activity, Class<?> tClass){
        Intent intent_login = new Intent();
        intent_login.setClass(activity,tClass);
        intent_login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //关键的一句，将新的activity置为栈顶，清空其他activity
        activity.startActivity(intent_login);
    }
}
