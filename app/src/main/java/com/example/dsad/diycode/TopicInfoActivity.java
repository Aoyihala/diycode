package com.example.dsad.diycode;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dsad.diycode.adpter.compileadpter.TopicReplyAdpter;
import com.example.dsad.diycode.api.DiyCodeApi;
import com.example.dsad.diycode.appliction.MyApplication;
import com.example.dsad.diycode.base.RequsetActivity;
import com.example.dsad.diycode.utils.DataCache;
import com.example.dsad.diycode.utils.ImagReplace;
import com.example.dsad.diycode.utils.RecyclerViewUtils;
import com.example.dsad.diycode.utils.TimeUtil;
import com.example.dsad.diycode.utils.UiUtlis;
import com.gcssloop.diycode_sdk.api.base.bean.State;
import com.gcssloop.diycode_sdk.api.topic.bean.Topic;
import com.gcssloop.diycode_sdk.api.topic.bean.TopicContent;
import com.gcssloop.diycode_sdk.api.topic.bean.TopicReply;
import com.gcssloop.diycode_sdk.api.topic.event.GetTopicEvent;
import com.gcssloop.diycode_sdk.api.topic.event.GetTopicRepliesListEvent;
<<<<<<< HEAD
import com.gcssloop.diycode_sdk.api.user.bean.User;
import com.gcssloop.diycode_sdk.api.user.event.FollowUserEvent;
import com.gcssloop.diycode_sdk.api.user.event.GetUserFollowerListEvent;
import com.gcssloop.diycode_sdk.api.user.event.UnFollowUserEvent;
import com.zzhoujay.richtext.RichText;
=======
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.Callback;
>>>>>>> origin/master
import com.zzhoujay.richtext.callback.OnImageClickListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/***
 * 基础requseractivity,eventbus请求数据
 */
public class TopicInfoActivity extends RequsetActivity {

    @Bind(R.id.top_topic_info)
    Toolbar topTopicInfo;
    @Bind(R.id.recycler_topicinfo_view)
    RecyclerView recyclerTopicinfoView;
    @Bind(R.id.btn_topinfo_reply)
    FloatingActionButton btnTopinfoReply;
    @Bind(R.id.img_topicinfo_userhead)
    CircleImageView imgTopicinfoUserhead;
    @Bind(R.id.tv_topicinfo_username)
    TextView tvTopicinfoUsername;
    @Bind(R.id.tv_topicinfo_time)
    TextView tvTopicinfoTime;
    @Bind(R.id.scroll_topicinfo_view)
    NestedScrollView scrollTopicinfoView;
    @Bind(R.id.tv_topicinfo_content)
    TextView tvTopicinfoContent;
<<<<<<< HEAD
    @Bind(R.id.tv_topicinfo_follow)
    TextView tvTopicinfoFollow;
    @Bind(R.id.tv_topicinfo_replycount)
    TextView tvTopicinfoReplycount;
=======
>>>>>>> origin/master
    private TopicContent topic_data;
    //话题元
    private Topic nowtopic;
    private TopicReplyAdpter reoly_adpter;
    private List<TopicReply> reply_list;
    private int count = DiyCodeApi.DEFAULT_COUNT;
    private TopicReply my_reply;
    //单独的一条线
    private int topic_id;
    private List<Topic> collection_topic = new ArrayList<>();
    private boolean isselect = true;
    private MenuItem item;
    private DataCache data_cache;
    public static final int REPLY_UPDATE = 2;
    private List<User> user_followlist;
    private State follow_state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_info);
        ButterKnife.bind(this);
        inintdata();
        inintui();
        inintsession();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topbar_topic_menu, menu);
        MenuItem item = menu.findItem(R.id.action_collect);
        if (iscollected() == true) {
            item.setIcon(R.mipmap.ic_favorite_black);
        } else {
            item.setIcon(R.mipmap.ic_favorite_outline_black);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.action_collect:
                //收藏
                if (nowtopic != null) {
                    if (iscollected()) {
                        //取消收藏(本地模拟)
                        //MyApplication.getmDiycode().unCollectionTopic(nowtopic.getId());
                        collection_topic.remove(nowtopic);
                        data_cache.saveListData(DiyCodeApi.GET_USERCOLLECTION, collection_topic);
                        item.setIcon(R.mipmap.ic_favorite_outline_black);
                    } else {
                        //加入收藏(本地模拟)
                        //MyApplication.getmDiycode().collectionTopic(nowtopic.getId());
                        collection_topic.add(0, nowtopic);
                        data_cache.saveListData(DiyCodeApi.GET_USERCOLLECTION, collection_topic);
                        item.setIcon(R.mipmap.ic_favorite_black);
                    }
                } else {
                    Snackbar.make(recyclerTopicinfoView, "加载中", 2000).show();
                }
                break;
            case R.id.action_browser:
                //浏览器打开
                Uri myBlogUri = Uri.parse(DiyCodeApi.TOPIC_URL + topic_data.getId());
                Intent intent = new Intent(Intent.ACTION_VIEW, myBlogUri);
                startActivity(intent);
<<<<<<< HEAD
                break;
            case android.R.id.home:
                finish();
=======
>>>>>>> origin/master
                break;

        }
        return true;
    }

    private void inintdata() {
        setSupportActionBar(topTopicInfo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getUserCollect();
        getTopicData();
        getData();

    }


    private void inintui() {
        inintrecycler();
    }

    private void inintrecycler() {
        reoly_adpter = new TopicReplyAdpter();
        recyclerTopicinfoView.setLayoutManager(new LinearLayoutManager(this));
        recyclerTopicinfoView.setAdapter(reoly_adpter);
        //交给父类滑动
        RecyclerViewUtils.init(getApplicationContext(), recyclerTopicinfoView, reoly_adpter);
    }

    private void inintsession() {
        btnTopinfoReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReplyActivity.class);
                intent.putExtra("topic_id", topic_data.getId());
                intent.putExtra("topic_title", topic_data.getTitle());
                startActivityForResult(intent, REPLY_UPDATE);
            }
        });
        imgTopicinfoUserhead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserInfoActivity.class);
                intent.putExtra("user", topic_data.getUser().getLogin());
                startActivity(intent);
            }
        });
