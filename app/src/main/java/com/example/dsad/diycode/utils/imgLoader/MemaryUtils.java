package com.example.dsad.diycode.utils.imgLoader;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 *
 * Created by dsad on 2017/10/30.
 */

class MemaryUtils
{
    private LruCache<String,Bitmap> bitmapcache;
    private long maxsize;
    private long M=1024*1024;
    public MemaryUtils()
    {
        maxsize = Runtime.getRuntime().maxMemory();
        bitmapcache = new LruCache<String,Bitmap>((int) (maxsize/5))
        {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    public Bitmap read(String url)
    {
        return bitmapcache.get(url);
    }

    public void load(String url, Bitmap response)
    {
        bitmapcache.put(url,response);
    }
}
