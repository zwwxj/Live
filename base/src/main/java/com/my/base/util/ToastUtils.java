package com.my.base.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.my.base.R;

/**
 * Created by Stefan Lau on 2019/11/14.
 */
public class ToastUtils {

    private static ToastUtils INSTANCE;
    private Toast toast;
    private ImageView mIvIcon;
    private TextView mTvMessage;

    private long toastTime;

    public ToastUtils(){}

    public static ToastUtils getInstance(){
        if(INSTANCE == null){
            INSTANCE = new ToastUtils();
        }
        return INSTANCE;
    }

    public void init(Context context){
    //    Glide.init();
        this.toast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.toast_view, null);
        this.mIvIcon = view.findViewById(R.id.mIvIcon);
        this.mTvMessage = view.findViewById(R.id.mTvMessage);
        this.mIvIcon.setVisibility(View.GONE);

        this.toast.setView(view);
        this.toast.setGravity(Gravity.CENTER, 0, 0);
        this.toast.setDuration(Toast.LENGTH_SHORT);
    }

    /**
     * 弹出土司
     * @param content   内容
     */
    public static void show(String content) {
        show(content, Gravity.CENTER);
    }

    /**
     * 弹出土司
     * @param content   内容
     * @param gravity   对齐
     */
    public static void show(String content, int gravity) {
        show(content, gravity, Toast.LENGTH_SHORT);
    }

    /**
     * 弹出土司
     * @param content   内容
     * @param gravity   对齐
     * @param duration 时长(Toast.LENGTH_SHORT和LENGTH_LONG两种)
     */
    public static void show(String content, int gravity, int duration) {
        show(0, content, gravity, duration);
    }

    /**
     * 弹出土司
     * @param resId   内容
     * @param gravity   对齐
     * @param duration 时长(Toast.LENGTH_SHORT和LENGTH_LONG两种)
     */
    public static void show(int resId, int gravity, int duration) {
        show(resId, "", gravity, duration);
    }


    /**
     * 弹出土司
     * @param iconResId 图标
     * @param content   内容
     * @param gravity   对齐
     * @param duration 时长(Toast.LENGTH_SHORT和LENGTH_LONG两种)
     */
    public static void show(int iconResId, String content, int gravity, int duration) {

        long currentTime = System.currentTimeMillis();
        if(INSTANCE != null && !TextUtils.isEmpty(content) && Math.abs(currentTime - INSTANCE.toastTime) > 2000) {
            INSTANCE.mTvMessage.setText(content);
            if(iconResId != 0) {
                INSTANCE.mIvIcon.setVisibility(View.VISIBLE);
                INSTANCE.mIvIcon.setImageResource(iconResId);
            }else {
                INSTANCE.mIvIcon.setVisibility(View.GONE);
            }
            INSTANCE.toast.setGravity(gravity, 0, 0);
            INSTANCE.toast.setDuration(duration);
            INSTANCE.toast.show();

            INSTANCE.toastTime = System.currentTimeMillis();
        }

    }
}
