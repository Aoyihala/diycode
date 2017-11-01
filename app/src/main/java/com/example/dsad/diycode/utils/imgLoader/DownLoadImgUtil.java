package com.example.dsad.diycode.utils.imgLoader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.dsad.diycode.utils.ConnectionUtils;
import com.example.dsad.diycode.utils.TosatUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import okhttp3.Call;

/**
 *
 * Created by dsad on 2017/9/16.
 */

public class DownLoadImgUtil
{
    //这里会加载图片,把缓存类的对象实例化
    private ImgCache img_cache;
     private Bitmap bitmap;
    private MemaryUtils me_utils;
    public DownLoadImgUtil()
    {
        me_utils = new MemaryUtils();
        img_cache = new ImgCache();
    }

    public void getImage(final String url, final ImageView view)
    {
        //异步加载的类替换掉,用okhttp
        ConnectionUtils.getData(url, new BitmapCallback()
        {
            @Override
            public void onError(Call call, Exception e, int id)
            {
                //不打印
            }
            @Override
            public void onResponse(Bitmap response, int id)
            {
                //设置tag
                view.setTag(url);
                //设置了图片,该设缓存了
                if (view.getTag()!=null&&url.equals(view.getTag()))
                {
                    view.setImageBitmap(response);
                }
                //内存
                me_utils.load(url,response);
                //本地
                img_cache.WriteCahce(url,response);
            }
        });
    }
}
