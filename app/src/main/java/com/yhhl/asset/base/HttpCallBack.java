package com.yhhl.asset.base;

import java.util.List;

public interface HttpCallBack<T> {
    void onRequest();
    void onDataSuccess(T data);
    void onFailed(String msg);
    void onDataEmpty();
    void onListSuccess(List<T> list);
}
