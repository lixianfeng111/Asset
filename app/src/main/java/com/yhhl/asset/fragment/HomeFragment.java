package com.yhhl.asset.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.qweather.sdk.bean.air.AirNowBean;
import com.qweather.sdk.bean.base.Code;
import com.qweather.sdk.bean.base.Lang;
import com.qweather.sdk.bean.base.Range;
import com.qweather.sdk.bean.base.Unit;
import com.qweather.sdk.bean.geo.GeoBean;
import com.qweather.sdk.bean.weather.WeatherDailyBean;
import com.qweather.sdk.bean.weather.WeatherNowBean;
import com.qweather.sdk.view.QWeather;
import com.yhhl.asset.R;
import com.yhhl.asset.base.BaseFragment;
import com.yhhl.asset.choosearea.ChooseAreaActivity;
import com.yhhl.asset.dialog.DialogUtils;
import com.yhhl.asset.event.AddEvent;
import com.yhhl.asset.home.checkresult.CheckResultFragment;
import com.yhhl.asset.home.checksafe.CheckSafeFragment;
import com.yhhl.asset.home.completionresults.CompletionResultFragment;
import com.yhhl.asset.home.decorate.DecorateActivity;
import com.yhhl.asset.home.design.fragment.DesignCheckFragment;
import com.yhhl.asset.home.homeactivity.HomeActivity;
import com.yhhl.asset.home.management.ManageFragment;
import com.yhhl.asset.home.progress.ProgressFragment;
import com.yhhl.asset.home.quality.QualityCheckFragment;
import com.yhhl.asset.net.LogUtil;
import com.yhhl.asset.util.GlideUtil;
import com.yhhl.asset.util.IntentUtil;
import com.yhhl.asset.util.SpzUtils;
import com.yhhl.asset.util.TimeFormartUtils;
import com.yhhl.asset.weather.bean.HeWeather5;
import com.yhhl.asset.weather.presenter.WeatherPresenter;
import com.yhhl.asset.weather.view.WeatherView;
import org.greenrobot.eventbus.EventBus;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

public class HomeFragment extends BaseFragment<WeatherPresenter> implements WeatherView {

