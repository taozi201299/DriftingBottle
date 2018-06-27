package com.driftingbottle.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.driftingbottle.R;
import com.driftingbottle.base.BaseActivity;
import com.driftingbottle.utils.SPUtils;
import com.driftingbottle.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Administrator on 2018/6/13.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener , EasyPermissions.PermissionCallbacks{
    @BindView(R.id.open_photo_pick)
    LinearLayout open_photo_pick;
    @BindView(R.id.user_heard)
    ImageView user_heard;
    private static final int RC_CAMERA_PERM = 100;
    private static final int PICKER_RESULT= 101;
    @Override
    public int getLayoutId() {
        return R.layout.activity_setting_layout;
    }

    @Override
    public void initListener() {
        open_photo_pick.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        showTitle("设置");
        String strUserHead = (String) SPUtils.get("user_head","");
        if(strUserHead!= null && !strUserHead.isEmpty()){
            Glide.with(this).load(strUserHead).into(user_heard);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.open_photo_pick:
                requestCameraPermission();
                break;
        }
    }
    private void startFile() {
        PhotoPickerIntent photo = new PhotoPickerIntent(this);
        photo.setShowCamera(false);
        photo.setPhotoCount(1);
        startActivityForResult(photo, PICKER_RESULT);
    }
    private boolean requestCameraPermission() {
        String[]permissions = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};
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
           List  photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
            if(photos == null || photos.size() == 0){
                ToastUtils.show("未选择图片");
                return;
            }
            SPUtils.put("user_head",photos.get(0));
            Glide.with(this).load(photos.get(0)).into(user_heard);
        }
    }
}
