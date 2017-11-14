package com.example.dsad.diycode.adpter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dsad.diycode.R;
import com.example.dsad.diycode.TopicInfoActivity;
import com.example.dsad.diycode.appliction.MyApplication;
import com.example.dsad.diycode.utils.ImagReplace;
import com.example.dsad.diycode.utils.TimeUtil;
import com.gcssloop.diycode_sdk.api.topic.bean.Topic;
import com.gcssloop.diycode_sdk.api.user.bean.User;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *
 * Created by dsad on 2017/9/25.
 */

public class MyFavoriteAdpter extends RecyclerView.Adapter<MyFavoriteAdpter.MyFavoriteViewHolder> {

    private List<Topic> data;
    @Override
    public MyFavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //这里收藏用的item和topic是一样的
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_item, parent, false);
        return new MyFavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyFavoriteViewHolder holder, int position)
    {
        Topic topic = data.get(position);
        User user = topic.getUser();
        //util.display(ImagReplace.getImageUrl(user.getAvatar_url()),holder.imgTopicitemUserhead);
        Glide.with(MyApplication.getmContext()).load(ImagReplace.getImageUrl(user.getAvatar_url()))
                .into(holder.imgTopicitemUserhead);
        holder.tvTopicitemCommentcount.setText("评论数:"+"\t\t"+topic.getReplies_count());
        holder.tvTopicitemNodename.setText(topic.getNode_name());
        holder.tvTopicitemTime.setText(TimeUtil.computePastTime(topic.getCreated_at()));
        holder.tvTopicitemTitle.setText(topic.getTitle());
        holder.tvTopicitemUsername.setText(user.getName());
    }
    @Override
    public int getItemCount()
    {
        return data==null?0:data.size();
    }

    public void setData(List<Topic> data) {
        this.data = data;
    }

    class MyFavoriteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        @Bind(R.id.img_topicitem_userhead)
        ImageView imgTopicitemUserhead;
        @Bind(R.id.tv_topicitem_username)
        TextView tvTopicitemUsername;
        @Bind(R.id.tv_topicitem_time)
        TextView tvTopicitemTime;
        @Bind(R.id.tv_topicitem_title)
        TextView tvTopicitemTitle;
        @Bind(R.id.tv_topicitem_nodename)
        TextView tvTopicitemNodename;
        @Bind(R.id.tv_topicitem_commentcount)
        TextView tvTopicitemCommentcount;
        public MyFavoriteViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            Intent intent = new Intent(itemView.getContext(), TopicInfoActivity.class);
            intent.putExtra("topic",data.get(getLayoutPosition()));
            itemView.getContext().startActivity(intent);
        }
    }

}
