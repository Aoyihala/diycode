package com.example.dsad.diycode;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.dsad.diycode.adpter.MyFavoriteAdpter;
import com.example.dsad.diycode.api.DiyCodeApi;
import com.example.dsad.diycode.appliction.MyApplication;
import com.example.dsad.diycode.base.BaseActivity;
import com.example.dsad.diycode.base.RequsetActivity;
import com.example.dsad.diycode.utils.DataCache;
import com.gcssloop.diycode_sdk.api.topic.bean.Topic;
import com.gcssloop.diycode_sdk.api.user.bean.UserDetail;
import com.gcssloop.diycode_sdk.api.user.event.GetUserCollectionTopicListEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import at.markushi.ui.ActionView;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MyFavoriteActivity extends BaseActivity {

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
        //获取收藏
        getFravorite();


    }

    private void setRefresh()
    {
        swipeMyfavoriteLayout.setRefreshing(true);
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

    private void ininitsession()
    {
        btnMyfavoriteBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void getuser() {
        cache = new DataCache(getApplicationContext());
        user = cache.getMe();
    }


    //适配数据
    private void configrecycler()
    {
        favorite_adpter.setData(collections);
        favorite_adpter.notifyDataSetChanged();
    }

    public void getFravorite()
    {
       collections = cache.getData(DiyCodeApi.GET_USERCOLLECTION);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (swipeMyfavoriteLayout.isRefreshing())
                {
                    swipeMyfavoriteLayout.setRefreshing(false);
                }
            }
        },2000);
        configrecycler();
    }

}
