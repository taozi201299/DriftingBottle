package com.driftingbottle.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/6/25.
 */

public class BottleBean0 implements Serializable {
    public String regionID;
    public String province;
    public String city;
    public String bottleName;
    public String headimage;
    public String CreatedDate;
    public String textData;
    public String imageData;
    public String title;
    public String voiceNumber;
   // 0，纯文本 1，纯图片 2，纯声音 3，文本+图片
    public String dataType;
    public String orderType;
    public   boolean bIsRead;
}
