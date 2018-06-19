package com.driftingbottle.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/6/13.
 */

public class MoonBean implements Serializable {

    public String count;
    public String moomUrl;

    public void setCount(String count) {
        this.count = count;
    }

    public String getCount() {
        return count;
    }

    public String getMoomUrl() {
        return moomUrl;
    }

    public void setMoomUrl(String moomUrl) {
        this.moomUrl = moomUrl;
    }
}
