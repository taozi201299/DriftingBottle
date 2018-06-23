package com.driftingbottle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.driftingbottle.activity.DriftinBottleListActivity;
import com.driftingbottle.activity.DriftinBottleMessageActivity;
import com.driftingbottle.activity.SettingActivity;
import com.driftingbottle.activity.TestActivity;
import com.driftingbottle.base.TranslucentActivity;
import com.driftingbottle.bean.MessageBean;
import com.driftingbottle.utils.CommonUtils;
import com.driftingbottle.utils.EmojiUtil;
import com.driftingbottle.utils.ToastUtils;
import com.driftingbottle.view.CustomImageSpan;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.rockerhieu.emojicon.EmojiconEditText;
import io.github.rockerhieu.emojicon.EmojiconSpan;
import io.github.rockerhieu.emojicon.EmojiconsFragment;
import io.github.rockerhieu.emojicon.emoji.Emojicon;

public class MainActivity extends TranslucentActivity implements View.OnClickListener,TranslucentActivity.IbtnClicked {


    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R.id.tv_activity_index_count)
    TextView tv_activity_index_count;
    @BindView(R.id.iv_action_bar2_left)
    ImageView iv_action_bar2_left;
    @BindView(R.id.tv_action_bar2_title)
    TextView tv_action_bar2_title;
    @BindView(R.id.iv_setting)
    ImageView iv_setting;
    @BindView(R.id.ll_title)
    LinearLayout ll_title;
    @BindView(R.id.rl_one)
    RelativeLayout rl_one;
    @BindView(R.id.iv_one)
    ImageView iv_one;
    @BindView(R.id.rl_two)
    RelativeLayout rl_two;
    @BindView(R.id.rl_activity_index_our_pingzi)
    RelativeLayout rl_three;
    @BindView(R.id.ll_dialog)
    LinearLayout ll_dialog;
    @BindView(R.id.tv_dialog_ok)
    Button tv_dialog_ok;
    @BindView(R.id.ll_baloon)
    LinearLayout ll_baloon;
    @BindView(R.id.iv_baloon)
    ImageView iv_baloon;
    @BindView(R.id.rl_msg)
    RelativeLayout rl_msg;
    @BindView(R.id.et_title)
    EmojiconEditText et_title;
    @BindView(R.id.et_content)
    EmojiconEditText et_content;
    @BindView(R.id.iv_img)
    ImageView iv_img;
    @BindView(R.id.btn_send)
    Button btn_send;
    private ImageView iv_shape;
    private ImageView iv_shape_center;
    private ImageView iv_shape_right;
    public static int iTotalCount = 0;
    private final int messageCount = 0;
    private int iCurrentHour = 0;
    public static boolean isForeground = false;
    private boolean bStart = false;

    private boolean bFinish = false;

    HashMap<String,Integer> emojisMap = new HashMap<String, Integer>(){
        {
            put("[微笑]", R.drawable.emoji_1f0078);
            put("[撇嘴]", R.drawable.emoji_1f0079);
            put("[色]", R.drawable.emoji_1f0080);
            put("[发呆]", R.drawable.emoji_1f0081);
            put("[得意]", R.drawable.emoji_1f0082);
            put("[流泪]", R.drawable.emoji_1f0083);
            put("[害羞]", R.drawable.emoji_1f0084);
//2
            put("[闭嘴]", R.drawable.emoji_1f0085);
            put("[睡]", R.drawable.emoji_1f0095);
            put("[大哭]", R.drawable.emoji_1f0096);
            put("[尴尬]", R.drawable.emoji_1f0097);
            put("[发怒]", R.drawable.emoji_1f0098);
            put("[调皮]", R.drawable.emoji_1f0099);
            put("[呲牙]", R.drawable.emoji_1f0088);

            //3
            put("[惊讶]", R.drawable.emoji_1f0087);
            put("[难过]", R.drawable.emoji_1f0086);
            put("[囧]", R.drawable.emoji_1f0094);
            put("[抓狂]", R.drawable.emoji_1f0093);
            put("[吐]", R.drawable.emoji_1f0092);
            put("[偷笑]", R.drawable.emoji_1f0091);

            //4
            put("[愉快]", R.drawable.emoji_1f0090);
            put("[白眼]", R.drawable.emoji_1f0089);
            put("[傲慢]", R.drawable.emoji_1f00100);
            put("[困]", R.drawable.emoji_1f00101);
            put("[惊恐]", R.drawable.emoji_1f00102);
            put("[流汗]", R.drawable.emoji_1f00103);
            put("[憨笑]", R.drawable.emoji_1f00104);

            //5
            put("[悠闲]", R.drawable.emoji_1f00105);
            put("[奋斗]", R.drawable.emoji_1f00106);
            put("[咒骂]", R.drawable.emoji_1f00107);
            put("[疑问]", R.drawable.emoji_1f00108);
            put("[嘘]", R.drawable.emoji_1f00116);
            put("[晕]", R.drawable.emoji_1f00117);
            put("[衰]", R.drawable.emoji_1f00118);
            //6


            put("[骷髅]", R.drawable.emoji_1f00119);
            put("[敲打]", R.drawable.emoji_1f00120);
            put("[再见]", R.drawable.emoji_1f00121);
            put("[擦汗]", R.drawable.emoji_1f00110);
            put("[抠鼻]", R.drawable.emoji_1f00109);
            put("[鼓掌]", R.drawable.emoji_1f00115);

            //7
            put("[坏笑]", R.drawable.emoji_1f00114);
            put("[左哼哼]", R.drawable.emoji_1f00113);
            put("[右哼哼]", R.drawable.emoji_1f00112);
            put("[哈欠]", R.drawable.emoji_1f00111);
            put("[鄙视]", R.drawable.emoji_1f00122);
            put("[哈欠]", R.drawable.emoji_1f00123);
            put("[快哭了]", R.drawable.emoji_1f00124);


            //8
            put("[阴险]", R.drawable.emoji_1f00125);
            put("[亲亲]", R.drawable.emoji_1f00126);
            put("[可怜]", R.drawable.emoji_1f00127);
            put("[菜刀]", R.drawable.emoji_1f00128);
            put("[西瓜]", R.drawable.emoji_1f00129);
            put("[啤酒]", R.drawable.emoji_1f00130);
            put("[咖啡]", R.drawable.emoji_1f00131);


            //9
            put("[猪头]", R.drawable.emoji_1f00137);
            put("[玫瑰]", R.drawable.emoji_1f00138);
            put("[凋谢]", R.drawable.emoji_1f00139);
            put("[嘴唇]", R.drawable.emoji_1f00140);
            put("[爱心]", R.drawable.emoji_1f00141);
            put("[心碎]", R.drawable.emoji_1f00142);

            //10

            put("[蛋糕]", R.drawable.emoji_1f00143);
            put("[炸弹]", R.drawable.emoji_1f00132);
            put("[便便]", R.drawable.emoji_1f00136);
            put("[月亮]", R.drawable.emoji_1f00135);
            put("[太阳]", R.drawable.emoji_1f00134);
            put("[拥抱]", R.drawable.emoji_1f00133);
            put("[强]", R.drawable.emoji_1f00144);

            //11
            put("[弱]", R.drawable.emoji_1f00145);
            put("[握手]", R.drawable.emoji_1f00146);
            put("[胜利]", R.drawable.emoji_1f00147);
            put("[抱拳]", R.drawable.emoji_1f00148);
            put("[勾引]", R.drawable.emoji_1f00149);
            put("[拳头]", R.drawable.emoji_1f00150);
            put("[OK]", R.drawable.emoji_1f00151);

            //12
            put("[跳跳]", R.drawable.emoji_1f00152);
            put("[发抖]", R.drawable.emoji_1f00153);
            put("[转圈]", R.drawable.emoji_1f00154);
        }
    };


    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String MESSAGE_REGISTRATION_ID = "com.example.jpushdemo.MESSAGE_REGISTRATION_ID";
    public static final String REGISTERID = "registerID";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    private PowerManager.WakeLock mWakeLock;

    private void acquireWakeLock() {
        if(mWakeLock == null) {
            PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP,
                    this.getClass().getCanonicalName());
            mWakeLock.acquire();

        }

    }

    private void releaseWakeLock() {
        if(mWakeLock != null) {
            mWakeLock.release();
            mWakeLock = null;
        }
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case messageCount:
                    if(iTotalCount == 0) return;
                    tv_activity_index_count.setVisibility(View.VISIBLE);
                    tv_activity_index_count.setText(String.valueOf(iTotalCount));
                    break;
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initListener() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        registerMessageReceiver();
        iv_action_bar2_left.setOnClickListener(this);
        iv_setting.setOnClickListener(this);
        ll_title.setOnClickListener(this);
        iv_one.setOnClickListener(this);
        rl_two.setOnClickListener(this);
        rl_three.setOnClickListener(this);
        tv_dialog_ok.setOnClickListener(this);
        setbtnClicked(this);
        et_content.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SpannableString endStr = null;
//                if(!bFinish) {
//                    String text = s.toString();
//                    SpannableStringBuilder builder = new SpannableStringBuilder(
//                            text);
//                    for (String key : emojisMap.keySet()) {
//                        String rexgString = "";
//                        if (text.contains(key)) {
//                            text.replace(key,"~");
//                            rexgString = "~";
//                            Pattern pattern = Pattern.compile(rexgString);
//                            endStr = EmojiUtil.getExpressionString(mContext,emojisMap.get(key),text,rexgString,s.length());
////                            Matcher matcher = pattern.matcher(text);
////                            while (matcher.find()) {
////                                //找到指定的字符后 setSpan的参数分别为（指定的图片，字符的开始位置，字符的结束位置）
////                                builder.setSpan(new ImageSpan(MainActivity.this,
////                                                emojisMap.get(key)), matcher.start(), matcher.end(),
////                                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
////                            }
//                            endStr.toString().replace("[","");
//                            endStr.toString().replace("]","");
//                        }
//                    }
//                    bFinish = true;
//                    emojiconEditText.setText(endStr);
//
//                    //需要替换的字符
//
//                }
                int  mEmojiconSize = (int) et_content.getTextSize();
                int mEmojiconTextSize = (int) et_content.getTextSize();
                if(!bFinish){
                    String text = s.toString();
                    String replaceText ;
                    SpannableStringBuilder builder = new SpannableStringBuilder(
                            text);
                    for(String key :emojisMap.keySet()){
                        if(text.contains(key)){
                            //需要替换的字符
                            String rexgString = key.replace("[","");
                            rexgString = rexgString.replace("]","");
                            rexgString = "\\["+rexgString +"\\]";
                            Pattern pattern = Pattern.compile(rexgString);
                            Matcher matcher = pattern.matcher(text);
                            while (matcher.find()) {

                                CustomImageSpan imageSpan = new CustomImageSpan(MainActivity.this, emojisMap.get(key), 2);
                                //找到指定的字符后 setSpan的参数分别为（指定的图片，字符的开始位置，字符的结束位置）
                                builder.setSpan(imageSpan,matcher.start(), matcher.end(),
                                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }
                        }
                    }

                    bFinish = true;
                    et_content.setText(builder);
                    et_content.setSelection(text.length());
                }
                bFinish = false;

            }
        });
    }
    @Override
    public void initView(){
        acquireWakeLock();
        iv_setting.setVisibility(View.VISIBLE);
    }



    @Override
    protected void onResume() {
        super.onResume();
        isForeground = true;
        updataView();
    }

    /**
     * 根据时间修改背景，以6点作为分界
     */
    private void updataView(){
        container.removeAllViews();
        int hour = CommonUtils.getHour();
        View view = null;
        if(hour <18) {
            ll_baloon.setVisibility(View.VISIBLE);
            container.setBackgroundResource(R.mipmap.bg_day);
            view = LayoutInflater.from(this).inflate(R.layout.activity_day, null);
            if(iCurrentHour == 0) {
                Thread thread = new Thread(new BollenAnimRunnable());
                thread.start();
            }
        }else {
            ll_baloon.setVisibility(View.GONE);
            view = LayoutInflater.from(this).inflate(R.layout.activity_night,null);
            container.setBackgroundResource(R.mipmap.bg_index);
            iv_shape = (ImageView)view.findViewById(R.id.iv_shape);
            iv_shape_center = (ImageView)view.findViewById(R.id.iv_shape_center);
            iv_shape_right  =(ImageView)view.findViewById(R.id.iv_shape_right);
            AnimationDrawable animationDrawable = (AnimationDrawable) iv_shape.getDrawable();
            AnimationDrawable lanimationDrawable = (AnimationDrawable) iv_shape_center.getDrawable();
            AnimationDrawable ranimationDrawable = (AnimationDrawable) iv_shape_right.getDrawable();
            animationDrawable.start();
            lanimationDrawable.start();
            ranimationDrawable.start();
            if(App.strMoonUrl.isEmpty()){
                // TODO: 2018/6/19 获取月亮图标
            }

        }
        iCurrentHour  = hour;
        container.addView(view);
    }
    @Override
    public  void  initData(){
        if(iTotalCount != 0 && bStart){
            tv_activity_index_count.setVisibility(View.VISIBLE);
            tv_activity_index_count.setText(String.valueOf(iTotalCount));
        }
        else {
            tv_activity_index_count.setVisibility(View.GONE);
        }
    }
    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseWakeLock();
        stopService();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_action_bar2_left:
                stopService();
                break;
            case R.id.ll_title:
                startService();
                break;
            case R.id.iv_one:
                processOne();
                break;
            case R.id.rl_two:
                processTwo();
                break;
            case R.id.rl_activity_index_our_pingzi:
                processThree();
                break;
            case R.id.iv_setting:
                intentActivity(this, SettingActivity.class,false,true);
                break;
            case R.id.tv_dialog_ok:
                ll_dialog.setVisibility(View.GONE);
                break;
        }

    }

    /**
     * 该点击事件没有操作
     */
    private void processOne(){
        ll_dialog.setVisibility(View.VISIBLE);
    }

    /**
     * 群发信息 文本和图片
     */
    private void processTwo(){
        if(bStart){
            initShare("","").showShareView();
        }else {
            ToastUtils.show("请点击标题启动服务");
        }
    }

    /**
     * 获取我的瓶子信息
     */
    private void processThree(){
        if(bStart){
            intentActivity(this, DriftinBottleListActivity.class,false,true);
        }else {
            ToastUtils.show("请点击标题启动服务");
        }

    }

    /**
     * 数据重置
     */
    private void stopService(){
        // TODO: 18-6-12 http 情况数据
        ToastUtils.show("停止服务");
        tv_activity_index_count.setVisibility(View.GONE);
        bStart = false;
        iTotalCount = 0;
        App.bottleIds.clear();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }

    /**
     * 开始服务
     */
    private void startService(){
        if(bStart){
            ToastUtils.show("服务已启动");
            return;
        }
        ToastUtils.show("服务启动成功");
        bStart = true;
        new Thread(new MyRunnable()).start();

    }

    @Override
    public void onBtnClicked(int type,String content) {
        // TODO: 2018/6/13 send
        App.content = content;

    }

    @Override
    public void onBtnClicked(int type) {
        switch (type){
            case 0:
                sendMessage();
                break;
        }
    }
    private void sendMessage(){
        rl_msg.setVisibility(View.VISIBLE);
        setEmojiconFragment(false);
    }

    class BollenAnimRunnable implements Runnable{

        @Override
        public void run() {
            TranslateAnimation alphaAnimation = new TranslateAnimation(0f, 550f, 350, 100);
            alphaAnimation.setDuration(11000);
            alphaAnimation.setRepeatCount(1);
            alphaAnimation.setRepeatMode(Animation.REVERSE);
            alphaAnimation.setFillAfter(true);
            iv_baloon.setAnimation(alphaAnimation);
            alphaAnimation.start();
        }
    }


    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        filter.addAction(MESSAGE_REGISTRATION_ID);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    /**
     * 接收服务端推送的消息
     */
    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("22222222222222",intent.getAction());
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                Log.d("1111111111111111",intent.getAction());
                iTotalCount ++;
                if(isForeground){
                    Log.d("11111111111111",String.valueOf(iTotalCount));
                    Message message = new Message();
                    message.what = messageCount;
                    mHandler.sendEmptyMessage(message.what);
                }
            }else if(MESSAGE_REGISTRATION_ID.equals(intent.getAction())){
                String msg = intent.getStringExtra(REGISTERID);
                App.registerId = msg== null ?"":msg;

            }
        }
    }
    private void setEmojiconFragment(boolean useSystemDefault) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.emojicons, EmojiconsFragment.newInstance(useSystemDefault))
                .commit();
    }

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(et_content, emojicon);
    }

    @Override
    public void onEmojiconBackspaceClicked(View v) {
        EmojiconsFragment.backspace(et_content);
    }

    private class MyTextWatcher implements TextWatcher
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
        @Override public void afterTextChanged(Editable s){

        }

    }
    class MyRunnable implements Runnable{

        @Override
        public void run() {
            while (true) {
                iTotalCount++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("111", String.valueOf(iTotalCount));
            }
        }
    }
}

