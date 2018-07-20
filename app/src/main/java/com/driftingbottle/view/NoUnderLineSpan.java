package com.driftingbottle.view;

import android.annotation.SuppressLint;
import android.text.TextPaint;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;

/**
 * Created by Administrator on 2018/7/14.
 */
public class NoUnderLineSpan extends UnderlineSpan {
    @Override
    public void updateDrawState(TextPaint ds) {
        if (ds != null) {
            ds.setUnderlineText(false);
        }
    }

}
