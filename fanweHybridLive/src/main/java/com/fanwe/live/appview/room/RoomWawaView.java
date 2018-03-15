package com.fanwe.live.appview.room;

import android.content.Context;
import android.util.AttributeSet;

import com.fanwe.live.R;

/**
 * 直播间娃娃view
 */
public class RoomWawaView extends RoomView {

    public RoomWawaView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public RoomWawaView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public RoomWawaView(Context context)
    {
        super(context);
    }

    @Override
    protected int onCreateContentView()
    {
        return R.layout.view_room_wawa;
    }
}
