package com.example.dsad.diycode.fragment.userinof;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.dsad.diycode.R;
import com.example.dsad.diycode.TopicInfoActivity;
import com.example.dsad.diycode.adpter.TopicAdpter;
import com.example.dsad.diycode.api.DiyCodeApi;
import com.example.dsad.diycode.appliction.MyApplication;
import com.example.dsad.diycode.base.BaseFragment;
import com.gcssloop.diycode_sdk.api.topic.bean.Topic;
import com.gcssloop.diycode_sdk.api.topic.bean.TopicContent;
import com.gcssloop.diycode_sdk.api.user.event.GetUserCreateTopicListEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 *
 * Created by dsad on 2017/11/13.
 */

public class UserTopicsFragment extends BaseFragment
{
    private String username;
    private List<Topic> topiclist;
    private TopicAdpter topic_adpter;
    @Override
    protected int getLayoutId() {
        return R.layout.base_recyclerview_layout;
    }

    @Override
    protected boolean setNotRefresh() {
        return false;
    }

    @Override
    protected void getDataFromSever() {
        getUserLogin();
        if (username!=null&&!username.equals(""))
        {
            MyApplication.getmDiycode().getUserCreateTopicList(username,null,null, DiyCodeApi.MAX_COUNT);
        }
    }
    protected  void inintbottmloader()
    {

        recyclerBaseView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState)
                {
                    case RecyclerView.SCROLL_STATE_IDLE:

                        count=DiyCodeApi.MAX_COUNT;
                        getDataFromSever();
                        break;
                }
            }
        });
    }

    @Override
    protected void inintrecycler()
    {
        topic_adpter = new TopicAdpter();
        topic_adpter.setOnItemClickListener(new TopicAdpter.onItemClickListener() {
            @Override
            public void onItemCilck(int topic_id,Topic nowtopic) {
                Intent intent = new Intent(context, TopicInfoActivity.class);
                intent.putExtra("topic_id",topic_id);
                intent.putExtra("topic",nowtopic);
                startActivity(intent);
            }
        });
        recyclerBaseView.setLayoutManager(new LinearLayoutManager(context));
        recyclerBaseView.setAdapter(topic_adpter);
    }
    public void getUserLogin() {
        username = this.getArguments().getString("user");
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getUserDetal(GetUserCreateTopicListEvent eva)
    {
        if (eva.isOk())
        {
            topiclist = eva.getBean();
            update();
        }
    }

    private void update()
    {
        topic_adpter.setData(topiclist);
        topic_adpter.notifyDataSetChanged();
    }
}
