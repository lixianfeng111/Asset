package com.yhhl.asset.home.quality;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import com.othershe.baseadapter.interfaces.OnLoadMoreListener;
import com.yhhl.asset.R;
import com.yhhl.asset.base.BaseFragment;
import com.yhhl.asset.base.BasePresenter;
import com.yhhl.asset.event.DismissEvent;
import com.yhhl.asset.home.checksafe.CheckSafeFragment;
import com.yhhl.asset.home.quality.adapter.QualityCheckAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class QualityCheckFragment extends BaseFragment {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private QualityCheckAdapter qualityCheckAdapter;
    private List<String> data;

    @Override
    public void initView() {
        title.setText("质量检查");
    }

    @Override
    public void initListener() {
        data = new ArrayList<>();
        data.add("GH878784234");
        data.add("GH215657980");
        data.add("GH215654590");
        data.add("GH215348248");
    }

    @Override
    public void initData() {
        qualityCheckAdapter = new QualityCheckAdapter(getContext(), data, true);
        qualityCheckAdapter.setLoadingView(R.layout.load_loading_layout);
        qualityCheckAdapter.setLoadFailedView(R.layout.load_failed_layout);
        qualityCheckAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                loadMore();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(qualityCheckAdapter);

    }

    private void loadMore() {
        qualityCheckAdapter.setLoadEndView(R.layout.load_end_layout);
        qualityCheckAdapter.loadEnd();
    }

    @Override
    public int initLayoutId() {
        return R.layout.fragment_check_quality;
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
                EventBus.getDefault().post(new DismissEvent(new QualityCheckFragment()));
                break;
        }
    }
}