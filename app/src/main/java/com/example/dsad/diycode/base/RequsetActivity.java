package com.example.dsad.diycode.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import org.greenrobot.eventbus.EventBus;

/**
 * 请求数据用的activivty
 * Created by dsad on 2017/9/21.
 */

public class RequsetActivity extends AppCompatActivity
{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }
    public void startActivity(Class clazz,boolean flag)
    {
        if (flag)
        {
            startActivity(new Intent(this,clazz));
            finish();
            return;
        }
        startActivity(new Intent(this,clazz));
    }

    //注册和注销event事件
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //这里只对返回按钮做统一处理
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;


        }

        return true;
    }
}
