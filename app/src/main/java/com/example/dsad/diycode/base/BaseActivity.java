package com.example.dsad.diycode.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;

/**
 *所有activity的基类
 * Created by dsad on 2017/9/20.
 */

public class BaseActivity extends AppCompatActivity
{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState)
    {
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

}
