package com.driftingbottle.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.driftingbottle.utils.ToastUtils;
import com.driftingbottle.view.PullRecyclerView;
import com.google.gson.Gson;
import com.lzy.okhttputils.cache.CacheMode;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by jidan on 18-6-12.
 */

public class DriftinBottleListActivity extends BaseActivity  implements CommonAdapter.OnItemClickListener{

    @BindView(R.id.bottleRecyclerView)
    RecyclerView bottleRecyclerView;
    @BindView(R.id.tv_emtry_message)
    TextView tv_emtry_message;
    private BottleBean bottleBean ;
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
        getBottle();
    }

    @Override
    public void initView() {
        showDataLoadingDialog();
        showTitle("我的瓶子" +"("+ MainActivity.iTotalCount +")");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置RecyclerView 布局
        bottleRecyclerView.setLayoutManager(layoutManager);
        bottleAdatper = new BottleAdatper(this,R.layout.activity_bottle_item);
        bottleRecyclerView.setAdapter(bottleAdatper);


    }
    private void getBottle(){
        String url = "http://192.168.1.8:8080/wcsps-supervision/v1/att/ad/base/attAdBases/";
        HashMap<String,String>params = new HashMap<>();
        params.put("clientId", "dd");
        HttpUtils.getInstance().requestGet(url, params, url, new RequestCallback<String>() {
            @Override
            public void onResponse(String result) {
                result ="{\n" +
                        "  \"code\": 0,\n" +
                        "  \"msg\": \"请求正常返回\",\n" +
                        "  \"totalCount\": 3479,\n" +
                        "  \"data\": [\n" +
                        "    {\n" +
                        "      \"bottleId\": \"7A52C1E4A3064127B6C20C54CB0BD88C\",\n" +
                        "      \"bottleImg\": \"000000000000\",\n" +
                        "      \"bottleName\": \"中华人民共和国\",\n" +
                        "      \"messageContent\": \"中华人民共和国\",\n" +
                        "      \"messageType\": \"0\",\n" +
                        "      \"messageDisplayTime\": \"中华人民共和国\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"bottleId\": \"7A52C1E4A3064127B6C20C54CB0BD881\",\n" +
                        "      \"bottleImg\": \"000000000000\",\n" +
                        "      \"bottleName\": \"中华人民共和国\",\n" +
                        "      \"messageContent\": \"中华人民共和国\",\n" +
                        "      \"messageType\": \"0\",\n" +
                        "      \"messageDisplayTime\": \"中华人民共和国\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"bottleId\": \"7A52C1E4A3064127B6C20C54CB0BD882\",\n" +
                        "      \"bottleImg\": \"000000000000\",\n" +
                        "      \"bottleName\": \"中华人民共和国\",\n" +
                        "      \"messageContent\": \"中华人民共和国\",\n" +
                        "      \"messageType\": \"0\",\n" +
                        "      \"messageDisplayTime\": \"中华人民共和国\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"bottleId\": \"7A52C1E4A3064127B6C20C54CB0BD883\",\n" +
                        "      \"bottleImg\": \"000000000000\",\n" +
                        "      \"bottleName\": \"中华人民共和国\",\n" +
                        "      \"messageContent\": \"中华人民共和国\",\n" +
                        "      \"messageType\": \"0\",\n" +
                        "      \"messageDisplayTime\": \"中华人民共和国\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"bottleId\": \"7A52C1E4A3064127B6C20C54CB0BD884\",\n" +
                        "      \"bottleImg\": \"000000000000\",\n" +
                        "      \"bottleName\": \"中华人民共和国\",\n" +
                        "      \"messageContent\": \"中华人民共和国\",\n" +
                        "      \"messageType\": \"0\",\n" +
                        "      \"messageDisplayTime\": \"中华人民共和国\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"bottleId\": \"7A52C1E4A3064127B6C20C54CB0BD885\",\n" +
                        "      \"bottleImg\": \"000000000000\",\n" +
                        "      \"bottleName\": \"中华人民共和国\",\n" +
                        "      \"messageContent\": \"中华人民共和国\",\n" +
                        "      \"messageType\": \"0\",\n" +
                        "      \"messageDisplayTime\": \"中华人民共和国\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"bottleId\": \"7A52C1E4A3064127B6C20C54CB0BD886\",\n" +
                        "      \"bottleImg\": \"000000000000\",\n" +
                        "      \"bottleName\": \"中华人民共和国\",\n" +
                        "      \"messageContent\": \"中华人民共和国\",\n" +
                        "      \"messageType\": \"0\",\n" +
                        "      \"messageDisplayTime\": \"中华人民共和国\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"bottleId\": \"7A52C1E4A3064127B6C20C54CB0BD887\",\n" +
                        "      \"bottleImg\": \"000000000000\",\n" +
                        "      \"bottleName\": \"中华人民共和国\",\n" +
                        "      \"messageContent\": \"中华人民共和国\",\n" +
                        "      \"messageType\": \"0\",\n" +
                        "      \"messageDisplayTime\": \"中华人民共和国\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"bottleId\": \"7A52C1E4A3064127B6C20C54CB0BD888\",\n" +
                        "      \"bottleImg\": \"000000000000\",\n" +
                        "      \"bottleName\": \"中华人民共和国\",\n" +
                        "      \"messageContent\": \"中华人民共和国\",\n" +
                        "      \"messageType\": \"0\",\n" +
                        "      \"messageDisplayTime\": \"中华人民共和国\"\n" +
                        "    }\n" +
                        "\n" +
                        "]\n" +
                        "}\n";
                closeDataDialog();
                Gson gson = new Gson();
                bottleBean = gson.fromJson(result,BottleBean.class);
                if(bottleBean == null){
                    ToastUtils.show(ErrorInfo.ErrorCode.valueOf(-5).getMessage());
                    return;
                }else {
                    refreshUI();
                }

            }

            @Override
            public void onFailure(ErrorInfo.ErrorCode errorInfo) {
                closeDataDialog();
                ToastUtils.show(errorInfo.getMessage());
                String result ="{\n" +
                        "  \"code\": 0,\n" +
                        "  \"msg\": \"请求正常返回\",\n" +
                        "  \"totalCount\": 3479,\n" +
                        "  \"data\": [\n" +
                        "    {\n" +
                        "      \"bottleId\": \"7A52C1E4A3064127B6C20C54CB0BD88C\",\n" +
                        "      \"bottleImg\": \"000000000000\",\n" +
                        "      \"bottleName\": \"中华人民共和国\",\n" +
                        "      \"messageContent\": \"中华人民共和国\",\n" +
                        "      \"messageType\": \"0\",\n" +
                        "      \"messageDisplayTime\": \"中华人民共和国\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"bottleId\": \"7A52C1E4A3064127B6C20C54CB0BD881\",\n" +
                        "      \"bottleImg\": \"000000000000\",\n" +
                        "      \"bottleName\": \"中华人民共和国\",\n" +
                        "      \"messageContent\": \"中华人民共和国\",\n" +
                        "      \"messageType\": \"0\",\n" +
                        "      \"messageDisplayTime\": \"中华人民共和国\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"bottleId\": \"7A52C1E4A3064127B6C20C54CB0BD882\",\n" +
                        "      \"bottleImg\": \"000000000000\",\n" +
                        "      \"bottleName\": \"中华人民共和国\",\n" +
                        "      \"messageContent\": \"中华人民共和国\",\n" +
                        "      \"messageType\": \"0\",\n" +
                        "      \"messageDisplayTime\": \"中华人民共和国\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"bottleId\": \"7A52C1E4A3064127B6C20C54CB0BD883\",\n" +
                        "      \"bottleImg\": \"000000000000\",\n" +
                        "      \"bottleName\": \"中华人民共和国\",\n" +
                        "      \"messageContent\": \"中华人民共和国\",\n" +
                        "      \"messageType\": \"0\",\n" +
                        "      \"messageDisplayTime\": \"中华人民共和国\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"bottleId\": \"7A52C1E4A3064127B6C20C54CB0BD884\",\n" +
                        "      \"bottleImg\": \"000000000000\",\n" +
                        "      \"bottleName\": \"中华人民共和国\",\n" +
                        "      \"messageContent\": \"中华人民共和国\",\n" +
                        "      \"messageType\": \"0\",\n" +
                        "      \"messageDisplayTime\": \"中华人民共和国\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"bottleId\": \"7A52C1E4A3064127B6C20C54CB0BD885\",\n" +
                        "      \"bottleImg\": \"000000000000\",\n" +
                        "      \"bottleName\": \"中华人民共和国\",\n" +
                        "      \"messageContent\": \"中华人民共和国\",\n" +
                        "      \"messageType\": \"0\",\n" +
                        "      \"messageDisplayTime\": \"中华人民共和国\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"bottleId\": \"7A52C1E4A3064127B6C20C54CB0BD886\",\n" +
                        "      \"bottleImg\": \"000000000000\",\n" +
                        "      \"bottleName\": \"中华人民共和国\",\n" +
                        "      \"messageContent\": \"中华人民共和国\",\n" +
                        "      \"messageType\": \"0\",\n" +
                        "      \"messageDisplayTime\": \"中华人民共和国\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"bottleId\": \"7A52C1E4A3064127B6C20C54CB0BD887\",\n" +
                        "      \"bottleImg\": \"000000000000\",\n" +
                        "      \"bottleName\": \"中华人民共和国\",\n" +
                        "      \"messageContent\": \"中华人民共和国\",\n" +
                        "      \"messageType\": \"0\",\n" +
                        "      \"messageDisplayTime\": \"中华人民共和国\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"bottleId\": \"7A52C1E4A3064127B6C20C54CB0BD888\",\n" +
                        "      \"bottleImg\": \"000000000000\",\n" +
                        "      \"bottleName\": \"中华人民共和国\",\n" +
                        "      \"messageContent\": \"中华人民共和国\",\n" +
                        "      \"messageType\": \"0\",\n" +
                        "      \"messageDisplayTime\": \"中华人民共和国\"\n" +
                        "    }\n" +
                        "\n" +
                        "]\n" +
                        "}\n";
                closeDataDialog();
                Gson gson = new Gson();
                bottleBean = gson.fromJson(result,BottleBean.class);
                if(bottleBean == null){
                    ToastUtils.show(ErrorInfo.ErrorCode.valueOf(-5).getMessage());
                    return;
                }else {
                    refreshUI();
                }

            }
        }, CacheMode.DEFAULT);
    }

    private void refreshUI(){
        for(BottleBean item : bottleBean.result){
            if(App.bottleIds.contains(item.getBottleId())){
                item.setbIsRead(true);
            }
        }
        bottleAdatper.setData(bottleBean.result);
        bottleAdatper.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(int position) {
        BottleBean item = bottleBean.result.get(position);
        if(!App.bottleIds.contains(item.getBottleId())) {
            App.bottleIds.add(item.getBottleId());
        }
        for(int i = 0; i< App.bottleIds.size() ; i++){
            Log.d("111111111111111111111",App.bottleIds.get(i));
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("key",item);
        intentActivity(this,DriftinBottleMessageActivity.class,false,bundle);

    }
}
