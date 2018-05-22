package com.beeshop.beeshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beeshop.beeshop.R;

import java.util.List;

/**
 * Author：cooper
 * Time：  2018/5/19 上午10:49
 * Description：我的工具
 */
public class MyToolsAdapter extends RvBaseAdapter<String,MyToolsAdapter.MyToolsViewHolder> {


    public MyToolsAdapter(Context mContext, List<String> mList) {
        super(mContext, mList);
    }

    @Override
    public MyToolsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyToolsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_my_tool,parent,false));
    }

    @Override
    public void onBindViewHolder(MyToolsViewHolder holder, final int position) {
        holder.tv_tool_name.setText("暗影战斧");
        holder.tv_tool_state.setText("已过期");
        holder.rl_tool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemOnClickListener != null) {
                    mItemOnClickListener.onItemClick(position);
                }
            }
        });
    }

    class MyToolsViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_tool_name;
        public TextView tv_tool_state;
        public RelativeLayout rl_tool;
        public MyToolsViewHolder(View itemView) {
            super(itemView);
            tv_tool_name = itemView.findViewById(R.id.tv_tool_name);
            tv_tool_state = itemView.findViewById(R.id.tv_tool_state);
            rl_tool = itemView.findViewById(R.id.rl_tool);
        }
    }
}