<<<<<<< HEAD
        //回复里的图片浏览事件
        reoly_adpter.setOnImageClickListener(new TopicReplyAdpter.onclickImageListener() {
            @Override
            public void onImgClick(String url) {
                Intent intent = new Intent(MyApplication.getmContext(), ImageActivity.class);
                intent.putExtra("imgurl", url);
                MyApplication.getmContext().startActivity(intent);
            }
        });
        //关注
        tvTopicinfoFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvTopicinfoFollow.getVisibility()==View.VISIBLE)
                {
                    if (tvTopicinfoFollow.getText().equals("关注"))
                    {
                        MyApplication.getmDiycode().followUser(topic_data.getUser().getLogin());
                        Snackbar.make(recyclerTopicinfoView,"关注成功",2000).show();
                        return;
                    }
                    MyApplication.getmDiycode().unFollowUser(topic_data.getUser().getLogin());

                }
            }
        });

=======
>>>>>>> origin/master
    }

    public void getTopicData() {
        Intent intent = getIntent();
        nowtopic = (Topic) intent.getSerializableExtra("topic");
        topic_id = intent.getIntExtra("topic_id", 0);

    }

    public void getData() {
        if (nowtopic != null) {
            //请求数据(本体)
            MyApplication.getmDiycode().getTopic(nowtopic.getId());
            getReply();
        } else {
            //请求数据(本体)
            nowtopic = new Topic();
            nowtopic.setId(topic_id);
            MyApplication.getmDiycode().getTopic(topic_id);
            getReply();
        }
    }

    //单独抽取出来出来
    public void getReply() {
        //请求回复体
        MyApplication.getmDiycode().getTopicRepliesList(nowtopic.getId(), null, count);
    }

    private void configcontent() {
        tvTopicinfoTime.setText(TimeUtil.computePastTime(topic_data.getCreated_at()));
        tvTopicinfoUsername.setText(topic_data.getUser().getName());
<<<<<<< HEAD
        //bitmaputl.display(ImagReplace.getImageUrl(topic_data.getUser().getAvatar_url()), imgTopicinfoUserhead);
        Glide.with(MyApplication.getmContext()).load(ImagReplace.getImageUrl(topic_data.getUser().getAvatar_url()))
                .into(imgTopicinfoUserhead);
=======
        bitmaputl.display(ImagReplace.getImageUrl(topic_data.getUser().getAvatar_url()), imgTopicinfoUserhead);
>>>>>>> origin/master
        //topicContent.setMarkDownText(topic_data.getBody());
        //关闭硬件加速
        //tvTopicinfoContent.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        RichText.fromMarkdown(topic_data.getBody())
                //不加载图片
                //.noImage(true)
<<<<<<< HEAD
                .clickable(true)
=======
>>>>>>> origin/master
                .autoFix(true)
                .imageClick(new OnImageClickListener() {
                    @Override
                    public void imageClicked(List<String> imageUrls, int position) {
                        //图片点击事件
                        Intent intent = new Intent(getApplicationContext(), ImageActivity.class);
                        intent.putExtra("imgurl", imageUrls.get(position));
                        startActivity(intent);
                    }
                })
                .into(tvTopicinfoContent);
<<<<<<< HEAD
        tvTopicinfoReplycount.setText(topic_data.getReplies_count()+"条回复");
        getUserinfo();
=======
>>>>>>> origin/master
    }

    //------------------------------请求内容部分-----------------------------------------------------
    //请求topic内容
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getTopic(GetTopicEvent event) {
        if (event.isOk()) {
            topic_data = event.getBean();
            configcontent();
        }
    }

    //请求topic回复
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getTopicReply(GetTopicRepliesListEvent eva) {
        if (eva.isOk()) {
            reply_list = eva.getBean();
            configrecyclerview();
        }
    }
    //获取发帖用户的关注列表
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getUserFollowList(GetUserFollowerListEvent eva)
    {
        if(eva.isOk())
        {
           user_followlist = eva.getBean();
            cofigtext();
        }
    }
    //关注用户
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void followUser(FollowUserEvent eva)
    {
        if (eva.isOk())
        {
          follow_state=eva.getBean();
            if (follow_state.getOk()==1)
            {
                //成功
                setFollow();
            }
            follow_state=null;
        }
    }
    //取关
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void unfollowuser(UnFollowUserEvent eva)
    {
        if (eva.isOk())
        {
            follow_state = eva.getBean();
            if (follow_state.getOk()==1)
            {
                //取消关注
                setUnFollow();
            }
        }
        follow_state=null;
    }

    //--------------------------------------------结束----------------------------------------------
    private void configrecyclerview() {
        reoly_adpter.setData(reply_list);
        reoly_adpter.notifyDataSetChanged();
    }

