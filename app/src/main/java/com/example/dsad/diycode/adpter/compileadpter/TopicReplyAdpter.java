package com.example.dsad.diycode.adpter.compileadpter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

<<<<<<< HEAD
import com.bumptech.glide.Glide;
=======
import com.example.dsad.diycode.ImageActivity;
>>>>>>> origin/master
import com.example.dsad.diycode.R;
import com.example.dsad.diycode.ReplyActivity;
import com.example.dsad.diycode.UserInfoActivity;
import com.example.dsad.diycode.appliction.MyApplication;
import com.example.dsad.diycode.utils.ImagReplace;
import com.example.dsad.diycode.utils.TimeUtil;
import com.gcssloop.diycode_sdk.api.topic.bean.TopicReply;
import com.gcssloop.diycode_sdk.api.user.bean.User;
<<<<<<< HEAD
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.OnImageClickListener;
=======
import com.zzhoujay.richtext.LinkHolder;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.LinkFixCallback;
import com.zzhoujay.richtext.callback.OnImageClickListener;
import com.zzhoujay.richtext.callback.OnImageLongClickListener;
import com.zzhoujay.richtext.callback.OnUrlClickListener;
>>>>>>> origin/master

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 回复的adpter
 * Created by dsad on 2017/9/23.
 */

public class TopicReplyAdpter extends RecyclerView.Adapter<TopicReplyAdpter.TopicReplyViewHolder> {
    private List<TopicReply> data;
    private onclickImageListener listener;
    private Spanned spanned_html;
    public TopicReplyAdpter() {

    }
    public void setOnImageClickListener(onclickImageListener listener)
    {
        this.listener = listener;
    }
    @Override
    public TopicReplyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_comment, parent, false);
        return new TopicReplyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TopicReplyViewHolder holder, final int position) {
        final TopicReply oneitem = data.get(position);
        final User user = oneitem.getUser();
<<<<<<< HEAD
        //util.display(ImagReplace.getImageUrl(user.getAvatar_url()), holder.imgTopicinfoCommenthead);
        Glide.with(MyApplication.getmContext()).load(ImagReplace.getImageUrl(user.getAvatar_url()))
                .into(holder.imgTopicinfoCommenthead);
=======
        util.display(ImagReplace.getImageUrl(user.getAvatar_url()), holder.imgTopicinfoCommenthead);
>>>>>>> origin/master
        holder.tvTopicinfoCommentusername.setText(user.getName());
        //holder.tvTopicinfoCommentcontent.loadDataWithBaseURL(null, oneitem.getBody_html(), "text/html", "utf-8", null);
        //holder.tvTopicinfoCommentcontent.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        RichText.fromHtml(oneitem.getBody_html())
                .autoFix(true)
                .clickable(true)
                .imageClick(new OnImageClickListener()
                {
                    @Override
                    public void imageClicked(List<String> imageUrls, int position) {
<<<<<<< HEAD
                        /*这里把url传到接口,方便接口实现的时候获取
                        Intent intent = new Intent(MyApplication.getmContext(), ImageActivity.class);
                        intent.putExtra("imgurl",imageUrls.get(position));
                        MyApplication.getmContext().startActivity(intent);
                        */
                        if (listener!=null)
                        {
                            listener.onImgClick(imageUrls.get(position));
                        }
=======
                        Intent intent = new Intent(MyApplication.getmContext(), ImageActivity.class);
                        intent.putExtra("imgurl",imageUrls.get(position));
                        MyApplication.getmContext().startActivity(intent);
>>>>>>> origin/master
                    }
                })
                .into(holder.tvTopicinfoCommentcontent);
        holder.tvTopicinfoCommentcontent.setClickable(false);
        holder.tvTopicinfoCommentcontent.setFocusable(false);
        holder.tvTopicinfoCommenttime.setText(TimeUtil.computePastTime(oneitem.getCreated_at()));

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<TopicReply> data) {
        this.data = data;
    }

    class TopicReplyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.img_topicinfo_commenthead)
        CircleImageView imgTopicinfoCommenthead;
        @Bind(R.id.tv_topicinfo_commentusername)
        TextView tvTopicinfoCommentusername;
        @Bind(R.id.tv_topicinfo_commenttime)
        TextView tvTopicinfoCommenttime;
        @Bind(R.id.web_topicinfo_commentcontent)
        TextView tvTopicinfoCommentcontent;
        @Bind(R.id.tv_reply_replyuser)
        TextView tvReplyReplyuser;
        public TopicReplyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            imgTopicinfoCommenthead.setOnClickListener(this);
            tvReplyReplyuser.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId())
            {
                case R.id.img_topicinfo_commenthead:
                    Intent user_intent = new Intent(itemView.getContext(), UserInfoActivity.class);
                    user_intent.putExtra("user", data.get(getLayoutPosition()).getUser().getLogin());
                    itemView.getContext().startActivity(user_intent);
                    break;
                case R.id.tv_reply_replyuser:
                    Intent reply_intent = new Intent(itemView.getContext(), ReplyActivity.class);
                    //楼层和用户的登录名(不是昵称)
                    reply_intent.putExtra("topic_id",data.get(getLayoutPosition()).getTopic_id());
                    reply_intent.putExtra("flooer",getLayoutPosition()+1);
                    reply_intent.putExtra("user_login",data.get(getLayoutPosition()).getUser().getLogin());
                    itemView.getContext().startActivity(reply_intent);
                    break;
            }

        }
    }
    public interface onclickImageListener
    {
        void onImgClick(String url);
    }
}
