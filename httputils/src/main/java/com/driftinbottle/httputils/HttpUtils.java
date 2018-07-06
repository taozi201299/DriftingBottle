package com.driftinbottle.httputils;

import android.service.carrier.CarrierMessagingService;
import android.support.annotation.Nullable;

import com.driftinbottle.callback.RequestCallback;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;
import com.lzy.okhttputils.callback.FileCallback;
import com.lzy.okhttputils.callback.StringCallback;
import com.lzy.okhttputils.model.HttpParams;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/5/14.
 */

public class HttpUtils {
    private static HttpUtils instance = null;

    private HttpUtils(){

    }

    public static HttpUtils getInstance(){
        if(instance == null){
            synchronized (HttpUtils.class){
                instance = new HttpUtils();
            }
        }
        return instance;
    }
    public void requestGet(String url, HashMap<String,String> params, String tag, final RequestCallback<String> callback, CacheMode cacheMode) {
        HttpParams httpParams = new HttpParams();
         for(String key:params.keySet()){
            httpParams.put(key,params.get(key));
        }
        OkHttpUtils.get(url)     // 请求方式和请求url
                .tag(tag)                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey(url)            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(cacheMode)    // 缓存模式，详细请看缓存介绍
                .params(httpParams)
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                       if(callback != null){
                           callback.sendResult(s);
                       }
                    }
                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        if(callback != null){
                            callback.sendResultFailed(-2);
                        }
                    }
                });
    }
    /**
     * 网络请求
     *
     * @param url
     * @param params
     * @param tag
     * @param callback
     */
    public void requestNet_put(String url, HashMap<String, String> params, String tag, final RequestCallback<String> callback) {
        url = processUrl(url, callback);
        if (url == null) return;
        FormBody.Builder builder = new FormBody.Builder();
        for(String key :params.keySet()) {
            builder.add(key, params.get(key));
        }
        RequestBody requestBody = builder.build();
        OkHttpUtils.put(url)
                .requestBody(requestBody)
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                        if(callback != null){
                            callback.sendResult(s);
                        }
                    }

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        if(callback != null){
                            callback.sendResultFailed(-2);
                        }
                    }
                });
    }
    /**
     * 网络请求
     *
     * @param url
     * @param params
     * @param tag
     * @param callback
     */
    public void requestNet_post(String url, HashMap<String, String> params, String tag, final RequestCallback<String> callback) {
        HttpParams httpParams = new HttpParams();
        for(String key:params.keySet()){
            httpParams.put(key,params.get(key));
        }
        url = processUrl(url, callback);
        if (url == null) return;
        OkHttpUtils
                .post(url)//
                .params(httpParams)//
                .tag(tag)
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                        if(callback != null){
                            callback.sendResult(s);
                        }
                    }

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        if(callback != null){
                            callback.sendResultFailed(-2);
                        }
                    }
                });
    }
    /**
     * 网络请求
     *
     * @param url
     * @param tag
     * @param callback
     */
    public void requestNet_download(String url, HashMap<String, String> params, String tag, FileCallback callback) {
        HttpParams httpParams = new HttpParams();
        for(String key:params.keySet()){
            httpParams.put(key,params.get(key));
        }
        OkHttpUtils
                .get(url)
                .params(httpParams)
                .tag(tag)
                .connTimeOut(20000)
                .readTimeOut(20000)
                .writeTimeOut(20000)
                .execute(callback);
    }

    public void requestNet_File(String url, HashMap<String, String> params, HashMap<String, File> fileMap, String tag, StringCallback callback) {
        HttpParams httpParams = new HttpParams();
        for(String key:params.keySet()){
            httpParams.put(key,params.get(key));
        }
        List<File> fileList = new ArrayList<>();
        for(File file :fileMap.values()){
            fileList.add(file);
        }
        OkHttpUtils.post(url)//
                .addFileParams("resourcefile[]",fileList )
                .params(httpParams)//
                .tag(tag)
                .execute(callback);
    }

    /**
     * @param tag
     */
    public void cancleHttp(String tag) {
        OkHttpUtils.getInstance().cancelTag(tag);
    }
    /**
     * 由于url由用户输入，所以需要处理url 非法问题
     *
     * @param url
     * @param callback
     * @return
     */
    @Nullable
    public String processUrl(String url, RequestCallback<String> callback) {
        if (url == null) {
            callback.sendResultFailed(-8);
            return null;
        }

        // Silently replace websocket URLs with HTTP URLs.
        if (url.regionMatches(true, 0, "ws:", 0, 3)) {
            url = "http:" + url.substring(3);
        } else if (url.regionMatches(true, 0, "wss:", 0, 4)) {
            url = "https:" + url.substring(4);
        }

        HttpUrl parsed = HttpUrl.parse(url);
        if (parsed == null) {

            callback.sendResultFailed(-8);
            return null;
        }
        return url;
    }
}
