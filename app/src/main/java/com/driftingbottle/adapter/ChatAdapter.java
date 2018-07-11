package com.driftingbottle.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.driftingbottle.App;
import com.driftingbottle.R;
import com.driftingbottle.bean.MessageBean;
import com.driftingbottle.bean.MessageBean0;
import com.driftingbottle.utils.CommonUtils;
import com.driftingbottle.utils.EmojiUtil;
import com.driftingbottle.utils.SPUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.rockerhieu.emojicon.EmojiconTextView;

import static android.widget.TextView.BufferType.SPANNABLE;


public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<MessageBean0> mDatas = new ArrayList<>();
    private String urlImage;
    private String  lastLMin ="1970-01-01 00:00:00";
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
    private String autoSplitText(final TextView tv, final String indent) {
        final String rawText = tv.getText().toString(); //原始文本
        final Paint tvPaint = tv.getPaint(); //paint，包含字体等信息
        final float tvWidth = tv.getWidth() - tv.getPaddingLeft() - tv.getPaddingRight(); //控件可用宽度

        //将缩进处理成空格
        String indentSpace = "";
        float indentWidth = 0;
        if (!TextUtils.isEmpty(indent)) {
            float rawIndentWidth = tvPaint.measureText(indent);
            if (rawIndentWidth < tvWidth) {
                while ((indentWidth = tvPaint.measureText(indentSpace)) < rawIndentWidth) {
                    indentSpace += " ";
                }
            }
        }

        //将原始文本按行拆分
        String [] rawTextLines = rawText.replaceAll("\r", "").split("\n");
        StringBuilder sbNewText = new StringBuilder();
        for (String rawTextLine : rawTextLines) {
            if (tvPaint.measureText(rawTextLine) <= tvWidth) {
                //如果整行宽度在控件可用宽度之内，就不处理了
                sbNewText.append(rawTextLine);
            } else {
                //如果整行宽度超过控件可用宽度，则按字符测量，在超过可用宽度的前一个字符处手动换行
                float lineWidth = 0;
                for (int cnt = 0; cnt != rawTextLine.length(); ++cnt) {
                    char ch = rawTextLine.charAt(cnt);
                    //从手动换行的第二行开始，加上悬挂缩进
                    if (lineWidth < 0.1f && cnt != 0) {
                        sbNewText.append(indentSpace);
                        lineWidth += indentWidth;
                    }
                    lineWidth += tvPaint.measureText(String.valueOf(ch));
                    if (lineWidth <= tvWidth) {
                        sbNewText.append(ch);
                    } else {
                        sbNewText.append("\n");
                        lineWidth = 0;
                        --cnt;
                    }
                }
            }
            sbNewText.append("\n");
        }

        //把结尾多余的\n去掉
        if (!rawText.endsWith("\n")) {
            sbNewText.deleteCharAt(sbNewText.length() - 1);
        }

        return sbNewText.toString();
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        MessageBean0 msg = mDatas.get(position);
        String time = msg.CreatedDate;
        String content = "" ;
        String type = msg.dataType;
        if(type.equals("0")){
            content = EmojiUtil.decodeUnicode(msg.textData);
        }else if(type.equals("1")){
            if(msg.answerType.equals("0")) {
                content = App.strIp + msg.imageData;
            }
            else if(msg.answerType.equals("1")){
                content = msg.imageData;
            }
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
            SimpleDateFormat sdf= new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
            Date utilDate = null;
            try {
                utilDate = sdf.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat sdf1= new SimpleDateFormat( "HH:mm");
            String displayTime = sdf1.format(utilDate);
            ((ChatLeftViewHolder) holder).mTvLeftTime.setText(displayTime);
            if(!urlImage.isEmpty())
                Glide.with(mContext).load(urlImage).priority(Priority.HIGH)
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
                        .placeholder(R.mipmap.dialog_loading_img)
                        .error(R.drawable.zl)
                        .into(((ChatLeftViewHolder) holder).iv_left_img);
            }else if("2".equals(type)){
                ((ChatLeftViewHolder) holder).ll_audio_left.setVisibility(View.VISIBLE);
                ((ChatLeftViewHolder) holder).tv_audio_left_time.setText(msg.voiceNumber  + "''");
                int len = Integer.valueOf(msg.voiceNumber);
                for(int i = 0 ; i < len; i++){
                    ((ChatLeftViewHolder) holder).tv_audio_left.append(String.valueOf(i));
                }
            }
        }else if(holder instanceof ChatRightViewHolder){
            bLeftEmoji = false;
            SimpleDateFormat sdf= new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
            Date utilDate = null;
            try {
                utilDate = sdf.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat sdf1= new SimpleDateFormat( "HH:mm");
            String displayTime = sdf1.format(utilDate);
            if(!lastLMin.isEmpty()){
                int interval = CommonUtils.getIntInterval(time,lastLMin);
                if(interval < 3){
                    ((ChatRightViewHolder) holder).mTvRightTime.setVisibility(View.GONE);
                }else {
                    ((ChatRightViewHolder) holder).mTvRightTime.setVisibility(View.VISIBLE);
                    ((ChatRightViewHolder) holder).mTvRightTime.setText(displayTime);
                }
            }
            lastLMin  = time;

            ((ChatRightViewHolder) holder).iv_right_img.setVisibility(View.GONE);
            ((ChatRightViewHolder) holder).mTvMsgRight.setVisibility(View.GONE);

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
                ((ChatRightViewHolder) holder).tv_audio_right_time.setText(msg.voiceNumber + "''");
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
        RelativeLayout ll_audio_right;
        @BindView(R.id.tv_audio_right)
        TextView tv_audio_right;
        @BindView(R.id.tv_audio_right_time)
        TextView tv_audio_right_time;

        ChatRightViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}