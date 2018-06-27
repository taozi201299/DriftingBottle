package com.driftingbottle.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.andview.refreshview.XRefreshView;
import com.driftinbottle.callback.ErrorInfo;
import com.driftinbottle.callback.RequestCallback;
import com.driftinbottle.httputils.HttpUtils;
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
import com.lzy.okhttputils.cache.CacheMode;

import java.util.ArrayList;
import java.util.HashMap;

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
    ChatAdapter chatAdapter;
    private BottleBean0 bottleBean;
    private MessageBean0 messageBean;
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
    protected void onDestroy() {
        super.onDestroy();
        iPageIndex = 0;
        bFinish = false;
    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getBundleExtra(DEFAULT_BUNDLE_NAME);
        bottleBean = (BottleBean0) bundle.getSerializable("key");
        chatAdapter.setMyImage(bottleBean.headimage);
        showTitle(bottleBean.bottleName);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getMessage();
            }
        }).start();
    }
    private void getMessage(){
        String url = "http://192.168.1.8:8080/wcsps-supervision/v1/att/ad/base/attAdBases/";
        HashMap<String,String> params = new HashMap<>();
        params.put("clientID", CommonUtils.getUniqueId(mContext));
        params.put("regionID",bottleBean.reginonID);
        params.put("pageIndex",String.valueOf(iPageIndex));
        params.put("pageCount","9");
        HttpUtils.getInstance().requestGet(url, params, url, new RequestCallback<String>() {
            @Override
            public void onResponse(String result) {
                iPageIndex ++;
                result = " {\n" +
                        "  \"code\": 0,\n" +
                        "  \"msg\": \"请求正常返回\",\n" +
                        " \"totalCount\": 0,\n" +
                        "  \"data\": [\n" +
                        "    {\n" +
                        "      \"dateType\": \"2\",   \n" +
                        "      \"CreatedDate\": \"2018-06-15 18:49:00\",  \n" +
                        "      \"textData\": \"000000000000000\",  \n" +
                        "      \"imageData\": \"\",  \n" +
                        "      \"voiceNumber\": \"10\",  \n" +
                        "      \"answerType\": \"0\"   \n" +
                        "},\n" +
                        "    {\n" +
                        "      \"dateType\": \"3\",   \n" +
                        "      \"CreatedDate\": \"2018-06-15 18:49:00\",  \n" +
                        "      \"textData\": \"\uD83C\uDF89\uD83C\uDF81\uD83D\uDE37\uD83D\uDC7B\uD83D\uDE4F\uD83C\uDF4E\",  \n" +
                        "      \"imageData\": \"/storage/emulated/0/Download/03-28-33-613e24431257c9bb_400.jpg\",  \n" +
                        "      \"voiceNumber\": \"\",  \n" +
                        "      \"answerType\": \"0\"   \n" +
                        "},\n" +
                        "    {\n" +
                        "      \"dateType\": \"0\",   \n" +
                        "      \"CreatedDate\": \"2018-06-15 18:49:00\",  \n" +
                        "      \"textData\": \"不辛苦睡觉睡觉[调皮][发怒][大哭][尴尬][大哭]\uD83D\uDE04\",  \n" +
                        "      \"imageData\": \"\",  \n" +
                        "      \"voiceNumber\": \"\",  \n" +
                        "      \"answerType\": \"1\"   \n" +
                        "}\n" +
                        "]\n" +
                        "}\n";
                final String finalResult = result;
                ((DriftinBottleMessageActivity)mContext).
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeDataDialog();
                        xRefreshView.stopRefresh();
                        Gson gson = new Gson();
                        messageBean = gson.fromJson(finalResult,MessageBean0.class);
                        if(messageBean.result.size() == 0){
                            ToastUtils.show("没有更多数据了");
                            return;
                        }
                        refresh();
                    };
                });
            }
            @Override
            public void onFailure(ErrorInfo.ErrorCode errorInfo) {
               final String  result = " {\n" +
                        "  \"code\": 0,\n" +
                        "  \"msg\": \"请求正常返回\",\n" +
                        " \"totalCount\": 0,\n" +
                        "  \"data\": [\n" +
                        "    {\n" +
                        "      \"dateType\": \"2\",   \n" +
                        "      \"CreatedDate\": \"2018-06-15 18:49:00\",  \n" +
                        "      \"textData\": \"000000000000000\",  \n" +
                        "      \"imageData\": \"\",  \n" +
                        "      \"voiceNumber\": \"10\",  \n" +
                        "      \"answerType\": \"0\"   \n" +
                        "},\n" +
                        "    {\n" +
                        "      \"dateType\": \"3\",   \n" +
                        "      \"CreatedDate\": \"2018-06-15 18:49:00\",  \n" +
                        "      \"textData\": \"不辛苦睡觉睡觉[调皮][发怒][大哭][尴尬][大哭]\uD83D\uDE04\",  \n" +
                        "      \"imageData\": \"/storage/emulated/0/Download/03-28-33-613e24431257c9bb_400.jpg\",  \n" +
                        "      \"voiceNumber\": \"\",  \n" +
                        "      \"answerType\": \"0\"   \n" +
                        "},\n" +
                        "    {\n" +
                        "      \"dateType\": \"0\",   \n" +
                        "      \"CreatedDate\": \"2018-06-15 18:49:00\",  \n" +
                        "      \"textData\": \"不辛苦睡觉睡觉[调皮][发怒][大哭][尴尬][大哭]\uD83D\uDE04\",  \n" +
                        "      \"imageData\": \"\",  \n" +
                        "      \"voiceNumber\": \"\",  \n" +
                        "      \"answerType\": \"1\"   \n" +
                        "}\n" +
                        "]\n" +
                        "}\n";
                ((DriftinBottleMessageActivity)mContext).
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                closeDataDialog();
                                xRefreshView.stopRefresh(false);
                                Gson gson = new Gson();
                                messageBean = gson.fromJson(result,MessageBean0.class);
                                if(messageBean.result.size() == 0){
                                    ToastUtils.show("没有更多数据了");
                                    return;
                                }
                                refresh();
                            };
                        });

            }
        }, CacheMode.DEFAULT);
    }
    @Override
    public void initView() {
        showDataLoadingDialog();
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        //设置RecyclerView 布局
        chatRecyclerView.setLayoutManager(layoutmanager);
        chatAdapter = new ChatAdapter(this);
        chatRecyclerView.setAdapter(chatAdapter);
        xRefreshView.setPullRefreshEnable(true);
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
        datas.clear();
        datas.addAll(messageBean.result);
        chatAdapter.setData(datas);
        chatAdapter.notifyDataSetChanged();
    }
    private void processResult(){
        for(MessageBean0 item : messageBean.result){
            if(item.dateType.equals("3")){
                MessageBean0 messageBean = new MessageBean0();
                messageBean.dateType = "1";
                messageBean.answerType = item.answerType;
                messageBean.CreatedDate = item.CreatedDate;
                messageBean.imageData = item.imageData;
                messageBean.voiceNumber = "";
                messageBean.textData = "";
                datas.add(messageBean);
                item.dateType = "0";
            }
        }

    }
}
