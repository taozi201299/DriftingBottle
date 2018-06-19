package com.driftingbottle.bean;

/**
 * Created by Administrator on 2018/6/15.
 */

public class MessageBean extends BaseBean<MessageBean> {
    public static int TYPE_LEFT  = 0;
    public static int TYPE_RIGHT = 1;

    private  String messageId;
    private  String messageContent;
    private  String messageType;
    private  String messageDisplayTime;
    private  int messageOwner;


    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
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

    public int getMessageOwner() {
        return messageOwner;
    }

    public void setMessageOwner(int messageOwner) {
        this.messageOwner = messageOwner;
    }
}
