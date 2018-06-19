package com.driftingbottle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.driftingbottle.activity.DriftinBottleListActivity;
import com.driftingbottle.activity.DriftinBottleMessageActivity;
import com.driftingbottle.activity.SettingActivity;
import com.driftingbottle.base.TranslucentActivity;
import com.driftingbottle.bean.MessageBean;
import com.driftingbottle.utils.CommonUtils;
import com.driftingbottle.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    private ImageView iv_shape;
    private ImageView iv_shape_center;
    private ImageView iv_shape_right;
    public static int iTotalCount = 0;
    private final int messageCount = 0;
    private int iCurrentHour = 0;
    public static boolean isForeground = false;
    private boolean bStart = false;


    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String MESSAGE_REGISTRATION_ID = "com.example.jpushdemo.MESSAGE_REGISTRATION_ID";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

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
        registerMessageReceiver();
        iv_action_bar2_left.setOnClickListener(this);
        iv_setting.setOnClickListener(this);
        ll_title.setOnClickListener(this);
        iv_one.setOnClickListener(this);
        rl_two.setOnClickListener(this);
        rl_three.setOnClickListener(this);
        tv_dialog_ok.setOnClickListener(this);
        setbtnClicked(this);
    }
    @Override
    public void initView(){
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
        stopService();

    }
    public void stop() {
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
            stop();
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

    }

    @Override
    public void onBtnClicked(int type,String content) {
        // TODO: 2018/6/13 send
        App.content = content;

    }
    class BollenAnimRunnable implements Runnable{

        @Override
        public void run() {
            TranslateAnimation alphaAnimation2 = new TranslateAnimation(0f, 550f, 350, 100);
            alphaAnimation2.setDuration(15000);
            alphaAnimation2.setRepeatCount(1);
            alphaAnimation2.setRepeatMode(Animation.REVERSE);
            alphaAnimation2.setFillAfter(true);
            iv_baloon.setAnimation(alphaAnimation2);
            alphaAnimation2.start();
        }
    }


    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
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
            }
        }
    }
}

