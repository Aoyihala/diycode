package com.example.dsad.diycode.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;

import com.example.dsad.diycode.R;
import com.example.dsad.diycode.appliction.MyApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用的ui工具
 * Created by dsad on 2017/8/29.
 */

public class UiUtlis
{
    private UiUtlis()
    {

    }

    private static Context getContext()
    {
        return MyApplication.getmContext();
    }



    public static List<String> getViewPagerTitle()
    {
        String[] rs = getContext().getResources().getStringArray(R.array.titles);
        List<String> list = new ArrayList<>();
        for (String s :rs)
        {
            list.add(s);
        }
        return list;
    }

    /**
     * 获取本地版本号
     * @return 版本号
     */
    public static String getVersion()
    {
        String version ="";

        PackageManager manager = getContext().getPackageManager();
        try
        {
            PackageInfo info = manager.getPackageInfo(getContext().getPackageName(),0);
            version = info.versionName;
            return version;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;

    }
    /**
     * 取得颜色
     * @param colorid 颜色id
     * @return
     */
    public static int getColor(int colorid)
    {
        return getContext().getResources().getColor(colorid);
    }

    /**
     * 获取view
     * @param viewid 视图id
     * @return
     */
    public static View getView(int viewid)
    {
        return View.inflate(getContext(),viewid,null);
    }

    /**
     * 根据 url 获取 host name
     * http://www.gcssloop.com/ => www.gcssloop.com
     */
    public static String getHost(String url) {
        if (url == null || url.trim().equals("")) {
            return "";
        }
        String host = "";
        Pattern p = Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");
        Matcher matcher = p.matcher(url);
        if (matcher.find()) {
            host = matcher.group();
        }
        return host;
    }


}
