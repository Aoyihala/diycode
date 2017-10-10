package com.example.dsad.diycode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dsad.diycode.adpter.MyFavoriteAdpter;
import com.example.dsad.diycode.adpter.TopicAdpter;
import com.example.dsad.diycode.appliction.MyApplication;
import com.example.dsad.diycode.base.RequsetActivity;
import com.example.dsad.diycode.utils.DataCache;
import com.example.dsad.diycode.utils.ImagReplace;
import com.example.dsad.diycode.utils.RecyclerViewUtils;
import com.example.dsad.diycode.utils.imgLoader.MyFuliBitmapUtil;
import com.gcssloop.diycode_sdk.api.topic.bean.Topic;
import com.gcssloop.diycode_sdk.api.user.bean.User;
import com.gcssloop.diycode_sdk.api.user.bean.UserDetail;
import com.gcssloop.diycode_sdk.api.user.event.GetUserCreateTopicListEvent;
import com.gcssloop.diycode_sdk.api.user.event.GetUserEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import at.markushi.ui.ActionView;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoActivity extends RequsetActivity {

    @Bind(R.id.btn_userinfo_back)
    ActionView btnUserinfoBack;
    @Bind(R.id.img_userinfo_userhead)
    ImageView imgUserinfoUserhead;
    @Bind(R.id.tv_userinfo_username)
    TextView tvUserinfoUsername;
    @Bind(R.id.tv_userinfo_usertie)
    TextView tvUserinfoUsertie;
    @Bind(R.id.tv_userinfo_usercollect)
    TextView tvUserinfoUsercollect;
    @Bind(R.id.tv_userinfo_userabout)
    TextView tvUserinfoUserabout;
    @Bind(R.id.top_userinfo_bar)
    Toolbar topUserinfoBar;
    @Bind(R.id.tv_userinfo_tisi)
    TextView tvUserinfoTisi;
    @Bind(R.id.recycler_userinfo_view)
    RecyclerView recyclerUserinfoView;
    private String login_name;
    private UserDetail user_info;
    private DataCache mCache;
    private MyFuliBitmapUtil bitmap;
    private List<Topic> user_topiclist;
    private MyFavoriteAdpter topic_adpter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        inintdata();
        inintui();
        ininitsession();
    }

    private void inintdata()
    {
        mCache = new DataCache(getApplicationContext());
        inintrecycler();
        getLoginName();
        getUserDetal();
    }

    private void inintrecycler()
    {
        topic_adpter = new MyFavoriteAdpter();
        recyclerUserinfoView.setLayoutManager(new LinearLayoutManager(this));
        recyclerUserinfoView.setAdapter(topic_adpter);
        RecyclerViewUtils.init(getApplicationContext(),recyclerUserinfoView,topic_adpter);
    }

    private void inintui()
    {

    }
    //设置用户相关
    private void configuserinfo()
    {
        bitmap = new MyFuliBitmapUtil();
        tvUserinfoUserabout.setText("关注:"+user_info.getFollowing_count());
        tvUserinfoUsercollect.setText("收藏:"+user_info.getFavorites_count());
        tvUserinfoUsername.setText(user_info.getName());
        tvUserinfoUsertie.setText("帖子:"+user_info.getTopics_count());
        bitmap.display(user_info.getAvatar_url(),imgUserinfoUserhead);

    }
    private void ininitsession()
    {
        btnUserinfoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void getLoginName()
    {
        Intent intent = getIntent();
        login_name = intent.getStringExtra("user");
    }

    public void getUserDetal()
    {

        //如果是当前用户
        if (login_name.equals(mCache.getMe().getLogin()))
        {
            user_info = mCache.getMe();
            MyApplication.getmDiycode().getUser(login_name);
        }
        else
        {
            //请求用户详细信息
            MyApplication.getmDiycode().getUser(login_name);
        }
        //请求用户发布的帖子
        MyApplication.getmDiycode().getUserCreateTopicList(login_name,null,null,150);
    }

    private void configrecycler()
    {
        if (user_topiclist!=null&&user_topiclist.size()>0)
        {
            tvUserinfoTisi.setVisibility(View.GONE);
            recyclerUserinfoView.setVisibility(View.VISIBLE);
            topic_adpter.setData(user_topiclist);
            topic_adpter.notifyDataSetChanged();
        }
        else
        {
            recyclerUserinfoView.setVisibility(View.GONE);
            tvUserinfoTisi.setVisibility(View.VISIBLE);
        }

    }
//---------------------------请求----------------------------------------
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getUser(GetUserEvent eva)
    {
        if (eva.isOk())
        {
            user_info = eva.getBean();
            configuserinfo();
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getTopicList(GetUserCreateTopicListEvent eva)
    {
        if (eva.isOk())
        {
            user_topiclist = eva.getBean();
            //设置recycler可见
            configrecycler();
        }
    }

}
