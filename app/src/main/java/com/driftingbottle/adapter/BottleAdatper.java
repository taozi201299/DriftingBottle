package com.driftingbottle.adapter;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.driftinbottle.httputils.HttpUtils;
import com.driftingbottle.App;
import com.driftingbottle.R;
import com.driftingbottle.bean.BottleBean;
import com.driftingbottle.bean.BottleBean0;
import com.driftingbottle.utils.EmojiUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import io.github.rockerhieu.emojicon.EmojiconTextView;


public class BottleAdatper extends CommonAdapter<BottleBean0> {

    private Context mContext;
    private final String strSplit = "#";
    public BottleAdatper(Context context, int layoutId) {
        super(context, layoutId);
        this.mContext = context;
    }
    @Override
    public void convert(ViewHolder holder, BottleBean0 bottleBean) {
        ImageView iv_activity_bottle_item_img = (ImageView)holder.getView(R.id.iv_activity_bottle_item_img);
        TextView tv_activity_bottle_item_area = (TextView) holder.getView(R.id.tv_activity_bottle_item_area);
        EmojiconTextView tv_activity_bottle_item_final_msg = (EmojiconTextView) holder.getView(R.id.tv_activity_bottle_item_final_msg);
        TextView tv_activity_bottle_item_time = (TextView) holder.getView(R.id.tv_activity_bottle_item_time);
        TextView wei_du_xiao_xi = (TextView)holder.getView(R.id.wei_du_xiao_xi);
        if(bottleBean.bIsRead){
            wei_du_xiao_xi.setVisibility(View.GONE);
        }else {
            wei_du_xiao_xi.setVisibility(View.VISIBLE);
        }
        Glide.with(mContext).load(App.strIp +bottleBean.headimage)
                .placeholder(R.mipmap.dialog_loading_img)
                .error(R.drawable.zl)
                .dontAnimate()
                .centerCrop()
                .into(iv_activity_bottle_item_img);

        tv_activity_bottle_item_area.setText(bottleBean.city.toString());
        String type = "0";
        String time = bottleBean.CreatedDate;

        SimpleDateFormat sdf= new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
        Date utilDate = null;
        try {
            utilDate = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf1= new SimpleDateFormat( "HH:mm");
        String displayTime = sdf1.format(utilDate);
        tv_activity_bottle_item_time.setText(displayTime);
        // 0，纯文本 1，纯图片 2，纯声音 3，文本+图片
        type = bottleBean.dataType;
        switch (type) {
            case "0":
                String content = "";
                if(bottleBean.title != null && !bottleBean.title.isEmpty()){
                    content = bottleBean.title;
                }else {
                    content = bottleBean.textData;
                }
                if(content.contains(strSplit)){
                    String[] arrayContent = content.split(strSplit);
                    if(arrayContent.length >0){
                        content = arrayContent[arrayContent.length -1];
                    }
                }
                SpannableStringBuilder builder = EmojiUtil.replaceStr2Emoji(content,mContext,tv_activity_bottle_item_final_msg.getTextSize(),
                        tv_activity_bottle_item_final_msg.getmEmojiconSize());
                tv_activity_bottle_item_final_msg.setText(builder);
                break;
            case "1":
                if(bottleBean.imageData.contains("gif")){
                    tv_activity_bottle_item_final_msg.setText("[动画表情]");
                }else {
                    tv_activity_bottle_item_final_msg.setText("[图片]");
                }
                break;
            case "3":
                if(bottleBean.orderType.equals("0")){
                    String content1 = "";
                    if(bottleBean.title != null && !bottleBean.title.isEmpty()){
                        content1 = bottleBean.title;
                    }else {
                        content1 = bottleBean.textData;
                    }
                    SpannableStringBuilder builder1 = EmojiUtil.replaceStr2Emoji(content1,mContext,tv_activity_bottle_item_final_msg.getTextSize(),
                            tv_activity_bottle_item_final_msg.getmEmojiconSize());
                    tv_activity_bottle_item_final_msg.setText(builder1);
                }else{
                    if(bottleBean.imageData.contains("gif")){
                        tv_activity_bottle_item_final_msg.setText("[动画表情]");
                    }else {
                        tv_activity_bottle_item_final_msg.setText("[图片]");
                    }
                }

                break;
            case "2":
                tv_activity_bottle_item_final_msg.setText("[语音通话]");
                break;
        }

    }

}
