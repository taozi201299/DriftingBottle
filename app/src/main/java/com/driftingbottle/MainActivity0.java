package com.driftingbottle;

import android.*;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.driftingbottle.activity.SettingActivity;
import com.driftingbottle.base.BaseActivity;
import com.driftingbottle.utils.CommonUtils;
import com.driftingbottle.utils.EmojiUtil;
import com.driftingbottle.utils.ToastUtils;
import com.driftingbottle.view.CustomImageSpan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import io.github.rockerhieu.emojicon.EmojiconEditText;
import io.github.rockerhieu.emojicon.EmojiconGridFragment;
import io.github.rockerhieu.emojicon.EmojiconsFragment;
import io.github.rockerhieu.emojicon.emoji.Emojicon;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.driftingbottle.utils.EmojiUtil.emojisMap;

/**
 * Created by Administrator on 2018/6/25.
 */

public class MainActivity0 extends BaseActivity implements View.OnClickListener ,EasyPermissions.PermissionCallbacks,EmojiconGridFragment.OnEmojiconClickedListener, EmojiconsFragment.OnEmojiconBackspaceClickedListener{

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

    /**
     * 白天背景布局
     */
    @BindView(R.id.rl_day_layout)
    RelativeLayout rl_day_layout;

    /**
     * 底部布局
     */
    @BindView(R.id.iv_one)
    ImageView iv_one;
    @BindView(R.id.iv_two)
    ImageView iv_two;
    @BindView(R.id.iv_three)
    ImageView iv_three;
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
    /**
     * 群发图片消息布局
     */
    @BindView(R.id.ll_dialog_send_photo)
    LinearLayout ll_dialog_send_photo;
    @BindView(R.id.iv_activity_index_photo)
    ImageView iv_activity_index_photo;
    @BindView(R.id.tv_send_photo)
    TextView tv_send_photo;

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
    private static final int RC_CAMERA_PERM = 100;
    private static final int PICKER_RESULT= 101;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main_layout;
    }

    @Override
    public void initListener() {
        iv_activity_index_back.setOnClickListener(this);
        tv_activity_index_start.setOnClickListener(this);
        index_set.setOnClickListener(this);
        iv_one.setOnClickListener(this);
        iv_two.setOnClickListener(this);
        iv_three.setOnClickListener(this);
        tv_dialog_ok.setOnClickListener(this);
        tv_send_msg.setOnClickListener(this);
        iv_activity_index_photo.setOnClickListener(this);
        tv_send_photo.setOnClickListener(this);
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
    }

    private void updateBackGround(){
        int hour = CommonUtils.getHour();
        if(hour < 18){
            rl_right_layout.setVisibility(View.GONE);
            rl_day_layout.setVisibility(View.VISIBLE);
            // 热气球动画
        }else {
            rl_day_layout.setVisibility(View.GONE);
            rl_right_layout.setVisibility(View.VISIBLE);
            // 灯塔光动画
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
            case R.id.iv_activity_index_photo:
                requestCameraPermission();
                break;
            case R.id.ll_activity_index_wenben:
                ll_dialog_select.setVisibility(View.GONE);
                ll_dialog_send_message.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_activity_index_photo:
                ll_dialog_select.setVisibility(View.GONE);
                ll_dialog_send_photo.setVisibility(View.VISIBLE);
                break;
        }

    }
    private void stopService(){
        ToastUtils.show("服务停止");
        bStart = false;
        bFinish = false;

    }
    private void startService(){
        if(bStart){
            ToastUtils.show("服务已经启动");
            return;
        }else {
            ToastUtils.show("服务启动");
            bStart = true;
        }
    }
    private void go2SettingActivity(){
        intentActivity(this, SettingActivity.class,false,true);
    }

    /**
     * 扔瓶子的处理
     */
    private void processOne(){
        ll_dialog.setVisibility(View.VISIBLE);
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

    }
    private void sendMessage(){
        String title = et_msg_tle.getText().toString();
        String msg = et_msg.getText().toString();

    }


    private void  showEmojiFragment(boolean useSystemDefault){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.emojicons, EmojiconsFragment.newInstance(useSystemDefault))
                .commit();
    }

    @AfterPermissionGranted(RC_CAMERA_PERM)
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
        ArrayList<String> photos = null;
        photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
        if(photos == null || photos.size() == 0){
            ToastUtils.show("未选择图片");
            return;
        }
        Glide.with(this).load(photos.get(0)).placeholder(R.mipmap.dialog_loading_img)
                .error(R.mipmap.dialog_loading_img).into(iv_activity_index_photo);
    }

    private SpannableStringBuilder  replaceStr2Emoji(String content){
        String text = content;
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

                    CustomImageSpan imageSpan = new CustomImageSpan(MainActivity0.this, emojisMap.get(key), 2);
                    //找到指定的字符后 setSpan的参数分别为（指定的图片，字符的开始位置，字符的结束位置）
                    builder.setSpan(imageSpan,matcher.start(), matcher.end(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        return builder;

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
                SpannableStringBuilder builder = replaceStr2Emoji(s.toString());
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

}
