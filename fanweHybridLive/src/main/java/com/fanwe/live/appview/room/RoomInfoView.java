package com.fanwe.live.appview.room;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.UiThread;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TimeUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.animator.SDAnimSet;
import com.fanwe.library.animator.listener.OnEndGone;
import com.fanwe.library.animator.listener.OnEndReset;
import com.fanwe.library.blocker.SDOnClickBlocker;
import com.fanwe.library.blocker.SDRunnableBlocker;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDRecyclerView;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveContActivity;
import com.fanwe.live.adapter.LiveViewerListRecyclerAdapter;
import com.fanwe.live.appview.RoomSdkInfoView;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.dialog.LiveUserInfoDialog;
import com.fanwe.live.event.ERequestFollowSuccess;
import com.fanwe.live.model.App_followActModel;
import com.fanwe.live.model.App_get_videoActModel;
import com.fanwe.live.model.RoomUserModel;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.utils.GlideUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * 直播间顶部view
 */
public class RoomInfoView extends RoomView
{

    private Activity mContext;
    public RoomInfoView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        this.mContext= (Activity) context;
        init();
    }

    public RoomInfoView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.mContext= (Activity) context;
        init();
    }

    public RoomInfoView(Context context)
    {
        super(context);
        this.mContext= (Activity) context;
        init();
    }

    private View ll_click_creater;
    private ImageView iv_head_image;
    private ImageView iv_level;
    protected TextView tv_video_title;
    private TextView tv_viewer_number;
    private View view_operate_viewer;
    private View view_add_viewer;
    private View view_minus_viewer;
    private SDRecyclerView hlv_viewer;
    private LinearLayout ll_ticket;
    private TextView tv_ticket;
    private TextView tv_user_number;
    private LinearLayout ll_follow;
    private TextView tv_creater_leave;
    private RoomSdkInfoView view_sdk_info;
    //百媚==================================
    private LinearLayout   ll_user_number;
    private LinearLayout ll_room_coomand;
    private TextView tv_room_command;
    //百媚==================================
    private LiveViewerListRecyclerAdapter adapter;
    private int hasFollow;
    private SDRunnableBlocker mRunnableBlocker;
    private ClickListener clickListener;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private int mCountNumber = 0;

   private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });

    public void setClickListener(ClickListener clickListener)
    {
        this.clickListener = clickListener;
    }

    @Override
    protected int onCreateContentView()
    {
        return R.layout.view_room_info;
    }

    protected void init()
    {
        ll_click_creater = find(R.id.ll_click_creater);
        iv_head_image = find(R.id.iv_head_image);
        iv_level = find(R.id.iv_level);
        tv_video_title = find(R.id.tv_video_title);
        tv_viewer_number = find(R.id.tv_viewer_number);
        view_operate_viewer = find(R.id.view_operate_viewer);
        view_add_viewer = find(R.id.view_add_viewer);
        view_minus_viewer = find(R.id.view_minus_viewer);
        hlv_viewer = find(R.id.hlv_viewer);
        ll_ticket = find(R.id.ll_ticket);
        tv_ticket = find(R.id.tv_ticket);
        tv_user_number = find(R.id.tv_user_number);
        ll_follow = find(R.id.ll_follow);
        tv_creater_leave = find(R.id.tv_creater_leave);
        view_sdk_info = find(R.id.view_sdk_info);
        //百媚===========================================
        ll_user_number=find(R.id.ll_user_number);
        ll_room_coomand = find(R.id.ll_room_coomand);
        tv_room_command = find(R.id.tv_room_command);
        //百媚===========================================
        view_add_viewer.setOnClickListener(this);
        view_minus_viewer.setOnClickListener(this);
        SDOnClickBlocker.setOnClickListener(ll_follow, 1000, this);
        ll_ticket.setOnClickListener(this);
        ll_click_creater.setOnClickListener(this);

        mRunnableBlocker = new SDRunnableBlocker();
        mRunnableBlocker.setBlockDuration(1000).setMaxBlockCount(30);

        hlv_viewer.setLinearHorizontal();
        adapter = new LiveViewerListRecyclerAdapter(getActivity());
        hlv_viewer.setAdapter(adapter);

    }
    private AlertDialog.Builder alertDialog;

    private void startTimer(int seconds){
        if(alertDialog == null){
            alertDialog = new AlertDialog.Builder(getContext());
            alertDialog.setMessage("要关注主播吗？");
            alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            });
            alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    clickFollow();
                    dialog.dismiss();
                }
            });
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mContext != null && !mContext.isFinishing()){
                    if(alertDialog != null && ll_follow.getVisibility() == View.VISIBLE){
                        alertDialog.show();
                    }
                }

            }
        },15*1000);
    }
    public void setTextVideoTitle(String text)
    {
        tv_video_title.setText(text);
    }

    public RoomSdkInfoView getSdkInfoView()
    {
        return view_sdk_info;
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == view_add_viewer)
        {
            if (clickListener != null)
            {
                clickListener.onClickAddViewer(v);
            }
        } else if (v == view_minus_viewer)
        {
            if (clickListener != null)
            {
                clickListener.onClickMinusViewer(v);
            }
        } else if (v == ll_follow)
        {
            clickFollow();
        } else if (v == ll_ticket)
        {
            clickTicket();
        } else if (v == ll_click_creater)
        {
            String id = getLiveActivity().getCreaterId();
            clickHeadImage(id);
        }
    }

    /**
     * 显示隐藏私密直播的邀请观众view
     *
     * @param show
     */
    public void showOperateViewerView(boolean show)
    {
        if (show)
        {
            SDViewUtil.setVisible(view_operate_viewer);
        } else
        {
            SDViewUtil.setGone(view_operate_viewer);
        }
    }

    /**
     * 绑定数据
     *
     * @param actModel
     */
    public void bindData(App_get_videoActModel actModel)
    {
        if (actModel == null)
        {
            return;
        }

        if (!TextUtils.isEmpty(actModel.getVideo_title())) {
            setTextVideoTitle(actModel.getVideo_title());
        } else {
            if (getLiveActivity().isPlayback()) {
                setTextVideoTitle("精彩回放");
            } else {
                setTextVideoTitle("直播Live");
            }
        }

        RoomUserModel createrModel = actModel.getPodcast();
        if (createrModel != null)
        {
            UserModel creater = createrModel.getUser();
            if (!creater.equals(UserModelDao.query()))
            {
                bindHasFollow(createrModel.getHas_focus(), false);
            } else
            {
                SDViewUtil.setGone(ll_follow);
            }
        }

        if (actModel.getIs_bm() == 1)
        {
            setTextViewCoomand(actModel.getPrivate_key());
            showLlRoomCoomand(true);
            showLlUserNumber(false);
        } else
        {
            showLlUserNumber(true);
            showLlRoomCoomand(false);
        }

        long currentTime = System.currentTimeMillis();
        final Long time=Long.parseLong(actModel.begin_time2);
        String  text = getTimeExpend(time*1000,currentTime);
        SDViewBinder.setTextView(tv_viewer_number, text);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                long currentTime1 = System.currentTimeMillis();
                final String  text1= getTimeExpend(time*1000,currentTime1);
                tv_viewer_number.post(new Runnable() {
                    @Override
                    public void run() {
                        SDViewBinder.setTextView(tv_viewer_number, text1);
                    }
                });

            }
        };
        Timer timer = new Timer();
        timer.schedule(task,0, 1000);

    }

    private String getTimeExpend(long startTime, long endTime){
        //传入字串类型 2016/06/28 08:30
        long longStart = startTime; //获取开始时间毫秒数
        long longEnd = endTime;  //获取结束时间毫秒数
        long longExpend = longEnd - longStart;  //获取时间差

        long longHours = longExpend / (60 * 60 * 1000); //根据时间差来计算小时数
        long longMinutes = (longExpend - longHours * (60 * 60 * 1000)) / (60 * 1000);   //根据时间差来计算分钟数
        long longSconds = (longExpend - longHours * (60 * 60 * 1000)- longMinutes * (60 * 1000)) / (1000);   //根据时间差来计算分钟数

        String hours =longHours +"";
        if(longHours <10){
            hours = "0"+ hours;
        }
        String minutes =longMinutes +"";
        if(longMinutes <10){
            minutes = "0"+ longMinutes;
        }
        String scound =longSconds +"";
        if(longSconds <10){
            scound = "0"+ longSconds;
        }

        return hours + ":" + minutes+":"+scound;
    }

    private long getTimeMillis(String strTime) {
        long returnMillis = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date d = null;
        try {
            d = sdf.parse(strTime);
            returnMillis = d.getTime();
        } catch (ParseException e) {
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
        return returnMillis;
    }

    protected void clickFollow()
    {
        CommonInterface.requestFollow(getLiveActivity().getCreaterId(), getLiveActivity().getRoomId(), new AppRequestCallback<App_followActModel>()
        {

            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.getStatus() == 1)
                {
                    bindHasFollow(actModel.getHas_focus(), true);
                }
            }
        });
    }

    protected void clickTicket()
    {
        Intent intent = new Intent(getActivity(), LiveContActivity.class);
        intent.putExtra(RoomContributionView.EXTRA_ROOM_ID, getLiveActivity().getRoomId());
        intent.putExtra(RoomContributionView.EXTRA_USER_ID, getLiveActivity().getCreaterId());
        getActivity().startActivity(intent);
    }

    /**
     * 绑定主播数据
     *
     * @param user
     */
    public void bindCreaterData(UserModel user)
    {
        GlideUtil.loadHeadImage(user.getHead_image()).into(iv_head_image);
        if (TextUtils.isEmpty(user.getV_icon()))
        {
            SDViewUtil.setGone(iv_level);
        } else
        {
            SDViewUtil.setVisible(iv_level);
            GlideUtil.load(user.getV_icon()).into(iv_level);
        }
        SDViewBinder.setTextView(tv_user_number, String.valueOf(user.getShowId()));
        updateTicket(user.getTicket());
    }

    /**
     * 更新秀豆数量
     *
     * @param ticket
     */
    public void updateTicket(long ticket)
    {
        if (ticket < 0)
        {
            ticket = 0;
        }
        SDViewBinder.setTextView(tv_ticket, String.valueOf(ticket));
    }

    /**
     * 更新观众列表
     *
     * @param listModel
     */
    public void onLiveRefreshViewerList(List<UserModel> listModel)
    {
        adapter.updateData(listModel);
    }

    /**
     * 移除观众
     *
     * @param model
     */
    public void onLiveRemoveViewer(UserModel model)
    {
        adapter.removeData(model);
    }

    /**
     * 插入观众
     *
     * @param position
     * @param model
     */
    public void onLiveInsertViewer(int position, UserModel model)
    {
        adapter.insertData(position, model);
        if (position == 0 && hlv_viewer.isIdle())
        {
            mRunnableBlocker.post(scrollToStartRunnable);
        }
    }

    private Runnable scrollToStartRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            hlv_viewer.scrollToStart();
        }
    };

    /**
     * 更新观众数量
     *
     * @param viewerNumber
     */
    public void updateViewerNumber(int viewerNumber)
    {
//        if (viewerNumber < 0)
//        {
//            viewerNumber = 0;
//        }
//        SDViewBinder.setTextView(tv_viewer_number, String.valueOf(viewerNumber));

    }

    protected void clickHeadImage(String to_userid)
    {
        // 显示用户信息窗口
        LiveUserInfoDialog dialog = new LiveUserInfoDialog(getActivity(), to_userid);
        dialog.show();
    }

    /**
     * 显示隐藏主播离开
     *
     * @param show
     */
    public void showCreaterLeave(boolean show)
    {
        if (show)
        {
            SDViewUtil.setVisible(tv_creater_leave);
        } else
        {
            SDViewUtil.setGone(tv_creater_leave);
        }
    }

    public void onEventMainThread(ERequestFollowSuccess event)
    {
        if (event.userId.equals(getLiveActivity().getCreaterId()))
        {
            bindHasFollow(event.actModel.getHas_focus(), true);
        }
    }

    public int getHasFollow()
    {
        return hasFollow;
    }

    private void bindHasFollow(int hasFollow, boolean anim)
    {
        this.hasFollow = hasFollow;
        if (hasFollow == 1)
        {
            if (anim)
            {
                SDAnimSet.from(ll_follow)
                        .scaleX(1, 0).setDuration(200)
                        .withClone().scaleY(1, 0)
                        .addListener(new OnEndGone())
                        .addListener(new OnEndReset())
                        .start();
            } else
            {
                SDViewUtil.setGone(ll_follow);
            }
        } else
        {
            if (anim)
            {
                SDAnimSet.from(ll_follow)
                        .scaleX(0, 1).setDuration(200)
                        .withClone().scaleY(0, 1)
                        .addListener(new OnEndReset())
                        .start();
            } else
            {
                SDViewUtil.setVisible(ll_follow);
            }
        }
        if(ll_follow.getVisibility() == VISIBLE){
            startTimer(15);
        }
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        mRunnableBlocker.onDestroy();
    }

    public interface ClickListener
    {
        void onClickAddViewer(View v);

        void onClickMinusViewer(View v);
    }

    //百媚======================================
    public void showLlUserNumber(boolean show)
    {
        if (show)
        {
            SDViewUtil.setVisible(ll_user_number);
        } else
        {
            SDViewUtil.setGone(ll_user_number);
        }
    }

    public void showLlRoomCoomand(boolean show)
    {
        if (show)
        {
            SDViewUtil.setVisible(ll_room_coomand);
        } else
        {
            SDViewUtil.setGone(ll_room_coomand);
        }
    }

    public void setTextViewCoomand(String coomand)
    {
        SDViewBinder.setTextView(tv_room_command, coomand);
    }
    //百媚======================================
}
