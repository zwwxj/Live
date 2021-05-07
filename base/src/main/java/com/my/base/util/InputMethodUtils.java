package com.my.base.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class InputMethodUtils {

    /**
     * 显示软键盘
     */
    public static void showSoftKeyboard(Context context, EditText editText) {
        if (editText != null) {
            editText.requestFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }
    }


    /**
     * 关闭软键盘
     */
    public static void hideSoftKeyboard(Context context, EditText editText) {
        if (editText != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            //如果软键盘已经开启
            if (inputMethodManager.isActive()) {
                inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }


    /**
     * 关闭软键盘
     */
    public static void hideSoftKeyboard(Activity activity) {
        if (activity != null && activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            //如果软键盘已经开启
            if (inputMethodManager.isActive()) {
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 切换软键盘的状态
     */
    public static void toggleSoftKeyboard(Context context) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 监听软键盘状态
     *
     * @param root
     */
    public static void observeKeyBoard(View root, KeyBoardChangeListener listener) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            boolean isShowSoftKeyboard = false;

            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                root.getWindowVisibleDisplayFrame(r);
                int heightDiff = ScreenUtils.getScreenHeight(root.getContext()) - r.bottom;
                if (heightDiff > ScreenUtils.getScreenHeight(root.getContext()) / 3.0f) {
                    isShowSoftKeyboard = true;
                    listener.onKeyboardChange(View.VISIBLE);
                } else if (isShowSoftKeyboard) {
                    isShowSoftKeyboard = false;
                    listener.onKeyboardChange(View.GONE);
                }
            }
        });
    }

    public interface KeyBoardChangeListener {
        void onKeyboardChange(int i);
    }
}
