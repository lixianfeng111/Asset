package com.yhhl.asset.weather.presenter;

import com.yhhl.asset.base.BaseCallBack;
import com.yhhl.asset.base.BaseModel;
import com.yhhl.asset.base.BasePresenter;
import com.yhhl.asset.base.ServerException;
import com.yhhl.asset.weather.view.WeatherView;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;

public class WeatherPresenter extends BasePresenter<WeatherView> {

    private final BaseModel baseModel;

    public WeatherPresenter(WeatherView iBaseView) {
        super(iBaseView);
        baseModel = new BaseModel();
    }

    public void getImage(String s){
        HashMap<String, Object> map = new HashMap<>();

        baseModel.getImage(s, map, new BaseCallBack<ResponseBody>() {

            @Override
            public void requestError(ServerException e) {
//                ServerException.getMessageAndCode();
            }

            @Override
            public void requestSuccess(ResponseBody imageBean) {
                try {
                    String string = imageBean.string();
                    getiBaseView().OnGetImageSuccess(string);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

//    public void getWeather(){
////        116.23590,40.21808
//        HashMap<String, Object> map = new HashMap<>();
//        baseModel.getWeather("https://geoapi.qweather.com/v2/city/lookup?location=河南&key=0844d696ba6442698956f3773e951ad7", map, new BaseCallBack<CityBean>() {
//
//            @Override
//            public void requestError(ServerException e) {
//                ServerException.getMessageAndCode();
//            }
//
//            @Override
//            public void requestSuccess(CityBean cityBean) {
//                LogUtil.d(new Gson().toJson(cityBean));
//                ToastUtil.showToast(cityBean.getCode());
//
//            }
//        });
//    }

}
