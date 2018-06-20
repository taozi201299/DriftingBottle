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
import com.driftingbottle.bean.MessageBean;
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
    private BottleBean  bottleBean;
    private MessageBean messageBean;
    ArrayList<MessageBean>datas = new ArrayList<>();
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
        showDataLoadingDialog();
        Bundle bundle = getIntent().getBundleExtra(DEFAULT_BUNDLE_NAME);
        bottleBean = (BottleBean) bundle.getSerializable("key");
        showTitle(bottleBean.getBottleName());
        chatAdapter.setMyImage(bottleBean.getBottleImg());
        getMessage();
    }
    private void getMessage(){
        String url = "http://192.168.1.8:8080/wcsps-supervision/v1/att/ad/base/attAdBases/";
        HashMap<String,String> params = new HashMap<>();
        params.put("clientId", "dd");
        HttpUtils.getInstance().requestGet(url, params, url, new RequestCallback<String>() {
            @Override
            public void onResponse(String result) {
                result = " {\n" +
                        "  \"code\": 0,\n" +
                        "  \"msg\": \"请求正常返回\",\n" +
                        " \"totalCount\": 0,\n" +
                        "  \"data\": [\n" +
                        "    {\n" +
                        "      \"messageId\": \"100\",\n" +
                        "      \"messageOwner\": \"0\",   \n" +
                        "      \"messageDisplayTime\": \"2018-06-15 18:49:00\",  \n" +
                        "      \"messageContent\": \"000000000000000\",  \n" +
                        "      \"messageType\": \"0\"   \n" +
                        "},\n" +
                        "    {\n" +
                        "       \"messageId\": \"100\",\n" +
                        "      \"messageOwner\": \"0\",   \n" +
                        "      \"messageDisplayTime\": \"2018-06-19 18:49:06\",  \n" +
                        "      \"messageContent\": \"啦啦啦啦啦啦啦啦(～￣▽￣)～\",  \n" +
                        "      \"messageType\": \"0\"   \n" +
                        "},\n" +
                        "    {\n" +
                        "       \"messageId\": \"100\",\n" +
                        "      \"messageOwner\": \"1\",   \n" +
                        "      \"messageDisplayTime\": \"2018-06-15 18:49:06\",  \n" +
                        "      \"messageContent\": \"哇哇哇哇^(*￣(oo)￣)^\",  \n" +
                        "      \"messageType\": \"1\"   \n" +
                        "}\n" +
                        "]\n" +
                        "}\n";
                closeDataDialog();
                xRefreshView.stopRefresh();
                Gson gson = new Gson();
                messageBean = gson.fromJson(result,MessageBean.class);
                if(messageBean.result.size() == 0){
                    ToastUtils.show("没有更多数据了");
                    return;
                }
                refresh();

            }
            @Override
            public void onFailure(ErrorInfo.ErrorCode errorInfo) {
                closeDataDialog();
                xRefreshView.stopRefresh(false);
                String result = " {\n" +
                        "  \"code\": 0,\n" +
                        "  \"msg\": \"请求正常返回\",\n" +
                        " \"totalCount\": 0,\n" +
                        "  \"data\": [\n" +
                        "    {\n" +
                        "      \"messageId\": \"100\",\n" +
                        "      \"messageOwner\": \"0\",   \n" +
                        "      \"messageDisplayTime\": \"2018-06-15 18:49:00\",  \n" +
                        "      \"messageContent\": \"000000000000000\",  \n" +
                        "      \"messageType\": \"0\"   \n" +
                        "},\n" +
                        "    {\n" +
                        "       \"messageId\": \"100\",\n" +
                        "      \"messageOwner\": \"0\",   \n" +
                        "      \"messageDisplayTime\": \"2018-06-16 15:49:06\",  \n" +
                        "      \"messageContent\": \"啦啦啦啦啦啦啦啦(～￣▽￣)～\",  \n" +
                        "      \"messageType\": \"0\"   \n" +
                        "},\n" +
                        "    {\n" +
                        "       \"messageId\": \"100\",\n" +
                        "      \"messageOwner\": \"1\",   \n" +
                        "      \"messageDisplayTime\": \"2018-06-16 18:49:06\",  \n" +
                        "      \"messageContent\": \"哇哇哇哇^(*￣(oo)￣)^\",  \n" +
                        "      \"messageType\": \"0\"   \n" +
                        "}\n" +
                        "]\n" +
                        "}\n";
                closeDataDialog();
                xRefreshView.stopRefresh();
                Gson gson = new Gson();
                messageBean = gson.fromJson(result,MessageBean.class);
                if(messageBean.result.size() == 0){
                    ToastUtils.show("没有更多数据了");
                    return;
                }
                refresh();

            }
        }, CacheMode.DEFAULT);
    }
    @Override
    public void initView() {
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
        datas.addAll(messageBean.result);
        chatAdapter.setData(datas);
        chatAdapter.notifyDataSetChanged();
    }
}
