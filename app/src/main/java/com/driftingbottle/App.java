package com.driftingbottle;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.driftingbottle.utils.CrashHandler;
import com.lzy.okhttputils.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;

import cn.jpush.android.api.JPushInterface;

public class  App extends Application {

    private static final String TAG = App.class.getSimpleName();
    private static Context context;
    public static final boolean bDebug = false;
    public static String content = "┗(＾0＾)┓\uD83D\uDE35\uD83D\uDE35";
    /**
     * 记录已读的bottleId
     */
    public static ArrayList<String> bottleIds = new ArrayList<>();
    public static String strIp = "http://123.56.68.127:8080";
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//
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
    private void  test(){
//        int iCount = 1000;
//        int iPageCount = 10;
//        int iPageIndex = iCount / iPageCount;
//        int ioneMin = (int) (1000* 0.6);
//        int ithirMin = (int) (1000*0.4);
//        double iperPage = 0.16;
//        double j = 0.25;
//        for(int i = 0 ; i < ioneMin ; i++){
//            int index = (int) ((i * iperPage) * iPageCount);
//            Log.d("index is ",String.valueOf(index) );
//        }
//        for(int i = 0 ; i < ithirMin ; i++){
//            int index = (int) ((i * j) * iPageCount);
//            Log.d("three index is ",String.valueOf(index));
//        }

    }

}

