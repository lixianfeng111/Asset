package com.yhhl.asset.fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.othershe.baseadapter.interfaces.OnLoadMoreListener;
import com.yhhl.asset.R;
import com.yhhl.asset.base.BaseFragment;
import com.yhhl.asset.base.BasePresenter;
import com.yhhl.asset.message.adapter.BackLogFragment;
import com.yhhl.asset.message.adapter.InformFragment;
import com.yhhl.asset.message.adapter.MessageAdapter;
import com.yhhl.asset.message.adapter.WarningMessageFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

public class MessageFragment extends BaseFragment {
    
//    @BindView(R.id.recyclerView)
//    RecyclerView recyclerView;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private MessageAdapter messageAdapter;
    private ArrayList<String> titles;
    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
//        messageAdapter = new MessageAdapter(getContext(), null, true);
//        messageAdapter.setLoadingView(R.layout.load_loading_layout);
//        messageAdapter.setLoadFailedView(R.layout.load_failed_layout);
//        messageAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(boolean isReload) {
//                loadMore();
//            }
//        });
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(messageAdapter);
//        //延时3s刷新列表
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                List<String> data = new ArrayList<>();
//                for (int i = 0; i < 4; i++) {
//                    data.add("item--" + i);
//                }
//                //刷新数据
//                messageAdapter.setNewData(data);
//            }
//        }, 0);
        //设置界面文件和文字一一对应
        titles = new ArrayList<>();
        titles.add("通知公告");
        titles.add("待办");
        titles.add("预警消息");
        ArrayList<Fragment> list = new ArrayList<>();
        list.add(new BackLogFragment());
        list.add(new InformFragment());
        list.add(new WarningMessageFragment());

        //预加载
        viewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
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
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setOffscreenPageLimit(list.size());
        //将viewpager与tabLayout绑定
        tab.setupWithViewPager(viewPager);
        for(int i=0;i<titles.size();i++){
            tab.getTabAt(i).setText(titles.get(i));
        }
    }

//    private void loadMore() {
//        messageAdapter.setLoadEndView(R.layout.load_end_layout);
//        messageAdapter.loadEnd();
//    }

    @Override
    public int initLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    public void initVariable() {

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
