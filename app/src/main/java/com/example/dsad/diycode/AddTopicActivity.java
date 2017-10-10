package com.example.dsad.diycode;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.dsad.diycode.appliction.MyApplication;
import com.example.dsad.diycode.base.RequsetActivity;
import com.gcssloop.diycode_sdk.api.base.bean.Node;
import com.gcssloop.diycode_sdk.api.news.event.GetNewsNodesListEvent;
import com.trycatch.mysnackbar.TSnackbar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.List;
import at.markushi.ui.ActionView;
import butterknife.Bind;
import butterknife.ButterKnife;

public class AddTopicActivity extends RequsetActivity {
    @Bind(R.id.btn_addtopic_back)
    ActionView btnAddtopicBack;
    @Bind(R.id.spiner_topic_node1)
    AppCompatSpinner spinerTopicNode1;
    @Bind(R.id.spiner_topic_node2)
    AppCompatSpinner spinerTopicNode2;
    @Bind(R.id.edit_topic_title)
    TextInputEditText editTopicTitle;
    @Bind(R.id.edit_topic_content)
    TextInputEditText editTopicContent;
    @Bind(R.id.top_addtopic_bar)
    Toolbar topAddtopicBar;
    @Bind(R.id.tv_addtopic_title)
    TextView tvAddtopicTitle;
    private List<Node> node_list = new ArrayList<>();
    private List<String> node_list_string = new ArrayList<>();
    private int code;
    private int potion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_topic);
        ButterKnife.bind(this);
        ininitdata();
        inintui();
        inintsession();
    }

    private void ininitdata() {
        //获取辨识码(辨识是帖子还是nwes)
        getCode();
        //获取发帖节点
        getNode();
        //设置toolbar
        configtopbar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topic_top_createtopic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_createtopic:
                //发布帖子
                if (code == 1) {
                    //发布话题
                }
                if (code == 2) {
                    //分享新闻
                    String content = editTopicContent.getText().toString();
                    String title = editTopicTitle.getText().toString();
                    if (TextUtils.isEmpty(content)&&TextUtils.isEmpty(title))
                    {
                        TSnackbar.make(topAddtopicBar,"标题内容均不能为空",2000).show();
                    }
                    else
                    {
                        //保留----以后待解决
                        //MyApplication.getmDiycode().createNews(title,content,node_list.get(potion).getId());
                    }
                }
                break;
        }
        return true;
    }

    private void inintui()
    {
        //设置发帖节点

    }

    private void inintsession()
    {

    }
    public void getNode() {
        if (code == 1) {

            return;
        }
        if (code == 2) {
            MyApplication.getmDiycode().getNewsNodesList();
            spinerTopicNode2.setVisibility(View.INVISIBLE);
            //设置描述
            tvAddtopicTitle.setText("分享资讯");
            editTopicTitle.setHint("\t分享标题");
            editTopicContent.setHint("\t资讯链接");
            return;
        }
        finish();
    }

    private void configtopbar() {
        setSupportActionBar(topAddtopicBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void getCode() {
        //1为topic,2为news
        code = getIntent().getIntExtra("code", 0);
    }

    //-------------------------------------请求数据部分----------------------------
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getAllNode(GetNewsNodesListEvent eva) {
        if (eva.isOk()) {
            node_list = eva.getBean();
            confignewsnode1();
        }
    }

    private void confignewsnode1() {
        if (node_list != null && node_list.size() > 0) {
            for (Node name : node_list) {
                node_list_string.add(name.getName());
            }
        }
        spinerTopicNode1.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.node_item, R.id.tv_node_item, node_list_string));
        //设置点击事件
        spinerTopicNode1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                potion = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                potion=0;
            }
        });
    }

}
