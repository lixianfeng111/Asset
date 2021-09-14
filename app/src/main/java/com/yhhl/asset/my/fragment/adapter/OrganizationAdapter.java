package com.yhhl.asset.my.fragment.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.othershe.baseadapter.ViewHolder;
import com.othershe.baseadapter.base.CommonBaseAdapter;
import com.yhhl.asset.R;
import com.yhhl.asset.util.ToastUtil;

import java.util.List;

public class OrganizationAdapter extends CommonBaseAdapter<String> {
    private Context context;
    private int n=0;
    private int i=0;
    public OrganizationAdapter(Context context, List<String> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
        this.context=context;
    }

    @Override
    protected void convert(ViewHolder holder, String data, int position) {
        TextView textView = holder.getView(R.id.textView);
        if (i==0){
            i=1;
            if (position==0){
                holder.setTextColor(R.id.textView,context.getResources().getColor(R.color.color_3));
            }else {
                holder.setTextColor(R.id.textView,context.getResources().getColor(R.color.color_9));
            }
        }else {
            if (n==position){
                holder.setTextColor(R.id.textView,context.getResources().getColor(R.color.color_3));
            }else {
                holder.setTextColor(R.id.textView,context.getResources().getColor(R.color.color_9));
            }
        }

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n=position;
                if (onChangeTextColorListener!=null){
                    onChangeTextColorListener.ChangeTextColorNotify(data,position);
                }
            }
        });
        holder.setText(R.id.textView,data);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.organization_item;
    }

    private OnChangeTextColorListener onChangeTextColorListener;

    public interface OnChangeTextColorListener{
        void ChangeTextColorNotify(String name,int n);
    }
    public void setOnChangeTextColorListener(OnChangeTextColorListener onChangeTextColorListener){
        this.onChangeTextColorListener=onChangeTextColorListener;
    }
}