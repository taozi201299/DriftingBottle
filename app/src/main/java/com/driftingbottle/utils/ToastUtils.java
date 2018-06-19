package com.driftingbottle.utils;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.driftingbottle.App;

public class ToastUtils {

    private static Toast mToast = null;

    /**
     * Show the view or text notification for a short period of time.  This time
     * could be user-definable.  This is the default.
     */
    public static final int LENGTH_SHORT = Toast.LENGTH_SHORT;

    /**
     * Show the view or text notification for a long period of time.  This time
     * could be user-definable.
     */
    public static final int LENGTH_LONG = Toast.LENGTH_LONG;

    /**
     * 显示Toast
     *
     * @param text
     * @param duration
     */
    @SuppressLint("ShowToast")
    public static void show(CharSequence text, int duration) {
        try{
            mToast.getView().isShown();
            mToast.setText(text);
        } catch (Exception e) {
            mToast = Toast.makeText(App.globalContext(), text, duration);
        }
        mToast.show();
    }

    /**
     * 显示Toast
     *
     * @param text
     * @see #show(CharSequence, int)
     */
    public static void show(CharSequence text) {
        show(text, Toast.LENGTH_SHORT);
    }

    public static void customViewShowCenter(View customView) {
        customViewShow(customView, Gravity.CENTER, 0, 0);
    }

    public static void customViewShow(View customView, int gravity, int xOffset, int yOffset) {
        Toast toast = new Toast(App.globalContext());
        toast.setGravity(gravity, xOffset, yOffset); // toast 显示位置
        toast.setView(customView);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
