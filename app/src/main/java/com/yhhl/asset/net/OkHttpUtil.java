package com.yhhl.asset.net;
import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by 枫叶🍁 on 2019/1/21 19:37
 * Email：3507801049@qq.com
 * Role:
 */
public class OkHttpUtil {

    private static OkHttpClient sOkHttpClient;


    public static void init(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(5000, TimeUnit.MILLISECONDS);
        builder.writeTimeout(5000, TimeUnit.MILLISECONDS);
        builder.connectTimeout(5000, TimeUnit.MILLISECONDS);
        builder.addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                if (message != null) {
                    if (message.startsWith("{") || message.startsWith("[")) {
                        LogUtil.json(message);
                    } else {
                        LogUtil.d(message);
                    }
                }
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY));
        builder.addInterceptor(new OkLogIntorceptor());
        builder.addInterceptor(new OkHeaderIntorceptor(context));
        sOkHttpClient = builder.build();
    }
    public static OkHttpClient getInstance()  {
        if (sOkHttpClient == null) {
            new OkHttpUtil();
        }
        return sOkHttpClient;
    }
    public static void sendRequestOkHttpForGet(String adress, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(adress)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
