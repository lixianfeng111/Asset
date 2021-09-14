package com.yhhl.asset.net;

import android.util.Log;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * function：okhttp  日志拦截器
 */
public class OkLogIntorceptor implements Interceptor {
    private static final String TAG = "OkLogInteror";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Log.d(TAG, "请求方法" + request.method());
        Log.d(TAG, "请求网址" + request.url());
        Log.d(TAG, "请求数据" + request.toString());
        //找到请求头
        Headers headers = request.headers();
        //获得set集合
        Set<String> names = headers.names();
        //迭代器
        Iterator<String> iterator = names.iterator();
        //遍历
        while (iterator.hasNext()) {
            //得到值   得到键
            String next = iterator.next();
            //得到值
            String value = headers.get(next);
            Log.d(TAG, next + ":" + value);
        }
        return chain.proceed(request);
    }
}
