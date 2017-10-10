package com.example.dsad.diycode;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.dsad.diycode.api.DiyCodeApi;
import com.example.dsad.diycode.appliction.MyApplication;
import com.example.dsad.diycode.base.BaseActivity;
import com.example.dsad.diycode.base.RequsetActivity;
import com.example.dsad.diycode.fragment.userinof.ReplyFragment;
import com.example.dsad.diycode.fragment.userinof.UnReplyFragment;
import com.example.dsad.diycode.utils.DataCache;
import com.gcssloop.diycode_sdk.api.notifications.bean.Notification;
import com.gcssloop.diycode_sdk.api.notifications.event.GetNotificationsListEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserReplyActivity extends BaseActivity
{

    @Bind(R.id.top_userreply_tab)
    TabLayout topUserreplyTab;
    @Bind(R.id.view_userreply_pages)
    ViewPager viewUserreplyPages;
    private MyReplyAdpter reply_adpter;
    private List<Fragment> reply_fragment = new ArrayList<>();
    private List<Notification> allnotificatoins = new ArrayList<>();
    private List<Notification> read_notification = new ArrayList<>();
    private List<Notification> unread_notification = new ArrayList<>();
    private DataCache mDatacache;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reply);
        ButterKnife.bind(this);
        inintdata();
        inintui();
        inintsession();
    }
    private void inintdata()
    {
        //先请求到数据
        getReplydata();
        //初始化fragment
        inintfragment();
    }

    public void getReplydata()
    {
        mDatacache = new DataCache(getApplicationContext());
        allnotificatoins = mDatacache.getData(DiyCodeApi.GET_USERNOTIFICTION);
        paresData();

    }
    private void inintfragment()
    {
        reply_fragment.add(new UnReplyFragment(unread_notification));
        reply_fragment.add(new ReplyFragment(read_notification));
    }

    private void configViewPager()
    {
        String[] tab = {"未读","已读"};
        //设置viewpager
        reply_adpter = new MyReplyAdpter(getSupportFragmentManager(),tab,reply_fragment);
        viewUserreplyPages.setAdapter(reply_adpter);
        topUserreplyTab.setupWithViewPager(viewUserreplyPages);
    }

    private void inintui()
    {
        configViewPager();
    }
    private void inintsession()
    {

    }

    //分开数据
    private void paresData()
    {
        if (allnotificatoins!=null&&allnotificatoins.size()>0)
        {
            for (Notification one:allnotificatoins)
            {
                if (one.getRead()==true)
                {
                    read_notification.add(one);
                }
                else
                {
                    unread_notification.add(one);
                }
            }
        }
    }
    static class MyReplyAdpter extends FragmentPagerAdapter
    {


        private List<Fragment> reply_fragment;
        private String[] tab;

        public MyReplyAdpter(FragmentManager fm, String[] tab, List<Fragment> reply_fragment) {
            super(fm);
            this.tab=tab;
            this.reply_fragment=reply_fragment;

        }

        @Override
        public Fragment getItem(int position) {
            return reply_fragment.get(position);
        }

        @Override
        public int getCount() {
            return reply_fragment.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tab[position];
        }
    }

}
