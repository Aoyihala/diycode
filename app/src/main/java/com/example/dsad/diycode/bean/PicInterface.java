package com.example.dsad.diycode.bean;

import com.example.dsad.diycode.api.DiyCodeApi;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * 图片加载
 * Created by dsad on 2017/11/13.
 */

public interface PicInterface
{
    @GET(DiyCodeApi.MEITU)
    Call<PicStory> getpicstory();

}
