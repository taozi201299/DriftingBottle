package com.driftinbottle.callback;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by Administrator on 2018/5/14.
 */

public abstract class RequestCallback<T> {
    public Handler mHandler = new Handler(Looper.getMainLooper());

    public RequestCallback() {

    }

    public abstract void onResponse(T result);

    public abstract void onFailure(ErrorInfo.ErrorCode errorInfo);

    public void sendResult(final T result){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                onResponse(result);
            }
        });
    }
    public void sendResultFailed(int errorCode){
        onFailure(ErrorInfo.ErrorCode.valueOf(errorCode));
    }
}
