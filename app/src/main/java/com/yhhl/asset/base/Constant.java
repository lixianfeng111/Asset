package com.yhhl.asset.base;

public interface Constant {

    String NO_ABLE="当前网络不可用";
    String AGAIN="再按一次退出程序";
    String SOCKET_TIME_OUT_EXCEPTION="网络连接超时，请检查您的网络状态，稍后重试！";
    String NULL_POINTER_EXCEPTION="空指针异常";
    String SSL_HAND_SHAKE_EXCEPTION="证书验证失败";
    String CLASS_CAST_EXCEPTION="类型转换错误";
    String PARSE_ERROR="解析错误";
    String UNKNOWN="未知错误";
    String EMPTY="数据为空";

    String SWITCH="switch";

    /**
     * 手势密码key
     */
    String GESTURELOCK_KEY = "gesturelock_key";

    /**
     * 是否设置指纹解锁key
     */
    String ISFINGERPRINT_KEY = "fingerprint_key";

    /**
     * 是否设置手势解锁key
     */
    String ISGESTURELOCK_KEY = "isgesturelock_key";
}
