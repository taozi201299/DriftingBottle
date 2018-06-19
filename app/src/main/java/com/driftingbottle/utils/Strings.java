/**
 * Copyright 2017 Alex Yanchenko
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.driftingbottle.utils;

import android.annotation.SuppressLint;
import android.webkit.URLUtil;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Strings {

    private static final String TAG = "Utils_Strings";

    public static final String UTF8 = "utf-8";
    public static final String SHA1 = "SHA-1";
    public static final String MD5 = "MD5";
    public static final String PATTERN_PHONE_NUMBER = "^((\\+?86)|(\\(\\+?86\\)))?1[0-9]{10}$";

    public static final String DEFAULT_BUNDLE_NAME = "bundle";

    public static boolean isNotEmpty(CharSequence str) {
        return !isEmpty(str);
    }

    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    public static <T> String join(Collection<T> coll, String separator) {
        return join(coll, separator, null);
    }

    public static String join(Object[] arr, String separator) {
        return join(arr, separator, null);
    }

    public static <T> String join(Collection<T> coll, String separator, String terminator) {
        return join(coll.toArray(new Object[coll.size()]), separator, terminator);
    }

    public static String join(Object[] arr, String separator, String terminator) {
        StringBuilder sb = new StringBuilder(arr.length * 2);
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) {
                sb.append(separator);
            } else if (terminator != null && arr.length > 0) {
                sb.append(terminator);
            }
        }
        return sb.toString();
    }

    public static String urlEncode(String str) {
        try {
            return URLEncoder.encode(str, UTF8);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("failed to encode", e);
        }
    }

    public static String urlDecode(String str) {
        try {
            return URLDecoder.decode(str, UTF8);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("failed to decode", e);
        }
    }

    public static String getMD5(String str) {
        try {
            return getHash(str, MD5, 32);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getSHA1(String str) {
        try {
            return getHash(str, SHA1, 40);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getHash(String str, String algorithm, int length)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] bytes = str.getBytes(UTF8);
        MessageDigest md = MessageDigest.getInstance(algorithm);
        byte[] digest = md.digest(bytes);
        BigInteger bigInt = new BigInteger(1, digest);
        String hash = bigInt.toString(16);
        while (hash.length() < length) {
            hash = "0" + hash;
        }
        return hash;
    }

    public static boolean isValidUrl(String str) {
        return URLUtil.isValidUrl(str);
    }

    /**
     * 正则表达式匹配判断
     * @param patternStr 匹配规则
     * @param input 需要做匹配操作的字符串
     * @return true if matched, else false
     */
    public static boolean isMatched(String patternStr, CharSequence input) {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public static boolean isValidPhoneNum(CharSequence phoneNum) {
        return isMatched(PATTERN_PHONE_NUMBER, phoneNum);
    }

    /**
     * 日期和时间格式化 yyyy-MM-dd HH:mm:ss
     *
     * @param time
     * @return
     */
    public static String formatDatetime(long time) {
        return formatDatetime(new Date(time));
    }

    /**
     * 日期和时间格式化 yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String formatDatetime(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
                .format(date);
    }

    /**
     * 日期格式化 yyyy-MM-dd
     *
     * @param time
     * @return
     */
    public static String formatDate(long time) {
        return formatDate(new Date(time));
    }

    /**
     * 日期格式化 yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
                .format(date);
    }

    /**
     * 年月格式化 yyyy-MM
     *
     * @param time
     * @return
     */
    public static String formatYearMonth(long time) {
        return formatYearMonth(new Date(time));
    }

    /**
     * 年月格式化 yyyy-MM
     *
     * @param date
     * @return
     */
    public static String formatYearMonth(Date date) {
        return new SimpleDateFormat("yyyy", Locale.CHINA)
                .format(date);
    }

    /**
     * 日期和时间格式化 yyyy-MM-dd HH:mm
     *
     * @param time
     * @return
     */
    public static String formatDatetimeHour(long time) {
        return formatDatetimeHour(new Date(time));
    }

    /**
     * 日期和时间格式化 yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String formatDatetimeHour(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA)
                .format(date);
    }
}
