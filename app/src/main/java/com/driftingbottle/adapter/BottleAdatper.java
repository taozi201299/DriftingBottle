package com.driftingbottle.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.driftingbottle.R;
import com.driftingbottle.bean.BottleBean;

import io.github.rockerhieu.emojicon.EmojiconTextView;


public class BottleAdatper extends CommonAdapter<BottleBean> {

    private Context mContext;
    public BottleAdatper(Context context, int layoutId) {
        super(context, layoutId);
        this.mContext = context;
    }
    @Override
    public void convert(ViewHolder holder, BottleBean bottleBean) {
        ImageView iv_activity_bottle_item_img = (ImageView)holder.getView(R.id.iv_activity_bottle_item_img);
        TextView tv_activity_bottle_item_area = (TextView) holder.getView(R.id.tv_activity_bottle_item_area);
        TextView tv_activity_bottle_item_final_msg = (TextView) holder.getView(R.id.tv_activity_bottle_item_final_msg);
        TextView tv_activity_bottle_item_time = (TextView) holder.getView(R.id.tv_activity_bottle_item_time);
        TextView wei_du_xiao_xi = (TextView)holder.getView(R.id.wei_du_xiao_xi);
        if(bottleBean.isbIsRead()){
            wei_du_xiao_xi.setVisibility(View.GONE);
        }else {
            wei_du_xiao_xi.setVisibility(View.VISIBLE);
        }
//        Glide.with(mContext).load(Constant.URL +
//              bottle.getBottle_img().toString())
//                .into(iv_activity_bottle_item_img);
        iv_activity_bottle_item_img.setImageResource(R.mipmap.daiyue);
        tv_activity_bottle_item_area.setText(bottleBean.getBottleName().toString());
        String type = "0";
        tv_activity_bottle_item_time.setText(bottleBean.getMessageDisplayTime());
        type = bottleBean.getMessageType();
        switch (type) {
            case "0":
                tv_activity_bottle_item_final_msg.setText(bottleBean.getMessageContent());
                break;
            case "1":
                tv_activity_bottle_item_final_msg.setText("[图片]");
                break;
            case "2":
                tv_activity_bottle_item_final_msg.setText("[动画表情]");
                break;
        }

    }
}
