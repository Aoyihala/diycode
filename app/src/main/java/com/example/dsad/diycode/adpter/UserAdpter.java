package com.example.dsad.diycode.adpter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dsad.diycode.R;
import com.example.dsad.diycode.appliction.MyApplication;
import com.example.dsad.diycode.utils.ImagReplace;
import com.gcssloop.diycode_sdk.api.user.bean.User;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 用户列表的适配器
 * Created by dsad on 2017/11/14.
 */

public class UserAdpter extends RecyclerView.Adapter<UserAdpter.UserViewHolder> {

    private List<User> data;

    public void setData(List<User> data) {
        this.data = data;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_itemlist, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User one_user = data.get(position);
        Glide.with(MyApplication.getmContext()).load(ImagReplace.getImageUrl(one_user.getAvatar_url())).into(holder.imgUseritemUserhead);
        holder.tvUseritemUsername.setText(one_user.getName());
        holder.tvUseritemUserwords.setText(one_user.getLogin());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_useritem_userhead)
        CircleImageView imgUseritemUserhead;
        @Bind(R.id.tv_useritem_username)
        TextView tvUseritemUsername;
        @Bind(R.id.tv_useritem_userwords)
        TextView tvUseritemUserwords;
        public UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
