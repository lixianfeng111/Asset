package com.yhhl.asset.base;

public interface BaseCallBack<T> {
    void requestError(ServerException e);
    void requestSuccess(T t);
}