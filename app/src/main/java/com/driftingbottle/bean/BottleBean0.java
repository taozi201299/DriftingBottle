package com.driftingbottle.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/6/25.
 */

public class BottleBean0 implements Serializable {
    /**
     * ID
     */
    public String regionID;
    public String province;
    /**
     * 瓶子名称
     */
    public String city;
    public String bottleName;
    /**
     * 瓶子显示头像
     */
    public String headimage;
    /**
     * 瓶子显示时间
     */
    public String CreatedDate;
    /**
     * 文本类型数据
     */
    public String textData;
    /**
     * 图片类型数据
     */
    public String imageData;
    /**
     * 最后一条信息显示
     */
    public String title;
    /**
     * 语音数据市场
     */
    public String voiceNumber;
   // 0，纯文本 1，纯图片 2，纯声音 3，文本+图片
    public String dataType;
    /**
     * 图片+文字 的先后顺序
     */
    public String orderType;
    /**
     * 消息是否已读
     */
    public   boolean bIsRead;
    /**
     * 信息类型 0 第一条信息 1 群发信息  2 系统二次回复信息
     */
    public String answerType;
}
