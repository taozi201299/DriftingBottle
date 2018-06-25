package com.driftingbottle.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/6/15.
 */

public class BaseBeanOne<T> implements Serializable {
    @SerializedName("code")
    public String code;
    @SerializedName("msg")
    public String msg;
    @SerializedName("data")
    public T result;
}
