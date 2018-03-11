package com.fanwe.libgame.wawa.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.games.R;


/**
 * Created by caoliang.song on 2018/3/1.
 */

public class AutoPollAdapter extends RecyclerView.Adapter<AutoPollAdapter.BaseViewHolder> {
    private final Context mContext;
    private int[] advance_drawable;
    private boolean isTop;
    private BaseViewHolder baseViewHolder;
    private int isInVisiblePos;
    public AutoPollAdapter(Context context, int[] advance_drawable, boolean isTop) {
        this.mContext = context;
        this.advance_drawable = advance_drawable;
        this.isTop=isTop;
    }
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;
        if(isTop){
            view= LayoutInflater.from(mContext).inflate(R.layout.item_auto_poll_top, parent, false);
        } else {
            view= LayoutInflater.from(mContext).inflate(R.layout.item_auto_poll, parent, false);
        }
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        baseViewHolder=holder;
        int resDrawable = advance_drawable[position%advance_drawable.length];
        holder.sort_icon.setImageResource(resDrawable);
        if(isInVisiblePos==position){
            holder.sort_icon.setVisibility(View.INVISIBLE);
        }else {
            holder.sort_icon.setVisibility(View.VISIBLE);
        }
    }
    public void updateIcon(int position){
        this.isInVisiblePos=position;
        notifyDataSetChanged();
//        baseViewHolder.sort_icon.setVisibility();
    }
    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public static class BaseViewHolder extends RecyclerView.ViewHolder {
        TextView sort_name;
        ImageView sort_icon;
        ImageView sort_icon_bottom;

        public BaseViewHolder(View convertView) {
            super(convertView);
            sort_name = (TextView) convertView.findViewById(R.id.sort_name);
            sort_icon = (ImageView) convertView.findViewById(R.id.sort_icon);
            sort_icon_bottom = (ImageView) convertView.findViewById(R.id.sort_icon);

        }
    }
}
