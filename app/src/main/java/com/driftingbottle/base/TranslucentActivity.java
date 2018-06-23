package com.driftingbottle.base;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.driftingbottle.R;
import com.driftingbottle.utils.EmojiUtil;
import com.driftingbottle.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.github.rockerhieu.emojicon.EmojiconEditText;
import io.github.rockerhieu.emojicon.EmojiconGridFragment;
import io.github.rockerhieu.emojicon.EmojiconTextView;
import io.github.rockerhieu.emojicon.EmojiconsFragment;
import io.github.rockerhieu.emojicon.EmojiconsFragment.OnEmojiconBackspaceClickedListener;
import io.github.rockerhieu.emojicon.emoji.Emojicon;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by jidan on 18-3-12.
 */

public abstract class TranslucentActivity extends BaseFragmentActivity implements EasyPermissions.PermissionCallbacks, EmojiconGridFragment.OnEmojiconClickedListener, OnEmojiconBackspaceClickedListener{

    private IbtnClicked m_btnClicked;
    private Dialog shareDialog;
    LinearLayout ll_wenben;
    LinearLayout ll_image;
    private static final int RC_CAMERA_PERM = 100;
    private static final int PICKER_RESULT= 101;
    List<String> photos = new ArrayList<String>();
    int type = 0;
    /**
     * 初始化分享模块
     *
     * @param text 分享的文本
     * @param url  分享的url
     */
    public TranslucentActivity initShare(final String text, final String url) {
        //初始化分享的view视图
        shareDialog = new Dialog(this);
        View v = LayoutInflater.from(this).inflate(R.layout.pop_dialog_layout, null);
        shareDialog.setContentView(v);
        Window dialogWindow = shareDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        dialogWindow.setAttributes(lp);
        dialogWindow.setBackgroundDrawableResource(R.drawable.pop_dialog_shape);
        ll_wenben = (LinearLayout)v.findViewById(R.id.ll_wenben);
        ll_image = (LinearLayout)v.findViewById(R.id.ll_image);
        ll_wenben.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 0;
                shareDialog.dismiss();
                m_btnClicked.onBtnClicked(type);

            }
        });
        ll_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 1;
               requestCameraPermission();
            }
        });
        return this;
    }

    /**
     * 显示分享的view
     */
    public void showShareView() {
        shareDialog.show();
    }

    public interface IbtnClicked{
        void onBtnClicked(int type,String content);
        void onBtnClicked(int type);
    }
    public void setbtnClicked(IbtnClicked ibtnClicked){
        if(ibtnClicked != null){
            this.m_btnClicked = ibtnClicked;
        }
    }
    private void startFile() {
        PhotoPickerIntent photo = new PhotoPickerIntent(this);
        photo.setShowCamera(false);
        photo.setPhotoCount(1);
        startActivityForResult(photo, PICKER_RESULT);
    }
    @AfterPermissionGranted(RC_CAMERA_PERM)
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
//            photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
//            if(photos == null || photos.size() == 0){
//                ToastUtils.show("未选择图片");
//                return;
//            }
//            ll_choice.setVisibility(View.GONE);
//            emojiconEditText.setVisibility(View.GONE);
//            ll_msg.setVisibility(View.GONE);
//            iv_img.setVisibility(View.VISIBLE);
//            btn_send.setVisibility(View.VISIBLE);
//            Glide.with(this).load(photos.get(0)).placeholder(R.mipmap.loading)
//                    .error(R.mipmap.error_img).into(iv_img);
        }
    }
    public TranslucentActivity showSendDialog(int resId){
        shareDialog = new Dialog(this);
        View v = LayoutInflater.from(this).inflate(resId, null);
        shareDialog.setContentView(v);
        Window dialogWindow = shareDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();

        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        shareDialog.setCancelable(false);
        dialogWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_bg_shape));
        dialogWindow.setAttributes(lp);
        Button bt_send = (Button)v.findViewById(R.id.btn_send);
        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(m_btnClicked != null){
                    m_btnClicked.onBtnClicked(1);
                    shareDialog.dismiss();
                }

            }
        });
        return this;
    }

}
