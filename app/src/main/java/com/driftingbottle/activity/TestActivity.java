package com.driftingbottle.activity;

import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
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
