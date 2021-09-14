package com.yhhl.asset.base;

import android.accounts.NetworkErrorException;

import com.yhhl.asset.net.NetUtils;
import com.yhhl.asset.util.ToastUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Observer基类
 * @param <T>
 */
public class BaseObserver<T>  implements Observer<T> {
    BaseCallBack<T> callback;
    //传泛型
    Class<?> dataType = null;
    private T t;
    private IBaseView<T> iBaseView;

    public BaseObserver(BaseCallBack<T> callback) {
        this.callback = callback;
    }

    @Override
    public void onSubscribe(Disposable d) {
        boolean isconnected = NetUtils.isconnected(App.sContext);
        if (!isconnected){
            ToastUtil.showToast("无网");
        }
    }

    @Override
    public void onNext(T t) {
//        callback.requestSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ConnectException
                || e instanceof TimeoutException
                || e instanceof NetworkErrorException
                || e instanceof UnknownHostException) {
            callback.requestError(ServerException.handleException(e));
        } else {
            callback.requestError(ServerException.handleException(e));
        }
    }

    @Override
    public void onComplete() {

    }

    public <T> void getDataType(T t){
        dataType=t.getClass();
    }

    private class ListWithElements<T> implements ParameterizedType {

        private Class<T> elementsClass;

        public ListWithElements(Class<T> elementsClass) {
            this.elementsClass = elementsClass;
        }

        public Type[] getActualTypeArguments() {
            return new Type[] {elementsClass};
        }

        public Type getRawType() {
            return List.class;
        }

        public Type getOwnerType() {
            return null;
        }
    }
}
