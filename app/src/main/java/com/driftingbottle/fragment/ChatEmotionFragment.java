package  com.driftingbottle.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.driftingbottle.App;
import com.driftingbottle.R;
import com.driftingbottle.adapter.EmotionGridViewAdapter;
import com.driftingbottle.adapter.EmotionPagerAdapter;
import com.driftingbottle.base.BaseFragment;
import com.driftingbottle.utils.CommonUtils;
import com.driftingbottle.utils.EmotionUtils;
import com.driftingbottle.utils.GlobalOnItemClickManagerUtils;
import com.driftingbottle.utils.Utils;
import com.driftingbottle.widget.IndicatorView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：Rance on 2016/12/13 16:01
 * 邮箱：rance935@163.com
 */
public class ChatEmotionFragment extends BaseFragment {
    @BindView(R.id.fragment_chat_vp)
    ViewPager fragmentChatVp;
    @BindView(R.id.fragment_chat_group)
    IndicatorView fragmentChatGroup;
    @BindView(R.id.rg_tab)
    RadioGroup rgTabs;
    private View rootView;
    private EmotionPagerAdapter emotionPagerAdapter;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_chat_emotion, container, false);
            ButterKnife.bind(this, rootView);
            initWidget();
        }
        return rootView;
    }

    private void initWidget() {
        fragmentChatVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int oldPagerPos = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                fragmentChatGroup.playByStartPointToNext(oldPagerPos, position);
                oldPagerPos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        initTab();
        initEmotion();
    }

    /**
     * 初始化表情面板
     * 思路：获取表情的总数，按每行存放7个表情，动态计算出每个表情所占的宽度大小（包含间距），
     *      而每个表情的高与宽应该是相等的，这里我们约定只存放3行
     *      每个面板最多存放7*3=21个表情，再减去一个删除键，即每个面板包含20个表情
     *      根据表情总数，循环创建多个容量为20的List，存放表情，对于大小不满20进行特殊
     *      处理即可。
     */
    private void initEmotion() {
        // 获取屏幕宽度
        int screenWidth = App.screenWidth;
        // item的间距
        int spacing = Utils.dp2px(getActivity(), 12);
        // 动态计算item的宽度和高度
        int itemWidth = (screenWidth - spacing * 8) / 7;
        //动态计算gridview的总高度
        int gvHeight = itemWidth * 3 + spacing * 6;

        List<GridView> emotionViews = new ArrayList<>();
        List<String> emotionNames = new ArrayList<>();
        // 遍历所有的表情的key
        for (String emojiName : EmotionUtils.EMOTION_STATIC_MAP.keySet()) {
            emotionNames.add(emojiName);
            // 每20个表情作为一组,同时添加到ViewPager对应的view集合中
            if (emotionNames.size() == 20) {
                GridView gv = createEmotionGridView(emotionNames, screenWidth, spacing, itemWidth, gvHeight);
                emotionViews.add(gv);
                // 添加完一组表情,重新创建一个表情名字集合
                emotionNames = new ArrayList<>();
            }
        }
        // 判断最后是否有不足23个表情的剩余情况
        if (emotionNames.size() > 0) {
            GridView gv = createEmotionGridView(emotionNames, screenWidth, spacing, itemWidth, gvHeight);
            emotionViews.add(gv);
        }

        //初始化指示器
        fragmentChatGroup.initIndicator(emotionViews.size());
        // 将多个GridView添加显示到ViewPager中
        emotionPagerAdapter = new EmotionPagerAdapter(emotionViews);
        fragmentChatVp.setAdapter(emotionPagerAdapter);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth, gvHeight);
        fragmentChatVp.setLayoutParams(params);


    }

    /**
     * 创建显示表情的GridView
     */
    private GridView createEmotionGridView(List<String> emotionNames, int gvWidth, int padding, int itemWidth, int gvHeight) {
        // 创建GridView
        GridView gv = new GridView(getActivity());
        //设置点击背景透明
        gv.setSelector(android.R.color.transparent);
        //设置7列
        gv.setNumColumns(7);
        gv.setPadding(22, padding, 22, padding);
        gv.setHorizontalSpacing(padding);
        gv.setVerticalSpacing(padding + 8);
        //设置GridView的宽高
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(gvWidth, gvHeight);
        gv.setLayoutParams(params);
        // 给GridView设置表情图片
        EmotionGridViewAdapter adapter = new EmotionGridViewAdapter(getActivity(), emotionNames, itemWidth);
        gv.setAdapter(adapter);
        //设置全局点击事件
        gv.setOnItemClickListener(GlobalOnItemClickManagerUtils.getInstance(getActivity()).getOnItemClickListener());
        return gv;
    }

    private void initTab(){
        int size = App.emojis.size();
        for(int i = 0 ; i < size; i++) {
            View view = LayoutInflater.from(App.globalContext()).inflate(R.layout.tab_bar_item,null );
            Drawable drawable = this.getResources().getDrawable(App.emojis.get(i));
            ((RadioButton)view).setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
            view.setId(App.emojis.get(i));
            View line = LayoutInflater.from(App.globalContext()).inflate(R.layout.line_layout,null);
            int w = CommonUtils.dp2px(App.globalContext(),60);
            int h = CommonUtils.dp2px(App.globalContext(),40);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(w,h);
            int lineW = CommonUtils.dp2px(App.globalContext(), Float.valueOf("0.6"));
            int lineH = CommonUtils.dp2px(App.globalContext(),40);
            line.setLayoutParams(new LinearLayout.LayoutParams(lineW,lineH));
            view.setLayoutParams(layoutParams);
            rgTabs.addView(line);
            rgTabs.addView(view);
        }
        View radioButton = LayoutInflater.from(App.globalContext()).inflate(R.layout.tab_bar_item,null );
        View line = LayoutInflater.from(App.globalContext()).inflate(R.layout.line_layout,null);
        int w = CommonUtils.dp2px(App.globalContext(),60);
        int h = CommonUtils.dp2px(App.globalContext(),40);
        Drawable drawable = this.getResources().getDrawable(R.mipmap.icon_tab_setting);
        ((RadioButton)radioButton).setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
        radioButton.setId(R.id.tab_setting);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(w,h);
        radioButton.setLayoutParams(layoutParams);
        int lineW = CommonUtils.dp2px(App.globalContext(), Float.valueOf("0.6"));
        int lineH = CommonUtils.dp2px(App.globalContext(),40);
        line.setLayoutParams(new LinearLayout.LayoutParams(lineW,lineH));
        rgTabs.addView(line);
        rgTabs.addView(radioButton);
        rgTabs.check(R.id.btn_emoji);
    }
}
