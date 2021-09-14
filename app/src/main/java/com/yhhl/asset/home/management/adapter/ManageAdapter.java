package com.yhhl.asset.home.management.adapter;

import android.content.Context;
import com.othershe.baseadapter.ViewHolder;
import com.othershe.baseadapter.base.CommonBaseAdapter;
import com.yhhl.asset.R;
import java.util.List;

public class ManageAdapter extends CommonBaseAdapter<String> {

    public ManageAdapter(Context context, List<String> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, String data, int position) {

    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.manage_item;
    }
}