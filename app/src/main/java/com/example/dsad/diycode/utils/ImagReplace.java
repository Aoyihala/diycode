package com.example.dsad.diycode.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * 替换url
 * Created by dsad on 2017/9/25.
 */

public class ImagReplace
{
    public static String getImageUrl(String url)
    {
        String url2 = url;
        if (url.contains("diycode"))
            url2 = url.replace("large_avatar", "avatar");
        return url2;
    }

}
