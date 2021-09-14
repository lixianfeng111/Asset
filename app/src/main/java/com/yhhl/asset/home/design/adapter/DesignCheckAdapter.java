package com.yhhl.asset.home.design.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.othershe.baseadapter.ViewHolder;
import com.othershe.baseadapter.base.CommonBaseAdapter;
import com.yhhl.asset.R;
import com.yhhl.asset.home.webview.WebViewUrl;
import com.yhhl.asset.util.SpzUtils;
import com.yhhl.asset.util.ToastUtil;

import java.util.List;

public class DesignCheckAdapter extends CommonBaseAdapter<String> {

    private Context context;

    public DesignCheckAdapter(Context context, List<String> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
        this.context=context;
    }

    @Override
    protected void convert(ViewHolder holder, String data, int position) {
        TextView accessory = holder.getView(R.id.accessory);
        holder.getView(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteListener!=null){
                    onDeleteListener.ChangeTextColorNotify(position,1,null);
                }
            }
        });
        holder.getView(R.id.accessory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteListener!=null){
                    onDeleteListener.ChangeTextColorNotify(position,2,accessory);
                }
            }
        });
        holder.getView(R.id.see_the_model).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteListener!=null){
                    onDeleteListener.ChangeTextColorNotify(position,3,accessory);
                }
            }
        });
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.design_check_item;
    }

    private OnDeleteListener onDeleteListener;

    public interface OnDeleteListener{
        void ChangeTextColorNotify(int n,int num, TextView textView);
    }
    public void setOnDeleteListener(OnDeleteListener onDeleteListener){
        this.onDeleteListener=onDeleteListener;
    }
}
