package com.driftingbottle.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jidan on 18-6-12.
 */


public class BaseBean<T> implements Serializable {
    @SerializedName("code")
    public String code;
    @SerializedName("msg")
    public String msg;
    @SerializedName("totalCount")
    public String totalCount;
    public @SerializedName("data")
    List<T>result;
}
