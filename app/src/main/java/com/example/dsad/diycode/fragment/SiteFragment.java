package com.example.dsad.diycode.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;

import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.donkingliang.groupedadapter.layoutmanger.GroupedGridLayoutManager;
import com.example.dsad.diycode.R;
import com.example.dsad.diycode.adpter.SitesAdpter;
import com.example.dsad.diycode.appliction.MyApplication;
import com.example.dsad.diycode.base.BaseFragment;
import com.gcssloop.diycode_sdk.api.sites.bean.Sites;
import com.gcssloop.diycode_sdk.api.sites.event.GetSitesEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * 站点
 * Created by dsad on 2017/9/21.
 */

public class SiteFragment extends BaseFragment
{
    private List<Sites> siteList;
    private SitesAdpter site_adpter;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_site;
    }

    @Override
    protected void getDataFromSever()
    {
        //获取支持站点
        MyApplication.getmDiycode().getSites();
    }
    @Override
    protected void inintrecycler()
    {
        site_adpter = new SitesAdpter(context);
        site_adpter.setOnChildClickListener(new GroupedRecyclerViewAdapter.OnChildClickListener() {
            @Override
            public void onChildClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder, int groupPosition, int childPosition)
            {
                //获取位置 chlid
                String url = siteList.get(groupPosition).getSites().get(childPosition).getUrl();
                Uri body = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW,body);
                startActivity(intent);
            }
        });
        recyclerBaseView.setLayoutManager(new GroupedGridLayoutManager(context,3,site_adpter));
        recyclerBaseView.setAdapter(site_adpter);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getSites(GetSitesEvent eva)
    {
        if (eva.isOk())
        {
          siteList = eva.getBean();
            configrecycler();
        }
    }

    private void configrecycler()
    {
        site_adpter.setData(siteList);
        site_adpter.notifyDataSetChanged();
        if (swipeBaseRefresh.isRefreshing())
        {
            swipeBaseRefresh.setRefreshing(false);
        }
    }

}
