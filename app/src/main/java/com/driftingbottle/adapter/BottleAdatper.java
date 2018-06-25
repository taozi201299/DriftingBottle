package com.driftingbottle.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.driftingbottle.R;
import com.driftingbottle.bean.BottleBean;
import com.driftingbottle.bean.BottleBean0;

import io.github.rockerhieu.emojicon.EmojiconTextView;


public class BottleAdatper extends CommonAdapter<BottleBean0> {

    private Context mContext;
    public BottleAdatper(Context context, int layoutId) {
        super(context, layoutId);
        this.mContext = context;
    }
    @Override
    public void convert(ViewHolder holder, BottleBean0 bottleBean) {
        ImageView iv_activity_bottle_item_img = (ImageView)holder.getView(R.id.iv_activity_bottle_item_img);
        TextView tv_activity_bottle_item_area = (TextView) holder.getView(R.id.tv_activity_bottle_item_area);
        TextView tv_activity_bottle_item_final_msg = (TextView) holder.getView(R.id.tv_activity_bottle_item_final_msg);
        TextView tv_activity_bottle_item_time = (TextView) holder.getView(R.id.tv_activity_bottle_item_time);
        TextView wei_du_xiao_xi = (TextView)holder.getView(R.id.wei_du_xiao_xi);
        if(bottleBean.bIsRead){
            wei_du_xiao_xi.setVisibility(View.GONE);
        }else {
            wei_du_xiao_xi.setVisibility(View.VISIBLE);
        }
//        Glide.with(mContext).load(Constant.URL +
//              bottle.getBottle_img().toString())
//                .into(iv_activity_bottle_item_img);
        iv_activity_bottle_item_img.setImageResource(R.mipmap.daiyue);
        tv_activity_bottle_item_area.setText(bottleBean.bottleName.toString());
        String type = "0";
        tv_activity_bottle_item_time.setText(bottleBean.creatdDate);
        // 0，纯文本 1，纯图片 2，纯声音 3，文本+图片
        type = bottleBean.dataType;
        switch (type) {
            case "0":
                tv_activity_bottle_item_final_msg.setText(bottleBean.title);
                break;
            case "1":
            case "3":
                if(bottleBean.imageData.contains("gif")){
                    tv_activity_bottle_item_final_msg.setText("[动画表情]");
                }else {
                    tv_activity_bottle_item_final_msg.setText("[图片]");
                }
                break;
            case "2":
                tv_activity_bottle_item_final_msg.setText("[语音通话]");
                break;
        }

    }
}
