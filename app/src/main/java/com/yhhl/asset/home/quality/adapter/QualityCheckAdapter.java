package com.yhhl.asset.home.quality.adapter;

import android.content.Context;
import com.othershe.baseadapter.ViewHolder;
import com.othershe.baseadapter.base.CommonBaseAdapter;
import com.yhhl.asset.R;

import java.util.List;

public class QualityCheckAdapter extends CommonBaseAdapter<String> {

    public QualityCheckAdapter(Context context, List<String> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, String data, int position) {
        holder.setText(R.id.serial_number,data);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.check_quality_item;
    }
}