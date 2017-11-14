package com.example.dsad.diycode.api;

import android.util.SparseArray;

import java.util.HashMap;
import java.util.Map;

/**
 * 多余字段补充
 * Created by dsad on 2017/9/21.
 */

public class DiyCodeApi
{
    //最新的被回复的话题
    public static final String TOPIC_TYPE_LAST_ACTIVIE="last_actived";
    //最新的帖子
    public static final String TOPIC_TYPE_RECENT ="recent";
    //热门的
    public static final String TOPIC_TYPE_POPULAR = "popular";
    //冷门帖子
    public static final String TOPIC_TYPE_NO_REPLY="no_reply";
    //精品帖子
    public static final String TOPIC_TYPE_EX="excellent";
    //默认数量
    public static final int DEFAULT_COUNT=20;
    //默认偏移量(页码)
    public static final int DEFAULT_PAGE=1;
    //最大条数
    public static final int MAX_COUNT = 100;
    //保存用户状态的key
    public static final String GET_USERCOLLECTION="user";
    //保存用户notifiactio状态的key
    public static final String GET_USERNOTIFICTION="allnotifi";
    //话题拼接
    public static final String TOPIC_URL="https://www.diycode.cc/topics/";
    //必应最新操作
    public static final String BING_PIC="https://api.dujin.org/bing/1366.php";
    //注册地址
    public static final String REGISTER="https://www.diycode.cc/account/sign_up";
<<<<<<< HEAD
    //还带小故事
    public static final String MEITU = "http://tu.ihuan.me/api/me_all_story_json/";
=======
>>>>>>> origin/master
}
