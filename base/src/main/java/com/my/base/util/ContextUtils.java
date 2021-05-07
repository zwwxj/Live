package com.my.base.util;

import android.app.Application;
import android.content.Context;


public class ContextUtils {

    /**
     * Application 的Context
     */
    private static Context context;

    private ContextUtils(){}

    public static void init(Application application) {
        context = application.getApplicationContext();
        ToastUtils.getInstance().init(context);
    }

    public static Context getContext() {
        return context;
    }
}
