package com.example.dsad.diycode.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;

import com.example.dsad.diycode.R;
import com.example.dsad.diycode.adpter.NewsAdpter;
import com.example.dsad.diycode.appliction.MyApplication;
import com.example.dsad.diycode.base.BaseFragment;
import com.gcssloop.diycode_sdk.api.news.bean.New;
import com.gcssloop.diycode_sdk.api.news.event.GetNewsListEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 *
 * Created by dsad on 2017/9/21.
 */

public class NewsFragment extends BaseFragment
{
    private List<New> news_list;
    private NewsAdpter news_adpter;
    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_news;
    }

    @Override
    protected void getDataFromSever()
    {
        MyApplication.getmDiycode().getNewsList(null,null,count);
    }

    @Override
    protected boolean setNotRefresh() {
        return true;
    }

    @Override
    protected void inintrecycler()
    {
        news_adpter = new NewsAdpter();
        news_adpter.setOnItemClickListener(new NewsAdpter.onNewsItemClickListener() {
            @Override
            public void onItemClick(New oneitem) {
                Uri myBlogUri = Uri.parse(oneitem.getAddress());
                Intent intent = new Intent(Intent.ACTION_VIEW, myBlogUri);
                startActivity(intent);
            }
        });

        recyclerBaseView.setLayoutManager(new LinearLayoutManager(context));
        recyclerBaseView.setAdapter(news_adpter);
    }
//-------------------------------------------------------------
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getNewsList(GetNewsListEvent eva)
    {
        if (eva.isOk())
        {
            news_list = eva.getBean();
            configrecycler();
        }
    }

    private void configrecycler()
    {
        news_adpter.setData(news_list);
        news_adpter.notifyDataSetChanged();
        if (swipeBaseRefresh.isRefreshing())
        {
            swipeBaseRefresh.setRefreshing(false);
        }
    }
}
