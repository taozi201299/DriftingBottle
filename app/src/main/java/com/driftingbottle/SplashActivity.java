package com.driftingbottle;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.driftinbottle.callback.ErrorInfo;
import com.driftinbottle.callback.RequestCallback;
import com.driftinbottle.httputils.HttpUtils;
import com.driftingbottle.activity.DriftinBottleMessageActivity;
import com.driftingbottle.base.BaseActivity;
import com.driftingbottle.bean.BaseBean;
import com.driftingbottle.bean.MoonBean;
import com.driftingbottle.utils.CommonUtils;
import com.google.gson.Gson;
import com.lzy.okhttputils.cache.CacheMode;

import java.util.HashMap;

import okhttp3.HttpUrl;

/**
 * Created by Administrator on 2018/6/13.
 */

public class SplashActivity extends BaseActivity {
    private MoonBean moonBean;
    private Handler mHandler = new Handler();

    @Override
    public int getLayoutId() {
        return R.layout.splash;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        int hour = CommonUtils.getHour();
        if(hour >= 18) {
            loadingData();
        }else {
            go2Activity();
        }

    }

    @Override
    public void initView() {
        setInitActionBar(false);
    }

    private void loadingData(){
        // TODO: 2018/6/13  获取瓶子数量和月亮路径
        String url = "http://192.168.1.18:8080/wcsps-supervision/v1/bis/bisStanSelf/bisStanSelfEvaRecs/";
        HashMap<String,String>params = new HashMap<>();
        params.put("clientId", "dd");
        HttpUtils.getInstance().requestGet(url, params, url, new RequestCallback<String>() {
            @Override
            public void onResponse(String result) {
                Gson gson = new Gson();
                moonBean = gson.fromJson(result,MoonBean.class);
                if(moonBean != null){
                    App.count = Integer.valueOf(moonBean.getCount());
                    App.strMoonUrl = moonBean.getMoomUrl();
                }
                go2Activity();

            }
            @Override
            public void onFailure(ErrorInfo.ErrorCode errorInfo) {
                go2Activity();
            }
        }, CacheMode.DEFAULT);
    }
    private void go2Activity(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                activityFinish();
                intentActivity(SplashActivity.this, MainActivity.class,false,true);
            }
        },300);
    }
}
