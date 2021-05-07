package com.my.base.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.Util;

/**
 * @author caishuzhan
 *
 * context检测，避免context已经销毁还做其他操作导致的内存泄漏
 * 典型的，如Glide加载图片时候的检测
 */
public class ContextChecker {

    /**
     * @param context
     * @return true:检查通过   false:检查不通过
     */
    public static boolean check(Context context) {
        if (context == null) {
            return false;
        } else if (Util.isOnMainThread() && !(context instanceof Application)) {
            if (context instanceof FragmentActivity) {
                return get((FragmentActivity) context);
            } else if (context instanceof Activity) {
                return get((Activity) context);
            } else if (context instanceof ContextWrapper && ((ContextWrapper) context).getBaseContext().getApplicationContext() != null) {
                return check(((ContextWrapper) context).getBaseContext());
            }
        }
        return true;
    }

    @NonNull
    public static boolean get(@NonNull FragmentActivity activity) {
        if (Util.isOnBackgroundThread()) {
            return check(activity.getApplicationContext());
        } else {
            return assertNotDestroyed(activity);
        }
    }

    @SuppressWarnings("deprecation")
    @NonNull
    public static boolean get(@NonNull Activity activity) {
        if (Util.isOnBackgroundThread()) {
            return check(activity.getApplicationContext());
        } else {
            return assertNotDestroyed(activity);
        }
    }


    @NonNull
    public static boolean get(@NonNull Fragment fragment) {
        Preconditions.checkNotNull(fragment.getContext(), "You cannot start a load on a fragment before it is attached or after it is destroyed");
        if (Util.isOnBackgroundThread()) {
            return check(fragment.getContext().getApplicationContext());
        } else {
            return true;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static boolean assertNotDestroyed(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()) {
            return false;
        }
        return true;
    }
}
