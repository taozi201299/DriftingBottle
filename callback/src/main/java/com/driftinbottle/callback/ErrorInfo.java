package com.driftinbottle.callback;

/**
 * Created by Administrator on 2018/5/14.
 */

public class ErrorInfo{

    public static enum ErrorCode {
        UNKNOWN(-1, "unknown"),
        NETWORKERROR(-2,"网络错误"),
        LOGINERROR(-3,"用户名或密码错误"),
        XMLERROR(-4,"xml解析失败"),
        DATAERROR(-5,"获取数据失败"),
        PARAERROR(-6,"参数错误"),
        NODATA(-7,"没有相关任务"),
        URLError(-8,"请求非法url");

        private int code;
        private String msg;

        private ErrorCode(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getValue() {
            return this.code;
        }

        public String getMessage() {
            return this.msg;
        }

        public static ErrorCode valueOf(int code) {
            ErrorCode[] arry = values();
            int len = arry.length;

            for (int i = 0; i < len; ++i) {
                ErrorCode c = arry[i];
                if (code == c.getValue()) {
                    return c;
                }
            }
            return UNKNOWN;
        }
    }
}
