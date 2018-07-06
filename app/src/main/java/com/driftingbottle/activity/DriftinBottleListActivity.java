package com.driftingbottle.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;
import android.widget.LinearLayout;
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

/**
 * Created by jidan on 18-6-12.
 * 获取瓶子列表 （包含最后一条信息）
 */

public class DriftinBottleListActivity extends BaseActivity  implements CommonAdapter.OnItemClickListener{

    @BindView(R.id.bottleRecyclerView)
    RecyclerView bottleRecyclerView;
    @BindView(R.id.tv_emtry_message)
    TextView tv_emtry_message;
    @BindView(R.id.action_bar)
    LinearLayout action_bar;
    private ArrayList<BottleBean0> bottleBeans ;
    private BottleAdatper bottleAdatper;

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
    }

    @Override
    public void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getBottle();
            }
        }).start();

    }

    @Override
    public void initView() {
        action_bar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        showDataLoadingDialog();
        showTitle("我的瓶子" +"("+ MainActivity.iCurrentCount +")");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置RecyclerView 布局
        bottleRecyclerView.setLayoutManager(layoutManager);
        bottleAdatper = new BottleAdatper(this,R.layout.activity_bottle_item);
        bottleRecyclerView.setAdapter(bottleAdatper);
    }
    private void getBottle(){
        String url = "http://123.56.68.127:8080/WebRoot/ClientGetPLPList";
        HashMap<String,String>params = new HashMap<>();
        params.put("clientID", CommonUtils.getUniqueId(mContext));
        HttpUtils.getInstance().requestGet(url, params, url, new RequestCallback<String>() {
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
        for(BottleBean0 item : bottleBeans){
            if(App.bottleIds.contains(item.regionID)){
                item.bIsRead = true;
            }else {
                item.bIsRead = false;
            }
        }
        bottleAdatper.setData(bottleBeans);
        bottleAdatper.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(int position) {
        BottleBean0 item = bottleBeans.get(position);
        if(!App.bottleIds.contains(item.regionID)) {
            App.bottleIds.add(item.regionID);
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("key",item);
        intentActivity(this,DriftinBottleMessageActivity.class,false,bundle);

    }
}
