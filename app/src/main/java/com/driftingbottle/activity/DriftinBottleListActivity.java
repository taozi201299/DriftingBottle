package com.driftingbottle.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.driftingbottle.bean.BottleBean0;
import com.driftingbottle.utils.CommonUtils;
import com.driftingbottle.utils.ToastUtils;
import com.google.gson.Gson;
import com.lzy.okhttputils.cache.CacheMode;

import java.util.HashMap;

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
    private BottleBean0 bottleBean ;
    private BottleAdatper bottleAdatper;

    @Override
    public int getLayoutId() {
        return R.layout.activity_bottle_list_layout;
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
        String url = "http://192.168.1.8:8080/wcsps-supervision/v1/att/ad/base/attAdBases/";
        HashMap<String,String>params = new HashMap<>();
        params.put("clientID", CommonUtils.getUniqueId(mContext));
        HttpUtils.getInstance().requestGet(url, params, url, new RequestCallback<String>() {
            @Override
            public void onResponse(String result) {
                result ="{\n" +
                        "  \"code\": 0,\n" +
                        "  \"msg\": \"请求正常返回\",\n" +
                        "  \"totalCount\": 3479,\n" +
                        "  \"data\": [\n" +
                        "    {\n" +
                        "      \"reginonID\": \"7A52C1E4A3064127B6C20C54CB0BD88C\",\n" +
                        "      \"headimage\": \"/storage/emulated/0/Download/03-28-33-613e24431257c9bb_400.jpg\",\n" +
                        "      \"bottleName\": \"北京\",\n" +
                        "      \"textData\": \"\",\n" +
                        "      \"imageData\": \"\",\n" +
                        "      \"title\": \"中华人民共和国\",\n" +
                        "      \"voiceNumber\": \"\",\n" +
                        "      \"dataType\": \"0\",\n" +
                        "      \"creatdDate\": \"2018-6-25 17:45:00\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"reginonID\": \"7A52C1E4A3064127B6C20C54CB0BD880\",\n" +
                        "      \"headimage\": \"/storage/emulated/0/Download/03-28-33-613e24431257c9bb_400.jpg\",\n" +
                        "      \"bottleName\": \"上海\",\n" +
                        "      \"textData\": \"\",\n" +
                        "      \"imageData\": \"\",\n" +
                        "      \"title\": \"中华人民共和国\",\n" +
                        "      \"voiceNumber\": \"\",\n" +
                        "      \"dataType\": \"0\",\n" +
                        "      \"creatdDate\": \"2018-6-25 17:45:01\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"reginonID\": \"7A52C1E4A3064127B6C20C54CB0BD881\",\n" +
                        "      \"headimage\": \"/storage/emulated/0/Download/03-28-33-613e24431257c9bb_400.jpg\",\n" +
                        "      \"bottleName\": \"北京\",\n" +
                        "      \"textData\": \"\",\n" +
                        "      \"imageData\": \"/storage/emulated/0/Download/03-28-33-613e24431257c9bb_400.jpg\",\n" +
                        "      \"title\": \"\",\n" +
                        "      \"voiceNumber\": \"\",\n" +
                        "      \"dataType\": \"1\",\n" +
                        "      \"creatdDate\": \"2018-6-25 17:45:05\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"reginonID\": \"7A52C1E4A3064127B6C20C54CB0BD882\",\n" +
                        "      \"headimage\": \"/storage/emulated/0/Download/03-28-33-613e24431257c9bb_400.jpg\",\n" +
                        "      \"bottleName\": \"中华人民共和国\",\n" +
                        "      \"textData\": \"中华人民共和国\",\n" +
                        "      \"imageData\": \"中华人民共和国\",\n" +
                        "      \"title\": \"中华人民共和国\",\n" +
                        "      \"voiceNumber\": \"10s\",\n" +
                        "      \"dataType\": \"2\",\n" +
                        "      \"creatdDate\": \"中华人民共和国\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"reginonID\": \"7A52C1E4A3064127B6C20C54CB0BD883\",\n" +
                        "      \"headimage\": \"/storage/emulated/0/Download/03-28-33-613e24431257c9bb_400.jpg\",\n" +
                        "      \"bottleName\": \"中华人民共和国\",\n" +
                        "      \"textData\": \"中华人民共和国\",\n" +
                        "      \"imageData\": \"中华人民共和国\",\n" +
                        "      \"title\": \"中华人民共和国\",\n" +
                        "      \"voiceNumber\": \"中华人民共和国\",\n" +
                        "      \"dataType\": \"0\",\n" +
                        "      \"creatdDate\": \"中华人民共和国\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"reginonID\": \"7A52C1E4A3064127B6C20C54CB0BD884\",\n" +
                        "      \"headimage\": \"/storage/emulated/0/Download/03-28-33-613e24431257c9bb_400.jpg\",\n" +
                        "      \"bottleName\": \"中华人民共和国\",\n" +
                        "      \"textData\": \"中华人民共和国\",\n" +
                        "      \"imageData\": \"中华人民共和国\",\n" +
                        "      \"title\": \"中华人民共和国\",\n" +
                        "      \"voiceNumber\": \"中华人民共和国\",\n" +
                        "      \"dataType\": \"0\",\n" +
                        "      \"creatdDate\": \"中华人民共和国\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"reginonID\": \"7A52C1E4A3064127B6C20C54CB0BD885\",\n" +
                        "      \"headimage\": \"/storage/emulated/0/Download/03-28-33-613e24431257c9bb_400.jpg\",\n" +
                        "      \"bottleName\": \"中华人民共和国\",\n" +
                        "      \"textData\": \"中华人民共和国\",\n" +
                        "      \"imageData\": \"中华人民共和国\",\n" +
                        "      \"title\": \"中华人民共和国\",\n" +
                        "      \"voiceNumber\": \"中华人民共和国\",\n" +
                        "      \"dataType\": \"0\",\n" +
                        "      \"creatdDate\": \"中华人民共和国\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"reginonID\": \"7A52C1E4A3064127B6C20C54CB0BD886\",\n" +
                        "      \"headimage\": \"/storage/emulated/0/Download/03-28-33-613e24431257c9bb_400.jpg\",\n" +
                        "      \"bottleName\": \"中华人民共和国\",\n" +
                        "      \"textData\": \"中华人民共和国\",\n" +
                        "      \"imageData\": \"中华人民共和国\",\n" +
                        "      \"title\": \"中华人民共和国\",\n" +
                        "      \"voiceNumber\": \"中华人民共和国\",\n" +
                        "      \"dataType\": \"0\",\n" +
                        "      \"creatdDate\": \"中华人民共和国\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"reginonID\": \"7A52C1E4A3064127B6C20C54CB0BD887\",\n" +
                        "      \"headimage\": \"/storage/emulated/0/Download/03-28-33-613e24431257c9bb_400.jpg\",\n" +
                        "      \"bottleName\": \"中华人民共和国\",\n" +
                        "      \"textData\": \"中华人民共和国\",\n" +
                        "      \"imageData\": \"中华人民共和国\",\n" +
                        "      \"title\": \"中华人民共和国\",\n" +
                        "      \"voiceNumber\": \"中华人民共和国\",\n" +
                        "      \"dataType\": \"0\",\n" +
                        "      \"creatdDate\": \"中华人民共和国\"\n" +
                        "    }\n" +
                        "\n" +
                        "]\n" +
                        "}\n";
                final String finalResult = result;
                ((DriftinBottleListActivity)mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //此时已在主线程中，可以更新UI了
                        closeDataDialog();
                        Gson gson = new Gson();
                        bottleBean = gson.fromJson(finalResult,BottleBean0.class);
                        if(bottleBean == null){
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
               final String result ="{\n" +
                        "  \"code\": 0,\n" +
                        "  \"msg\": \"请求正常返回\",\n" +
                        "  \"totalCount\": 3479,\n" +
                        "  \"data\": [\n" +
                        "    {\n" +
                        "      \"reginonID\": \"7A52C1E4A3064127B6C20C54CB0BD88C\",\n" +
                        "      \"headimage\": \"/storage/emulated/0/Download/03-28-33-613e24431257c9bb_400.jpg\",\n" +
                        "      \"bottleName\": \"北京\",\n" +
                        "      \"textData\": \"\",\n" +
                        "      \"imageData\": \"\",\n" +
                        "      \"title\": \"中华人民共和国\",\n" +
                        "      \"voiceNumber\": \"\",\n" +
                        "      \"dataType\": \"0\",\n" +
                        "      \"creatdDate\": \"2018-6-25 17:45:00\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"reginonID\": \"7A52C1E4A3064127B6C20C54CB0BD880\",\n" +
                        "      \"headimage\": \"/storage/emulated/0/Download/03-28-33-613e24431257c9bb_400.jpg\",\n" +
                        "      \"bottleName\": \"上海\",\n" +
                        "      \"textData\": \"\",\n" +
                        "      \"imageData\": \"\",\n" +
                        "      \"title\": \"中华人民共和国\",\n" +
                        "      \"voiceNumber\": \"\",\n" +
                        "      \"dataType\": \"0\",\n" +
                        "      \"creatdDate\": \"2018-6-25 17:45:01\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"reginonID\": \"7A52C1E4A3064127B6C20C54CB0BD881\",\n" +
                        "      \"headimage\": \"/storage/emulated/0/Download/03-28-33-613e24431257c9bb_400.jpg\",\n" +
                        "      \"bottleName\": \"北京\",\n" +
                        "      \"textData\": \"\",\n" +
                        "      \"imageData\": \"/storage/emulated/0/Download/03-28-33-613e24431257c9bb_400.jpg\",\n" +
                        "      \"title\": \"\",\n" +
                        "      \"voiceNumber\": \"\",\n" +
                        "      \"dataType\": \"1\",\n" +
                        "      \"creatdDate\": \"2018-6-25 17:45:05\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"reginonID\": \"7A52C1E4A3064127B6C20C54CB0BD882\",\n" +
                        "      \"headimage\": \"/storage/emulated/0/Download/03-28-33-613e24431257c9bb_400.jpg\",\n" +
                        "      \"bottleName\": \"中华人民共和国\",\n" +
                        "      \"textData\": \"中华人民共和国\",\n" +
                        "      \"imageData\": \"中华人民共和国\",\n" +
                        "      \"title\": \"中华人民共和国\",\n" +
                        "      \"voiceNumber\": \"10s\",\n" +
                        "      \"dataType\": \"2\",\n" +
                        "      \"creatdDate\": \"中华人民共和国\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"reginonID\": \"7A52C1E4A3064127B6C20C54CB0BD883\",\n" +
                        "      \"headimage\": \"/storage/emulated/0/Download/03-28-33-613e24431257c9bb_400.jpg\",\n" +
                        "      \"bottleName\": \"中华人民共和国\",\n" +
                        "      \"textData\": \"中华人民共和国\",\n" +
                        "      \"imageData\": \"中华人民共和国\",\n" +
                        "      \"title\": \"中华人民共和国\",\n" +
                        "      \"voiceNumber\": \"中华人民共和国\",\n" +
                        "      \"dataType\": \"0\",\n" +
                        "      \"creatdDate\": \"中华人民共和国\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"reginonID\": \"7A52C1E4A3064127B6C20C54CB0BD884\",\n" +
                        "      \"headimage\": \"/storage/emulated/0/Download/03-28-33-613e24431257c9bb_400.jpg\",\n" +
                        "      \"bottleName\": \"中华人民共和国\",\n" +
                        "      \"textData\": \"中华人民共和国\",\n" +
                        "      \"imageData\": \"中华人民共和国\",\n" +
                        "      \"title\": \"中华人民共和国\",\n" +
                        "      \"voiceNumber\": \"中华人民共和国\",\n" +
                        "      \"dataType\": \"0\",\n" +
                        "      \"creatdDate\": \"中华人民共和国\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"reginonID\": \"7A52C1E4A3064127B6C20C54CB0BD885\",\n" +
                        "      \"headimage\": \"/storage/emulated/0/Download/03-28-33-613e24431257c9bb_400.jpg\",\n" +
                        "      \"bottleName\": \"中华人民共和国\",\n" +
                        "      \"textData\": \"中华人民共和国\",\n" +
                        "      \"imageData\": \"中华人民共和国\",\n" +
                        "      \"title\": \"中华人民共和国\",\n" +
                        "      \"voiceNumber\": \"中华人民共和国\",\n" +
                        "      \"dataType\": \"0\",\n" +
                        "      \"creatdDate\": \"中华人民共和国\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"reginonID\": \"7A52C1E4A3064127B6C20C54CB0BD886\",\n" +
                        "      \"headimage\": \"/storage/emulated/0/Download/03-28-33-613e24431257c9bb_400.jpg\",\n" +
                        "      \"bottleName\": \"中华人民共和国\",\n" +
                        "      \"textData\": \"中华人民共和国\",\n" +
                        "      \"imageData\": \"中华人民共和国\",\n" +
                        "      \"title\": \"中华人民共和国\",\n" +
                        "      \"voiceNumber\": \"中华人民共和国\",\n" +
                        "      \"dataType\": \"0\",\n" +
                        "      \"creatdDate\": \"中华人民共和国\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"reginonID\": \"7A52C1E4A3064127B6C20C54CB0BD887\",\n" +
                        "      \"headimage\": \"/storage/emulated/0/Download/03-28-33-613e24431257c9bb_400.jpg\",\n" +
                        "      \"bottleName\": \"中华人民共和国\",\n" +
                        "      \"textData\": \"中华人民共和国\",\n" +
                        "      \"imageData\": \"中华人民共和国\",\n" +
                        "      \"title\": \"中华人民共和国\",\n" +
                        "      \"voiceNumber\": \"中华人民共和国\",\n" +
                        "      \"dataType\": \"0\",\n" +
                        "      \"creatdDate\": \"中华人民共和国\"\n" +
                        "    }\n" +
                        "\n" +
                        "]\n" +
                        "}\n";
                ((DriftinBottleListActivity)mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //此时已在主线程中，可以更新UI了
                        closeDataDialog();
                        ToastUtils.show(errorInfo.getMessage());
                        Gson gson = new Gson();
                        bottleBean = gson.fromJson(result,BottleBean0.class);
                        if(bottleBean == null){
                            ToastUtils.show(ErrorInfo.ErrorCode.valueOf(-5).getMessage());
                            return;
                        }else {
                            refreshUI();
                        }

                    }
                });

            }
        }, CacheMode.DEFAULT);
    }

    private void refreshUI(){
        for(BottleBean0 item : bottleBean.result){
            if(App.bottleIds.contains(item.reginonID)){
                item.bIsRead = true;
            }
        }
        bottleAdatper.setData(bottleBean.result);
        bottleAdatper.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(int position) {
        BottleBean0 item = bottleBean.result.get(position);
        if(!App.bottleIds.contains(item.reginonID)) {
            App.bottleIds.add(item.reginonID);
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("key",item);
        intentActivity(this,DriftinBottleMessageActivity.class,false,bundle);

    }
}
