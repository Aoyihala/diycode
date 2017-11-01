package com.example.dsad.diycode.adpter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dsad.diycode.R;
import com.example.dsad.diycode.api.DiyCodeApi;
import com.example.dsad.diycode.utils.TimeUtil;
import com.example.dsad.diycode.utils.TosatUtils;
import com.example.dsad.diycode.utils.imgLoader.MyFuliBitmapUtil;
import com.gcssloop.diycode_sdk.api.topic.bean.Topic;
import com.gcssloop.diycode_sdk.api.user.bean.User;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 话题适配器
 * Created by dsad on 2017/9/21.
 */

public class TopicAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int NORMAL_TYPE = 0;
    private final int FOOT_TYPE = 1;
    private List<Topic> data;
    private MyFuliBitmapUtil bitmapUtil = new MyFuliBitmapUtil();
    private onItemClickListener listener;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == NORMAL_TYPE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_item, parent, false);
            return new TopicViewHoler(view);
        } else if (viewType == FOOT_TYPE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_layout, parent, false);
            return new FootViewHolder(view);
        }
        return null;
    }
    public void setOnItemClickListener(onItemClickListener listener)
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == NORMAL_TYPE)
        {
            TopicViewHoler topic_viewholder = (TopicViewHoler) holder;
            Topic topic = data.get(position);
            User user = topic.getUser();
            topic_viewholder.tvTopicitemTime.setText(TimeUtil.computePastTime(topic.getCreated_at()));
            topic_viewholder.tvTopicitemTitle.setText(topic.getTitle());
            topic_viewholder.tvTopicitemUsername.setText(user.getName());
            //Glide.with(holder.itemView.getContext()).load(user.getAvatar_url()).load(holder.imgTopicitemUserhead);
            bitmapUtil.display(user.getAvatar_url(), topic_viewholder.imgTopicitemUserhead);
            topic_viewholder.tvTopicitemNodename.setText(topic.getNode_name());
            topic_viewholder.tvTopicitemCommentcount.setText(topic.getReplies_count()+"个回复");
        } else if (getItemViewType(position) == FOOT_TYPE) {
            FootViewHolder foot_viweholder = (FootViewHolder) holder;
            if (data.size() == DiyCodeApi.MAX_COUNT)
            {
                foot_viweholder.proFootBar.setVisibility(View.GONE);
                foot_viweholder.tvFootDescription.setText("到底了_(:зゝ∠)_");
            }
        }
    }

    @Override
    public int getItemCount() {
        //又多一条布局显示？
        return data == null ? 0 : data.size() + 1;
    }

    public void setData(List<Topic> data) {
        this.data = data;
    }

    class TopicViewHoler extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        @Bind(R.id.img_topicitem_userhead)
        ImageView imgTopicitemUserhead;
        @Bind(R.id.tv_topicitem_username)
        TextView tvTopicitemUsername;
        @Bind(R.id.tv_topicitem_time)
        TextView tvTopicitemTime;
        @Bind(R.id.tv_topicitem_title)
        TextView tvTopicitemTitle;
        @Bind(R.id.tv_topicitem_commentcount)
        TextView tvTopicitemCommentcount;
        @Bind(R.id.tv_topicitem_nodename)
        TextView tvTopicitemNodename;

        public TopicViewHoler(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view)
        {
            if (listener!=null)
            {
                listener.onItemCilck(data.get(getLayoutPosition()).getId(),data.get(getLayoutPosition()));
            }
        }
    }

    class FootViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.pro_foot_bar)
        ProgressBar proFootBar;
        @Bind(R.id.tv_foot_description)
        TextView tvFootDescription;
        public FootViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public interface onItemClickListener
    {
        //这里直接去拿id,重新请求单独的topic
        void onItemCilck(int topic_id,Topic nowtopic);
    }

}
