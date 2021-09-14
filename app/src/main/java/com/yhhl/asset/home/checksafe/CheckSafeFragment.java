package com.yhhl.asset.home.checksafe;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import com.othershe.baseadapter.interfaces.OnLoadMoreListener;
import com.yhhl.asset.R;
import com.yhhl.asset.base.BaseFragment;
import com.yhhl.asset.base.BasePresenter;
import com.yhhl.asset.event.DismissEvent;
import com.yhhl.asset.home.checksafe.adapter.CheckSafeAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class CheckSafeFragment extends BaseFragment {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private CheckSafeAdapter checkSafeAdapter;
    private List<String> data;

    @Override
    public void initView() {
        title.setText("安全检查");
    }

    @Override
    public void initListener() {
        data = new ArrayList<>();
        data.add("AH568783412");
        data.add("AH098783233");
        data.add("AH367887834");
        data.add("AH023783299");
    }

    @Override
    public void initData() {
        checkSafeAdapter = new CheckSafeAdapter(getContext(), data, true);
        checkSafeAdapter.setLoadingView(R.layout.load_loading_layout);
        checkSafeAdapter.setLoadFailedView(R.layout.load_failed_layout);
        checkSafeAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                loadMore();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(checkSafeAdapter);

    }

    private void loadMore() {
        checkSafeAdapter.setLoadEndView(R.layout.load_end_layout);
        checkSafeAdapter.loadEnd();
    }

    @Override
    public int initLayoutId() {
        return R.layout.fragment_check_safe;
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
                EventBus.getDefault().post(new DismissEvent(new CheckSafeFragment()));
                break;
        }
    }
}
