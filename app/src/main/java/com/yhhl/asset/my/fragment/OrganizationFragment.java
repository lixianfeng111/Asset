package com.yhhl.asset.my.fragment;

import android.os.Handler;

import com.othershe.baseadapter.ViewHolder;
import com.othershe.baseadapter.interfaces.OnItemClickListener;
import com.othershe.baseadapter.interfaces.OnLoadMoreListener;
import com.yhhl.asset.R;
import com.yhhl.asset.base.BaseFragment;
import com.yhhl.asset.base.BasePresenter;
import com.yhhl.asset.my.fragment.adapter.OrganizationAdapter;
import com.yhhl.asset.my.fragment.adapter.ProjectAdapter;
import com.yhhl.asset.util.SpzUtils;
import com.yhhl.asset.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class OrganizationFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerView2)
    RecyclerView recyclerView2;
    private OrganizationAdapter organizationAdapter;
    private ProjectAdapter projectAdapter;
    private String name_o="项目管理部0";
    private String name_p="项目名称0";
    private List<String> data2 = new ArrayList<>();
    private List<String> data;

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {
        for (int i = 0; i < 5; i++) {
            data2.add("项目名称" + i);
        }
    }

    @Override
    public void initData() {
        organization();
        project();
        SpzUtils.putString("name_o",name_o);
        SpzUtils.putString("name_p",name_p);
    }

    private void project() {
        projectAdapter = new ProjectAdapter(getContext(), null, true);
        projectAdapter.setLoadingView(R.layout.load_loading_layout);
        projectAdapter.setLoadFailedView(R.layout.load_failed_layout);
        projectAdapter.setOnChangeChildTextColorListener(new ProjectAdapter.OnChangeChildTextColorListener() {
            @Override
            public void ChangeTextColorNotify(String name) {
                SpzUtils.putString("name_p",name);
                projectAdapter.notifyDataSetChanged();
            }
        });
        projectAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                projectAdapter.loadEnd();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView2.setLayoutManager(layoutManager);
        recyclerView2.setAdapter(projectAdapter);
        //延时3s刷新列表
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                data = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    data.add("项目名称" + i);
                }
                //刷新数据
                projectAdapter.setNewData(data);
            }
        }, 0);
    }

    private void organization() {
        organizationAdapter = new OrganizationAdapter(getContext(), null, true);
        organizationAdapter.setLoadingView(R.layout.load_loading_layout);
        organizationAdapter.setLoadFailedView(R.layout.load_failed_layout);
        organizationAdapter.setOnChangeTextColorListener(new OrganizationAdapter.OnChangeTextColorListener() {
            @Override
            public void ChangeTextColorNotify(String name,int n) {
                organizationAdapter.notifyDataSetChanged();
                SpzUtils.putString("name_o",name);
                if (n%2==0){
                    projectAdapter.setNewData(data);
                }else {
                    projectAdapter.setNewData(data2);
                }

            }
        });
        organizationAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                organizationAdapter.loadEnd();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(organizationAdapter);
        //延时3s刷新列表
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> data = new ArrayList<>();
                for (int i = 0; i < 8; i++) {
                    data.add("项目管理部" + i);
                }
                //刷新数据
                organizationAdapter.setNewData(data);
            }
        }, 0);
    }


    @Override
    public int initLayoutId() {
        return R.layout.fragment_organization;
    }

    @Override
    public void initVariable() {

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}