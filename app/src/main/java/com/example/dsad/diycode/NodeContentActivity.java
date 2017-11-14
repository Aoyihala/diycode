package com.example.dsad.diycode;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.dsad.diycode.adpter.NewsAdpter;
import com.example.dsad.diycode.api.DiyCodeApi;
import com.example.dsad.diycode.appliction.MyApplication;
import com.example.dsad.diycode.base.RequsetActivity;
import com.gcssloop.diycode_sdk.api.news.bean.New;
import com.gcssloop.diycode_sdk.api.news.event.GetNewsListEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NodeContentActivity extends RequsetActivity {

    @Bind(R.id.tv_nodecontent_title)
    TextView tvNodecontentTitle;
    @Bind(R.id.top_nodecontent_bar)
    Toolbar topNodecontentBar;
    @Bind(R.id.recycler_nodecontent_view)
    RecyclerView recyclerNodecontentView;
    private int nodeid;
    private List<New> newslist;
    private NewsAdpter newsadpter;
    private String title;
    private int defaultcount= DiyCodeApi.DEFAULT_COUNT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_content);
        ButterKnife.bind(this);
        inintdata();
        inintui();
        inintsession();
    }
    private void inintdata()
    {
        inintrecycler();
        //获取新闻节点id
        getNewsIdTitle();
        //获取数据
        getData();
    }

    private void inintrecycler()
    {
        newsadpter = new NewsAdpter();
        recyclerNodecontentView.setLayoutManager(new LinearLayoutManager(this));
        recyclerNodecontentView.setAdapter(newsadpter);
    }

    private void inintui()
    {
        setSupportActionBar(topNodecontentBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvNodecontentTitle.setText(title);
    }
    private void inintsession()
    {
        //跳转链接
        newsadpter.setOnItemClickListener(new NewsAdpter.onNewsItemClickListener() {
            @Override
            public void onItemClick(New oneitem) {
                Uri myBlogUri = Uri.parse(oneitem.getAddress());
                Intent intent = new Intent(Intent.ACTION_VIEW, myBlogUri);
                startActivity(intent);
            }
        });
        //底部加载
        recyclerNodecontentView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState)
                {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        //到底了
                        if (defaultcount!=DiyCodeApi.MAX_COUNT)
                        {
                            defaultcount+=DiyCodeApi.DEFAULT_COUNT;
                            getData();
                        }
                        break;

                }
            }
        });
    }
    public void getNewsIdTitle() {
        nodeid = getIntent().getIntExtra("node_id",0);
        title = getIntent().getStringExtra("title");
    }
    public void getData() {
        if (nodeid!=0)
        {
            MyApplication.getmDiycode().getNewsList(nodeid,null,defaultcount);
        }
        else
        {
            Snackbar.make(topNodecontentBar,"节点选择失败",2000).show();
        }

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public  void getNodeNewsList(GetNewsListEvent eva)
    {
        if (eva.isOk())
        {
            newslist = eva.getBean();
            update();
        }
    }
    private void update()
    {
        newsadpter.setData(newslist);
        newsadpter.notifyDataSetChanged();
    }
}
