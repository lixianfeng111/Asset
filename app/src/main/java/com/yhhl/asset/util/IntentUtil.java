package com.yhhl.asset.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Role: intent跳转类
 */
public class IntentUtil {

    private static IntentUtil ourInstance;

    public static IntentUtil getInstance() {
        if (ourInstance == null) {
            synchronized (IntentUtil.class) {
                if (ourInstance == null) {
                    ourInstance = new IntentUtil();
                }
            }
        }
        return ourInstance;
    }

    private IntentUtil() {

    }

    public void intent(Context context, Class<?> cs, Bundle bundle) {
        Intent intent = new Intent(context, cs);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
//        intent.getExtras();
    }
}
