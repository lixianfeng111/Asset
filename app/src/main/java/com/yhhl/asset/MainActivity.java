package com.yhhl.asset;


import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.yhhl.asset.base.AppManager;
import com.yhhl.asset.base.BaseActivity;
import com.yhhl.asset.base.BasePresenter;
import com.yhhl.asset.event.AddEvent;
import com.yhhl.asset.event.DismissEvent;
import com.yhhl.asset.fragment.HomeFragment;
import com.yhhl.asset.fragment.MessageFragment;
import com.yhhl.asset.fragment.MyFragment;
import com.yhhl.asset.tabview.TabViewChild;
import com.yhhl.asset.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    private long exitTime=0;
    private List<TabViewChild> mTabViewList = new ArrayList<>();
    private FragmentManager supportFragmentManager;
    private List<Fragment> fragments=new ArrayList<>();
    private Fragment curFragment;

    @Override
    public void initView() {
        supportFragmentManager = getSupportFragmentManager();
        EventBus.getDefault().register(this);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        //设置底部导航
        TabViewChild tabViewChild_home = new TabViewChild(R.mipmap.home, R.mipmap.home, "首页", new HomeFragment());
//        TabViewChild tabViewChild_1 = new TabViewChild(R.mipmap.message, R.mipmap.message, "工作台", new MessageFragment());
//        TabViewChild tabViewChild_2 = new TabViewChild(R.mipmap.my, R.mipmap.my, "个人", new MyFragment());
//
//        mTabViewList.add(tabViewChild_home);
//        mTabViewList.add(tabViewChild_1);
//        mTabViewList.add(tabViewChild_2);
//        tabView.setTabViewChild(mTabViewList, getSupportFragmentManager());
        ArrayList<Fragment> list = new ArrayList<>();
        list.add(new HomeFragment());
        list.add(new MessageFragment());
        list.add(new MyFragment());
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
        viewPager.setOffscreenPageLimit(list.size());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                radioGroup.check(radioGroup.getChildAt(position).getId());
                clearAll(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioHome:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.radioWork:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.radioMy:
                        viewPager.setCurrentItem(2);
                        break;
                }
            }
        });
    }

    @Override
    public int initLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initVariable() {

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Subscribe
    public void eventAdd(AddEvent event){
        if(viewPager.getVisibility()!= View.GONE){
            viewPager.setVisibility(View.GONE);
        }
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        if(curFragment!=null){
            transaction.hide(curFragment);
        }
        curFragment = event.getNeedAdd();
        fragments.add(curFragment);
        transaction.add(R.id.frameLayout,curFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Subscribe
    public void eventDismiss(DismissEvent event){
//        Fragment needDismiss = event.getNeedDismiss();
//        fragments.remove(needDismiss);
////        fragments.remove(needDismiss);
//        curFragment=null;
//        if(fragments.size()>0){
//            curFragment=fragments.get(fragments.size()-1);
//        }else if(viewPager.getVisibility()!=View.VISIBLE){
//            viewPager.setVisibility(View.VISIBLE);
//        }
        supportFragmentManager.popBackStack();
        fragments.remove(curFragment);
        curFragment=null;
        if(fragments.size()>0){
            curFragment=fragments.get(fragments.size()-1);
        }else if(viewPager.getVisibility()!=View.VISIBLE){
            viewPager.setVisibility(View.VISIBLE);
        }
    }



    private void clearAll(int position) {
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            supportFragmentManager.popBackStack();
        }
        curFragment=null;
        fragments.clear();
        viewPager.setVisibility(View.VISIBLE);
        viewPager.setCurrentItem(position);
//        viewPager.setTabViewDefaultPosition(position);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        fragments.remove(curFragment);
        curFragment=null;
        if(fragments.size()>0){
            curFragment=fragments.get(fragments.size()-1);
        }else if(viewPager.getVisibility()!=View.VISIBLE){
            viewPager.setVisibility(View.VISIBLE);
        }
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event){
//        if(keyCode==KeyEvent.KEYCODE_BACK){
//            exit();
//            return false;
//        }
//        return super.onKeyDown(keyCode,event);
//    }

    private void exit(){
        if((System.currentTimeMillis()-exitTime)>2000) {
            Toast.makeText(getApplicationContext(),
                    "再按一次退出云合易企", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else{
            AppManager.getAppManager().finishAllActivity();
            System.exit(0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}