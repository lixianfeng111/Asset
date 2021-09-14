package com.yhhl.asset.base;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yhhl.asset.R;


public class ErrorView extends LinearLayout {
    private View mView;

    public ErrorView(Context context) {
        this(context,null);
    }

    public ErrorView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

   public ErrorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        mView = View.inflate(context, R.layout.net,this);

        initView(context);
    }

    private void initView(final Context context) {
//        RelativeLayout relativeLayout = mView.findViewById(R.id.error);
//        relativeLayout.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent =  new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
//                context.startActivity(intent);
//            }
//        });
    }
}
