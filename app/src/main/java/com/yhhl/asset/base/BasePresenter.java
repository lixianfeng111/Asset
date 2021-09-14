package com.yhhl.asset.base;

public abstract class BasePresenter<T extends IBaseView>{
    private T iBaseView;

    public BasePresenter(T iBaseView) {
        this.iBaseView = iBaseView;
    }

    public T getiBaseView() {
        return iBaseView;
    }

    public void onDestory() {
        if (iBaseView != null) {
            iBaseView = null;
        }
    }

}
