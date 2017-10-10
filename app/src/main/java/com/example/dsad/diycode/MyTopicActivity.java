package com.example.dsad.diycode;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.dsad.diycode.adpter.MyFavoriteAdpter;
import com.example.dsad.diycode.appliction.MyApplication;
import com.example.dsad.diycode.base.RequsetActivity;
import com.example.dsad.diycode.utils.DataCache;
import com.gcssloop.diycode_sdk.api.topic.bean.Topic;
import com.gcssloop.diycode_sdk.api.user.bean.UserDetail;
import com.gcssloop.diycode_sdk.api.user.event.GetUserCollectionTopicListEvent;
import com.gcssloop.diycode_sdk.api.user.event.GetUserCreateTopicListEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import at.markushi.ui.ActionView;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MyTopicActivity extends RequsetActivity implements SwipeRefreshLayout.OnRefreshListener
{

    @Bind(R.id.btn_myfavorite_back)
    ActionView btnMyfavoriteBack;
    @Bind(R.id.top_myfavorite_bar)
    Toolbar topMyfavoriteBar;
    @Bind(R.id.recycler_myfavotie_view)
    RecyclerView recyclerMyfavotieView;
    @Bind(R.id.swipe_myfavorite_layout)
    SwipeRefreshLayout swipeMyfavoriteLayout;
    private UserDetail user;
    private DataCache cache;
    private List<Topic> collections;
    private MyFavoriteAdpter favorite_adpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorite);
        ButterKnife.bind(this);
        inintdata();
        inintui();
        ininitsession();
    }

    private void inintdata()
    {
        //设刷新
        setRefresh();
        //设置topbar
        cofigtop();
        //获取用户数据
        getuser();
        //初始化
        inintrecyclerview();
        //获取我的帖子
        getMyTopic();


    }

    private void setRefresh()
    {
        swipeMyfavoriteLayout.setRefreshing(true);
        swipeMyfavoriteLayout.setOnRefreshListener(this);
    }
    private void cofigtop() {
        setSupportActionBar(topMyfavoriteBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    private void inintui() {

    }

    private void inintrecyclerview()
    {
        favorite_adpter = new MyFavoriteAdpter();
        recyclerMyfavotieView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerMyfavotieView.setAdapter(favorite_adpter);
    }

    private void ininitsession() {

    }

    public void getuser() {
        cache = new DataCache(getApplicationContext());
        user = cache.getMe();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCollection(GetUserCreateTopicListEvent eva) {
        if (eva.isOk()) {
            collections = eva.getBean();
            configrecycler();
            if (swipeMyfavoriteLayout.isRefreshing())
            {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeMyfavoriteLayout.setRefreshing(false);
                    }
                },2000);

            }
        }
    }

    //适配数据
    private void configrecycler()
    {
        favorite_adpter.setData(collections);
        favorite_adpter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh()
    {
        //刷新呃呃
       getMyTopic();
    }
    public void getMyTopic()
    {
       MyApplication.getmDiycode().getUserCreateTopicList(user.getLogin(),null,null,150);
    }
}
