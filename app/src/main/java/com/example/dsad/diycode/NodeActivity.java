package com.example.dsad.diycode;

import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.gravity.IChildGravityResolver;
import com.beloo.widget.chipslayoutmanager.layouter.breaker.IRowBreaker;
import com.example.dsad.diycode.adpter.NodeAdpter;
import com.example.dsad.diycode.appliction.MyApplication;
import com.example.dsad.diycode.base.BaseActivity;
import com.example.dsad.diycode.base.RequsetActivity;
import com.example.dsad.diycode.utils.SpaceRecyclerItem;
import com.gcssloop.diycode_sdk.api.base.bean.Node;
import com.gcssloop.diycode_sdk.api.news.event.GetNewsNodesListEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Request;

public class NodeActivity extends RequsetActivity{

    @Bind(R.id.top_nodeselect_bar)
    Toolbar topNodeselectBar;
    @Bind(R.id.recycler_nodeselct_view)
    RecyclerView recyclerNodeselctView;
    private List<Node> nodes;
    private NodeAdpter node_adpter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node);
        ButterKnife.bind(this);
        inintdata();
        inintui();
        inintsession();
    }
    private void inintdata()
    {
        inintrecycler();
        //获取节点
        MyApplication.getmDiycode().getNewsNodesList();
    }

    private void inintrecycler()
    {
        node_adpter = new NodeAdpter();
        recyclerNodeselctView.setLayoutManager(new StaggeredGridLayoutManager(2,1));
        recyclerNodeselctView.setAdapter(node_adpter);
        recyclerNodeselctView.addItemDecoration(new SpaceRecyclerItem(8));
    }

    private void inintui()
    {
        setSupportActionBar(topNodeselectBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void inintsession()
    {

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getNewsNodeList(GetNewsNodesListEvent eva)
    {
        if (eva.isOk())
        {
            nodes = eva.getBean();
            update();
        }
    }

    private void update()
    {
        node_adpter.setData(nodes);
        node_adpter.notifyDataSetChanged();
    }
}
