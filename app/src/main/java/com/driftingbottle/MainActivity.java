package com.driftingbottle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.driftinbottle.callback.ErrorInfo;
import com.driftinbottle.callback.RequestCallback;
import com.driftinbottle.httputils.HttpUtils;
import com.driftingbottle.activity.DriftinBottleListActivity;
import com.driftingbottle.activity.SettingActivity;
import com.driftingbottle.base.BaseActivity;
import com.driftingbottle.bean.BottleCountBean;
import com.driftingbottle.bean.MoonBean;
import com.driftingbottle.utils.CommonUtils;
import com.driftingbottle.utils.EmojiUtil;
import com.driftingbottle.utils.ToastUtils;
import com.google.gson.Gson;
import com.lzy.okhttputils.cache.CacheMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.github.rockerhieu.emojicon.EmojiconEditText;
import io.github.rockerhieu.emojicon.EmojiconGridFragment;
import io.github.rockerhieu.emojicon.EmojiconsFragment;
import io.github.rockerhieu.emojicon.emoji.Emojicon;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Administrator on 2018/6/25.
 */

public class MainActivity extends BaseActivity implements View.OnClickListener ,EasyPermissions.PermissionCallbacks,EmojiconGridFragment.OnEmojiconClickedListener, EmojiconsFragment.OnEmojiconBackspaceClickedListener{

    /**
     * 主界面
     */
    @BindView(R.id.rootview)
    RelativeLayout rootview;
    /**
     * actionBar 布局
     */
    @BindView(R.id.iv_activity_index_back)
    ImageView iv_activity_index_back;
    @BindView(R.id.tv_activity_index_start)
    TextView tv_activity_index_start;
    @BindView(R.id.index_set)
    Button index_set;
    /**
     * 晚上背景布局
     */
    @BindView(R.id.rl_right_layout)
    RelativeLayout rl_right_layout;
    @BindView(R.id.iv_activity_index_moon)
    ImageView iv_activity_index_moon;
    @BindView(R.id.iv_shape)
    ImageView iv_shape;
    @BindView(R.id.iv_shape_center)
    ImageView iv_shape_center;
    @BindView(R.id.iv_shape_right)
    ImageView iv_shape_right;

    /**
     * 白天背景布局
     */
    @BindView(R.id.rl_day_layout)
    RelativeLayout rl_day_layout;
    @BindView(R.id.iv_bollen)
    ImageView iv_bollen;

    /**
     * 底部布局
     */
    @BindView(R.id.iv_one)
    ImageView iv_one;
    @BindView(R.id.iv_two)
    ImageView iv_two;
    @BindView(R.id.iv_three)
    ImageView iv_three;
    @BindView(R.id.tv_activity_index_count)
    TextView tv_activity_index_count;
    /**
     * dialog for iv_one click
     */
    @BindView(R.id.ll_dialog)
    LinearLayout ll_dialog;
    @BindView(R.id.tv_dialog_ok)
    Button tv_dialog_ok;
    /**
     * 群发文本消息布局
     */
    @BindView(R.id.ll_dialog_send_message)
    LinearLayout ll_dialog_send_message;
    @BindView(R.id.et_msg_tle)
    EmojiconEditText et_msg_tle;
    @BindView(R.id.tv_activity_index_select_biaoqing)
    TextView tv_activity_index_select_biaoqing;
    @BindView(R.id.et_msg)
    EmojiconEditText et_msg;
    @BindView(R.id.tv_send_msg)
    TextView tv_send_msg;
    @BindView(R.id.tv_cancel_msg)
    TextView tv_cancel_msg;
    /**
     * 群发图片消息布局
     */
    @BindView(R.id.ll_dialog_send_photo)
    LinearLayout ll_dialog_send_photo;
    @BindView(R.id.iv_activity_index_photo)
    ImageView iv_activity_index_photo;
    @BindView(R.id.tv_send_photo)
    TextView tv_send_photo;
    @BindView(R.id.tv_cancel_photo)
    TextView tv_cancel_photo;

    /**
     * 选择dialog
     */
    @BindView(R.id.ll_dialog_select)
    LinearLayout ll_dialog_select;
    @BindView(R.id.ll_activity_index_wenben)
    LinearLayout ll_activity_index_wenben;
    @BindView(R.id.ll_activity_index_photo)
    LinearLayout ll_activity_index_photo;

    @BindView(R.id.emojicons)
    FrameLayout emojicons;


    private PowerManager.WakeLock mWakeLock;
    private boolean bStart =false;
    private boolean bFinish = false;
    private boolean bEmojiVisible = false;
    private int iEmojiEditText = 0 ; // 0 title 1 msg
    private boolean bWorking = true;
    private int iTotalCount = 0;
    public static int iCurrentCount = 0;
    private long interval;
    /**
     * 消息类型 0 文本 1 图片
     */
    private int messageType = 0;
    /**
     * 图片list
     */
    ArrayList<String> photos = null;
    private static final int RC_CAMERA_PERM = 100;
    private static final int PICKER_RESULT= 101;

