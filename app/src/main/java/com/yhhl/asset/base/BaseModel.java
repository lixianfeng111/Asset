package com.yhhl.asset.base;


import com.yhhl.asset.net.LogUtil;
import com.yhhl.asset.net.RetrofitUtil;
import com.yhhl.asset.weather.bean.CityBean;

import java.util.HashMap;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class BaseModel {

    //查询天气
    public void getWeather(String url, HashMap<String,Object> map, final BaseCallBack<CityBean> callBack){
        BaseService baseService = RetrofitUtil.getInstance().createApi(BaseService.class);
        baseService.getWeather(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<CityBean>(callBack){
                    @Override
                    public void onNext(CityBean baseBeans) {
                        super.onNext(baseBeans);
                        callBack.requestSuccess(baseBeans);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        LogUtil.d(e.getMessage());
                    }
                });
    }

    public void getImage(String url, HashMap<String,Object> map, final BaseCallBack<ResponseBody> callBack){
        BaseService baseService = RetrofitUtil.getInstance().createApi(BaseService.class);
        baseService.getImage(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ResponseBody>(callBack){
                    @Override
                    public void onNext(ResponseBody baseBeans) {
                        super.onNext(baseBeans);
                        callBack.requestSuccess(baseBeans);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        LogUtil.d(e.getMessage());
                    }
                });
    }
}