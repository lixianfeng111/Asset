package com.yhhl.asset.home.checkresult;

import android.app.Dialog;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import com.othershe.baseadapter.interfaces.OnLoadMoreListener;
import com.yhhl.asset.R;
import com.yhhl.asset.base.BaseFragment;
import com.yhhl.asset.base.BasePresenter;
import com.yhhl.asset.dialog.DialogUtils;
import com.yhhl.asset.event.DismissEvent;
import com.yhhl.asset.home.checkresult.adapter.CheckResultAdapter;
import com.yhhl.asset.home.design.adapter.DesignCheckAdapter;
import com.yhhl.asset.home.design.fragment.DesignCheckFragment;

import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.List;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class CheckResultFragment extends BaseFragment {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private CheckResultAdapter checkResultAdapter;
    private List<String> data;
    private Dialog dialog;
    @Override
    public void initView() {
        title.setText("竣工成果验收");
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        checkResultAdapter = new CheckResultAdapter(getContext(), null, true);
        checkResultAdapter.setLoadingView(R.layout.load_loading_layout);
        checkResultAdapter.setLoadFailedView(R.layout.load_failed_layout);
        checkResultAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                loadMore();
            }
        });

        checkResultAdapter.setOnDeleteListener(new DesignCheckAdapter.OnDeleteListener() {
            @Override
            public void ChangeTextColorNotify(int n, int num, TextView textView) {
                DialogUtils.showDefaultAlertDialog("是否确认删除该数据", new DialogUtils.ClickListener() {
                    @Override
                    public void clickConfirm() {
                        data.remove(n);
                        data.remove(n);
                        checkResultAdapter.setNewData(data);
                        checkResultAdapter.loadEnd();
                        checkResultAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(checkResultAdapter);
        //延时3s刷新列表
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                data = new ArrayList<>();
                for (int i = 0; i < 2; i++) {
                    data.add("item--" + i);
                }
                //刷新数据
                checkResultAdapter.setNewData(data);
            }
        }, 0);
    }

    private void loadMore() {
        checkResultAdapter.setLoadEndView(R.layout.load_end_layout);
        checkResultAdapter.loadEnd();
    }

    @Override
    public int initLayoutId() {
        return R.layout.fragment_check_result;
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
                EventBus.getDefault().post(new DismissEvent(new CheckResultFragment()));
                break;
        }
    }
}
