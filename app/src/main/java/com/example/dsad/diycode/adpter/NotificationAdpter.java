package com.example.dsad.diycode.adpter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dsad.diycode.R;
import com.example.dsad.diycode.UserInfoActivity;
import com.example.dsad.diycode.appliction.MyApplication;
import com.example.dsad.diycode.utils.ImagReplace;
import com.gcssloop.diycode_sdk.api.notifications.bean.Notification;
import com.gcssloop.diycode_sdk.api.notifications.bean.Reply;
import com.gcssloop.diycode_sdk.api.user.bean.User;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 通知的适配器
 * Created by dsad on 2017/9/29.
 */

public class NotificationAdpter extends RecyclerView.Adapter<NotificationAdpter.NotifiViewHolder> {

    private List<Notification> data;
    private Spanned spanned_html;
    private onNotifItemClickListener listener;
    public NotificationAdpter(List<Notification> allnotification) {
        this.data = allnotification;
    }

    @Override
    public NotifiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        return new NotifiViewHolder(view);
    }
    public void setOnItemClickListener(onNotifItemClickListener listener)
    {
        this.listener = listener;
    }
    @Override
    public void onBindViewHolder(NotifiViewHolder holder, int position)
    {
        Notification oneitem = data.get(position);
        User user = oneitem.getActor();
        Reply reply = oneitem.getMention();
        //bitmapUtil.display(ImagReplace.getImageUrl(user.getAvatar_url()),holder.imgNotifiUserhead);
        Glide.with(MyApplication.getmContext()).load(ImagReplace.getImageUrl(user.getAvatar_url()))
                .into(holder.imgNotifiUserhead);
        holder.tvNotifiUsername.setText(user.getName());
        holder.tvNotifiWhere.setText("在"+reply.getTopic_id()+"提及你");
        String text = reply.getBody_html();
        spanned_html = Html.fromHtml(text);
        holder.webNotifiContent.setText(spanned_html);
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    public void setData(List<Notification> data) {
        this.data = data;
    }

    class NotifiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        @Bind(R.id.img_notifi_userhead)
        ImageView imgNotifiUserhead;
        @Bind(R.id.tv_notifi_username)
        TextView tvNotifiUsername;
        @Bind(R.id.tv_notifi_where)
        TextView tvNotifiWhere;
        @Bind(R.id.tv_notifi_content)
        TextView webNotifiContent;
        public NotifiViewHolder(final View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            imgNotifiUserhead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), UserInfoActivity.class);
                    intent.putExtra("user",data.get(getLayoutPosition()).getActor().getLogin());
                    itemView.getContext().startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View view) {
            if (listener!=null)
            {
                if (data.get(getLayoutPosition()).getType().equals("Mention"))
                {
                    listener.onItemClick(data.get(getLayoutPosition()).getMention().getTopic_id());
                }

            }
        }
    }
  public interface onNotifItemClickListener
    {
        void onItemClick(int top_id);
    }
}
