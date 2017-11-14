package com.example.dsad.diycode.adpter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.example.dsad.diycode.R;
import com.example.dsad.diycode.appliction.MyApplication;
import com.example.dsad.diycode.utils.ImagReplace;
import com.gcssloop.diycode_sdk.api.sites.bean.Sites;
import java.util.List;
/**
 * site的适配器
 * Created by dsad on 2017/9/27.
 */

public class SitesAdpter extends GroupedRecyclerViewAdapter {



    private List<Sites> data;
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
        Glide.with(MyApplication.getmContext()).load(ImagReplace.getImageUrl(onesite.getAvatar_url()))
                .into((ImageView) holder.get(R.id.img_siteitem_siteimg));

    }

    public void setData(List<Sites> data)
    {
        this.data = data;
    }

}
