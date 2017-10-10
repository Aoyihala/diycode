package com.example.dsad.diycode.utils;

import android.widget.Toast;

import com.example.dsad.diycode.appliction.MyApplication;

/**
 * 弹窗工具,减少消耗
 * Created by My on 2017/6/14.
 */
public class TosatUtils
{
    private static Toast toast;
    private TosatUtils()
    {

    }
    public static void ShowToast(String str)
    {
        if (toast==null)
        {
            //第一次创建
           toast= Toast.makeText(MyApplication.getmContext(),str, Toast.LENGTH_SHORT);
        }
        else
        {
            toast.setText(str);
        }
        toast.show();
    }
}
