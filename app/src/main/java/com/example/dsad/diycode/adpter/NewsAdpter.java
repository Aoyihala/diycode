package com.example.dsad.diycode.adpter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dsad.diycode.R;
import com.example.dsad.diycode.api.DiyCodeApi;
import com.example.dsad.diycode.appliction.MyApplication;
import com.example.dsad.diycode.utils.ImagReplace;
import com.example.dsad.diycode.utils.TimeUtil;
import com.example.dsad.diycode.utils.UiUtlis;
import com.gcssloop.diycode_sdk.api.news.bean.New;
import com.gcssloop.diycode_sdk.api.user.bean.User;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *
 * Created by dsad on 2017/9/22.
 */

public class NewsAdpter extends RecyclerView.Adapter {
    private final int NORMAL_TYPE = 0;
    private final int FOOT_TYPE = 1;
    private List<New> data;
    private onNewsItemClickListener listener;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == NORMAL_TYPE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
            return new NewsViewHoler(view);
        } else if (viewType == FOOT_TYPE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_layout, parent, false);
            return new FootViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (getItemViewType(position)==NORMAL_TYPE)
        {
            NewsViewHoler news_viewholder = (NewsViewHoler) holder;
            New oneitem = data.get(position);
            User user = oneitem.getUser();
            //util.display(user.getAvatar_url(),news_viewholder.imgNewsitemUserhead);
            Glide.with(MyApplication.getmContext()).load(ImagReplace.getImageUrl(user.getAvatar_url()))
                    .into(news_viewholder.imgNewsitemUserhead);
            news_viewholder.tvNewsitemNodename.setText(oneitem.getNode_name());
            news_viewholder.tvNewsitemSource.setText(UiUtlis.getHost(oneitem.getAddress()));
            news_viewholder.tvNewsitemTime.setText(TimeUtil.computePastTime(oneitem.getCreated_at()));
            news_viewholder.tvNewsitemUsername.setText(user.getName());
            news_viewholder.tvNewsitemTitle.setText(oneitem.getTitle());

        }
        else if (getItemViewType(position)==FOOT_TYPE)
        {
            if (data.size()== DiyCodeApi.MAX_COUNT)
            {
                FootViewHolder foot_viewholder = (FootViewHolder) holder;
                foot_viewholder.proFootBar.setVisibility(View.GONE);
                foot_viewholder.tvFootDescription.setText("到底了_(:зゝ∠)_");
            }
        }
    }
    public void setOnItemClickListener(onNewsItemClickListener listener)
    {
        this.listener = listener;
    }
    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return FOOT_TYPE;
        }
        return NORMAL_TYPE;
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size()+1;
    }

    public void setData(List<New> data) {
        this.data = data;
    }


    class NewsViewHoler extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        @Bind(R.id.img_newsitem_userhead)
        ImageView imgNewsitemUserhead;
        @Bind(R.id.tv_newsitem_username)
        TextView tvNewsitemUsername;
        @Bind(R.id.tv_newsitem_nodename)
        TextView tvNewsitemNodename;
        @Bind(R.id.tv_newsitem_time)
        TextView tvNewsitemTime;
        @Bind(R.id.tv_newsitem_title)
        TextView tvNewsitemTitle;
        @Bind(R.id.tv_newsitem_source)
        TextView tvNewsitemSource;

        public NewsViewHoler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener!=null)
            {
                listener.onItemClick(data.get(getLayoutPosition()));
            }
        }
    }

    class FootViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        @Bind(R.id.pro_foot_bar)
        ProgressBar proFootBar;
        @Bind(R.id.tv_foot_description)
        TextView tvFootDescription;
        public FootViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View view) {
            Uri myBlogUri = Uri.parse(data.get(getLayoutPosition()).getAddress());
            Intent intent = new Intent(Intent.ACTION_VIEW, myBlogUri);
            itemView.getContext().startActivity(intent);
        }
    }
    public interface onNewsItemClickListener
    {
        void onItemClick(New oneitem);
    }
}
