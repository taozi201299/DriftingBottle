package com.driftingbottle.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.util.Log;

import com.driftingbottle.R;

import java.lang.reflect.Field;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/6/18.
 */

public class EmojiUtil {
    /**
     * 显示不可见字符的Unicode
     *
     * @param input
     * @return
     */
    public static String escapeUnicode(String input) {
        StringBuilder sb = new StringBuilder(input.length());
        @SuppressWarnings("resource")
        Formatter format = new Formatter(sb);
        for (char c : input.toCharArray()) {
            if (c < 128) {
                sb.append(c);
            } else {
                format.format("\\u%04x", (int) c);
            }
        }
        return sb.toString();
    }

    /**
     * 将emoji替换为unicode
     *
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {
        if (source != null) {
            Pattern emoji = Pattern.compile("[\ue000-\uefff]", Pattern.CASE_INSENSITIVE);
            Matcher emojiMatcher = emoji.matcher(source);
            Map<String, String> tmpMap = new HashMap<>();
            while (emojiMatcher.find()) {
                // System.out.println(escapeUnicode(emojiMatcher.group()));
                // System.out.println(emojiMatcher.start());
                String key = emojiMatcher.group();
                String value = escapeUnicode(emojiMatcher.group());
                //System.out.println("key：" + key);
                //System.out.println("value：" + value);
                tmpMap.put(key, value);
                // source =
                // emojiMatcher.replaceAll(escapeUnicode(emojiMatcher.group()));
            }
            if (!tmpMap.isEmpty()) {
                for (Map.Entry<String, String> entry : tmpMap.entrySet()) {
                    String key = entry.getKey().toString();
                    String value = entry.getValue().toString();
                    source = source.replace(key, value);
                }
            }
        }
        return source;
    }
    /**
     * 将系统表情转化为字符串
     *
     * @param s
     * @return
     */
    public static String getString(String s) {
        int length = s.length();
        String context = "";
        //循环遍历字符串，将字符串拆分为一个一个字符
        for (int i = 0; i < length; i++) {
            char codePoint = s.charAt(i);
            //判断字符是否是emoji表情的字符
            if (isEmojiCharacter(codePoint)) {
                //如果是将以大括号括起来
                String emoji = "{" + Integer.toHexString(codePoint) + "}";
                context = context + emoji;
                continue;
            }
            context = context + codePoint;
        }
        return context;
    }

    /**
     * 是否包含表情
     *
     * @param codePoint
     * @return 如果不包含 返回false,包含 则返回true
     */

    private static boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));
    }
    /**
     * 将表情描述转换成表情
     *
     * @param str
     * @return
     */
    public static String getEmoji(Context context, String str) {
        String string = str;
        String rep = "\\{(.*?)\\}";
        Pattern p = Pattern.compile(rep);
        Matcher m = p.matcher(string);
        while (m.find()) {
            String s1 = m.group().toString();
            String s2 = s1.substring(1, s1.length() - 1);
            String s3;
            try {
                s3 = String.valueOf((char) Integer.parseInt(s2, 16));
                string = string.replace(s1, s3);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return string;
    }
    public static String substring(String source, int start, int end) {
        String result;
        try {
            result = source.substring(source.offsetByCodePoints(0, start),
                    source.offsetByCodePoints(0, end));
        } catch (Exception e) {
            result = "";
        }
        return result;
    }

        public static void dealExpression(Context context,int resId,SpannableString spannableString, Pattern patten, int start, int textSize) throws SecurityException, NoSuchFieldException, NumberFormatException, IllegalArgumentException, IllegalAccessException {
            Matcher matcher = patten.matcher(spannableString);
            while (matcher.find()) {
                String key = matcher.group();
                if (matcher.start() < start) {
                    continue;
                }

                if (resId != 0) {
                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
// 设置想要的大小
                    int newWidth = 500;
                    int newHeight = 400;
// 计算缩放比例
                    float scaleWidth = ((float) newWidth) / width;
                    float scaleHeight = ((float) newHeight) / height;
// 取得想要缩放的matrix参数
                    Matrix matrix = new Matrix();
                    matrix.postScale(scaleWidth, scaleHeight);
// 得到新的图片
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
                   // bitmap = Bitmap.createScaledBitmap(bitmap, Math.round(textSize*2), Math.round(textSize*2), true);
                    ImageSpan imageSpan = new ImageSpan(bitmap, DynamicDrawableSpan.ALIGN_BOTTOM); //通过图片资源id来得到bitmap，用一个ImageSpan来包装
                    int end = matcher.start() + key.length(); //计算该图片名字的长度，也就是要替换的字符串的长度
                    spannableString.setSpan(imageSpan, matcher.start(), end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE); //将该图片替换字符串中规定的位置中
                    if (end < spannableString.length()) { //如果整个字符串还未验证完，则继续。。
                        dealExpression(context,resId,spannableString, patten, end, textSize);
                    }
                    break;
                }
            }
        }

        /**
         * 得到一个SpanableString对象，通过传入的字符串,并进行正则判断
         * @param context
         * @param str
         * @return
         */
        public static SpannableString getExpressionString(Context context, int resId,String str, String zhengze, int textSize){
            SpannableString spannableString = new SpannableString(str);
            Pattern sinaPatten = Pattern.compile(zhengze, Pattern.CASE_INSENSITIVE); //通过传入的正则表达式来生成一个pattern
            try {
                dealExpression(context,resId,spannableString, sinaPatten, 0, textSize+5);
            } catch (Exception e) {
                Log.e("dealExpression exception", e.getMessage());
            }
            return spannableString;
        }
}