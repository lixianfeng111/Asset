package com.yhhl.asset.home.checkresult.adapter;

import android.content.Context;
import android.view.View;
import com.othershe.baseadapter.ViewHolder;
import com.othershe.baseadapter.base.CommonBaseAdapter;
import com.yhhl.asset.R;
import com.yhhl.asset.home.design.adapter.DesignCheckAdapter;

import java.util.List;

public class CheckResultAdapter extends CommonBaseAdapter<String> {

    public CheckResultAdapter(Context context, List<String> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, String data, int position) {
        holder.getView(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteListener!=null){
                    onDeleteListener.ChangeTextColorNotify(position,1,null);
                }
            }
        });
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.check_result_item;
    }

    private DesignCheckAdapter.OnDeleteListener onDeleteListener;

    public interface OnDeleteListener{
        void ChangeTextColorNotify(int n);
    }

    public void setOnDeleteListener(DesignCheckAdapter.OnDeleteListener onDeleteListener){
        this.onDeleteListener=onDeleteListener;
    }
}
