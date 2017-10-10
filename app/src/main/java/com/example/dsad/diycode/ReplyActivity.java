package com.example.dsad.diycode;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.example.dsad.diycode.appliction.MyApplication;
import com.example.dsad.diycode.base.RequsetActivity;
import com.example.dsad.diycode.utils.TosatUtils;
import com.gcssloop.diycode_sdk.api.topic.bean.TopicReply;
import com.gcssloop.diycode_sdk.api.topic.event.CreateTopicReplyEvent;
import com.trycatch.mysnackbar.TSnackbar;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import at.markushi.ui.ActionView;
import butterknife.Bind;
import butterknife.ButterKnife;
public class ReplyActivity extends RequsetActivity {
    @Bind(R.id.btn_reply_back)
    ActionView btnReplyBack;
    @Bind(R.id.tv_reply_topictitle)
    TextView tvReplyTopictitle;
    @Bind(R.id.edit_reply_content)
    TextInputEditText editReplyContent;
    @Bind(R.id.edit_reply_contentlayout)
    TextInputLayout editReplyContentlayout;
    @Bind(R.id.top_reply_bar)
    Toolbar topReplyBar;
    private int topic_id;
    private TopicReply state;
    private String topic_title;
    private String user_login;
    private int flooer;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        ButterKnife.bind(this);
        inintdata();
        inintui();
        inintsession();
    }

    private void inintdata() {
        //获取id
        getTopicid();
        //设置topbar
        setSupportActionBar(topReplyBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        configedit();

    }

    private void configedit()
    {
        if (TextUtils.isEmpty(user_login)&&flooer==0)
        {
            editReplyContent.setHint("说点什么_(:зゝ∠)_");
        }
        else
        {
            if (TextUtils.isEmpty(topic_title))
            {
                tvReplyTopictitle.setText("回复用户"+user_login);
            }
            editReplyContent.setText("#"+flooer+"楼"+" "+"@"+user_login+" ");
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.reply_top_meu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_reply:
                String content = editReplyContent.getText().toString();
                if (TextUtils.isEmpty(content))
                {
                    TSnackbar.make(editReplyContent,"内容不能为空",2000).show();
                    editReplyContent.setText(null);
                }
                else
                {
                    //回复用户
                    MyApplication.getmDiycode().createTopicReply(topic_id,content);
                }
                break;
        }
        return true;
    }

    private void inintui()
    {
        tvReplyTopictitle.setText(topic_title);
    }
    private void inintsession()
    {

    }
    public void getTopicid()
    {
        Intent intent = getIntent();
        topic_id = intent.getIntExtra("topic_id", 0);
        topic_title = intent.getStringExtra("topic_title");
        //回复楼层
        user_login = intent.getStringExtra("user_login");
        flooer = intent.getIntExtra("flooer",0);
    }
    //-------------------------------回帖-----------------------------
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void createReply(CreateTopicReplyEvent eva)
    {
        if (eva.isOk())
        {
            state = eva.getBean();
            TosatUtils.ShowToast("回复成功");
            editReplyContent.setText(null);
            setResult(TopicInfoActivity.REPLY_UPDATE);
            finish();
        }
    }
}