<<<<<<< HEAD
    private void cofigtext()
    {
        if (user_followlist!=null&&user_followlist.size()>0)
        {
            tvTopicinfoFollow.setVisibility(View.VISIBLE);
            //遍历userlist
            for (User item:user_followlist)
            {
                if (item.getLogin().equals(data_cache.getMe().getLogin()))
                {
                    //符合条件,跳出
                    setFollow();
                    break;
                }
            }
        }
        else
        {
            tvTopicinfoFollow.setVisibility(View.GONE);
        }
    }
=======
>>>>>>> origin/master
    public void getUserCollect() {
        data_cache = new DataCache(getApplicationContext());
        //获取用户收藏然后与当前topic判断
    }

    public boolean iscollected() {
        collection_topic = data_cache.getData(DiyCodeApi.GET_USERCOLLECTION);
        if (collection_topic != null && collection_topic.size() > 0) {
            for (int i = 0; i < collection_topic.size(); i++) {
                if (nowtopic.getId() == collection_topic.get(i).getId()) {
                    return true;
                }
            }
        } else {
            collection_topic = new ArrayList<>();
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REPLY_UPDATE && resultCode == REPLY_UPDATE) {
            //执行刷新
            getReply();
            //滚到底部
            scrollTopicinfoView.fullScroll(NestedScrollView.FOCUS_DOWN);
        }
    }
    /**
     * 设置已关注(ui状态)
     */
    public void setFollow()
    {
        if (tvTopicinfoFollow.getVisibility()==View.VISIBLE)
        {
            tvTopicinfoFollow.setText("已关注");
            tvTopicinfoFollow.setTextColor(UiUtlis.getColor(R.color.TopicItemNodeColor));
            tvTopicinfoFollow.setBackgroundResource(R.drawable.follow);

        }
    }
    /**
     * 设置未关注
     */
    public void setUnFollow()
    {
        if (tvTopicinfoFollow.getVisibility()==View.VISIBLE)
        {
            tvTopicinfoFollow.setText("关注");
            tvTopicinfoFollow.setTextColor(UiUtlis.getColor(R.color.TabTextSelectColor));
            tvTopicinfoFollow.setBackgroundResource(R.drawable.unfollow);
        }
    }
    /**
     * 获取用户与当前用户的关系
     */
    public void getUserinfo()
    {
        //获取当前发帖用户的关注列表中的用户(登录时才去获取关注)
        if (MyApplication.getmDiycode().isLogin())
        {
            MyApplication.getmDiycode().getUserFollowerList(topic_data.getUser().getLogin(),null,DiyCodeApi.MAX_COUNT);
        }
    }
}
