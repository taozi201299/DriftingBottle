package com.driftingbottle.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.TextView;

import com.driftingbottle.App;
import com.driftingbottle.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;


public class CommonUtils {
    private static final String TAG = CommonUtils.class.getName();

    public static int getHour() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        return hour;
    }

    public static String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    public static Date stringToDate(String strTime)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-d");
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    //字符串转指定格式时间
    public static String getMyDate(String str) {
        return StringToDate(str, "yyyy-MM-dd", "yyyy-M-d");
    }

    //字符串转指定格式时间
    public static String getToDate(String str) {
        return StringToDate(str, "yyyy-M-d", "yyyy-MM-dd");
    }

    /**
     * 根据对应日期格式转成相应格式
     *
     * @param dateStr
     * @param dateFormatStr
     * @param formatStr
     * @return
     */
    public static String StringToDate(String dateStr, String dateFormatStr, String formatStr) {
        DateFormat sdf = new SimpleDateFormat(dateFormatStr);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat s = new SimpleDateFormat(formatStr);

        return s.format(date);
    }

    /**
     * unicode 转换成 utf-8
     *
     * @param theString
     * @return
     * @author fanhui
     * 2007-3-15
     */
    public static String unicodeToUtf8(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }

    public static String getFileName(String fileName) {

        if (TextUtils.isEmpty(fileName)) {
            return "";
        } else {
            String[] file = fileName.split("/");
            return file[file.length - 1];
        }
    }

    /**
     * 时间格式化
     *
     * @param time
     * @return
     */

    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }

    /**
     * 检测是否有emoji表情
     *
     * @param source 原数据
     * @return true 有表情符 false 没有表情符
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return true 表情符 false 非表情符
     */
    static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
                (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000)
                && (codePoint <= 0x10FFFF));
    }

    /**
     * 获取手机IMEI
     *
     * @param context
     * @return
     */
    public static final String getIMEI(Context context) {
        try {
            //实例化TelephonyManager对象
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //获取IMEI号
            String imei = telephonyManager.getSimSerialNumber();
            //在次做个验证，也不是什么时候都能获取到的啊
            if (imei == null) {
                imei = "";
            }
            return imei;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * 获取手机IMSI
     */
    public static String getIMSI(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //获取IMSI号
            String imsi = telephonyManager.getSubscriberId();
            if (null == imsi) {
                imsi = "";
            }
            return imsi;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static int getIntInterval(String afterTime, String lastTime) {
        int m = 0;
        if (afterTime.length() != 19) {
            //return 0;
        }
        try {
            SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sd2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ParsePosition pos = new ParsePosition(0);
            ParsePosition pos1 = new ParsePosition(0);
            Date d1 = (Date) sd1.parse(afterTime, pos);
            Date last = (Date) sd2.parse(lastTime, pos1);
            // 用现在距离1970年的时间间隔new

            // Date().getTime()减去以前的时间距离1970年的时间间隔d1.getTime()得出的就是以前的时间与现在时间的时间间隔
            long time = d1.getTime() - last.getTime();// 得出的时间间隔是毫秒
          //  m = (int) ((time % 3600000) / 60000);// 得出的时间间隔的单位是分钟
            m = (int) (time / 60000);
        } catch (Exception e) {
            return 0;
        }
        return m;

    }

    public static String getInterval(String inputTime, String lastTime) {

        if (inputTime.length() != 19) {

            return inputTime;
        }
        String result = null;

        try {

            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            ParsePosition pos = new ParsePosition(0);

            Date d1 = (Date) sd.parse(inputTime, pos);
            Date last = (Date) sd.parse(lastTime, pos);

            // 用现在距离1970年的时间间隔new

            // Date().getTime()减去以前的时间距离1970年的时间间隔d1.getTime()得出的就是以前的时间与现在时间的时间间隔

            long time = d1.getTime() - last.getTime();// 得出的时间间隔是毫秒

            if (time / 1000 <= 0) {

                // 如果时间间隔小于等于0秒则显示“刚刚”time/10得出的时间间隔的单位是秒

                result = "刚刚";

            } else if (time / 1000 < 60) {

                // 如果时间间隔小于60秒则显示多少秒前

                int se = (int) ((time % 60000) / 1000);

                result = se + "秒前";

            } else if (time / 60000 < 60) {

                // 如果时间间隔小于60分钟则显示多少分钟前

                int m = (int) ((time % 3600000) / 60000);// 得出的时间间隔的单位是分钟

                result = m + "分钟前";

            } else if (time / 3600000 < 24) {

                // 如果时间间隔小于24小时则显示多少小时前

                int h = (int) (time / 3600000);// 得出的时间间隔的单位是小时

                result = h + "小时前";

            } else if (time / 86400000 < 2) {

                // 如果时间间隔小于2天则显示昨天

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

                result = sdf.format(d1.getTime());

                result = "昨天" +
                        result;

            } else if (time / 86400000 < 3) {

                // 如果时间间隔小于3天则显示前天

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

                result = sdf.format(d1.getTime());

                result = "前天" +
                        result;

            } else if (time / 86400000 < 30) {

                // 如果时间间隔小于30天则显示多少天前

                SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm");

                result = sdf.format(d1.getTime());

            } else if (time / 2592000000l < 12) {

                // 如果时间间隔小于12个月则显示多少月前

                SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");

                result = sdf.format(d1.getTime());


            } else {

//                // 大于1年，显示年月日时间

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");

                result = sdf.format(d1.getTime());

            }

        } catch (Exception e) {

            return inputTime;

        }

        return result;

    }

    public static String getUniqueId(Context context) {
        String androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String id = androidID + Build.SERIAL;
        return id;
    }
}
