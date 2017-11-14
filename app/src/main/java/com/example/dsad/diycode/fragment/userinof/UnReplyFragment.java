package com.example.dsad.diycode.fragment.userinof;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dsad.diycode.R;
import com.example.dsad.diycode.TopicInfoActivity;
import com.example.dsad.diycode.adpter.NotificationAdpter;
import com.example.dsad.diycode.utils.UiUtlis;
import com.gcssloop.diycode_sdk.api.notifications.bean.Notification;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 另一个fragment
 * Created by dsad on 2017/9/29.
 */

public class UnReplyFragment extends Fragment {
    private List<Notification> allnotification;
    @Bind(R.id.recycler_userreply_view)
    RecyclerView recyclerUserreplyView;
    private List<android.app.Notification> notifica_list = new ArrayList<>();
    private NotificationAdpter no_adpter;
<<<<<<< HEAD

    /**
    public UnReplyFragment(List<com.gcssloop.diycode_sdk.api.notifications.bean.Notification> allnotification) {
        this.allnotification = allnotification;
    }
**/
=======
/**
    public UnReplyFragment(List<com.gcssloop.diycode_sdk.api.notifications.bean.Notification> allnotification) {
        this.allnotification = allnotification;
    }
 **/

>>>>>>> origin/master
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = UiUtlis.getView(R.layout.fragment_reply);
        ButterKnife.bind(this, view);
        inintui();
        return view;
    }

    private void inintui() {
        //初始化inint
        inintrecyclerview();
    }

    private void inintrecyclerview()
    {
        no_adpter = new NotificationAdpter(allnotification);
        no_adpter.setOnItemClickListener(new NotificationAdpter.onNotifItemClickListener() {
            @Override
            public void onItemClick(int top_id)
            {
                Intent intent = new Intent(getContext(), TopicInfoActivity.class);
                intent.putExtra("topic_id",top_id);
                startActivity(intent);
            }
        });
        recyclerUserreplyView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerUserreplyView.setAdapter(no_adpter);
    }
}
