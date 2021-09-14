package com.yhhl.asset.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.yhhl.asset.R;
import com.yhhl.asset.base.BaseFragment;
import com.yhhl.asset.base.BasePresenter;
import com.yhhl.asset.event.DismissEvent;
import com.yhhl.asset.event.EmptyEvent;
import com.yhhl.asset.home.checkresult.CheckResultFragment;
import com.yhhl.asset.my.fragment.OrganizationFragment;
import com.yhhl.asset.my.fragment.ProjectFragment;
import com.yhhl.asset.net.LogUtil;
import com.yhhl.asset.util.GlideUtil;
import com.yhhl.asset.util.MyDialogFragment;
import com.yhhl.asset.util.SpzUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;


public class MyFragment extends BaseFragment {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.headPic)
    ImageView headPic;
    private ArrayList<String> titles;

    @Override
    public void initView() {
        title.setText("我的");
        back.setVisibility(View.GONE);
        EventBus.getDefault().register(this);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        GlideUtil.showImage(headPic,R.drawable.headpic,2, null);
    }

    @Override
    public int initLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void initVariable() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void project_name(EmptyEvent emptyEvent){
        String name_o = SpzUtils.getString("name_o");
        String name_p = SpzUtils.getString("name_p");
        name.setText(name_o+name_p);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @OnClick({R.id.project})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.project:
                MyDialogFragment.newInstance().show(getChildFragmentManager(), "dialogTag2");
                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(getContext())){
            EventBus.getDefault().unregister(this);
        }
    }
}