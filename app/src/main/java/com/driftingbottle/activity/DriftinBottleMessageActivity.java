package com.driftingbottle.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.driftinbottle.callback.ErrorInfo;
import com.driftinbottle.callback.RequestCallback;
import com.driftinbottle.httputils.HttpUtils;
import com.driftingbottle.App;
import com.driftingbottle.R;
import com.driftingbottle.adapter.ChatAdapter;
import com.driftingbottle.base.BaseActivity;
import com.driftingbottle.bean.BottleBean;
import com.driftingbottle.bean.BottleBean0;
import com.driftingbottle.bean.MessageBean;
import com.driftingbottle.bean.MessageBean0;
import com.driftingbottle.utils.CommonUtils;
import com.driftingbottle.utils.ToastUtils;
import com.driftingbottle.view.PullRecyclerView;
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
 */

public class DriftinBottleMessageActivity extends BaseActivity implements PullRecyclerView.OnPullRefreshListener {
    @BindView(R.id.chatRecyclerView)
    RecyclerView chatRecyclerView;
    @BindView(R.id.xRefreshView)
    XRefreshView xRefreshView;
    @BindView(R.id.layout_bar)
    LinearLayout action_bar;
    @BindView(R.id.index_set)
    Button index_set;
    @BindView(R.id.index_set1)
    Button index_set1;
    ChatAdapter chatAdapter;
    private BottleBean0 bottleBean;
    ArrayList<MessageBean0>datas = new ArrayList<>();
    int iPageIndex = 0;
    boolean bFinish = false;
    @Override
    public int getLayoutId() {
        return R.layout.activity_chat_layout;
    }

    @Override
    public void initListener() {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPageIndex = 0;
        bFinish = false;
        xRefreshView.setPullRefreshEnable(false);
        xRefreshView.setPullLoadEnable(false);
    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getBundleExtra(DEFAULT_BUNDLE_NAME);
        bottleBean = (BottleBean0) bundle.getSerializable("key");
        chatAdapter.setMyImage(App.strIp + bottleBean.headimage);
        index_set.setVisibility(View.GONE);
        index_set1.setVisibility(View.VISIBLE);
        showTitle("来自"+ bottleBean.city +"的瓶子");
        new Thread(new Runnable() {
            @Override
            public void run() {
                getMessage();
            }
        }).start();
    }
    private void getMessage(){
        String url = App.strIp + "/WebRoot/ClientDetails";
        HashMap<String,String> params = new HashMap<>();
        params.put("clientID", CommonUtils.getUniqueId(mContext));
        params.put("regionID",bottleBean.regionID);
//
//        params.put("pageIndex",String.valueOf(iPageIndex));
//        params.put("pageCount","9");
        HttpUtils.getInstance().requestGet(url, params, url, new RequestCallback<String>() {
            @Override
            public void onResponse(String result) {
                iPageIndex ++;
                final String finalResult = result;
                ((DriftinBottleMessageActivity)mContext).
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeDataDialog();
                        xRefreshView.stopRefresh();
                        Gson gson = new Gson();
                        datas = gson.fromJson(finalResult,new TypeToken<List<MessageBean0>>(){}.getType());
                        if(datas.size() == 0){
                            ToastUtils.show("没有更多数据了");
                            return;
                        }
                        refresh();
                    };
                });
            }
            @Override
            public void onFailure(final ErrorInfo.ErrorCode errorInfo) {
                ((DriftinBottleMessageActivity)mContext).
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                closeDataDialog();
                                xRefreshView.stopRefresh(false);
                                ToastUtils.show(errorInfo.getMessage());
                            };
                        });

            }
        }, CacheMode.DEFAULT);
    }
    @Override
    public void initView() {

        action_bar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        showDataLoadingDialog();
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        //设置RecyclerView 布局
        chatRecyclerView.setLayoutManager(layoutmanager);
        chatAdapter = new ChatAdapter(this);
        chatRecyclerView.setAdapter(chatAdapter);
        xRefreshView.setPullRefreshEnable(false);
        xRefreshView.setPullLoadEnable(false);
        xRefreshView.setMoveHeadWhenDisablePullRefresh(false);
        xRefreshView.setDividerPadding(20);
        xRefreshView.setXRefreshViewListener(new XRefreshView.XRefreshViewListener() {
            @Override
            public void onRefresh() {
                iPageIndex ++ ;
                getMessage();
            }

            @Override
            public void onRefresh(boolean isPullDown) {

            }

            @Override
            public void onLoadMore(boolean isSilence) {

            }

            @Override
            public void onRelease(float direction) {

            }

            @Override
            public void onHeaderMove(double headerMovePercent, int offsetY) {

            }
        });

    }

    @Override
    public void onRefresh() {
        // TODO: 2018/6/14 分页加载message 1次10条
        getMessage();
    }

    @Override
    public void onLoadMore() {

    }
    private void refresh(){
        processResult();
        for(MessageBean0 item :datas){
            if(item.dataType.equals("-100")){
                datas.remove(item);
            }
        }
        chatAdapter.setData(datas);
        chatAdapter.notifyDataSetChanged();
    }
    private void processResult(){
        int size = datas.size();
        String []arrayContent;
        for(int i = 0; i <size; i++){
            MessageBean0 item = datas.get(i);
            if(item.dataType.equals("3")){
                MessageBean0 messageBean = new MessageBean0();
                messageBean.dataType = "1";
                messageBean.answerType = item.answerType;
                messageBean.CreatedDate = item.CreatedDate;
                messageBean.imageData = item.imageData;
                messageBean.voiceNumber = "";
                messageBean.textData = "";
                if(item.orderType.equals("0")){
                    datas.add(i,messageBean);
                }else {
                    datas.add(i+1,messageBean);
                }
                item.dataType = "0";
            }else if(item.dataType.equals("0")) {
                if(item.textData.contains("#")){
                    arrayContent = item.textData.split("#");
                    if(arrayContent == null || arrayContent.length == 0){
                        return;
                    }else {
                        for(int index = arrayContent.length -1 ; index >=0; index --){
                            MessageBean0 newMessage = new MessageBean0();
                            newMessage.dataType = "0";
                            newMessage.answerType = item.answerType;
                            newMessage.CreatedDate = item.CreatedDate;
                            newMessage.imageData = item.imageData;
                            newMessage.voiceNumber = "";
                            newMessage.textData = arrayContent[index];
                            datas.add(i,newMessage);
                        }
                        item.dataType = "-100";
                    }
                }
            }
        }

    }
}
