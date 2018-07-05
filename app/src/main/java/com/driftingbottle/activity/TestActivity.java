package com.driftingbottle.activity;

import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.driftingbottle.R;
import com.driftingbottle.base.BaseActivity;
import com.driftingbottle.utils.EmojiUtil;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import io.github.rockerhieu.emojicon.EmojiconEditText;

/**
 * Created by jidan on 18-6-22.
 */

public class TestActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.et_msg_tle)
    EditText et_msg_tle;
    @BindView(R.id.et_msg_tle1)
    EditText et_msg_tle1;
    @BindView(R.id.btn_test)
    Button btn_test;
    @BindView(R.id.et_msg_tle2)
    EmojiconEditText et_msg_tle2;
    HashMap<String,Integer>emojiMap = new HashMap(){
        {
            put("[微笑]",R.mipmap.daiyue);
        }
    };
    @Override
    public int getLayoutId() {
        return R.layout.emoji_layout;
    }

    @Override
    public void initListener() {
        btn_test = (Button)findViewById(R.id.btn_test);
        btn_test.setOnClickListener(this);

    }

    @Override
    public void initData() {
        StringBuffer b=new StringBuffer();
        String time = "12312312312\\uD83C\\uDF89\\uD83C\\uDF811312312377";
        String test = decode(time);
        String [] array = time.split("\\\\u");
        Matcher m = Pattern.compile("\\\\u([0-9a-fA-F]{4})").matcher(time);


        while(m.find()) {
            b.append((char) Integer.parseInt(m.group(1), 16));
        }
        Log.d("11111111",b.toString());
        String ll = "12312312312\uD83C\uDF89�1312312377�";
    }
    protected static String decode(String theString){
        char aChar;
        int len = theString.length();
        StringBuffer b=new StringBuffer();

        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                StringBuffer temp = new StringBuffer();
                temp.append(aChar);
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    temp.append(aChar);
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        temp.append(aChar);
                        Matcher m = Pattern.compile("\\\\u([0-9a-fA-F]{4})").matcher(temp);
                        while(m.find()) {
                            b.append((char) Integer.parseInt(m.group(1), 16));
                        }

                    }
                }else {
                    b.append(temp);
                }
            }else {
                 b.append(aChar);
            }
        }
        return b.toString();
    }
    public static String decodeUnicode(String theString) {
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

    @Override
    public void initView() {
        setInitActionBar(false);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_test:
                String text = et_msg_tle.getText().toString();
                SpannableStringBuilder builder = new SpannableStringBuilder(
                        text);
                //需要替换的字符
                String rexgString = "[微笑]";
                Pattern pattern = Pattern.compile(rexgString);
                Matcher matcher = pattern.matcher(text);
                while (matcher.find()) {
                    //找到指定的字符后 setSpan的参数分别为（指定的图片，字符的开始位置，字符的结束位置）
                    builder.setSpan(new ImageSpan(TestActivity.this,
                                    R.drawable.emoji_1f0078), matcher.start(), matcher.end(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                et_msg_tle1.setText(builder);
                break;
        }
    }

    private class MyTextWatcher implements TextWatcher
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
        @Override public void afterTextChanged(Editable s){
            String txt = EmojiUtil.getEmoji(mContext,s.toString());
            et_msg_tle.setText(txt);

        }

    }

}
