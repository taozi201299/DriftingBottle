
package com.driftingbottle.utils;

import com.driftingbottle.App;
import com.driftingbottle.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 作者：Rance on 2016/11/29 10:47
 * 邮箱：rance935@163.com
 * 表情加载类,可自己添加多种表情，分别建立不同的map存放和不同的标志符即可
 */
public class EmotionUtils {

    /**
     * key-表情文字;
     * value-表情图片资源
     */
    public static LinkedHashMap<String, Integer> EMPTY_GIF_MAP;
    public static LinkedHashMap<String, Integer> EMOTION_STATIC_MAP;


    static {
        EMPTY_GIF_MAP = new LinkedHashMap<>();

        EMPTY_GIF_MAP.put("[微笑]", R.drawable.icon_weixin1);
        EMPTY_GIF_MAP.put("[撇嘴]", R.drawable.icon_weixin2);
        EMPTY_GIF_MAP.put("[色]", R.drawable.icon_weixin3);
        EMPTY_GIF_MAP.put("[发呆]", R.drawable.icon_weixin4);
        EMPTY_GIF_MAP.put("[得意]",  R.drawable.icon_weixin5);
        EMPTY_GIF_MAP.put("[流泪]",  R.drawable.icon_weixin6);
        EMPTY_GIF_MAP.put("[害羞]",  R.drawable.icon_weixin7);

        EMPTY_GIF_MAP.put("[闭嘴]",  R.drawable.icon_weixin8);
        EMPTY_GIF_MAP.put("[睡]", R.drawable.icon_weixin9);
        EMPTY_GIF_MAP.put("[大哭]", R.drawable.icon_weixin10);
        EMPTY_GIF_MAP.put("[尴尬]", R.drawable.icon_weixin11);
        EMPTY_GIF_MAP.put("[发怒]", R.drawable.icon_weixin12);
        EMPTY_GIF_MAP.put("[调皮]", R.drawable.icon_weixin13);
        EMPTY_GIF_MAP.put("[呲牙]", R.drawable.icon_weixin14);

        EMPTY_GIF_MAP.put("[惊讶]", R.drawable.icon_weixin15);
        EMPTY_GIF_MAP.put("[难过]", R.drawable.icon_weixin16);
        EMPTY_GIF_MAP.put("[囧]", R.drawable.icon_weixin17);
        EMPTY_GIF_MAP.put("[抓狂]", R.drawable.icon_weixin18);
        EMPTY_GIF_MAP.put("[吐]", R.drawable.icon_weixin19);
        EMPTY_GIF_MAP.put("[偷笑]", R.drawable.icon_weixin20);

        EMPTY_GIF_MAP.put("[愉快]", R.drawable.icon_weixin21);
        EMPTY_GIF_MAP.put("[白眼]", R.drawable.icon_weixin22);
        EMPTY_GIF_MAP.put("[傲慢]", R.drawable.icon_weixin23);
        EMPTY_GIF_MAP.put("[困]", R.drawable.icon_weixin24);
        EMPTY_GIF_MAP.put("[惊恐]", R.drawable.icon_weixin25);
        EMPTY_GIF_MAP.put("[流汗]", R.drawable.icon_weixin26);
        EMPTY_GIF_MAP.put("[憨笑]", R.drawable.icon_weixin27);


        EMPTY_GIF_MAP.put("[悠闲]", R.drawable.icon_weixin28);
        EMPTY_GIF_MAP.put("[奋斗]", R.drawable.icon_weixin29);
        EMPTY_GIF_MAP.put("[咒骂]", R.drawable.icon_weixin30);
        EMPTY_GIF_MAP.put("[疑问]", R.drawable.icon_weixin31);
        EMPTY_GIF_MAP.put("[嘘]", R.drawable.icon_weixin32);
        EMPTY_GIF_MAP.put("[晕]", R.drawable.icon_weixin33);
        EMPTY_GIF_MAP.put("[衰]", R.drawable.icon_weixin34);


        EMPTY_GIF_MAP.put("[骷髅]", R.drawable.icon_weixin35);
        EMPTY_GIF_MAP.put("[敲打]", R.drawable.icon_weixin36);
        EMPTY_GIF_MAP.put("[再见]", R.drawable.icon_weixin37);
        EMPTY_GIF_MAP.put("[擦汗]", R.drawable.icon_weixin38);
        EMPTY_GIF_MAP.put("[抠鼻]", R.drawable.icon_weixin39);
        EMPTY_GIF_MAP.put("[鼓掌]", R.drawable.icon_weixin40);


        EMPTY_GIF_MAP.put("[坏笑]", R.drawable.icon_weixin41);
        EMPTY_GIF_MAP.put("[左哼哼]", R.drawable.icon_weixin42);
        EMPTY_GIF_MAP.put("[右哼哼]", R.drawable.icon_weixin43);
        EMPTY_GIF_MAP.put("[哈欠]", R.drawable.icon_weixin44);
        EMPTY_GIF_MAP.put("[鄙视]", R.drawable.icon_weixin45);
        EMPTY_GIF_MAP.put("[委屈]", R.drawable.icon_weixin46);
        EMPTY_GIF_MAP.put("[快哭了]", R.drawable.icon_weixin47);


        EMPTY_GIF_MAP.put("[阴险]", R.drawable.icon_weixin48);
        EMPTY_GIF_MAP.put("[亲亲]", R.drawable.icon_weixin49);
        EMPTY_GIF_MAP.put("[可怜]", R.drawable.icon_weixin50);
        EMPTY_GIF_MAP.put("[菜刀]", R.drawable.icon_weixin51);
        EMPTY_GIF_MAP.put("[西瓜]", R.drawable.icon_weixin52);
        EMPTY_GIF_MAP.put("[啤酒]", R.drawable.icon_weixin53);
        EMPTY_GIF_MAP.put("[咖啡]", R.drawable.icon_weixin54);


        EMPTY_GIF_MAP.put("[猪头]", R.drawable.icon_weixin55);
        EMPTY_GIF_MAP.put("[玫瑰]", R.drawable.icon_weixin56);
        EMPTY_GIF_MAP.put("[凋谢]", R.drawable.icon_weixin57);
        EMPTY_GIF_MAP.put("[嘴唇]", R.drawable.icon_weixin58);
        EMPTY_GIF_MAP.put("[爱心]", R.drawable.icon_weixin59);
        EMPTY_GIF_MAP.put("[心碎]", R.drawable.icon_weixin60);


        EMPTY_GIF_MAP.put("[蛋糕]", R.drawable.icon_weixin61);
        EMPTY_GIF_MAP.put("[炸弹]", R.drawable.icon_weixin62);
        EMPTY_GIF_MAP.put("[便便]", R.drawable.icon_weixin63);
        EMPTY_GIF_MAP.put("[月亮]", R.drawable.icon_weixin64);
        EMPTY_GIF_MAP.put("[太阳]", R.drawable.icon_weixin65);
        EMPTY_GIF_MAP.put("[拥抱]", R.drawable.icon_weixin66);
        EMPTY_GIF_MAP.put("[强]", R.drawable.icon_weixin67);


        EMPTY_GIF_MAP.put("[弱]", R.drawable.icon_weixin68);
        EMPTY_GIF_MAP.put("[握手]", R.drawable.icon_weixin69);
        EMPTY_GIF_MAP.put("[胜利]", R.drawable.icon_weixin70);
        EMPTY_GIF_MAP.put("[抱拳]", R.drawable.icon_weixin71);
        EMPTY_GIF_MAP.put("[勾引]", R.drawable.icon_weixin72);
        EMPTY_GIF_MAP.put("[拳头]", R.drawable.icon_weixin73);
        EMPTY_GIF_MAP.put("[OK]", R.drawable.icon_weixin74);


        EMPTY_GIF_MAP.put("[跳跳]", R.drawable.icon_weixin75);
        EMPTY_GIF_MAP.put("[发抖]", R.drawable.icon_weixin76);
        EMPTY_GIF_MAP.put("[怄火]", R.drawable.icon_weixin77);
        EMPTY_GIF_MAP.put("[转圈]", R.drawable.icon_weixin78);



        EMPTY_GIF_MAP.put("[微信72]", R.drawable.icon_weixin79);
        EMPTY_GIF_MAP.put("[微信73]", R.drawable.icon_weixin80);
        EMPTY_GIF_MAP.put("[微信74]", R.drawable.icon_weixin81);
        EMPTY_GIF_MAP.put("[微信75]", R.drawable.icon_weixin82);
        EMPTY_GIF_MAP.put("[微信76]", R.drawable.icon_weixin83);
        EMPTY_GIF_MAP.put("[微信77]", R.drawable.icon_weixin84);
        EMPTY_GIF_MAP.put("[微信78]", R.drawable.icon_weixin85);
        EMPTY_GIF_MAP.put("[微信79]", R.drawable.icon_weixin86);
        EMPTY_GIF_MAP.put("[嘿哈]", R.drawable.icon_weixin87);

        EMPTY_GIF_MAP.put("[捂脸]", R.drawable.icon_weixin88);
        EMPTY_GIF_MAP.put("[奸笑]", R.drawable.icon_weixin89);
        EMPTY_GIF_MAP.put("[机智]", R.drawable.icon_weixin90);
        EMPTY_GIF_MAP.put("[皱眉]", R.drawable.icon_weixin91);
        EMPTY_GIF_MAP.put("[耶]", R.drawable.icon_weixin92);


        EMPTY_GIF_MAP.put("[欢呼]", R.drawable.icon_weixin93);
        EMPTY_GIF_MAP.put("[祈祷]", R.drawable.icon_weixin94);
        EMPTY_GIF_MAP.put("[肌肉]", R.drawable.icon_weixin95);
        EMPTY_GIF_MAP.put("[喝彩]", R.drawable.icon_weixin96);
        EMPTY_GIF_MAP.put("[礼物]", R.drawable.icon_weixin97);
        EMPTY_GIF_MAP.put("[红包]", R.drawable.icon_weixin98);
        EMPTY_GIF_MAP.put("[發]", R.drawable.icon_weixin99);
        EMPTY_GIF_MAP.put("[小狗]", R.drawable.icon_weixin100);

        EMOTION_STATIC_MAP = new LinkedHashMap<>();



        EMOTION_STATIC_MAP.put("[微笑]", R.drawable.icon_weixin1);
        EMOTION_STATIC_MAP.put("[撇嘴]", R.drawable.icon_weixin2);
        EMOTION_STATIC_MAP.put("[色]", R.drawable.icon_weixin3);
        EMOTION_STATIC_MAP.put("[发呆]", R.drawable.icon_weixin4);
        EMOTION_STATIC_MAP.put("[得意]",  R.drawable.icon_weixin5);
        EMOTION_STATIC_MAP.put("[流泪]",  R.drawable.icon_weixin6);
        EMOTION_STATIC_MAP.put("[害羞]",  R.drawable.icon_weixin7);

        EMOTION_STATIC_MAP.put("[闭嘴]",  R.drawable.icon_weixin8);
        EMOTION_STATIC_MAP.put("[睡]", R.drawable.icon_weixin9);
        EMOTION_STATIC_MAP.put("[大哭]", R.drawable.icon_weixin10);
        EMOTION_STATIC_MAP.put("[尴尬]", R.drawable.icon_weixin11);
        EMOTION_STATIC_MAP.put("[发怒]", R.drawable.icon_weixin12);
        EMOTION_STATIC_MAP.put("[调皮]", R.drawable.icon_weixin13);
        EMOTION_STATIC_MAP.put("[呲牙]", R.drawable.icon_weixin14);

        EMOTION_STATIC_MAP.put("[惊讶]", R.drawable.icon_weixin15);
        EMOTION_STATIC_MAP.put("[难过]", R.drawable.icon_weixin16);
        EMOTION_STATIC_MAP.put("[囧]", R.drawable.icon_weixin17);
        EMOTION_STATIC_MAP.put("[抓狂]", R.drawable.icon_weixin18);
        EMOTION_STATIC_MAP.put("[吐]", R.drawable.icon_weixin19);
        EMOTION_STATIC_MAP.put("[偷笑]", R.drawable.icon_weixin20);

        EMOTION_STATIC_MAP.put("[愉快]", R.drawable.icon_weixin21);
        EMOTION_STATIC_MAP.put("[白眼]", R.drawable.icon_weixin22);
        EMOTION_STATIC_MAP.put("[傲慢]", R.drawable.icon_weixin23);
        EMOTION_STATIC_MAP.put("[困]", R.drawable.icon_weixin24);
        EMOTION_STATIC_MAP.put("[惊恐]", R.drawable.icon_weixin25);
        EMOTION_STATIC_MAP.put("[流汗]", R.drawable.icon_weixin26);
        EMOTION_STATIC_MAP.put("[憨笑]", R.drawable.icon_weixin27);


        EMOTION_STATIC_MAP.put("[悠闲]", R.drawable.icon_weixin28);
        EMOTION_STATIC_MAP.put("[奋斗]", R.drawable.icon_weixin29);
        EMOTION_STATIC_MAP.put("[咒骂]", R.drawable.icon_weixin30);
        EMOTION_STATIC_MAP.put("[疑问]", R.drawable.icon_weixin31);
        EMOTION_STATIC_MAP.put("[嘘]", R.drawable.icon_weixin32);
        EMOTION_STATIC_MAP.put("[晕]", R.drawable.icon_weixin33);
        EMOTION_STATIC_MAP.put("[衰]", R.drawable.icon_weixin34);


        EMOTION_STATIC_MAP.put("[骷髅]", R.drawable.icon_weixin35);
        EMOTION_STATIC_MAP.put("[敲打]", R.drawable.icon_weixin36);
        EMOTION_STATIC_MAP.put("[再见]", R.drawable.icon_weixin37);
        EMOTION_STATIC_MAP.put("[擦汗]", R.drawable.icon_weixin38);
        EMOTION_STATIC_MAP.put("[抠鼻]", R.drawable.icon_weixin39);
        EMOTION_STATIC_MAP.put("[鼓掌]", R.drawable.icon_weixin40);


        EMOTION_STATIC_MAP.put("[坏笑]", R.drawable.icon_weixin41);
        EMOTION_STATIC_MAP.put("[左哼哼]", R.drawable.icon_weixin42);
        EMOTION_STATIC_MAP.put("[右哼哼]", R.drawable.icon_weixin43);
        EMOTION_STATIC_MAP.put("[哈欠]", R.drawable.icon_weixin44);
        EMOTION_STATIC_MAP.put("[鄙视]", R.drawable.icon_weixin45);
        EMOTION_STATIC_MAP.put("[委屈]", R.drawable.icon_weixin46);
        EMOTION_STATIC_MAP.put("[快哭了]", R.drawable.icon_weixin47);


        EMOTION_STATIC_MAP.put("[阴险]", R.drawable.icon_weixin48);
        EMOTION_STATIC_MAP.put("[亲亲]", R.drawable.icon_weixin49);
        EMOTION_STATIC_MAP.put("[可怜]", R.drawable.icon_weixin50);
        EMOTION_STATIC_MAP.put("[菜刀]", R.drawable.icon_weixin51);
        EMOTION_STATIC_MAP.put("[西瓜]", R.drawable.icon_weixin52);
        EMOTION_STATIC_MAP.put("[啤酒]", R.drawable.icon_weixin53);
        EMOTION_STATIC_MAP.put("[咖啡]", R.drawable.icon_weixin54);


        EMOTION_STATIC_MAP.put("[猪头]", R.drawable.icon_weixin55);
        EMOTION_STATIC_MAP.put("[玫瑰]", R.drawable.icon_weixin56);
        EMOTION_STATIC_MAP.put("[凋谢]", R.drawable.icon_weixin57);
        EMOTION_STATIC_MAP.put("[嘴唇]", R.drawable.icon_weixin58);
        EMOTION_STATIC_MAP.put("[爱心]", R.drawable.icon_weixin59);
        EMOTION_STATIC_MAP.put("[心碎]", R.drawable.icon_weixin60);


        EMOTION_STATIC_MAP.put("[蛋糕]", R.drawable.icon_weixin61);
        EMOTION_STATIC_MAP.put("[炸弹]", R.drawable.icon_weixin62);
        EMOTION_STATIC_MAP.put("[便便]", R.drawable.icon_weixin63);
        EMOTION_STATIC_MAP.put("[月亮]", R.drawable.icon_weixin64);
        EMOTION_STATIC_MAP.put("[太阳]", R.drawable.icon_weixin65);
        EMOTION_STATIC_MAP.put("[拥抱]", R.drawable.icon_weixin66);
        EMOTION_STATIC_MAP.put("[强]", R.drawable.icon_weixin67);


        EMOTION_STATIC_MAP.put("[弱]", R.drawable.icon_weixin68);
        EMOTION_STATIC_MAP.put("[握手]", R.drawable.icon_weixin69);
        EMOTION_STATIC_MAP.put("[胜利]", R.drawable.icon_weixin70);
        EMOTION_STATIC_MAP.put("[抱拳]", R.drawable.icon_weixin71);
        EMOTION_STATIC_MAP.put("[勾引]", R.drawable.icon_weixin72);
        EMOTION_STATIC_MAP.put("[拳头]", R.drawable.icon_weixin73);
        EMOTION_STATIC_MAP.put("[OK]", R.drawable.icon_weixin74);


        EMOTION_STATIC_MAP.put("[跳跳]", R.drawable.icon_weixin75);
        EMOTION_STATIC_MAP.put("[发抖]", R.drawable.icon_weixin76);
        EMOTION_STATIC_MAP.put("[怄火]", R.drawable.icon_weixin77);
        EMOTION_STATIC_MAP.put("[转圈]", R.drawable.icon_weixin78);



        EMOTION_STATIC_MAP.put("[微信72]", R.drawable.icon_weixin79);
        EMOTION_STATIC_MAP.put("[微信73]", R.drawable.icon_weixin80);
        EMOTION_STATIC_MAP.put("[微信74]", R.drawable.icon_weixin81);
        EMOTION_STATIC_MAP.put("[微信75]", R.drawable.icon_weixin82);
        EMOTION_STATIC_MAP.put("[微信76]", R.drawable.icon_weixin83);
        EMOTION_STATIC_MAP.put("[微信77]", R.drawable.icon_weixin84);
        EMOTION_STATIC_MAP.put("[微信78]", R.drawable.icon_weixin85);
        EMOTION_STATIC_MAP.put("[微信79]", R.drawable.icon_weixin86);
        EMOTION_STATIC_MAP.put("[嘿哈]", R.drawable.icon_weixin87);

        EMOTION_STATIC_MAP.put("[捂脸]", R.drawable.icon_weixin88);
        EMOTION_STATIC_MAP.put("[奸笑]", R.drawable.icon_weixin89);
        EMOTION_STATIC_MAP.put("[机智]", R.drawable.icon_weixin90);
        EMOTION_STATIC_MAP.put("[皱眉]", R.drawable.icon_weixin91);
        EMOTION_STATIC_MAP.put("[耶]", R.drawable.icon_weixin92);

        EMOTION_STATIC_MAP.put("[欢呼]", R.drawable.icon_weixin93);
        EMOTION_STATIC_MAP.put("[祈祷]", R.drawable.icon_weixin94);
        EMOTION_STATIC_MAP.put("[肌肉]", R.drawable.icon_weixin95);
        EMOTION_STATIC_MAP.put("[喝彩]", R.drawable.icon_weixin96);
        EMOTION_STATIC_MAP.put("[礼物]", R.drawable.icon_weixin97);
        EMOTION_STATIC_MAP.put("[红包]", R.drawable.icon_weixin98);
        EMOTION_STATIC_MAP.put("[發]", R.drawable.icon_weixin99);
        EMOTION_STATIC_MAP.put("[小狗]", R.drawable.icon_weixin100);
    }
    private static  int[]emojiSize = {0,1,2,3,4,5};
    private static int[]emojis ={R.mipmap.icon_tab0,R.mipmap.icon_tab1,R.mipmap.icon_tab2,
            R.mipmap.icon_tab3,R.mipmap.icon_tab4,R.mipmap.icon_tab5,R.mipmap.icon_tab6,
    R.mipmap.icon_tab7, R.mipmap.icon_tab8,R.mipmap.icon_tab9,R.mipmap.icon_tab10};

    private static  int getRandEmojiNum (){
        return (int)(Math.random()*emojiSize.length);
    }
    public static ArrayList<Integer> getEmojis(){
        ArrayList list = new ArrayList();
        int size = getRandEmojiNum();
        String strEmoji = "";
        String json = (String) SPUtils.get("tab","");
        if(json == null || json.isEmpty()) {
            for (int i = 0; i < size; i++) {
                int index = (int) (Math.random() * emojis.length);
                if (list.contains(emojis[index])) {
                    i--;
                    continue;
                }
                list.add(emojis[index]);
                strEmoji += emojis[index];
                strEmoji +=",";
            }
        }else {
            Gson gson = new Gson();
            HashMap<String,String>value = gson.fromJson(json,HashMap.class);
            int count = Integer.parseInt(value.get("size"));
            if (count == 0) return list;
            String[]array = value.get("value").split(",");
            for(int i = 0; i< count ; i++){
                list.add(Integer.valueOf(array[i]));
            }
            return list;
        }
        Gson gson = new Gson();
        HashMap<String ,String >map = new HashMap<>();
        map.put("size",String.valueOf(size));
        map.put("value",strEmoji);
        SPUtils.put("tab",gson.toJson(map));
        return list;
    }

}
