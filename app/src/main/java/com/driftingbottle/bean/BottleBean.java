package com.driftingbottle.bean;

import java.io.Serializable;

public class BottleBean extends BaseBean<BottleBean> {

    private  String bottleId;
    private  String bottleImg;
    private  String bottleName;
    private  String messageContent;
    private  String messageType;
    private  String messageDisplayTime;
    private  boolean bIsRead;

    public void setbIsRead(boolean bIsRead) {
        this.bIsRead = bIsRead;
    }

    public boolean isbIsRead() {
        return bIsRead;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageDisplayTime() {
        return messageDisplayTime;
    }

    public void setMessageDisplayTime(String messageDisplayTime) {
        this.messageDisplayTime = messageDisplayTime;
    }

    public String getBottleId() {
        return bottleId;
    }

    public void setBottleId(String bottleId) {
        this.bottleId = bottleId;
    }

    public String getBottleImg() {
        return bottleImg;
    }

    public void setBottleImg(String bottleImg) {
        this.bottleImg = bottleImg;
    }

    public String getBottleName() {
        return bottleName;
    }

    public void setBottleName(String bottleName) {
        this.bottleName = bottleName;
    }
}
