package com.fanwe.shortvideo.adapter;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.fanwe.library.adapter.SDSimpleRecyclerAdapter;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.live.R;
import com.fanwe.shortvideo.appview.mian.VideoCommentItemView;
import com.fanwe.shortvideo.model.VideoCommentListModel;

import java.util.ArrayList;

/**
 * @author wxy
 */
public class ShortVideoCommentAdapter extends SDSimpleRecyclerAdapter<VideoCommentListModel.CommentItemModel>
{

    private Activity activity;

    public ShortVideoCommentAdapter(ArrayList<VideoCommentListModel.CommentItemModel> listModel, Activity activity)
    {
        super(listModel, activity);
        this.activity = activity;
    }

    @Override
    public void onBindData(SDRecyclerViewHolder<VideoCommentListModel.CommentItemModel> holder, final int position, VideoCommentListModel.CommentItemModel model) {
        VideoCommentItemView item0 = holder.get(R.id.item_comment);
        item0.setModel(model);
        item0.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public int getLayoutId(ViewGroup parent, int viewType)
    {
        return R.layout.item_comment_list;
    }


}
