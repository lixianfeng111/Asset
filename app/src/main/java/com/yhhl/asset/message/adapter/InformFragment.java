package com.yhhl.asset.message.adapter;

import android.os.Handler;

import com.othershe.baseadapter.interfaces.OnLoadMoreListener;
import com.yhhl.asset.R;
import com.yhhl.asset.base.BaseFragment;
import com.yhhl.asset.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class InformFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        messageAdapter = new MessageAdapter(getContext(), null, true);
        messageAdapter.setLoadingView(R.layout.load_loading_layout);
        messageAdapter.setLoadFailedView(R.layout.load_failed_layout);
        messageAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                loadMore();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(messageAdapter);
        //延时3s刷新列表
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> data = new ArrayList<>();
                for (int i = 0; i < 1; i++) {
                    data.add("待办");
                }
                //刷新数据
                messageAdapter.setNewData(data);
            }
        }, 0);
    }

    private void loadMore() {
        messageAdapter.setLoadEndView(R.layout.load_end_layout);
        messageAdapter.loadEnd();
    }

    @Override
    public int initLayoutId() {
        return R.layout.fragment_backlog;
    }

    @Override
    public void initVariable() {

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
