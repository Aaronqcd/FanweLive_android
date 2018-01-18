package com.fanwe.shortvideo.adapter;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.fanwe.library.adapter.SDSimpleRecyclerAdapter;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.R;
import com.fanwe.shortvideo.appview.mian.ItemShortVideoView;
import com.fanwe.shortvideo.model.ShortVideoModel;

import java.util.ArrayList;

/**
 * @author wxy
 */
public class LiveTabShortVideoAdapter extends SDSimpleRecyclerAdapter<ShortVideoModel>
{

    public LiveTabShortVideoAdapter(ArrayList<ShortVideoModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public void onBindData(SDRecyclerViewHolder<ShortVideoModel> holder, int position, ShortVideoModel model) {
        ItemShortVideoView item0 = holder.get(R.id.item_short_video);
        item0.setModel(model);
        item0.setOnClickListener(clickImageListener);
    }


    @Override
    public int getLayoutId(ViewGroup parent, int viewType)
    {
        return R.layout.item_live_tab_shortvideo;
    }

    private OnClickListener clickImageListener = new OnClickListener()
    {

        @Override
        public void onClick(View v)
        {
            ItemShortVideoView view = (ItemShortVideoView) v;
            ShortVideoModel model = view.getModel();
            if (model == null)
            {
                SDToast.showToast("数据为空");
                return;
            }
        }
    };

}
