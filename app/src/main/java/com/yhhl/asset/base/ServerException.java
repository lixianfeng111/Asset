package com.yhhl.asset.base;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializer;
import com.yhhl.asset.net.GsonUtil;
import com.yhhl.asset.net.LogUtil;
import com.yhhl.asset.util.ToastUtil;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.io.IOException;
import java.io.NotSerializableException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import retrofit2.HttpException;

/**
 * 异常处理
 */
public class ServerException extends Exception {

    private final int code;
    private static String message;

    public ServerException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
        this.message = throwable.getMessage();
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public static ServerException handleException(Throwable e) {
        ServerException ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ServerException(httpException, httpException.code());
            try {
                ex.message = httpException.response().errorBody().string();
            } catch (IOException e1) {
                e1.printStackTrace();
                ex.message = e1.getMessage();
            }
            return ex;
        } else if (e instanceof SocketTimeoutException) {
            ex = new ServerException(e, ERROR.TIMEOUT_ERROR);
            ex.message = Constant.SOCKET_TIME_OUT_EXCEPTION;
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ServerException(e, ERROR.TIMEOUT_ERROR);
            ex.message = Constant.SOCKET_TIME_OUT_EXCEPTION;
            return ex;
        } else if (e instanceof ConnectTimeoutException) {
            ex = new ServerException(e, ERROR.TIMEOUT_ERROR);
            ex.message = Constant.SOCKET_TIME_OUT_EXCEPTION;
            return ex;
        } else if (e instanceof UnknownHostException) {
            ex = new ServerException(e, ERROR.TIMEOUT_ERROR);
            ex.message = Constant.SOCKET_TIME_OUT_EXCEPTION;
            return ex;
        } else if (e instanceof NullPointerException) {
            ex = new ServerException(e, ERROR.NULL_POINTER_EXCEPTION);
            ex.message = Constant.NULL_POINTER_EXCEPTION;
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ServerException(e, ERROR.SSL_ERROR);
            ex.message = Constant.SSL_HAND_SHAKE_EXCEPTION;
            return ex;
        } else if (e instanceof ClassCastException) {
            ex = new ServerException(e, ERROR.CAST_ERROR);
            ex.message = Constant.CLASS_CAST_EXCEPTION;
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof JsonSerializer
                || e instanceof NotSerializableException
                || e instanceof ParseException) {
            ex = new ServerException(e, ERROR.PARSE_ERROR);
            ex.message = Constant.PARSE_ERROR;
            return ex;
        } else if (e instanceof IllegalStateException) {
            ex = new ServerException(e, ERROR.ILLEGAL_STATE_ERROR);
            ex.message = e.getMessage();
            return ex;
        } else {
            ex = new ServerException(e, ERROR.UNKNOWN);
            ex.message = Constant.UNKNOWN;
            return ex;
        }
    }

    /**
     * 约定异常
     */
    public static class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 连接超时
         */
        public static final int TIMEOUT_ERROR = 1001;
        /**
         * 空指针错误
         */
        public static final int NULL_POINTER_EXCEPTION = 1002;

        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1003;

        /**
         * 类转换错误
         */
        public static final int CAST_ERROR = 1004;

        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1005;

        /**
         * 非法数据异常
         */
        public static final int ILLEGAL_STATE_ERROR = 1006;
    }

    public static void getMessageAndCode(){
        if (message != null) {
            if (message.startsWith("{") || message.startsWith("[")) {
                ErrorBean errorBean = GsonUtil.getInstance().mGson.fromJson(message, ErrorBean.class);
                ToastUtil.showToast(errorBean.getMessage());
                LogUtil.json(message);
            } else {
                ToastUtil.showToast(message);
                LogUtil.d(message);
            }
        }
    }
}