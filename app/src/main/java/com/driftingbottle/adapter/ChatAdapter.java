package com.driftingbottle.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.driftingbottle.App;
import com.driftingbottle.R;
import com.driftingbottle.bean.MessageBean;
import com.driftingbottle.bean.MessageBean0;
import com.driftingbottle.utils.CommonUtils;
import com.driftingbottle.utils.EmojiUtil;
import com.driftingbottle.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.rockerhieu.emojicon.EmojiconTextView;



public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<MessageBean0> mDatas = new ArrayList<>();
    private String urlImage;
    private String  lastLMin ="";
    boolean bFinish = false;
    boolean bLeftEmoji = true;

    public ChatAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }


    public  void setMyImage(String image){
        urlImage = image;
    }
    public void setData(ArrayList<MessageBean0>mDatas){
        this.mDatas= mDatas;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MessageBean.TYPE_LEFT) {
            View view = mLayoutInflater.inflate(R.layout.activity_lchat_layout, parent, false);
            return new ChatLeftViewHolder(view);
        } else {
            View view = mLayoutInflater.inflate(R.layout.activity_rchat_layout, parent, false);
            return new ChatRightViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        MessageBean0 msg = mDatas.get(position);
        String time = msg.CreatedDate;
        String content = "" ;
        String type = msg.dataType;
        if(type.equals("0")){
            content = msg.textData;
        }else if(type.equals("1")){
            content = App.strIp +msg.imageData;
        }else if(type.equals("2")){
            content = msg.voiceNumber;
        }
        String imgUrl = (String) SPUtils.get("user_head","");

        if(holder instanceof ChatLeftViewHolder){
            bLeftEmoji = true;
            if(!lastLMin.isEmpty()){
                int interval = CommonUtils.getIntInterval(time,lastLMin);
                if(interval < 3){
                    ((ChatLeftViewHolder) holder).mTvLeftTime.setVisibility(View.GONE);
                }else {
                    ((ChatLeftViewHolder) holder).mTvLeftTime.setVisibility(View.VISIBLE);
                }
            }
             lastLMin  = time;
            ((ChatLeftViewHolder) holder).mTvLeftTime.setText(time);
            if(!urlImage.isEmpty())
                Glide.with(mContext).load(urlImage)
                        .error(R.drawable.zl).into(((ChatLeftViewHolder) holder).iv_owner_img);

            ((ChatLeftViewHolder) holder).mTvMsgLeft.setVisibility(View.GONE);
            ((ChatLeftViewHolder) holder).iv_left_img.setVisibility(View.GONE);

            if("0".equals(type)) {  // 文本
                 ((ChatLeftViewHolder) holder).mTvMsgLeft.setVisibility(View.VISIBLE);
                SpannableStringBuilder builder = EmojiUtil.replaceStr2Emoji(content,mContext,((ChatLeftViewHolder) holder).mTvMsgLeft.getTextSize(),
                        ((ChatLeftViewHolder) holder).mTvMsgLeft.getmEmojiconSize());
                ((ChatLeftViewHolder) holder).mTvMsgLeft.setText(builder);
            }else if("1".equals(type)){
                ((ChatLeftViewHolder) holder).iv_left_img.setVisibility(View.VISIBLE);
                Glide.with(mContext)
                        .load(content)
                        .placeholder(R.drawable.emoji_00a9)
                        .error(R.drawable.emoji_00a9)
                        .into(((ChatLeftViewHolder) holder).iv_left_img);
            }else if("2".equals(type)){
                ((ChatLeftViewHolder) holder).ll_audio_left.setVisibility(View.VISIBLE);
                ((ChatLeftViewHolder) holder).tv_audio_left_time.setText(msg.voiceNumber);
                int len = Integer.valueOf(msg.voiceNumber);
                for(int i = 0 ; i < len; i++){
                    ((ChatLeftViewHolder) holder).tv_audio_left.append(String.valueOf(i));
                }
            }
        }else if(holder instanceof ChatRightViewHolder){
            bLeftEmoji = false;
            if(!lastLMin.isEmpty()){
                int interval = CommonUtils.getIntInterval(time,lastLMin);
                if(interval < 3){
                    ((ChatRightViewHolder) holder).mTvRightTime.setVisibility(View.GONE);
                }else {
                    ((ChatRightViewHolder) holder).mTvRightTime.setVisibility(View.VISIBLE);
                }
            }
            ((ChatRightViewHolder) holder).iv_right_img.setVisibility(View.GONE);
            ((ChatRightViewHolder) holder).mTvMsgRight.setVisibility(View.GONE);
            ((ChatRightViewHolder) holder).mTvRightTime.setText(time);
            if(imgUrl != null && !imgUrl.isEmpty()) {
                Glide.with(mContext).load(imgUrl)
                        .error(R.drawable.zl)
                        .into(((ChatRightViewHolder) holder).iv_owner_img);
            }
            if("0".equals(type)) {  // 文本
                ((ChatRightViewHolder) holder).mTvMsgRight.setVisibility(View.VISIBLE);
                SpannableStringBuilder builder = EmojiUtil.replaceStr2Emoji(content,mContext,((ChatRightViewHolder) holder).mTvMsgRight.getTextSize(),
                        ((ChatRightViewHolder) holder).mTvMsgRight.getmEmojiconSize());
                ((ChatRightViewHolder) holder).mTvMsgRight.setText(builder);
            }else if("1".equals(type)){
                ((ChatRightViewHolder) holder).iv_right_img.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(content)
                        .placeholder(R.mipmap.dialog_loading_img)
                        .error(R.mipmap.dialog_loading_img)
                        .into(((ChatRightViewHolder) holder).iv_right_img);
            }
            else if("2".equals(type)){
                ((ChatRightViewHolder) holder).ll_audio_right.setVisibility(View.VISIBLE);
                ((ChatRightViewHolder) holder).tv_audio_right_time.setText(msg.voiceNumber);
                int len = Integer.valueOf(msg.voiceNumber);
                for(int i = 0 ; i < len; i++){
                    ((ChatRightViewHolder) holder).tv_audio_right.append(String.valueOf(i));
                }
            }

        }
    }

    @Override
    public int getItemViewType(int position) {
        return Integer.valueOf(mDatas.get(position).answerType);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    static class ChatLeftViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_left_time)
        TextView mTvLeftTime;
        @BindView(R.id.iv_left_img)
        ImageView iv_left_img;
        @BindView(R.id.iv_owner_img)
        ImageView iv_owner_img;
        @BindView(R.id.tv_left_msg)
        EmojiconTextView mTvMsgLeft;
        @BindView(R.id.ll_audio_left)
        LinearLayout ll_audio_left;
        @BindView(R.id.tv_audio_left)
        TextView tv_audio_left;
        @BindView(R.id.tv_audio_left_time)
        TextView tv_audio_left_time;

        ChatLeftViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ChatRightViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_right_time)
        TextView mTvRightTime;
        @BindView(R.id.iv_right_img)
        ImageView iv_right_img;
        @BindView(R.id.iv_owner_img)
        ImageView iv_owner_img;
        @BindView(R.id.tv_right_msg)
        EmojiconTextView mTvMsgRight;
        @BindView(R.id.ll_audio_right)
        LinearLayout ll_audio_right;
        @BindView(R.id.tv_audio_right)
        TextView tv_audio_right;
        @BindView(R.id.tv_audio_right_time)
        TextView tv_audio_right_time;

        ChatRightViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private class MyTextWatcher implements TextWatcher
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
        @Override public void afterTextChanged(Editable s){

        }
    }
}