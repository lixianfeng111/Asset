package com.yhhl.asset.home.completionresults;

import android.content.Context;
import android.view.View;
import com.othershe.baseadapter.ViewHolder;
import com.othershe.baseadapter.base.CommonBaseAdapter;
import com.yhhl.asset.R;
import com.yhhl.asset.home.webview.WebViewUrl;

import java.util.List;

public class CompletionResultAdapter extends CommonBaseAdapter<String> {

    private String url;
    public CompletionResultAdapter(Context context, List<String> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, String data, int position) {
        holder.setText(R.id.fileName,data);
        holder.getView(R.id.see_the_model).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSeeTheModelListener!=null){
                    if (position==0){
                        url="http://114.251.113.1:9090/freedobim/#/app/modelPreview?modelId=fd5bed59-92ee-40e2-afe7-af236211e4eb";
                    }else if (position==1){
                        url="http://114.251.113.1:9090/freedobim/#/app/modelPreview?modelId=ce7d2415-70ec-41b3-ba8b-b015773bff33";
                    }else {
                        url="http://114.251.113.1:9090/freedobim/#/app/modelPreview?modelId=ce7d2415-70ec-41b3-ba8b-b015773bff33";
                    }
                    WebViewUrl.TITLE="竣工成果";
                    WebViewUrl.WebView_URL=url;
                    onSeeTheModelListener.OnSeeTheModelListener();
                }
            }
        });
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.completion_result_item;
    }
    private OnSeeTheModelListener onSeeTheModelListener;

    public interface OnSeeTheModelListener{
        void OnSeeTheModelListener();
    }
    public void setOnSeeTheModelListener(OnSeeTheModelListener onSeeTheModelListener){
        this.onSeeTheModelListener=onSeeTheModelListener;
    }
}
