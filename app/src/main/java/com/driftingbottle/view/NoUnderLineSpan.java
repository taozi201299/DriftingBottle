package com.driftingbottle.view;

import android.annotation.SuppressLint;
import android.text.TextPaint;
import android.text.style.URLSpan;

/**
 * Created by Administrator on 2018/7/14.
 */

@SuppressLint("ParcelCreator")
public class NoUnderLineSpan extends URLSpan {
    public NoUnderLineSpan(String url) {
        super(url);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        if (ds != null) {
            ds.setColor(ds.linkColor);
            ds.setUnderlineText(false);
        }
    }

}
