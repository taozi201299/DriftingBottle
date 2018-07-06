package com.driftingbottle.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDialog;
import com.driftingbottle.R;
import com.driftingbottle.activity.SettingActivity;
import com.driftingbottle.utils.Constant;
import com.driftingbottle.utils.ScreenManager;
import com.driftingbottle.utils.ToastUtils;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {
    public Context mContext;

    // 是否允许全屏
    private boolean allowFullScreen = true;
    private Dialog dialog;
    private Dialog dataDialog;
    private boolean mInitActionBar = true;
    private boolean finishOnBackKeyDown = true;

    protected boolean useThemestatusBarColor = true;//是否使用特殊的标题栏背景颜色，android5.0以上可以设置状态栏背景色，如果不使用则使用透明色值
    protected boolean useStatusBarColor = true;//是否使用状态栏文字和图标为暗色，如果状态栏采用了白色系，则需要使状态栏和图标为暗色，android6.0以上可以设置

    private IDialogInterface iDialogInterface ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (allowFullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE); // 取消标题
        }
        mContext = this;
        ScreenManager.getScreenManager().pushActivity(this);
        setContentView(getLayoutId());
        setStatusBar();
       // getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        ButterKnife.bind(this);
        initView();
        initListener();
        if (mInitActionBar) {
            initActionBar();
        }
    }
    protected void showTitle(String title){
        ((TextView)findViewById(R.id.tv_activity_index_start)).setText(title);
    }
    public void setActionBarTitle(String title){
    }
    protected void initActionBar(){
        Button index_set = findViewById(R.id.index_set);
        index_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentActivity(BaseActivity.this, SettingActivity.class,false,true);
            }
        });
        findViewById(R.id.iv_activity_index_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityFinish();
            }
        });
    }
    public abstract int getLayoutId();
    /**
     * 设置监听事件
     */
    public abstract void initListener() ;
    /**
     * 记载数据
     */
    public abstract void initData() ;
    public abstract void initView();

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            setIntent(intent);
            mContext = this;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        initData();

    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        ScreenManager.getScreenManager().popActivity(this);
        super.onDestroy();
    }
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
                View decorView = getWindow().getDecorView();
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
                decorView.setSystemUiVisibility(option);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            //根据上面设置是否对状态栏单独设置颜色
            if (useThemestatusBarColor) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
            } else {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !useStatusBarColor ) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
    public static void hideInput(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

        /**
         * 隐藏软件盘
         * @param activity
         */
    public static void hideSoftInput(Activity activity){
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /**
     * 弹出软件盘
     * @param et
     * @param context
     */
    public static void showSoftInput(EditText et,Context context){
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, 0);

    }

    public void showDataLoadingDialog(String content){
        LoadingDialog.Builder loadBuilder=new LoadingDialog.Builder(this)
                .setMessage(content)
                .setCancelable(true)
                .setCancelOutside(true);
        LoadingDialog dialog=loadBuilder.create();
        dialog.show();
    }
    public void showDataLoadingDialog(){
        LoadingDialog.Builder loadBuilder=new LoadingDialog.Builder(this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dataDialog=loadBuilder.create();
        dataDialog.show();

    }
    public void closeDataDialog(){
        if (dataDialog != null && dataDialog.isShowing()) {
            dataDialog.dismiss();
        }
    }
    public interface IDialogInterface{
        void dialogClick();
        void dialogCancel();
    }

    protected void setDialogInterface(IDialogInterface iDialogInterface){
        this.iDialogInterface = iDialogInterface;
    }
    /**
     * 显示toast信息
     * @param msg
     */
    public void showToast(String msg){
        ToastUtils.show(msg);
    }


    /**
     * activity跳转
     * @param activity
     * @param targetActivity
     * @param isfinish
     */
    public void   intentActivity(Activity activity,Class<? extends Activity> targetActivity,boolean isfinish,boolean isAnim){
        Intent intent = new Intent(activity,targetActivity);
        activity.startActivity(intent);
        if(isAnim){
            overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
            if(isfinish){
                activity.finish();
            }
        }else{
            if(isfinish){
                finish();
            }
        }
    }
    /**
     * activity跳转
     * @param activity
     * @param targetActivity
     * @param isfinish
     */
    public void   intentActivity(Activity activity,Class<? extends Activity> targetActivity,boolean isfinish,boolean isAnim,int flag){
        Intent intent = new Intent(activity,targetActivity);
        intent.setFlags(flag);
        activity.startActivity(intent);
        if(isAnim){
            overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
            if(isfinish){
                activity.finish();
            }
        }else{
            if(isfinish){
                finish();
            }
        }
    }
    /**
     * activity跳转
     * @param activity
     * @param targetActivity
     * @param isfinish
     */
    public void intentActivity(Activity activity,Class<? extends Activity> targetActivity,boolean isfinish,int requestCode){
        Intent intent = new Intent(activity,targetActivity);
        activity.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
        if(isfinish){
            activity.finish();
        }
    }

    /**
     * activity跳转
     * @param activity
     * @param targetActivity
     * @param isfinish
     */
    public void intentActivity(Activity activity,Class<? extends Activity> targetActivity,boolean isfinish,Bundle bundle,int requestCode){
        Intent intent = new Intent(activity,targetActivity);
        intent.putExtra(Constant.DEFAULT_BUNDLE_NAME, bundle);
        activity.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
        if(isfinish){
            activity.finish();

        }
    }
    /**
     * activity跳转
     * @param activity
     * @param targetActivity
     * @param isfinish
     */
    public void intentActivity(Activity activity,Class<? extends Activity> targetActivity,boolean isfinish,Bundle bundle){
        Intent intent = new Intent(activity,targetActivity);
        intent.putExtra(Constant.DEFAULT_BUNDLE_NAME, bundle);
        activity.startActivity(intent);
        overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
        if(isfinish){
            activity.finish();

        }
    }
    /**
     * activity跳转
     * @param activity
     * @param targetActivity
     * @param isfinish
     */
    public void intentActivity(Activity activity,Class<? extends Activity> targetActivity,boolean isfinish,int flag,Bundle bundle){
        Intent intent = new Intent(activity,targetActivity);
        intent.putExtra(Constant.DEFAULT_BUNDLE_NAME, bundle);
        intent.setFlags(flag);
        activity.startActivity(intent);
        overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
        if(isfinish){
            activity.finish();

        }
    }
    public void activityFinish(){
        finish();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
    }

    //处理后退键的情况
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && finishOnBackKeyDown){

            this.finish();  //finish当前activity
            overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean isAllowFullScreen() {
        return allowFullScreen;
    }

    public void setAllowFullScreen(boolean allowFullScreen) {
        this.allowFullScreen = allowFullScreen;
    }

    public boolean isInitActionBar() {
        return mInitActionBar;
    }

    public void setInitActionBar(boolean initActionBar) {
        this.mInitActionBar = initActionBar;
    }

    public void setFinishOnBackKeyDown(boolean finishOnBackKeyDown) {
        this.finishOnBackKeyDown = finishOnBackKeyDown;
    }
}
