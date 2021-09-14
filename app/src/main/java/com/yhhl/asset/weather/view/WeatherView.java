package com.yhhl.asset.weather.view;

import com.yhhl.asset.base.IBaseView;
import com.yhhl.asset.weather.bean.HeWeather5;
import com.yhhl.asset.weather.bean.ImageBean;

public interface WeatherView extends IBaseView<HeWeather5> {
    void OnGetImageSuccess(String imageBean);
}
