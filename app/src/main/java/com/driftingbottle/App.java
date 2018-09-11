package com.driftingbottle;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;

import com.driftingbottle.utils.CommonUtils;
import com.driftingbottle.utils.CrashHandler;
import com.driftingbottle.utils.EmojiUtil;
import com.driftingbottle.utils.EmotionUtils;
import com.driftingbottle.utils.ToastUtils;
import com.lzy.okhttputils.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import pub.devrel.easypermissions.EasyPermissions;

public class  App extends Application {

    private static final String TAG = App.class.getSimpleName();
    private static Context context;
    public static final boolean bDebug = false;
    public static String content = "┗(＾0＾)┓\uD83D\uDE35\uD83D\uDE35";
    private static final int RC_STORAGE_PERM = 101;
    /**
     * 记录已读的bottleId
     */
    public static HashMap<String,String>bottles = new HashMap<>();
    public static HashMap<String,Integer>bottleCount = new HashMap<>();
//    public static String strIp = "http://118.190.155.234:8080"; // old server
    public  static String strIp = "http://47.105.59.177:8080";  // new server
    public static double IRand = 0.2;

    public static ArrayList<Integer>emojis;

    public static ArrayList<String>bottlesIds = new ArrayList<>();
    public static boolean isShow = true;

    /**
     * 屏幕宽度
     */
    public static int screenWidth;
    /**
     * 屏幕高度
     */
    public static int screenHeight;
    /**
     * 屏幕密度
     */
    public static float screenDensity;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init();

    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        OkHttpUtils.init(this);
        OkHttpUtils.getInstance().setConnectTimeout(10000);
        initScreenSize();
        emojis =  EmotionUtils.getEmojis();

    }

    public static Context globalContext() {
        return context;
    }

    /**
     * 初始化当前设备屏幕宽高
     */
    private void initScreenSize() {
        DisplayMetrics curMetrics = getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = curMetrics.widthPixels;
        screenHeight = curMetrics.heightPixels;
        screenDensity = curMetrics.density;
    }
}

