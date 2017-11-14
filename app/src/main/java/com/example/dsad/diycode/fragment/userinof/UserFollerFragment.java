package com.example.dsad.diycode.fragment.userinof;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;

import com.example.dsad.diycode.R;
import com.example.dsad.diycode.TopicInfoActivity;
import com.example.dsad.diycode.adpter.TopicAdpter;
import com.example.dsad.diycode.adpter.UserAdpter;
import com.example.dsad.diycode.api.DiyCodeApi;
import com.example.dsad.diycode.appliction.MyApplication;
import com.example.dsad.diycode.base.BaseFragment;
import com.gcssloop.diycode_sdk.api.topic.bean.Topic;
import com.gcssloop.diycode_sdk.api.user.bean.User;
import com.gcssloop.diycode_sdk.api.user.event.GetUserCreateTopicListEvent;
import com.gcssloop.diycode_sdk.api.user.event.GetUserFollowingListEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 *
 * Created by dsad on 2017/11/13.
 */

public class UserFollerFragment extends BaseFragment
{

    private String username;
    private List<User> userlist;
    private UserAdpter user_adpter;
    @Override
    protected int getLayoutId() {
        return R.layout.base_recyclerview_layout;
    }

    @Override
    protected void getDataFromSever() {
        getUserLogin();
        if (username!=null&&!username.equals(""))
        {
            MyApplication.getmDiycode().getUserFollowingList(username,null, DiyCodeApi.MAX_COUNT);
        }
    }

    @Override
    protected boolean setNotRefresh() {
        return false;
    }

    @Override
    protected void inintrecycler()
    {
         user_adpter = new UserAdpter();
        recyclerBaseView.setLayoutManager(new LinearLayoutManager(context));
        recyclerBaseView.setAdapter(user_adpter);
    }
    public void getUserLogin() {
        username = this.getArguments().getString("user");
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getUserDetal(GetUserFollowingListEvent eva)
    {
        if (eva.isOk())
        {
            userlist = eva.getBean();
            update();
        }
    }

    private void update()
    {
        user_adpter.setData(userlist);
        user_adpter.notifyDataSetChanged();
    }
}
