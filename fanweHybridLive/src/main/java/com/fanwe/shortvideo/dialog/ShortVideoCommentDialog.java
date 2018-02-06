package com.fanwe.shortvideo.dialog;

import android.app.Activity;

import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.dialog.LiveBaseDialog;
import com.fanwe.live.event.ELivePrivateChatDialogDissmis;
import com.fanwe.shortvideo.appview.mian.VideoCommentView;
import com.fanwe.shortvideo.model.ShortVideoModel;

/**
 * Created by wxy on 2018/2/4.
 */
public class ShortVideoCommentDialog extends LiveBaseDialog {
    private ShortVideoModel detailModel;
    public ShortVideoCommentDialog(Activity activity, ShortVideoModel detailModel) {
        super(activity);
        this.detailModel=detailModel;
        init();
    }

    private void init() {
        VideoCommentView videoCommentView = new VideoCommentView(getOwnerActivity());
        videoCommentView.setTextData(detailModel.getCount_comment(),detailModel.getSv_time(),detailModel.getClick());
        videoCommentView.setClickListener(new VideoCommentView.ClickListener() {
            @Override
            public void onClickBack() {
                dismiss();
            }
        });
        setContentView(videoCommentView);
        videoCommentView.requestData(detailModel.getSv_id());
        SDViewUtil.setHeight(videoCommentView, SDViewUtil.getScreenHeight() / 2);

        setCanceledOnTouchOutside(true);
        paddingLeft(0);
        paddingRight(0);
        paddingBottom(0);
    }


    /*私人聊天窗口点击外面关闭的时候关闭当前私聊*/
    public void onEventMainThread(ELivePrivateChatDialogDissmis event) {
        if (event.dialog_close_type == 0) {
            dismiss();
        }
    }
}
