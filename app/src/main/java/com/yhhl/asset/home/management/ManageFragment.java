package com.yhhl.asset.home.management;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import com.othershe.baseadapter.interfaces.OnLoadMoreListener;
import com.yhhl.asset.R;
import com.yhhl.asset.base.BaseFragment;
import com.yhhl.asset.base.BasePresenter;
import com.yhhl.asset.event.DismissEvent;
import com.yhhl.asset.home.management.adapter.ManageAdapter;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.List;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class ManageFragment extends BaseFragment {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ManageAdapter manageAdapter;
    @Override
    public void initView() {
        title.setText("施工方案管理");
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        manageAdapter = new ManageAdapter(getContext(), null, true);
        manageAdapter.setLoadingView(R.layout.load_loading_layout);
        manageAdapter.setLoadFailedView(R.layout.load_failed_layout);
        manageAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                loadMore();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(manageAdapter);
        //延时3s刷新列表
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> data = new ArrayList<>();
                for (int i = 0; i < 1; i++) {
                    data.add("item--" + i);
                }
                //刷新数据
                manageAdapter.setNewData(data);
            }
        }, 0);
    }

    private void loadMore() {
        manageAdapter.setLoadEndView(R.layout.load_end_layout);
        manageAdapter.loadEnd();
    }

    @Override
    public int initLayoutId() {
        return R.layout.fragment_manage;
    }

    @Override
    public void initVariable() {

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                EventBus.getDefault().post(new DismissEvent(new ManageFragment()));
                break;
        }
    }
}
