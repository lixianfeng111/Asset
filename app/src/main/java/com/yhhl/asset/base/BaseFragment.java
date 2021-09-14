package com.yhhl.asset.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yhhl.asset.net.LogUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * baseFragment
 */
@SuppressLint("NewApi")
public abstract class BaseFragment<T extends BasePresenter> extends Fragment {
    protected T miBasePresenter;
    public AppCompatActivity mcompatActivity;
    protected String TAG = "";
    protected View view;
    protected boolean isInitData = false;
    private Unbinder unbinder;

    protected ViewManager mViewManager;

    private BaseTitleBar mBaseTitleBar;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mcompatActivity = (AppCompatActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(initLayoutId(), container, false);
//        mViewManager = new ViewManager(getContext(), R.layout.base_activity_layout);
//        FrameLayout baseTitleBar = mViewManager.findView(R.id.base_title_bar);
//        FrameLayout baseContent = mViewManager.findView(R.id.base_content);
//
//        //标题栏区域
//        mBaseTitleBar = createTitleBar();
//        if (mBaseTitleBar != null && mBaseTitleBar.getTitleBarViewRes() != 0) {
//            View titleBarView = LayoutInflater.from(getContext()).inflate(mBaseTitleBar.getTitleBarViewRes(), null, false);
//            baseTitleBar.addView(titleBarView);
//            onCreateTitleBar(mBaseTitleBar);
//            mBaseTitleBar.createTitleBar(mViewManager);
//        }

        unbinder = ButterKnife.bind(this, view);

        initVariable();
        //初始化视图
        initView();
        LogUtil.d("onCreateView");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        miBasePresenter = initPresenter();
        //初始化点击事件
        LogUtil.d("onActivityCreated");
        initListener();
        if (getUserVisibleHint() && !isInitData) {
            initData();
            isInitData = true;
        }
    }

    public abstract void initView();

    public abstract void initListener();

    public abstract void initData();

    public abstract int initLayoutId();

    //初始化变量
    public abstract void initVariable();

    public abstract T initPresenter();


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (view != null) {
                if (!isInitData) {
                    //加载数据
                    initData();
                    isInitData = true;
                } else {
                    //调用显示方法
                    onVisiable();
                }
            }
        }
    }

    protected void onVisiable() {

    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mcompatActivity != null) {
            mcompatActivity = null;
        }
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroyView();
    }
    //加载空布局    方便统一处理
    public void showEmptyLayout() {

    }

    //加载错误的布局
    public void showErrorLayout() {

    }

    //显示正在加载布局
    public void showLoadingLayout() {

    }

    //显示内容
    public void showContent() {

    }

    /**
     * 获取titleBar的布局
     *
     * @return
     */
//    public BaseTitleBar createTitleBar() {
//        SimpleTitleBar titleBar = new SimpleTitleBar(getContext());
//        titleBar.setLeftIcon(getLeftIcon()).setLeftClickListener(getLeftClickListener())
//                .setTitleContent(getTitleContent());
//        return titleBar;
//    }

    /**
     * 获取左侧图标
     *
     * @return
     */
//    public int getLeftIcon() {
//        return R.mipmap.icon;
//    }

    /**
     * 左侧按钮 点击事件
     *
     * @return
     */
    public View.OnClickListener getLeftClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        };
    }

    /**
     * 页面标题信息
     *
     * @return
     */
    public String getTitleContent() {
        return "";
    }

    /**
     * titleBar 的处理
     *
     * @param titleBar
     */
    protected void onCreateTitleBar(BaseTitleBar titleBar) {

    }
}