    @BindView(R.id.decorate)
    TextView decorate;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.weatherBa)
    ImageView weatherBa;
    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.now_temperature)
    TextView now_temperature;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.week)
    TextView week;
    @BindView(R.id.weather)
    TextView weather;
    @BindView(R.id.today_temperature)
    TextView today_temperature;
    @BindView(R.id.wind)
    TextView wind;
    @BindView(R.id.air_quality)
    TextView air_quality;
    @BindView(R.id.aqi)
    TextView aqi;
    @BindView(R.id.tomorrow_weather)
    TextView tomorrow_weather;
    @BindView(R.id.tomorrow_temperature)
    TextView tomorrow_temperature;
    @BindView(R.id.tomorrow_wind)
    TextView tomorrow_wind;
    private String HE_URL = "https://free-api.heweather.com/v5/weather?city=";
    public String KEY = "a0187789a4424bc89254728acd4a08ed";
    private String locationWeatherId;
    private Dialog dialog;
    private String requestBingPic = "http://guolin.tech/api/bing_pic";
    private String countyName;
    //初始化集合装载权限
    private String[] permssions={Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    private LocationClient locationClient;
    @Override
    public void initView() {
//        gone.setVisibility(View.INVISIBLE);
        title.setText("资产协同管理平台");
        back.setVisibility(View.GONE);
    }

    @Override
    public void initListener() {
        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), ChooseAreaActivity.class),1);
            }
        });
    }

    @Override
    public void initData() {
        week.setText(TimeFormartUtils.getWeek());//星期
        date.setText(TimeFormartUtils.getTimeDay());//今天日期

        if (EasyPermissions.hasPermissions(getContext(), permssions)) {
            //已经打开权限
            initLocationOption();
        } else {
            //没有打开相关权限、申请权限
            EasyPermissions.requestPermissions(this, "需要获取您的位置权限", 2, permssions);
        }
    }

    @Override
    public int initLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initVariable() {

    }

    @Override
    public WeatherPresenter initPresenter() {
        return new WeatherPresenter(this);
    }

    @OnClick({R.id.homePager, R.id.design_check, R.id.progress_simulation, R.id.completion_results, R.id.check_results,
            R.id.safety_check, R.id.quality_check, R.id.project_management, R.id.city, R.id.decorate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.homePager:
                IntentUtil.getInstance().intent(getContext(), HomeActivity.class,null);
                break;
            case R.id.design_check:
                EventBus.getDefault().post(new AddEvent(new DesignCheckFragment()));
                break;
            case R.id.progress_simulation:
                EventBus.getDefault().post(new AddEvent(new ProgressFragment()));
                break;
            case R.id.completion_results:
                EventBus.getDefault().post(new AddEvent(new CompletionResultFragment()));
                break;
            case R.id.check_results:
                EventBus.getDefault().post(new AddEvent(new CheckResultFragment()));
                break;
            case R.id.safety_check:
                EventBus.getDefault().post(new AddEvent(new CheckSafeFragment()));
                break;
            case R.id.quality_check:
                EventBus.getDefault().post(new AddEvent(new QualityCheckFragment()));
                break;
            case R.id.project_management:
                EventBus.getDefault().post(new AddEvent(new ManageFragment()));
                break;
            case R.id.city:
//                IntentUtil.getInstance().intent(getContext(), ChooseAreaActivity.class,null);
                startActivityForResult(new Intent(getActivity(),ChooseAreaActivity.class),1);
                break;
            case R.id.decorate:
                IntentUtil.getInstance().intent(getContext(), DecorateActivity.class,null);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
            Bundle bundle = data.getExtras();
            String countyName = bundle.getString("countyName");
            String locationWeatherId = bundle.getString("locationWeatherId");
            city.setText(countyName);
            SpzUtils.putString("locationWeatherId",locationWeatherId);
            SpzUtils.putString("countyName",countyName);
            update(locationWeatherId);
        }

    }

    private void update(String locationWeatherId) {
        //启动图片更新,异步
        updateBingPic();

        //有网的情况
        getWeather(locationWeatherId);
    }

    private void getWeather(String locationWeatherId) {
        QWeather.getGeoCityLookup(getContext(), locationWeatherId, Range.CN, 20, Lang.ZH_HANS, new QWeather.OnResultGeoListener() {
            @Override
            public void onError(Throwable throwable) {
                Log.i("TAG", "getGeoCityLookup onError: " + throwable);
            }

            @Override
            public void onSuccess(GeoBean geoBean) {
                Log.i("TAG", "getGeoCityLookup onSuccess: " + new Gson().toJson(geoBean));
                if(countyName==null){
                    city.setText(geoBean.getLocationBean().get(0).getName());
                }

            }
        });

        QWeather.getWeatherNow(getActivity(),locationWeatherId, Lang.ZH_HANS, Unit.METRIC, new QWeather.OnResultWeatherNowListener() {
            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "getWeather onError: " + e);
            }

            @Override
            public void onSuccess(WeatherNowBean weatherBean) {
                Log.i(TAG, "getWeather onSuccess: " + new Gson().toJson(weatherBean));
                //先判断返回的status是否正确，当status正确时获取数据，若status不正确，可查看status对应的Code值找到原因
                if (Code.OK == weatherBean.getCode()) {
                    WeatherNowBean.NowBaseBean now = weatherBean.getNow();
                    now_temperature.setText(now.getTemp()+ "℃");//现在温度
                    weather.setText(now.getText());//现在天气情况
                    wind.setText(now.getWindDir()+now.getWindScale()+"级");//现在的风向和风力
                } else {
                    //在此查看返回数据失败的原因
                    Code code = weatherBean.getCode();
                    Log.i(TAG, "failed code: " + code);
                }
            }
        });

        QWeather.getAirNow(getContext(), locationWeatherId, Lang.ZH_HANS, new QWeather.OnResultAirNowListener() {
            @Override
            public void onError(Throwable throwable) {
                LogUtil.d("getAirNow onError",throwable.getMessage());
            }

            @Override
            public void onSuccess(AirNowBean airNowBean) {
                Code code = airNowBean.getCode();
                String code1 = code.getCode();
                if (code1.equals("200")){
                    AirNowBean.NowBean now = airNowBean.getNow();
                    air_quality.setText("空气质量："+now.getCategory());
                    aqi.setText("空气指数："+now.getAqi());
                }
            }
        });

        QWeather.getWeather3D(getContext(), locationWeatherId, Lang.ZH_HANS, Unit.METRIC, new QWeather.OnResultWeatherDailyListener() {
            @Override
            public void onError(Throwable throwable) {
                LogUtil.d("getWeather3D onError"+throwable.getMessage());
            }

            @Override
            public void onSuccess(WeatherDailyBean weatherDailyBean) {
                List<WeatherDailyBean.DailyBean> daily = weatherDailyBean.getDaily();
                WeatherDailyBean.DailyBean dailyBean = daily.get(0);
                //今天温度范围
                today_temperature.setText(dailyBean.getTempMin()+"/"+dailyBean.getTempMax()+ "℃");
                WeatherDailyBean.DailyBean dailyBean1 = daily.get(1);
                tomorrow_weather.setText("明天白天："+dailyBean1.getTextDay()+"  "+"夜晚："+dailyBean1.getTextNight());
                tomorrow_wind.setText(dailyBean1.getWindDirDay()+dailyBean1.getWindScaleDay());
                tomorrow_temperature.setText(dailyBean1.getTempMin()+"/"+dailyBean1.getTempMax()+ "℃");
            }
        });
    }

    private void updateBingPic() {
        miBasePresenter.getImage(requestBingPic);
    }

    @Override
    public void showLoading() {
        if (dialog==null){
            dialog = DialogUtils.showLoading(getContext());
        }else {
            dialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (dialog!=null&&dialog.isShowing()){
            dialog.hide();
        }
    }

    @Override
    public void onDataSuccess(HeWeather5 data) {

    }

    @Override
    public void onDataFailed(String msg) {

    }

    @Override
    public void onDataList(List<HeWeather5> list) {

    }

    /**
     * 初始化定位参数配置
     */

    private void initLocationOption() {
        //定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
        locationClient = new LocationClient(getContext());
        //声明LocationClient类实例并配置定位参数

        MyLocationListener myLocationListener = new MyLocationListener();
        locationClient.registerLocationListener(myLocationListener);
        LocationClientOption locationOption = new LocationClientOption();

        //可选，设置是否需要地址信息，默认不需要
        locationOption.setIsNeedAddress(true);
        locationOption.setAddrType("all"); //加上这个配置后才可以取到详细地址信息

        ////需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        locationClient.setLocOption(locationOption);
        //开始定位
        locationClient.start();

    }

    /**
     * 实现定位回调
     */
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){
//            String city = location.getCity();    //获取城市
//            String district = location.getDistrict();    //获取区县
//            LogUtil.d("定位：",location.getAltitude()+","+location.getLongitude());
//            LogUtil.d("定位：",city+":"+district);
            LogUtil.d("经纬度：",location.getLatitude()+","+location.getLongitude());
            if (getContext()!=null){
                update(location.getLongitude()+","+location.getLatitude());
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==2){
            initLocationOption();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dialog!=null){
            dialog.dismiss();
        }
    }

    @Override
    public void OnGetImageSuccess(String imageBean) {
        GlideUtil.showImage(getContext(),weatherBa,imageBean);
    }
}