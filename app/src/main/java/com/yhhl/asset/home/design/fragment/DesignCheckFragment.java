package com.yhhl.asset.home.design.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.othershe.baseadapter.interfaces.OnLoadMoreListener;
import com.yhhl.asset.R;
import com.yhhl.asset.base.BaseFragment;
import com.yhhl.asset.base.BasePresenter;
import com.yhhl.asset.dialog.DialogUtils;
import com.yhhl.asset.event.DismissEvent;
import com.yhhl.asset.home.checkmodel.SeeTheModelActivity;
import com.yhhl.asset.home.design.activity.AddDesignActivity;
import com.yhhl.asset.home.design.adapter.DesignCheckAdapter;
import com.yhhl.asset.util.IntentUtil;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.List;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class DesignCheckFragment extends BaseFragment implements DesignCheckAdapter.OnDeleteListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.right_icon)
    ImageView right_icon;
    private DesignCheckAdapter designCheckAdapter;
    private Dialog dialog;
    private List<String> data;

    @Override
    public void initView() {
        title.setText("设计交底");
        right_icon.setVisibility(View.VISIBLE);
        right_icon.setBackgroundResource(R.drawable.add);
    }

    @Override
    public void initListener() {
        data = new ArrayList<>();
        data.add("JN06-CS-C01-TJ");
        data.add("JHJ394839434");
    }

    @Override
    public void initData() {
        designCheckAdapter = new DesignCheckAdapter(getContext(), data, true);
        designCheckAdapter.setOnDeleteListener(this);
        designCheckAdapter.setLoadingView(R.layout.load_loading_layout);
        designCheckAdapter.setLoadFailedView(R.layout.load_failed_layout);
        designCheckAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                loadMore();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(designCheckAdapter);

    }

    @Override
    public void ChangeTextColorNotify(int n,int num, TextView textView) {
        if (num==1){
            DialogUtils.showDefaultAlertDialog("是否确认删除该数据", new DialogUtils.ClickListener() {
                @Override
                public void clickConfirm() {
                    data.remove(n);
                    designCheckAdapter.setNewData(data);
                    designCheckAdapter.loadEnd();
                    designCheckAdapter.notifyDataSetChanged();
                }
            });
        }else if (num==2){
            pop_fu(textView);
        }else {
            Bundle bundle = new Bundle();
            bundle.putInt("see_the_model",n);
            IntentUtil.getInstance().intent(getContext(), SeeTheModelActivity.class,bundle);
        }
    }

    private void pop_fu(TextView textView) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.popu_fu, null, false);
        TextView fu1 = view.findViewById(R.id.fu1);
        TextView fu2 = view.findViewById(R.id.fu2);
//        TextView fu3 = view.findViewById(R.id.fu3);
        final PopupWindow popupWindow=new PopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_fu));
        popupWindow.showAsDropDown(textView,-100,10);
        fu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = fu1.getText().toString();
                textView.setText(s);
                popupWindow.dismiss();
            }
        });
        fu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = fu2.getText().toString();
                textView.setText(s);
                popupWindow.dismiss();
            }
        });
    }



    public interface Callback {
        void onDialogClick();
    }

    private void loadMore() {
        designCheckAdapter.setLoadEndView(R.layout.load_end_layout);
        designCheckAdapter.loadEnd();
    }

    @Override
    public int initLayoutId() {
        return R.layout.fragment_design_check;
    }

    @Override
    public void initVariable() {

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @OnClick({R.id.back, R.id.right_icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                EventBus.getDefault().post(new DismissEvent(new DesignCheckFragment()));
                break;
            case R.id.right_icon:
                IntentUtil.getInstance().intent(getContext(), AddDesignActivity.class,null);
                break;
        }
    }
}