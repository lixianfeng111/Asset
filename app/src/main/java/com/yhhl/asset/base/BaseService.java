package com.yhhl.asset.base;

import com.yhhl.asset.db.BaseBean;
import com.yhhl.asset.weather.bean.CityBean;
import com.yhhl.asset.weather.bean.HeWeather5;
import com.yhhl.asset.weather.bean.ImageBean;

import java.util.List;
import java.util.Map;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface BaseService {
    @GET
    Observable<List<BaseBean>> getArea(@Url String url, @QueryMap Map<String, Object> params);

//    @GET
//    Observable<HeWeather5> getWeather(@Url String url, @QueryMap Map<String, Object> params);
    @GET
    Observable<ResponseBody> getImage(@Url String url, @QueryMap Map<String, Object> params);

    @GET
    Observable<CityBean> getWeather(@Url String url, @QueryMap Map<String, Object> params);
}
