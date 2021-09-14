package com.yhhl.asset.login;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.yhhl.asset.MainActivity;
import com.yhhl.asset.R;
import com.yhhl.asset.base.BaseActivity;
import com.yhhl.asset.base.BasePresenter;
import com.yhhl.asset.statusBar.StatusManager;
import com.yhhl.asset.util.GlideUtil;
import com.yhhl.asset.util.IntentUtil;
import com.yhhl.asset.util.SoftKeyboard;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.userName)
    EditText userName;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.login_logo)
    ImageView login_logo;

    @Override
    public void initView() {
        StatusManager.getInstance().immersionStatusBar(this,false);
    }

    @Override
    public void initListener() {
        SoftKeyboard.popSoftKeyboard(this,userName);
        SoftKeyboard.popSoftKeyboard(this,password);
    }

    @Override
    public void initData() {
        GlideUtil.showImage(login_logo,R.drawable.login_logo,2, null);
    }

    @Override
    public int initLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initVariable() {

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @OnClick({R.id.login_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                String s = userName.getText().toString();
                String s1 = password.getText().toString();
                IntentUtil.getInstance().intent(this, MainActivity.class,null);
                finish();
                break;
        }
    }
}