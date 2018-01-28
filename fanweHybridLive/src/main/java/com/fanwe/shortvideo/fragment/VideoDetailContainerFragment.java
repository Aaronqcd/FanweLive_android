package com.fanwe.shortvideo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.hybrid.fragment.BaseFragment;
import com.fanwe.library.view.CircleImageView;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveUserHomeActivity;
import com.fanwe.live.utils.GlideUtil;
import com.fanwe.shortvideo.model.ShortVideoDetailModel;

import java.util.Locale;

/**
 * Created by wxy on 2018/1/20.
 */

public class VideoDetailContainerFragment extends BaseFragment implements View.OnClickListener {
    private CircleImageView iv_head_image;
    private TextView tv_user_name;
    private TextView tv_follow;
    private TextView tv_msg;
    private TextView tv_praise_num;
    private TextView tv_gift_num;
    private ImageView img_share;
    private ImageView img_close;
    private FrameLayout fl_msg;
    private FrameLayout fl_praise;
    private FrameLayout fl_gift;
    private FrameLayout fl_share;

    private ShortVideoDetailModel.VideoDetail detailModel;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_head_image:
                Intent intent = new Intent(getActivity(), LiveUserHomeActivity.class);
                intent.putExtra(LiveUserHomeActivity.EXTRA_USER_ID, detailModel.user_id);
                getActivity().startActivity(intent);
                getActivity().finish();
                break;
            default:
                break;
        }

    }


    public static VideoDetailContainerFragment newInstance() {
        return new VideoDetailContainerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_detail_container, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        iv_head_image = (CircleImageView) view.findViewById(R.id.iv_head_image);
        tv_user_name = (TextView) view.findViewById(R.id.tv_user_name);
        tv_follow = (TextView) view.findViewById(R.id.tv_follow);
        tv_msg = (TextView) view.findViewById(R.id.tv_msg);
        tv_praise_num = (TextView) view.findViewById(R.id.tv_praise_num);
        tv_gift_num = (TextView) view.findViewById(R.id.tv_gift_num);
        img_share = (ImageView) view.findViewById(R.id.img_share);
        img_close = (ImageView) view.findViewById(R.id.img_close);
        fl_msg = (FrameLayout) view.findViewById(R.id.fl_msg);
        fl_praise = (FrameLayout) view.findViewById(R.id.fl_praise);
        fl_gift = (FrameLayout) view.findViewById(R.id.fl_gift);
        fl_share = (FrameLayout) view.findViewById(R.id.fl_share);
        initListener();
    }

    private void initListener() {
        iv_head_image.setOnClickListener(this);
        img_share.setOnClickListener(this);
        img_close.setOnClickListener(this);

    }

    /**
     * 进入提示
     */
    protected void addMsgView() {
//        if (mRoomViewerJoinRoomView == null) {
//            mRoomViewerJoinRoomView = new RoomViewerJoinRoomView(this);
//            SDViewUtil.replaceView(R.id.fl_msg, child);
//        }
    }

    public void updataData(ShortVideoDetailModel.VideoDetail detail) {
        detailModel = detail;
        GlideUtil.load(detail.head_image).into(iv_head_image);
        tv_user_name.setText(detail.nick_name);
        tv_msg.setText(detail.count_comment);
        tv_praise_num.setText(detail.count_praise);
        //TODO 礼物个数字段
        tv_gift_num.setText(detail.count_praise);

    }


}
