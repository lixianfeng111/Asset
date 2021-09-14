package com.yhhl.asset.message.adapter;

import android.content.Context;
import com.othershe.baseadapter.ViewHolder;
import com.othershe.baseadapter.base.CommonBaseAdapter;
import com.yhhl.asset.R;
import java.util.List;

public class MessageAdapter extends CommonBaseAdapter<String> {

    public MessageAdapter(Context context, List<String> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, String data, int position) {

        if (data.contains("通知公告")){
            holder.setText(R.id.title,data);
            holder.setText(R.id.content,"设计文件年中检查，请相关单位准备迎检。");
        }else if (data.contains("待办")){
            holder.setText(R.id.title,data);
            holder.setText(R.id.content,"您有一条待办未完成");
        }else {
            holder.setText(R.id.title,data);
            holder.setText(R.id.content,"您有一条预警消息请查看");
        }
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.message_item;
    }

}