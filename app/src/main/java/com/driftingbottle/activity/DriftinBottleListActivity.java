package com.driftingbottle.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.driftinbottle.callback.ErrorInfo;
import com.driftinbottle.callback.RequestCallback;
import com.driftinbottle.httputils.HttpUtils;
import com.driftingbottle.App;
import com.driftingbottle.MainActivity;
import com.driftingbottle.R;
import com.driftingbottle.adapter.BottleAdatper;
import com.driftingbottle.adapter.CommonAdapter;
import com.driftingbottle.base.BaseActivity;
import com.driftingbottle.bean.BottleBean;
import com.driftingbottle.bean.BottleBean0;
import com.driftingbottle.utils.CommonUtils;
import com.driftingbottle.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okhttputils.cache.CacheMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

import static com.driftingbottle.utils.Constant.DEFAULT_BUNDLE_NAME;

/**
 * Created by jidan on 18-6-12.
 * 获取瓶子列表 （包含最后一条信息）
 */

public class DriftinBottleListActivity extends BaseActivity  implements CommonAdapter.OnItemClickListener{

    private final String TAG = DriftinBottleListActivity.class.getSimpleName();
    @BindView(R.id.activity_index)
    LinearLayout activity_index;
    @BindView(R.id.bottleRecyclerView)
    RecyclerView bottleRecyclerView;
    @BindView(R.id.tv_emtry_message)
    TextView tv_emtry_message;
    @BindView(R.id.action_bar)
    LinearLayout action_bar;
    @BindView(R.id.index_set)
    Button index_set;
    @BindView(R.id.index_set1)
    Button index_set1;
    @BindView(R.id.tv_activity_index_start)
    TextView tv_activity_index_start;
    private ArrayList<BottleBean0> bottleBeans ;
    private BottleAdatper bottleAdatper;
    public static  int bStart = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_bottle_list_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    @Override
    public void initListener() {
        bottleAdatper.setOnItemClickListener(this);
        tv_activity_index_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.isShow = !App.isShow;
                App.bottlesIds.clear();
                if(App.isShow){
                    ToastUtils.show("角标长显");
                }else {
                    ToastUtils.show("关闭角标长显");
                }
                initData();
            }
        });
    }

    @Override
    public void initData() {
        if(bStart == 0) return;
        if(bStart == 1 ) {
            if(App.bOld){
                showTitle("我的瓶子(" + MainActivity.iCurrentCount + ")");
            }else {
                showTitle("我的瓶子");
            }
        }else if(bStart == 3) {
            double count = MainActivity.iTotalCount * App.IRand;
            int icount = new Double(count).intValue();
            showTitle("我的瓶子(" + icount + ")");
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                getBottle();
            }
        }).start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        HttpUtils.getInstance().cancleHttp(TAG);
    }

    @Override
    public void initView() {
        index_set.setVisibility(View.GONE);
        index_set1.setVisibility(View.GONE);
        action_bar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置RecyclerView 布局
        bottleRecyclerView.setLayoutManager(layoutManager);
        bottleAdatper = new BottleAdatper(this,R.layout.activity_bottle_item);
        bottleRecyclerView.setAdapter(bottleAdatper);
        Bundle bundle = getIntent().getBundleExtra(DEFAULT_BUNDLE_NAME);
        bStart = bundle.getInt("start");
        if(bStart == 0){
            activity_index.setBackgroundColor(getResources().getColor(R.color.white));
            tv_emtry_message.setVisibility(View.VISIBLE);
            bottleRecyclerView.setVisibility(View.GONE);
            showTitle("我的瓶子");
        }else {
            showDataLoadingDialog();
            activity_index.setBackgroundColor(getResources().getColor(R.color.gray_bb));
            tv_emtry_message.setVisibility(View.GONE);
            bottleRecyclerView.setVisibility(View.VISIBLE);
            if(bStart == 1) {
                showTitle("我的瓶子(" + MainActivity.iCurrentCount + ")");
            }else {
                double count = MainActivity.iTotalCount * App.IRand;
                int icount = new Double(count).intValue();
                showTitle("我的瓶子(" + icount + ")");
            }
        }
    }
    private void getBottle(){
        String url = App.strIp +"/WebRoot/ClientGetPLPList";
        HashMap<String,String>params = new HashMap<>();
        params.put("clientID", CommonUtils.getUniqueId(mContext));
        HttpUtils.getInstance().requestGet(url, params, TAG, new RequestCallback<String>() {
            @Override
            public void onResponse(final String result) {

                ((DriftinBottleListActivity)mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //此时已在主线程中，可以更新UI了
                        closeDataDialog();
                        Gson gson = new Gson();
                        bottleBeans = gson.fromJson(result,new TypeToken<List<BottleBean0>>(){}.getType());
                        if(bottleBeans == null){
                            ToastUtils.show(ErrorInfo.ErrorCode.valueOf(-5).getMessage());
                            return;
                        }else {
                            refreshUI();
                        }

                    }
                });

            }

            @Override
            public void onFailure(final ErrorInfo.ErrorCode errorInfo) {
                ((DriftinBottleListActivity)mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //此时已在主线程中，可以更新UI了
                        closeDataDialog();
                        ToastUtils.show(errorInfo.getMessage());
                    }
                });

            }
        }, CacheMode.DEFAULT);
    }

    private void refreshUI(){
        if(!App.bOld) {
            setBottleNum();
            if (bStart != 3) {
                if (App.bottleNum == 0) {
                    showTitle("我的瓶子");
                } else {
                    showTitle("我的瓶子(" + App.bottleNum + ")");
                }
            } else {
                showTitle("我的瓶子(" + App.bottle2Num + ")");
            }
        }
        if(App.isShow) {
            for (BottleBean0 item : bottleBeans) {
                if (bStart == 3) {
                    item.bIsRead = true;
                } else {
                    item.bIsRead = false;
                }
            }
        }else {
            for(BottleBean0 bottleBean0 :bottleBeans){
                if(App.bottlesIds.contains(bottleBean0.regionID)){
                    bottleBean0.bIsRead = true;
                }else {
                    bottleBean0.bIsRead = false;
                }
            }
        }
        bottleAdatper.setData(bottleBeans);
        bottleAdatper.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(int position) {
        BottleBean0 item = bottleBeans.get(position);
        if(!App.bottlesIds.contains(item.regionID)){
            App.bottlesIds.add(item.regionID);
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("key",item);
        intentActivity(this,DriftinBottleMessageActivity.class,false,bundle);

    }
    private void setBottleNum(){
        String strSplit = "#";
        App.bottleNum = 0;
        App.bottle2Num = 0;
        int count;
        for(BottleBean0 bottleBean0 :bottleBeans){
            count = 1;
            if ("0".equals(bottleBean0.dataType)) {
                String data = bottleBean0.textData;
                String array[];
                if (data.contains(strSplit)) {
                    array = data.split(strSplit);
                    count += array.length;
                    count--;
                }
            } else if ("3".equals(bottleBean0.dataType)) {
                String data1 = bottleBean0.textData;
                String array1[];
                if (data1.contains(strSplit)) {
                    array1 = data1.split(strSplit);
                    count += array1.length;
                }else {
                    count ++;
                }
            }
            if (!"2".equals(bottleBean0.answerType)) {
                if(count >=2){
                    App.bottleNum ++;
                }
            }else {
                App.bottle2Num ++;
            }
            }
        }
}
