package com.yhhl.asset.choosearea;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.qweather.sdk.bean.base.Lang;
import com.qweather.sdk.bean.base.Range;
import com.qweather.sdk.bean.geo.GeoBean;
import com.qweather.sdk.view.QWeather;
import com.yhhl.asset.MainActivity;
import com.yhhl.asset.R;
import com.yhhl.asset.base.BaseActivity;
import com.yhhl.asset.base.BasePresenter;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

public class ChooseAreaActivity extends BaseActivity {

    @BindView(R.id.choose_city)
    TextView choose_city;
    @BindView(R.id.back_btn)
    ImageView back_btn;
    @BindView(R.id.area_listview)
    ListView listView;
    /**
     * 城市等级
     */
    public static final int LEVEL_PROVINCE = 1;
    public static final int LEVEL_CITY = 2;
    public static final int LEVEL_COUNTY = 3;
    private int currentLevel = 0;


    private ArrayAdapter<String> adapter;
    private List dataList = new ArrayList<>();
    private String[] provinceArray={"北京","上海","天津","重庆","黑龙江","吉林","辽宁","内蒙古", "河北","河南","山西","山东","江苏","浙江","福建","江西",
            "安徽","湖北","湖南","广东","广西","海南","贵州","云南","四川", "西藏","陕西","宁夏","甘肃","青海","新疆","香港","澳门","台湾"};
    /**
     * 省市县数据list列表
     */

    private List<GeoBean.LocationBean> locationBeanList;
    private List<GeoBean.LocationBean> locationBeanList_county;
    private String provinceName;

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == LEVEL_PROVINCE){
                    provinceName = (String) dataList.get(position);
                    queryCity();
                }else if (currentLevel == LEVEL_CITY){
                    GeoBean.LocationBean locationBean = locationBeanList.get(position);
                    String name = locationBean.getName();
                    queryCounty(name);
                }else if (currentLevel == LEVEL_COUNTY) {
                    GeoBean.LocationBean locationBean = locationBeanList_county.get(position);
                    Intent intent = new Intent(ChooseAreaActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("countyName", locationBean.getName());
                    bundle.putString("locationWeatherId", locationBean.getId());
                    intent.putExtras(bundle);
                    setResult(1,intent);
                    finish();
                }
            }
        });
        queryProvinces();
    }

    private void back(){

    }
    @Override
    public void initData() {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
    }

    @Override
    public int initLayoutId() {
        return R.layout.activity_choose_area;
    }

    @Override
    public void initVariable() {

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    /**
     * 显示中国所有省份的数据在listView中
     */
    public void queryProvinces() {
        choose_city.setText("中国");
        for (String province : provinceArray){
            dataList.add(province);
        }
        adapter.notifyDataSetChanged();
        listView.setSelection(0);
        currentLevel = LEVEL_PROVINCE;
    }

    /**
     * 显示当前选择的省的所有城市
     */
    public void queryCity(){
        choose_city.setText(provinceName);
        QWeather.getGeoCityLookup(ChooseAreaActivity.this, provinceName, Range.CN, 20, Lang.ZH_HANS, new QWeather.OnResultGeoListener() {
            @Override
            public void onError(Throwable throwable) {
                Log.i("TAG", "getGeoCityLookup onError: " + throwable);
            }

            @Override
            public void onSuccess(GeoBean geoBean) {
                Log.i("TAG", "getWeather onSuccess: " + new Gson().toJson(geoBean));
                dataList.clear();
                locationBeanList = geoBean.getLocationBean();
                for (int i = 0; i < locationBeanList.size(); i++) {
                    GeoBean.LocationBean locationBean = locationBeanList.get(i);
                    if (locationBean.getAdm1().contains(provinceName)){
                        dataList.add(locationBean.getName());
                    }else {
                        locationBeanList.remove(locationBean);
                        i--;
                    }
                }
                adapter.notifyDataSetChanged();
                currentLevel = LEVEL_CITY;
            }
        });
    }

    /**
     * 显示当前选择的市的所有县城
     */
    public void queryCounty(String name) {
        choose_city.setText(name);
        QWeather.getGeoCityLookup(ChooseAreaActivity.this, name, Range.CN, 20, Lang.ZH_HANS, new QWeather.OnResultGeoListener() {
            @Override
            public void onError(Throwable throwable) {
                Log.i("TAG", "getGeoCityLookup onError: " + throwable);
            }

            @Override
            public void onSuccess(GeoBean geoBean) {
                Log.i("TAG", "getWeather onSuccess: " + new Gson().toJson(geoBean));
                dataList.clear();
                locationBeanList_county = geoBean.getLocationBean();
                for (int i = 0; i < locationBeanList_county.size(); i++) {
                    GeoBean.LocationBean locationBean = locationBeanList_county.get(i);
                    if (locationBean.getAdm1().contains(provinceName)){
                        dataList.add(locationBean.getName());
                    }else {
                        locationBeanList.remove(locationBean);
                        i--;
                    }
                }
                adapter.notifyDataSetChanged();
                currentLevel = LEVEL_COUNTY;
            }
        });
    }

}