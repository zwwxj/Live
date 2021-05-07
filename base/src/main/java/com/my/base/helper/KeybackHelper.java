package com.my.base.helper;

import android.app.Activity;
import android.view.KeyEvent;

import com.my.base.util.ToastUtils;

/**
 * @author caishuzhan
 */
public class KeybackHelper {

    private static long clickTime;

    /**
     * @param keyCode
     * @param event
     * @return true:消费  false:未消费
     */
    public static boolean handleKeyDown(Activity activity, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.ACTION_DOWN) {
            exit(activity);
            return true;
        }
        return false;
    }

    private static void exit(Activity activity) {
        if ((System.currentTimeMillis() - clickTime) > 1000) {
            ToastUtils.show("再次点击退出");
            clickTime = System.currentTimeMillis();
        } else {
            activity.moveTaskToBack(true);
        }
    }
}
