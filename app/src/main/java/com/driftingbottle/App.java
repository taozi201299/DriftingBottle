package com.driftingbottle;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import com.driftingbottle.utils.CrashHandler;
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
    public static String strIp = "http://123.56.68.127:8080";
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

    }

    public static Context globalContext() {
        return context;
    }

}

