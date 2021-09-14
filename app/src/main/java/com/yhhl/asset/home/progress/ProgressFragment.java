package com.yhhl.asset.home.progress;

import android.view.View;
import android.widget.TextView;

import com.othershe.baseadapter.interfaces.OnLoadMoreListener;
import com.yhhl.asset.R;
import com.yhhl.asset.base.BaseFragment;
import com.yhhl.asset.base.BasePresenter;
import com.yhhl.asset.event.AddEvent;
import com.yhhl.asset.event.DismissEvent;
import com.yhhl.asset.home.completionresults.CompletionResultAdapter;
import com.yhhl.asset.home.webview.WebViewFragment;
import com.yhhl.asset.home.webview.WebViewUrl;
import com.yhhl.asset.util.WebViewUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class ProgressFragment extends BaseFragment {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<String> data;
    private CompletionResultAdapter completionResultAdapter;
    @Override
    public void initView() {
        title.setText("进度模拟");
    }

    @Override
    public void initListener() {
        data = new ArrayList<>();
        data.add("DLM4-SS-C01-JG-2020");
        data.add("DLM4-SS-C01-JG-TT-2020");
        data.add("DLM4-SS-C02-JN-ZT-2020");
    }

    @Override
    public void initData() {
        completionResultAdapter = new CompletionResultAdapter(getContext(), data, true);
        completionResultAdapter.setLoadingView(R.layout.load_loading_layout);
        completionResultAdapter.setLoadFailedView(R.layout.load_failed_layout);
        completionResultAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                loadMore();
            }
        });
        completionResultAdapter.setOnSeeTheModelListener(new CompletionResultAdapter.OnSeeTheModelListener() {
            @Override
            public void OnSeeTheModelListener() {
//                WebViewUrl.TITLE="进度模拟";
//                EventBus.getDefault().post(new AddEvent(new WebViewFragment()));
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(completionResultAdapter);
    }

    private void loadMore() {
        completionResultAdapter.setLoadEndView(R.layout.load_end_layout);
        completionResultAdapter.loadEnd();
    }

    @Override
    public int initLayoutId() {
        return R.layout.fragment_completion_results;
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
                EventBus.getDefault().post(new DismissEvent(new ProgressFragment()));
                break;
        }
    }

}