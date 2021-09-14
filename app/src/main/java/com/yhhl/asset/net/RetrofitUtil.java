package com.yhhl.asset.net;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author：@Fairy
 * Data：2019/1/17
 * Effect：
 */
public class RetrofitUtil {
    private static Retrofit mRetrofit;
    private static RetrofitUtil instance;

    private RetrofitUtil() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(HttpUrlUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttpUtil.getInstance())
                .build();
    }

    public static RetrofitUtil getInstance() {
        if (instance == null) {
            instance = new RetrofitUtil();
        }
        return instance;
    }

    public <T> T createApi(Class<T> tClass) {
        T t = mRetrofit.create(tClass);
        return t;
    }
}
