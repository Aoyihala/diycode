package com.example.dsad.diycode.adpter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.example.dsad.diycode.R;
import com.example.dsad.diycode.utils.ConnectionUtils;
import com.example.dsad.diycode.utils.ImagReplace;
import com.example.dsad.diycode.utils.imgLoader.MyFuliBitmapUtil;
import com.gcssloop.diycode_sdk.api.sites.bean.Sites;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;


/**
 * site的适配器
 * Created by dsad on 2017/9/27.
 */

public class SitesAdpter extends GroupedRecyclerViewAdapter {



    private List<Sites> data;
    private MyFuliBitmapUtil bitmapUtil = new MyFuliBitmapUtil();
    public SitesAdpter(Context context) {
        super(context);
    }

    @Override
    public int getGroupCount() {
        //改data意为分组，里面又分了个list
        return data == null ? 0 : data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return data == null ? 0 : data.get(groupPosition).getSites().size();
    }

    //----------------------------------
    //打开开关
    @Override
    public boolean hasHeader(int groupPosition) {
        return true;
    }

    @Override
    public boolean hasFooter(int groupPosition) {
        return false;
    }

    //-----------------------------------
    @Override
    public int getHeaderLayout(int viewType) {
        return R.layout.site_head;
    }

    @Override
    public int getFooterLayout(int viewType) {
        return 0;
    }

    @Override
    public int getChildLayout(int viewType) {
        return R.layout.site_content_item;
    }

    @Override
    public void onBindHeaderViewHolder(BaseViewHolder holder, int groupPosition)
    {
        //设置组名
       holder.setText(R.id.tv_siteitem_groupname,data.get(groupPosition).getName());
    }
    @Override
    public void onBindFooterViewHolder(BaseViewHolder holder, int groupPosition)
    {

    }
    @Override
    public void onBindChildViewHolder(final BaseViewHolder holder, int groupPosition, int childPosition)
    {
        final Sites.Site  onesite= data.get(groupPosition).getSites().get(childPosition);
        holder.setText(R.id.tv_siteitem_sitename,onesite.getName());
        if (bitmapUtil.getBitmap(ImagReplace.getImageUrl(onesite.getAvatar_url()))!=null)
        {
            holder.setImageBitmap(R.id.img_siteitem_siteimg,bitmapUtil.getBitmap(ImagReplace.getImageUrl(onesite.getAvatar_url())));
        }
        else
        {
            ConnectionUtils.getData(ImagReplace.getImageUrl(onesite.getAvatar_url()), new BitmapCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {

                }

                @Override
                public void onResponse(Bitmap response, int id) {
                    holder.setImageBitmap(R.id.img_siteitem_siteimg,response);
                    bitmapUtil.setCache(ImagReplace.getImageUrl(onesite.getAvatar_url()),response);
                }
            });
        }

    }

    public void setData(List<Sites> data)
    {
        this.data = data;
    }

}
