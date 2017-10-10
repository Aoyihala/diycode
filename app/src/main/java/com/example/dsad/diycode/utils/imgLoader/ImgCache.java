package com.example.dsad.diycode.utils.imgLoader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.example.dsad.diycode.utils.MD5Encoder;
import com.example.dsad.diycode.utils.TosatUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 *
 * Created by dsad on 2017/9/16.
 */

public class ImgCache
{

    public static final String LOCAL_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+
            "/diycode/user_cache";
    public void WriteCahce(String url, Bitmap bitmap) {
        if (PictUtil.hasSDCard())
        {
            File file = new File(LOCAL_PATH);
            if (file.exists() == false||!file.isDirectory())
            {
                //创建,需要权限
                file.mkdir();
                file.mkdirs();
            }
            //这里的url有很多特殊符号,转成md5
            try {
                String filename = MD5Encoder.encode(url);
                File file1 = new File(file,filename);
                //compress方法
                //1.图片格式
                //2.压缩比例
                //3.输出流
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,new FileOutputStream(file1));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            TosatUtils.ShowToast("请插入sd卡");
        }


    }
    public Bitmap ReadCach(String url)
    {
        //读缓存
        try {
            File fi = new File(LOCAL_PATH,MD5Encoder.encode(url));
            if (fi.exists())
            {
                Bitmap map =  BitmapFactory.decodeStream(new FileInputStream(fi));
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
