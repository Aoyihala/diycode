package com.example.dsad.diycode.appliction;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.dsad.diycode.R;
import com.example.dsad.diycode.UserReplyActivity;
import com.example.dsad.diycode.api.DiyCodeApi;
import com.example.dsad.diycode.utils.DataCache;
import com.gcssloop.diycode_sdk.api.Diycode;
import com.gcssloop.diycode_sdk.api.notifications.bean.Notification;
import com.gcssloop.diycode_sdk.api.notifications.event.GetNotificationsListEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.GET;

/**
 * 全局的appliction域
 * Created by dsad on 2017/9/20.
 */

public class MyApplication extends Application
{   private static Diycode mDiycode;
    private static Context mContext;
    private DataCache mDatacache;
    private List<Notification> notifi_list;
    private NotificationManager  on_manmanger;
    @Override
    public void onCreate()
    {
        super.onCreate();
        //注册bus
        EventBus.getDefault().register(this);
        mContext = getApplicationContext();
        on_manmanger = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        inintdiysdk();
        getUserReplyData();
    }

    //初始化sdk
    private void inintdiysdk()
    {
        Diycode.init(getApplicationContext(),"08537db5","46fd80ddcea93fbd7f603f207b8033f0d6e7c2c017868930fda739ccff3da2fb");
        mDiycode = Diycode.getSingleInstance();
    }

    public static Diycode getmDiycode()
    {
        return mDiycode;
    }

    public static Context getmContext() {
        return mContext;
    }

    public void getUserReplyData()
    {
        mDatacache = new DataCache(getApplicationContext());
        //获取列表
        MyApplication.getmDiycode().getNotificationsList(null,150);

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getUserReply(GetNotificationsListEvent eva)
    {
        if (eva.isOk())
        {
            notifi_list = eva.getBean();
            parseData();
        }
    }

    private void parseData()
    {
        //判断未读直接展示
        List<Notification> unread_list = new ArrayList<>();
        if (notifi_list!=null&&notifi_list.size()>0)
        {
            //在这里存入本地
            mDatacache.saveListData(DiyCodeApi.GET_USERNOTIFICTION,notifi_list);
            for (Notification one:notifi_list)
            {
                if (one.getRead()==false)
                {
                    //未读
                    unread_list.add(one);
                }
            }
            if (unread_list!=null&&unread_list.size()>0)
            {
                PendingIntent pend = PendingIntent.getActivity(getApplicationContext(),0,new Intent(getApplicationContext(), UserReplyActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);
                int id=0;
                String title="message";
                String channel = "diycode";
                String ticktext ="diycode";
                String textcontent = "有"+unread_list.size()+"新消息";
                setNotificton(id,channel,title,ticktext,textcontent,pend);
            }
        }
        else
        {
            //如果从服务器拿到的为空,那么就删除原来有的 无论是几个用户
            mDatacache.removeDate(DiyCodeApi.GET_USERNOTIFICTION);
        }
    }
    private void setNotificton(int id, String channel, String title, String ticktext, String textcontent, PendingIntent pend)
    {
        NotificationChannel channelbody = new NotificationChannel(channel,"消息推送", NotificationManager.IMPORTANCE_DEFAULT);
        on_manmanger.createNotificationChannel(channelbody);
        android.app.Notification.Builder notifi = new android.app.Notification.Builder(getApplicationContext());
        notifi.setSmallIcon(R.mipmap.ic_launcher);
        notifi.setTicker(ticktext);
        notifi.setContentTitle(title);
        notifi.setContentText(textcontent);
        notifi.setContentIntent(pend);
        notifi.setChannelId(channel);
        notifi.setAutoCancel(true);
        notifi.setWhen(System.currentTimeMillis());
        android.app.Notification notifi_true = notifi.build();
        on_manmanger.notify(id,notifi_true);
    }
}
