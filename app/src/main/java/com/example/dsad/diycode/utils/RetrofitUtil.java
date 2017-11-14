package com.example.dsad.diycode.utils;

import com.example.dsad.diycode.api.DiyCodeApi;

import java.lang.reflect.ParameterizedType;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 *
 *Retrofit工具
 * Created by dsad on 2017/11/13.
 */

public class RetrofitUtil
{
    private Retrofit retrofit;
    private static volatile RetrofitUtil instance;
    private OkHttpClient client;
    public RetrofitUtil()
    {
        inintretrofit();
    }
    public static RetrofitUtil getInstance()
    {
        if (instance==null)
        {
            synchronized (RetrofitUtil.class)
            {
                if (instance == null)
                {
                    instance = new RetrofitUtil();
                }
            }
        }
        return instance;
    }
    private void inintretrofit()
    {
        if (retrofit!=null)
        {
            return;
        }
        client = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10,TimeUnit.SECONDS)
                .build();
        //沿用okhttp的设置
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(DiyCodeApi.MEITU)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public OkHttpClient getClient() {
        return client;
    }
}
