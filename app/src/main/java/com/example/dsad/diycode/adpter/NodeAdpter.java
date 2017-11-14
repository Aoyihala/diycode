package com.example.dsad.diycode.adpter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dsad.diycode.NodeActivity;
import com.example.dsad.diycode.NodeContentActivity;
import com.example.dsad.diycode.R;
import com.example.dsad.diycode.appliction.MyApplication;
import com.gcssloop.diycode_sdk.api.base.bean.Node;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 节点的adpter
 * Created by dsad on 2017/11/9.
 */

public class NodeAdpter extends RecyclerView.Adapter<NodeAdpter.NodeViewHolder> {


    private List<Node> data;

    @Override
    public NodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.node_item, parent, false);
        return new NodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NodeViewHolder holder, int position) {
        holder.tvNodeItem.setText(data.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    public void setData(List<Node> data) {
        this.data = data;
    }

    class NodeViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_node_item)
        TextView tvNodeItem;
        public NodeViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyApplication.getmContext(), NodeContentActivity.class);
                    intent.putExtra("node_id",data.get(getLayoutPosition()).getId());
                    intent.putExtra("title",data.get(getLayoutPosition()).getName());
                    itemView.getContext().startActivity(intent);
                }
            });

        }
    }

}
