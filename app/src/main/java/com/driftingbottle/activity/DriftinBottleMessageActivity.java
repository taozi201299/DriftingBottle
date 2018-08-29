package com.driftingbottle.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.driftinbottle.callback.ErrorInfo;
import com.driftinbottle.callback.RequestCallback;
import com.driftinbottle.httputils.HttpUtils;
import com.driftingbottle.App;
import com.driftingbottle.R;
import com.driftingbottle.adapter.ChatAdapter;
import com.driftingbottle.adapter.CommonFragmentPagerAdapter;
import com.driftingbottle.base.BaseActivity;
import com.driftingbottle.bean.BottleBean0;
import com.driftingbottle.bean.MessageBean0;
import com.driftingbottle.fragment.ChatEmotionFragment;
import com.driftingbottle.fragment.ChatFunctionFragment;
import com.driftingbottle.utils.CommonUtils;
import com.driftingbottle.utils.GlobalOnItemClickManagerUtils;
import com.driftingbottle.utils.ToastUtils;
import com.driftingbottle.view.PullRecyclerView;
import com.driftingbottle.widget.EmotionInputDetector;
import com.driftingbottle.widget.NoScrollViewPager;
import com.driftingbottle.widget.StateButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okhttputils.cache.CacheMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.github.rockerhieu.emojicon.EmojiconEditText;

import static com.driftingbottle.utils.Constant.DEFAULT_BUNDLE_NAME;


/**
 * Created by jidan on 18-6-12.
 */

public class DriftinBottleMessageActivity extends BaseActivity implements PullRecyclerView.OnPullRefreshListener {
    @BindView(R.id.chatRecyclerView)
    RecyclerView chatRecyclerView;
    @BindView(R.id.layout_bar)
    LinearLayout action_bar;
    @BindView(R.id.index_set)
    Button index_set;
    @BindView(R.id.index_set1)
    Button index_set1;

    @BindView(R.id.emotion_voice)
    ImageView emotionVoice;
    @BindView(R.id.edit_text)
    EmojiconEditText editText;
    @BindView(R.id.voice_text)
    TextView voiceText;
    @BindView(R.id.emotion_button)
    ImageView emotionButton;
    @BindView(R.id.emotion_add)
    ImageView emotionAdd;
    @BindView(R.id.emotion_send)
    StateButton emotionSend;
    @BindView(R.id.viewpager)
    NoScrollViewPager viewpager;
    @BindView(R.id.emotion_layout)
    RelativeLayout emotionLayout;

    ChatAdapter chatAdapter;
    private BottleBean0 bottleBean;
    ArrayList<MessageBean0>datas = new ArrayList<>();
    int iPageIndex = 0;
    boolean bFinish = false;



    private EmotionInputDetector mDetector;
    private ArrayList<Fragment> fragments;
    private ChatEmotionFragment chatEmotionFragment;
    private ChatFunctionFragment chatFunctionFragment;
    private CommonFragmentPagerAdapter adapter;
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
    }

    private void initWidget() {
        editText.clearFocus();
        editText.setCursorVisible(false);
        Drawable bottom = getResources().getDrawable(R.drawable.line_normal);// 获取res下的图片drawable
        bottom.setBounds(0, 0, bottom.getMinimumWidth(), bottom.getMinimumHeight());// 一定要设置setBounds();
        editText.setCompoundDrawables(null, null, null, bottom);
        fragments = new ArrayList<>();
        chatEmotionFragment = new ChatEmotionFragment();
        fragments.add(chatEmotionFragment);
        chatFunctionFragment = new ChatFunctionFragment();
        fragments.add(chatFunctionFragment);
        adapter = new CommonFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setCursorVisible(true);
                Drawable bottom = getResources().getDrawable(R.drawable.line);// 获取res下的图片drawable
                bottom.setBounds(0, 0, bottom.getMinimumWidth(), bottom.getMinimumHeight());// 一定要设置setBounds();
                editText.setCompoundDrawables(null, null, null, bottom);
            }
        });
        mDetector = EmotionInputDetector.with(this)
                .setEmotionView(emotionLayout)
                .setViewPager(viewpager)
                .bindToContent(chatRecyclerView)
                .bindToEditText(editText)
                .bindToEmotionButton(emotionButton)
                .bindToAddButton(emotionAdd)
                .bindToSendButton(emotionSend)
                .bindToVoiceButton(emotionVoice)
                .bindToVoiceText(voiceText)
                .build();

        GlobalOnItemClickManagerUtils globalOnItemClickListener = GlobalOnItemClickManagerUtils.getInstance(this);
        globalOnItemClickListener.attachToEditText(editText);
    }

    @Override
    public void initData() {
        datas.clear();
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
        initWidget();

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
        ArrayList<MessageBean0> results = new ArrayList<>();
        results.addAll(datas);
        for(MessageBean0 item :datas){
            if(item.dataType.equals("-100")){
                results.remove(item);
            }
        }
        App.bottleCount.put(bottleBean.regionID,results.size());
        chatAdapter.setData(results);
        chatAdapter.notifyDataSetChanged();
    }
    private void processResult(){
        String []arrayContent;
        for(int i = 0; i <datas.size(); i++){
            MessageBean0 item = datas.get(i);
            if(item.dataType.equals("3") && !item.textData.contains("#")){
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
            }else if(item.dataType.equals("0") || (item.dataType.equals("3"))) {
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
                            if(item.dataType.equals("0")) {
                                datas.add(i, newMessage);
                            }else if(item.dataType.equals("3")){
                                if(item.orderType.equals("0")){
                                    datas.add(i+1,newMessage);
                                }else {
                                    datas.add(i,newMessage);
                                }
                            }
                        }
                        if(item.dataType.equals("0")) {
                            item.dataType = "-100";
                        }else if(item.dataType.equals("3")){
                            item.dataType = "1";
                        }
                    }
                }
            }
        }
    }
    //处理后退键的情况
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK ){
            if (mDetector.interceptBackPress()){
                return  true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
