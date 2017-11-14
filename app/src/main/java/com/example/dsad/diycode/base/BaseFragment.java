package com.example.dsad.diycode.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dsad.diycode.R;
import com.example.dsad.diycode.api.DiyCodeApi;
import com.example.dsad.diycode.utils.UiUtlis;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *
 * Created by dsad on 2017/9/21.
 */

public abstract class BaseFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
    protected AppCompatActivity activity;
    protected Context context;
    private boolean refrsh=true;
    @Bind(R.id.recycler_base_view)
    protected RecyclerView recyclerBaseView;
    @Bind(R.id.swipe_base_refresh)
    protected SwipeRefreshLayout swipeBaseRefresh;
    protected int page= 0;
    protected int count = DiyCodeApi.DEFAULT_COUNT;
    protected Map<Integer,String> type_map = new HashMap<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = UiUtlis.getView(getLayoutId());
        ButterKnife.bind(this,v);
        EventBus.getDefault().register(this);
        return v;

    }

    protected abstract int getLayoutId();
    protected  abstract void getDataFromSever();
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (AppCompatActivity) getActivity();
        context = getContext();
        inintdata();
    }
    protected abstract boolean setNotRefresh();

    @Override
    public void onDestroyView() 
    {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    protected  void inintdata()
    {
        inintmap();
        setRefresh();
        inintrecycler();
        inintbottmloader();
        getDataFromSever();
    }
    protected  void inintbottmloader()
    {

        recyclerBaseView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState)
                {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        if (count!=DiyCodeApi.MAX_COUNT)
                        {
                            count+=20;
                            getDataFromSever();
                        }
                        break;
                }
            }
        });
    }
    protected  void inintmap()
    {
        type_map.put(0,DiyCodeApi.TOPIC_TYPE_EX);
        type_map.put(1,DiyCodeApi.TOPIC_TYPE_LAST_ACTIVIE);
        type_map.put(2,DiyCodeApi.TOPIC_TYPE_NO_REPLY);
        type_map.put(3,DiyCodeApi.TOPIC_TYPE_POPULAR);
        type_map.put(4,DiyCodeApi.TOPIC_TYPE_RECENT);
    }

    private void setRefresh()
    {
        refrsh = setNotRefresh();
        if (refrsh==true)
        {
            swipeBaseRefresh.setRefreshing(true);
        }
        swipeBaseRefresh.setOnRefreshListener(this);

    }
    protected abstract void inintrecycler();

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
    @Override
    public void onRefresh()
    {
        if (page==4)
        {
            page=0;
        }
        else
        {
            page+=1;
        }
        count=20;
        getDataFromSever();
    }
}
