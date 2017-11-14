package com.example.dsad.diycode.fragment;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.dsad.diycode.R;
import com.example.dsad.diycode.TopicInfoActivity;
import com.example.dsad.diycode.adpter.TopicAdpter;
import com.example.dsad.diycode.api.DiyCodeApi;
import com.example.dsad.diycode.appliction.MyApplication;
import com.example.dsad.diycode.base.BaseFragment;
import com.gcssloop.diycode_sdk.api.topic.bean.Topic;
import com.gcssloop.diycode_sdk.api.topic.event.GetTopicsListEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Bind;

/**
 * 话题
 * Created by dsad on 2017/9/21.
 */

public class TopicFragment extends BaseFragment
{

    private List<Topic> topic_list;
    private TopicAdpter topic_adpter;
    private String response_discreble;
    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_topic;
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
    @Override
    protected void getDataFromSever()
    {
        MyApplication.getmDiycode().getTopicsList(type_map.get(page),null,page,count);
    }

    @Override
    protected boolean setNotRefresh() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getTopicList(GetTopicsListEvent eva)
    {
        if (eva.isOk())
        {
            topic_list = eva.getBean();
            configrecyclerview();
        }
        response_discreble = eva.getCodeDescribe();
        //Snackbar.make(recyclerBaseView,response_discreble,2000).show();
    }

    private void configrecyclerview()
    {
        topic_adpter.setData(topic_list);
        topic_adpter.notifyDataSetChanged();
        if (swipeBaseRefresh.isRefreshing())
        {
            swipeBaseRefresh.setRefreshing(false);
        }
    }

}
