package com.example.dsad.diycode.utils.imgLoader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

/**
 * 图片的异步下载框架
 * Created by dsad on 2017/9/16.
 */

public class MyFuliBitmapUtil
{
    private MemaryUtils memary_utlis;
    private Bitmap bitmap;
    private ImgCache cache;
    private DownLoadImgUtil downLoadImgUtil;
    public MyFuliBitmapUtil()
    {
        memary_utlis = new MemaryUtils();
        cache = new ImgCache();
        downLoadImgUtil = new DownLoadImgUtil();

    }
    public void display(String url, ImageView view)
    {
        //这里还可以设置默认图片
        //先到内存里去读
        Bitmap me_bitmap = memary_utlis.read(url);
        if (me_bitmap!=null)
        {
            view.setImageBitmap(me_bitmap);
            return;
        }
        //内存没有就文件
        Bitmap file_bitmap = cache.ReadCach(url);
        if (file_bitmap!=null)
        {
            //file_bitmap.setConfig(Bitmap.Config.RGB_565);
            view.setImageBitmap(file_bitmap);
            return;
        }
        //最后是网络
        downLoadImgUtil.getImage(url,view);
    }
    /***
     * 单纯的从缓存获取图片
     * @param url
     * @return
     */
    public Bitmap getBitmap(String url)
    {
        bitmap = cache.ReadCach(url);
        return bitmap;
    }
    /***
     * 在bitmap取得图片时有用
     * @return 设置
     */
    public BitmapFactory.Options getoption()
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize=2;//原图的4分之1
        return options;
    }
    /***
     * 外部缓存设置
     */
    public void setCache(String imageUrl, Bitmap response)
    {
        cache.WriteCahce(imageUrl,response);
    }
}
