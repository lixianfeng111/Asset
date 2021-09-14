package com.yhhl.asset.net;

import android.text.TextUtils;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * 网络日志工具类
 */
public class LogUtil {

    public static void init() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(0)
                .methodOffset(7)
                .logStrategy(null)
                .tag("资产建设")
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    public static void json(String json) {
        if (!TextUtils.isEmpty(json)) {
            Logger.json(json);
        }
    }

    public static void json(String tag, String json) {
        if (!TextUtils.isEmpty(json)) {
            Logger.t(tag).json(json);
        }
    }

    public static void d(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Logger.d(msg);
        }
    }

    public static void d(String tab, String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Logger.t(tab).d(msg);
        }
    }

    public static void e(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Logger.e(msg);
        }
    }

    public static void w(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Logger.w(msg);
        }
    }

    public static void v(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Logger.v(msg);
        }
    }

    public static void i(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Logger.i(msg);
        }
    }
}
