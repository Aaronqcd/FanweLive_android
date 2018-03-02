package com.fanwe.shortvideo.appview.mian;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.view.SDRecyclerView;
import com.fanwe.live.R;
import com.fanwe.live.appview.BaseAppView;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.view.SDProgressPullToRefreshRecyclerView;
import com.fanwe.shortvideo.adapter.ShortVideoCommentAdapter;
import com.fanwe.shortvideo.model.VideoCommentListModel;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import org.xutils.view.annotation.ViewInject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Administrator on 2016/12/9.
 */

public class VideoCommentView extends BaseAppView {
    @ViewInject(R.id.tv_comment_num)
    private TextView tv_comment_num;
    @ViewInject(R.id.edit_comment)
    private TextView edit_comment;
    @ViewInject(R.id.img_close)
    private ImageView img_close;
    @ViewInject(R.id.tv_data)
    private TextView tv_data;
    @ViewInject(R.id.lv_comment)
    private SDProgressPullToRefreshRecyclerView lv_comment;
    private int page = 0;
    private boolean has_next = true;
    private String sv_id;
    private ShortVideoCommentAdapter adapter;
    private List<VideoCommentListModel.CommentItemModel> listModel = new ArrayList<>();


    public VideoCommentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public VideoCommentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VideoCommentView(Context context) {
        super(context);
        init();
    }

    protected void init() {
        setContentView(R.layout.view_short_video_comment);
        lv_comment.getRefreshableView().setGridVertical(1);
        setAdapter();
        initPullToRefresh();
        register();
    }
    public void setTextData(String commentNum,String data,String playNum){
        tv_comment_num.setText(commentNum);
        Date date=new Date(new Long(data)*1000);
        tv_data.setText(playNum+"播放"+new SimpleDateFormat("yyyy.MM.dd").format(date));

    }


    private void register() {
        img_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onClickBack();
                }
            }
        });
        edit_comment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               clickSendMsg(null);
            }
        });

    }

    public void clickSendMsg(VideoCommentListModel.CommentItemModel model){
        if (clickListener != null) {
            clickListener.onClickSendMsg(model);
        }
    }

    private void setAdapter() {
        adapter = new ShortVideoCommentAdapter(new ArrayList<VideoCommentListModel.CommentItemModel>(), getActivity());
        adapter.setCommentView(this);
        lv_comment.getRefreshableView().setAdapter(adapter);
    }

    private void initPullToRefresh() {
        lv_comment.setMode(PullToRefreshBase.Mode.BOTH);
        lv_comment.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<SDRecyclerView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<SDRecyclerView> refreshView) {
                page=0;
                requestData(sv_id);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<SDRecyclerView> refreshView) {
                if (has_next) {
                    page++;
                    requestData(sv_id);
                } else {
                    SDToast.showToast("没有更多数据了");
                    lv_comment.onRefreshComplete();
                }
            }
        });
    }

    public void requestData(String sv_id) {
        this.sv_id=sv_id;
        CommonInterface.requestCommentList(page, sv_id, new AppRequestCallback<VideoCommentListModel>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if (actModel.isOk()) {
                    listModel = actModel.getList();
                    if (page == 0) {
                        adapter.updateData(listModel);
                    } else {
                        adapter.appendData(listModel);
                    }
                    has_next = listModel.size() < 20 ? false : true;
                }
            }

            @Override
            protected void onFinish(SDResponse resp) {
                lv_comment.onRefreshComplete();
                super.onFinish(resp);
            }
        });
    }

    private VideoCommentView.ClickListener clickListener;

    public interface ClickListener {
        void onClickBack();
        void onClickSendMsg(VideoCommentListModel.CommentItemModel model);
    }

    public void setClickListener(VideoCommentView.ClickListener clickListener) {
        this.clickListener = clickListener;
    }

}
