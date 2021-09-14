package com.yhhl.asset.home.design.activity;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.yhhl.asset.R;
import com.yhhl.asset.base.BaseActivity;
import com.yhhl.asset.base.BasePresenter;
import com.yhhl.asset.util.PickerViewUtil;
import com.yhhl.asset.util.SoftKeyboard;
import com.yhhl.asset.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class AddDesignActivity extends BaseActivity implements PickerViewUtil.OnListenerTime {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.bills)
    TextView bills;
    @BindView(R.id.company)
    TextView company;
    @BindView(R.id.state)
    TextView state;
    @BindView(R.id.project_name)
    TextView project_name;
    @BindView(R.id.principal)
    TextView principal;
    @BindView(R.id.start_time)
    TextView start_time;
    @BindView(R.id.remark)
    EditText remark;
    @BindView(R.id.bian)
    EditText bian;
    @BindView(R.id.job_number)
    EditText job_number;
    @BindView(R.id.item_coding)
    EditText item_coding;
    private int color;

    @Override
    public void initView() {
        title.setText("设计交底");
    }

    @Override
    public void initListener() {
        SoftKeyboard.popSoftKeyboard(this,remark);
        SoftKeyboard.popSoftKeyboard(this,bian);
        SoftKeyboard.popSoftKeyboard(this,job_number);
        SoftKeyboard.popSoftKeyboard(this,item_coding);

        PickerViewUtil pickerViewUtil = new PickerViewUtil();
        pickerViewUtil.setListenerTime(this);
    }

    @Override
    public void initData() {
        color = getResources().getColor(R.color.color_3);
    }

    @Override
    public int initLayoutId() {
        return R.layout.activity_add_design;
    }

    @Override
    public void initVariable() {

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }


    @OnClick({R.id.back, R.id.submit, R.id.bills, R.id.project_name, R.id.company, R.id.state, R.id.principal,R.id.start_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.submit:
                ToastUtil.showToast("提交成功");
                finish();
                break;
            case R.id.project_name:
                company_name("项目","大连地铁4号一期工程", project_name);
                break;
            case R.id.company:
                company_name("单位","设计单位" ,company);
                break;
            case R.id.state:
                company_name("状态","" ,state);
                break;
            case R.id.principal:
                company_name("负责人","设计A02" ,principal);
                break;
            case R.id.start_time:
                PickerViewUtil.initTimePicker(this, start_time);
                break;
        }
    }

    private void company_name(String title,String name,TextView textView) {
        View view = LayoutInflater.from(this).inflate(R.layout.pop_accessor, null, false);
        TextView dan1 = view.findViewById(R.id.dan1);
        TextView dan2 = view.findViewById(R.id.dan2);
        TextView dan3 = view.findViewById(R.id.dan3);
        TextView select = view.findViewById(R.id.select);
        select.setText("选择"+title);
        dan1.setText(name+1);
        dan2.setText(name+2);
        dan3.setText(name+3);
        final PopupWindow popupWindow=new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);
        popupWindow.setFocusable(true);
        setBackgroundAlpha(0.5f);
        popupWindow.setOutsideTouchable(true);
//        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
        //点击外部消失并回复透明度
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });
        dan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = dan1.getText().toString();
                textView.setText(s);
//                textView.setTextColor(color);
                popupWindow.dismiss();
                setBackgroundAlpha(1.0f);
            }
        });

        dan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = dan2.getText().toString();
                textView.setText(s);
//                textView.setTextColor(color);
                popupWindow.dismiss();
                setBackgroundAlpha(1.0f);
            }
        });
        dan3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = dan3.getText().toString();
                textView.setText(s);
//                textView.setTextColor(color);
                popupWindow.dismiss();
                setBackgroundAlpha(1.0f);
            }
        });
    }

    private void pop_accessor() {
        View view = LayoutInflater.from(this).inflate(R.layout.pop_accessor, null, false);
        TextView dan1 = view.findViewById(R.id.dan1);
        TextView dan2 = view.findViewById(R.id.dan2);
        TextView dan3 = view.findViewById(R.id.dan3);
        final PopupWindow popupWindow=new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);
        popupWindow.setFocusable(true);
        setBackgroundAlpha(0.5f);
        popupWindow.setOutsideTouchable(true);
//        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
        //点击外部消失并回复透明度
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });
        dan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = dan1.getText().toString();
                bills.setText(s);
                bills.setTextColor(color);
                popupWindow.dismiss();
                setBackgroundAlpha(1.0f);
            }
        });

        dan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = dan2.getText().toString();
                bills.setText(s);
                bills.setTextColor(color);
                popupWindow.dismiss();
                setBackgroundAlpha(1.0f);
            }
        });
        dan3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = dan3.getText().toString();
                bills.setText(s);
                bills.setTextColor(color);
                popupWindow.dismiss();
                setBackgroundAlpha(1.0f);
            }
        });
    }

    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    @Override
    public void startTime(String time) {
        start_time.setText(time);
    }

    @Override
    public void endTime(String time) {

    }
}