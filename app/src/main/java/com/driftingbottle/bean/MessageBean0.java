package com.driftingbottle.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/6/25.
 */

public class MessageBean0 implements Serializable{
    /**
     * 信息类型 0 第一条信息 1 群发信息  2 系统二次回复信息
     */
    public  String answerType;
    public String CreatedDate;
    /**
     * 文本信息数据
     */
    public String textData;
    /**
     * 图片信息数据
     */
    public String imageData;
    /**
     * 语音时长
     */
    public String voiceNumber;
    //0，纯文本 1，纯图片 2，纯声音 3，文本+图片
    public String dataType;
    /**
     * 图片+文字 的先后顺序
     */
    public String orderType;
}