    private Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    if(iCurrentCount > 0) {
                        Log.d("1111111111",String.valueOf(iCurrentCount));
                        tv_activity_index_count.setVisibility(View.VISIBLE);
                        tv_activity_index_count.setText(String.valueOf(iCurrentCount));
                    }
                    break;
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_main_layout;
    }

    @Override
    public void initListener() {
        rootview.setOnClickListener(this);
        iv_activity_index_back.setOnClickListener(this);
        tv_activity_index_start.setOnClickListener(this);
        index_set.setOnClickListener(this);
        iv_one.setOnClickListener(this);
        iv_two.setOnClickListener(this);
        iv_three.setOnClickListener(this);
        tv_dialog_ok.setOnClickListener(this);
        tv_send_msg.setOnClickListener(this);
        tv_cancel_msg.setOnClickListener(this);
        iv_activity_index_photo.setOnClickListener(this);
        tv_send_photo.setOnClickListener(this);
        tv_cancel_photo.setOnClickListener(this);
        ll_activity_index_wenben.setOnClickListener(this);
        ll_activity_index_photo.setOnClickListener(this);
        tv_activity_index_select_biaoqing.setOnClickListener(this);
        et_msg.addTextChangedListener(new MyTextWatcher());
        et_msg_tle.addTextChangedListener(new MyTextWatcher());
        et_msg_tle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                iEmojiEditText = 0;
            }
        });
        et_msg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                iEmojiEditText = 1;
            }
        });
    }

    @Override
    public void initData() {

    }
    @Override
    public void initView() {
        setInitActionBar(false);
        acquireWakeLock();
        /**
         * 根据时间获取月亮图标
         */
        int hour = CommonUtils.getHour();
        if(hour >= 18){
           // getMoon();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        updateBackGround();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseWakeLock();
        stopService();
        messageType = 0;
        ll_dialog_select.setVisibility(View.GONE);
        ll_dialog_send_message.setVisibility(View.GONE);
        ll_dialog_send_photo.setVisibility(View.GONE);
    }

    private void updateBackGround(){
        int hour = CommonUtils.getHour();
        if(hour < 18){
            rl_right_layout.setVisibility(View.GONE);
            rl_day_layout.setVisibility(View.VISIBLE);
            // 热气球动画
            new Thread(new BollenAnimRunnable()).start();

        }else {
            rl_day_layout.setVisibility(View.GONE);
            rl_right_layout.setVisibility(View.VISIBLE);
            // 灯塔光动画
            AnimationDrawable animationDrawable = (AnimationDrawable) iv_shape.getDrawable();
            AnimationDrawable lanimationDrawable = (AnimationDrawable) iv_shape_center.getDrawable();
            AnimationDrawable ranimationDrawable = (AnimationDrawable) iv_shape_right.getDrawable();
            animationDrawable.start();
            lanimationDrawable.start();
            ranimationDrawable.start();
        }
    }
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

    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_activity_index_back:
                stopService();
                break;
            case R.id.tv_activity_index_start:
                startService();
                break;
            case R.id.index_set:
                go2SettingActivity();
                break;
            case R.id.iv_one:
                processOne();
                break;
            case R.id.iv_two:
                processTwo();
                break;
            case R.id.iv_three:
                processThree();
                break;
            case R.id.tv_dialog_ok:
                ll_dialog.setVisibility(View.GONE);
                break;
            case R.id.tv_activity_index_select_biaoqing:
                if(bEmojiVisible){
                    bEmojiVisible = false;
                    emojicons.setVisibility(View.GONE);
                    tv_activity_index_select_biaoqing.setText("选择表情");
                }else {
                    bEmojiVisible = true;
                    emojicons.setVisibility(View.VISIBLE);
                    tv_activity_index_select_biaoqing.setText("隐藏表情");
                }
                showEmojiFragment(false);
                break;
            case R.id.tv_send_msg:
            case R.id.tv_send_photo:
                sendMessage();
                break;
            case R.id.tv_cancel_msg:
                ll_dialog_send_message.setVisibility(View.GONE);
                et_msg.setText("");
                et_msg_tle.setText("");
                break;
            case R.id.tv_cancel_photo:
                ll_dialog_send_photo.setVisibility(View.GONE);
                iv_activity_index_photo.setImageResource(0);
                break;
            case R.id.iv_activity_index_photo:
                requestCameraPermission();
                break;
            case R.id.ll_activity_index_wenben:
                messageType = 0;
                ll_dialog_select.setVisibility(View.GONE);
                ll_dialog_send_message.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_activity_index_photo:
                messageType = 1;
                ll_dialog_select.setVisibility(View.GONE);
                ll_dialog_send_photo.setVisibility(View.VISIBLE);
                break;
            default:
                if(ll_dialog_select.getVisibility() == View.VISIBLE){
                    ll_dialog_select.setVisibility(View.GONE);
                }
                break;
        }

    }
    private void stopService(){
        ToastUtils.show("服务停止");
        String url = "";
        HashMap<String,String>param = new HashMap<>();
        param.put("clientID ",CommonUtils.getUniqueId(mContext));
        HttpUtils.getInstance().requestGet(url, param, url, new RequestCallback<String>() {
            @Override
            public void onResponse(String result) {
                bStart = false;
                bFinish = false;
                iTotalCount = 0;
            }

            @Override
            public void onFailure(ErrorInfo.ErrorCode errorInfo) {
                ToastUtils.show("网络错误，服务停止失败");

            }
        },CacheMode.DEFAULT);

    }
    private void startService(){
        if(bStart){
            ToastUtils.show("服务已经启动");
            return;
        }else {
            ToastUtils.show("服务启动");
            bStart = true;
        }
        String url = "http://www.baidu.com";
        HashMap<String,String> param = new HashMap<>();
        param.put("clientID ",CommonUtils.getUniqueId(mContext));
        HttpUtils.getInstance().requestGet(url, param, url, new RequestCallback<String>() {
            @Override
            public void onResponse(String result) {
                String reponse ="{\n" +
                        "  \"code\": 0,\n" +
                        "  \"msg\": \"请求正常返回\",\n" +
                        "  \"data\": [\n" +
                        "    {\n" +
                        "      \"bottleCount\": \"100\",\n" +
                        "      \"buildMinutes\": \"8\"   \n" +
                        "}\n" +
                        "]\n" +
                        "}\n";
                BottleCountBean bottleCountBean ;
                Gson gson = new Gson();
                int count = Integer.valueOf("145");
                iTotalCount  = count;
                int time = Integer.valueOf("3");
                interval = (time * 60 *1000 ) /(count -1);
                Thread thread = new Thread(new BottleRunnable());
                thread.start();
//                bottleCountBean = gson.fromJson(reponse,BottleCountBean.class);
//                if(bottleCountBean != null && bottleCountBean.result!= null){
//                    int count = Integer.valueOf(bottleCountBean.bottleCount);
//                    iTotalCount  = count;
//                    int time = Integer.valueOf(bottleCountBean.buildMinutes);
//                    interval = (time * 60 *1000 ) /(count -1);
//                    Thread thread = new Thread(new BottleRunnable());
//                    thread.start();
//                }
            }

            @Override
            public void onFailure(ErrorInfo.ErrorCode errorInfo) {
                bStart = false;
                ToastUtils.show("网络错误，服务启动失败");

            }
        },CacheMode.DEFAULT);
    }
    private void go2SettingActivity(){
        intentActivity(this, SettingActivity.class,false,true);
    }

    private void getMoon(){
        String url = "http://www.baidu.com";
        HashMap<String,String>param = new HashMap<>();
        HttpUtils.getInstance().requestGet(url, param, url, new RequestCallback<String>() {
            @Override
            public void onResponse(String result) {
                Gson gson = new Gson();
                MoonBean moonBean = gson.fromJson(result,MoonBean.class);
                if(moonBean != null && moonBean.result != null ){
                    Glide.with(MainActivity.this).load(moonBean.moomUrl).into(iv_activity_index_moon);
                }
            }

            @Override
            public void onFailure(ErrorInfo.ErrorCode errorInfo) {
            }
        }, CacheMode.DEFAULT);
    }
    /**
     * 扔瓶子的处理
     */
    private void processOne(){
        ll_dialog.setVisibility(View.VISIBLE);
        ll_dialog_select.setVisibility(View.GONE);
        ll_dialog_send_message.setVisibility(View.GONE);
        ll_dialog_send_photo.setVisibility(View.GONE);
    }

    /**
     * 检一个的处理
     */
    private void processTwo(){
        if(!bStart){
            ToastUtils.show("请点击标题开启服务");
            return;
        }
        ll_dialog_select.setVisibility(View.VISIBLE);
        ll_activity_index_wenben.setVisibility(View.VISIBLE);
        ll_activity_index_photo.setVisibility(View.VISIBLE);
    }

    /**
     * 我的瓶子
     */
    private void processThree(){
        if(!bStart){
            ToastUtils.show("请点击标题开启服务");
            return;
        }
        intentActivity(this, DriftinBottleListActivity.class,false, true);

    }
    private void sendMessage(){
        if(messageType == 0) {
            String title = et_msg_tle.getText().toString();
            String msg = et_msg.getText().toString();
            if (title == null || title.isEmpty()) {
                ToastUtils.show("标题不能为空");
                return;
            }
            if (msg == null || msg.isEmpty()) {
                ToastUtils.show("消息不能为空");
                return;
            }
        }
        else if(messageType == 1){
            if(photos == null || photos.size() == 0){
                ToastUtils.show("群发内容为空");
                return;
            }
        }
        String url = "http://www.baidu.com";
        HashMap<String,String>param = new HashMap<>();
        param.put("clientID",CommonUtils.getUniqueId(mContext));
        param.put("answerType","1");
        param.put("dateType",String.valueOf(messageType));
        if(messageType == 0) {
            param.put("title", et_msg_tle.getText().toString());
            param.put("content", et_msg.getText().toString());
        }else if(messageType == 1){
            param.put("title","");
            param.put("content",photos.get(0));
        }
        HttpUtils.getInstance().requestGet(url, param, url, new RequestCallback<String>() {
            @Override
            public void onResponse(String result) {
                ll_dialog_send_message.setVisibility(View.GONE);
                ToastUtils.show("群发成功");
            }

            @Override
            public void onFailure(ErrorInfo.ErrorCode errorInfo) {
                ToastUtils.show("网络错误，群发失败");
            }
        }, CacheMode.DEFAULT);
    }


    private void  showEmojiFragment(boolean useSystemDefault){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.emojicons, EmojiconsFragment.newInstance(useSystemDefault))
                .commit();
    }
    private boolean requestCameraPermission() {
        String[]permissions = {android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(mContext, permissions)) {
            startFile();
            return true;
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions((Activity) mContext, "需要使用系统相机功能",
                    RC_CAMERA_PERM, permissions);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 将结果转发到EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if(requestCode == RC_CAMERA_PERM){
            startFile();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if(requestCode == RC_CAMERA_PERM){
            ToastUtils.show("权限被拒绝");
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICKER_RESULT && resultCode == RESULT_OK) {
            ProcessImageMessage(data);
        }
    }
    private void startFile() {
        PhotoPickerIntent photo = new PhotoPickerIntent(this);
        photo.setShowCamera(false);
        photo.setPhotoCount(1);
        startActivityForResult(photo, PICKER_RESULT);
    }
    private void ProcessImageMessage(Intent data){
        photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
        if(photos == null || photos.size() == 0){
            ToastUtils.show("未选择图片");
            return;
        }
        Glide.with(this).load(photos.get(0)).placeholder(R.mipmap.dialog_loading_img)
                .error(R.mipmap.dialog_loading_img).into(iv_activity_index_photo);
    }
    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        if (iEmojiEditText == 1){
            EmojiconsFragment.input(et_msg, emojicon);
        }else{
            EmojiconsFragment.input(et_msg_tle, emojicon);
        }
    }

    @Override
    public void onEmojiconBackspaceClicked(View v)
    {
        if (iEmojiEditText == 1){
            EmojiconsFragment.backspace(et_msg);
        }else{
            EmojiconsFragment.backspace(et_msg_tle);
        }
    }

    private class MyTextWatcher implements TextWatcher
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
        @Override public void afterTextChanged(Editable s){
            if(!bFinish){
                SpannableStringBuilder builder = EmojiUtil.replaceStr2Emoji(s.toString(),mContext);
                bFinish = true;
                if(iEmojiEditText == 1) {
                    et_msg.setText(builder);
                    et_msg.setSelection(s.length());
                }else {
                    et_msg_tle.setText(builder);
                    et_msg_tle.setSelection(s.length());
                }
            }
            bFinish = false;
        }
    }

    private void postMessage(){
        Message message = new Message();
        message.what = 0;
        mHandler.sendMessage(message);
    }
    class BottleRunnable implements Runnable{

        @Override
        public void run() {
            while (bWorking){
                if(iTotalCount == 0){
                    iCurrentCount ++;
                    postMessage();
                    continue;
                }
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                iCurrentCount ++;
                postMessage();
                if(iCurrentCount == iTotalCount){
                    bWorking = false;
                }
            }
        }
    }
    class BollenAnimRunnable implements Runnable{

        @Override
        public void run() {
            TranslateAnimation alphaAnimation = new TranslateAnimation(0f, 200f, 100, 20);
            alphaAnimation.setDuration(20000);
            alphaAnimation.setRepeatCount(1);
            alphaAnimation.setRepeatMode(Animation.REVERSE);
            alphaAnimation.setFillAfter(true);
            iv_bollen.setAnimation(alphaAnimation);
            alphaAnimation.start();
        }
    }

}
