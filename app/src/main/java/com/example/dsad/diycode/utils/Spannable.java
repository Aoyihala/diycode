package com.example.dsad.diycode.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;

/**
 * 设置文本变色
 * Created by dsad on 2017/9/27.
 */

public class Spannable
{
    private static SpannableString ables;
    private Spannable()
    {

    }

    /**
     * 展示文字和图片
     * @param text 文本
     * @param image 图片
     * @return
     */
    public static SpannableString ShowTextWhithImage(Context context, String text, int image)
    {
        ables = new SpannableString(text);
        //根据图片id获取到Drawble
        Drawable now_emoje = context.getDrawable(image);
        //绘制图片边界(必须)
        now_emoje.setBounds(0,0,20,20);
        //替换内容开始的位置(中括号内是表情包)
        int start = text.indexOf("[");
        int end = text.indexOf("]")+1;
        //文字图片结合最终
        ImageSpan span = new ImageSpan(now_emoje);
        ables.setSpan(span,start,end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return ables;
    }

    /**
     * 设置文字颜色
     * @param text 文本
     * @param start 开始变色的位置
     * @param end 结束变色的位置
     * @param color 变成什么颜色
     * @return
     */
    public static SpannableString ShowTextWhithColor(String text,int start,int end,int color)
    {
        ables = new SpannableString(text);
        //设置文字前景色
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        //背景色设置了就是傻子
        ables.setSpan(colorSpan,start,end,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);//包含头包含尾
        return ables;

    }
}
