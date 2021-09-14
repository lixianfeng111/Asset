package com.yhhl.asset.net;

import com.google.gson.Gson;

/**
 * gson工具类
 */
public class GsonUtil {

    public  static Gson mGson;
    public static GsonUtil instance;

    private GsonUtil() {
        mGson = new Gson();
    }
    public static GsonUtil getInstance(){
        if (instance==null) {
            synchronized (GsonUtil.class){
                instance =new GsonUtil();
            }
        }
        return  instance;
    }

}
