package com.fanwe.libgame.wawa.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanwe.games.R;
import com.fanwe.libgame.dice.DiceManager;
import com.fanwe.libgame.model.GameBetCoinsOptionModel;
import com.fanwe.libgame.view.BaseGameView;
import com.fanwe.libgame.wawa.WawaManager;
import com.fanwe.libgame.wawa.adapter.AutoPollAdapter;
import com.fanwe.libgame.wawa.model.WawaItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxy on 2018/3/10.
 */

public class WawaGameView extends BaseGameView implements View.OnClickListener {
    private RelativeLayout rootView;
    private AutoPollRecyclerView mRecyclerView;
    private AutoPollRecyclerView mRecyclerView2;
    private LinearLayoutManager mLineManager;
    private AutoPollAdapter adapter1;
    private ImageView bottom_iv;
    private ImageView wawa_line;
    private ImageView wawa_stub;
    private ImageView start_grab_animation;
    private ImageView select_coin_bg;
    private ImageView img_chongzhi;
    private TextView txt_coin;
    private RadioGroup radioGroup;
    private List<WawaItemModel> list = new ArrayList<>();
    private WawaGameViewCallback mCallback;
    private WawaManager mManager;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int viewId = v.getId();
        if (viewId == R.id.img_chongzhi) {
            mCallback.onClickRecharge();
        } else if (viewId == R.id.start_grab_animation) {
            startClick();
        }
    }

    public WawaManager getManager() {
        if (mManager == null) {
            mManager = new WawaManager();
            mManager.setCallback(mDiceManagerCallback);
        }
        return mManager;
    }
    public void setTopView(ImageView line,ImageView stub){
        wawa_line=line;
        wawa_stub=stub;
    }

    private WawaManager.WawaManagerCallback mDiceManagerCallback = new WawaManager.WawaManagerCallback() {
        @Override
        public void onStart(long countTime) {

        }

        @Override
        public void onCountDownTick(long leftTime) {

        }

        @Override
        public void onCountDownFinish() {

        }

        @Override
        public void onShowCountDown(boolean show) {

        }

        @Override
        public void onMarkWinPosition(int winPosition) {

        }

        @Override
        public void onUpdateBetMultiple(List<String> listData) {

        }

        @Override
        public void onUpdateTotalBet(List<Integer> listData) {

        }

        @Override
        public void onUpdateUserBet(List<Integer> listData) {

        }

        @Override
        public void onUpdateBetCoinsOption(List<GameBetCoinsOptionModel> listData) {

        }

        @Override
        public void onCanBetChanged(boolean canBet) {

        }

        @Override
        public void onBetSuccess(int betPosition, long betCoins) {

        }

        @Override
        public void onUpdateUserCoins(long userCoins) {
            txt_coin.setText(userCoins + "");
        }

        @Override
        public void onGameStateChanged(int oldState, int newState) {

        }

        @Override
        public void onHasAutoStartMode(boolean hasAutoStartMode) {

        }

        @Override
        public void onAutoStartModeChanged(boolean isAutoStartMode) {

        }

        @Override
        public void onUserCoinsImageRes(int res) {

        }

        @Override
        public void onShowResult(List<Integer> listData) {

        }
    };

    public void setCallback(WawaGameViewCallback callback) {
        mCallback = callback;
    }

    public WawaGameView(@NonNull Context context) {
        this(context, null);
    }

    public WawaGameView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WawaGameView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_wawa_game, this);
        list.add(new WawaItemModel(R.drawable.prizeitem_01, 2));
        list.add(new WawaItemModel(R.drawable.prizeitem_02, 3));
        list.add(new WawaItemModel(R.drawable.prizeitem_01, 2));
        list.add(new WawaItemModel(R.drawable.prizeitem_03, 5));
        list.add(new WawaItemModel(R.drawable.prizeitem_04, 10));
        list.add(new WawaItemModel(R.drawable.prizeitem_05, 25));
        list.add(new WawaItemModel(R.drawable.prizeitem_01, 2));
        list.add(new WawaItemModel(R.drawable.prizeitem_02, 3));
        list.add(new WawaItemModel(R.drawable.prizeitem_01, 2));
        list.add(new WawaItemModel(R.drawable.prizeitem_03, 5));
        list.add(new WawaItemModel(R.drawable.prizeitem_04, 10));
        list.add(new WawaItemModel(R.drawable.prizeitem_06, 99));
        initView();
    }

    private void initView() {
        rootView = (RelativeLayout) findViewById(R.id.rootView);
        start_grab_animation = (ImageView) findViewById(R.id.start_grab_animation);
        select_coin_bg = (ImageView) findViewById(R.id.select_coin_bg);
//        bottom_iv = (ImageView) findViewById(R.id.bottom_iv);
        img_chongzhi = (ImageView) findViewById(R.id.img_chongzhi);
        txt_coin = (TextView) findViewById(R.id.txt_coin);
        setOnClickListener(img_chongzhi, this);
        setOnClickListener(start_grab_animation, this);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.btn_0) {
                    select_coin_bg.setBackgroundResource(R.drawable.prizeclaw_docker_2);

                } else if (checkedId == R.id.btn_1) {
                    select_coin_bg.setBackgroundResource(R.drawable.prizeclaw_docker_3);

                } else if (checkedId == R.id.btn_2) {
                    select_coin_bg.setBackgroundResource(R.drawable.prizeclaw_docker_4);

                } else if (checkedId == R.id.btn_3) {
                    select_coin_bg.setBackgroundResource(R.drawable.prizeclaw_docker_5);

                }
            }
        });

        mRecyclerView = (AutoPollRecyclerView) findViewById(R.id.rv_recycleView);
        mRecyclerView.setXY(2, 2);
        mRecyclerView2 = (AutoPollRecyclerView) findViewById(R.id.rv_recycleView2);
        mRecyclerView2.setXY(-2, -2);
        AutoPollAdapter adapter = new AutoPollAdapter(getContext(), list, true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(adapter);
        adapter1 = new AutoPollAdapter(getContext(), list, false);
        mLineManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        mRecyclerView2.setLayoutManager(mLineManager);
        mRecyclerView2.setAdapter(adapter1);
        if (true) //保证itemCount的总个数宽度超过屏幕宽度->自己处理
        {
            mRecyclerView.start();
            mRecyclerView2.start();
        }
    }

    private void startClick() {
        final int lineHeight=wawa_line.getHeight();
        // 1先下来
        ValueAnimator animator = ValueAnimator.ofFloat(lineHeight, rootView.getHeight() - mRecyclerView2.getHeight() - radioGroup.getHeight());
        animator.setTarget(wawa_stub);
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();
                wawa_stub.setTranslationY(currentValue);
                ViewGroup.LayoutParams params = wawa_line.getLayoutParams();
                params.height = (int) currentValue;
                wawa_line.setLayoutParams(params);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                int position = getCurrentViewIndex();
//                adapter1.updateIcon(position);
//                bottom_iv.setImageResource(advance_drawable[position % advance_drawable.length]);
//                swingAnimation(bottom_iv);
                ValueAnimator animator1=ValueAnimator.ofFloat(rootView.getHeight() - mRecyclerView2.getY(),lineHeight);
                animator1.setDuration(2000);
                animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float currentY= (float) animation.getAnimatedValue();
                        ViewGroup.LayoutParams params= wawa_line.getLayoutParams();
                        params.height= (int) currentY;
                        wawa_line.setLayoutParams(params);
                        wawa_stub.setTranslationY(currentY);

                    }
                });
                animator1.start();
//                down2UpRun2(wawa_stub);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();

//                // 2晃动===========================================
//                wawa_stub.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        //抓中了
//                        int position = getCurrentViewIndex();
//                        adapter1.updateIcon(position);
//                        bottom_iv.setImageResource(advance_drawable[position % advance_drawable.length]);
//                        swingAnimation(bottom_iv);
//                    }
//                }, 0);
//                // 3双飞 向上============================================
//                wawa_line.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
////                        down2UpRun(wawa_line);
//                    }
//                }, 3000);
//                wawa_stub.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
////                        down2UpRun2(wawa_stub);
//                    }
//                }, 3000);

    }


    public interface WawaGameViewCallback {
        /**
         * 投注倍数
         *
         * @param betCoin 投注金额
         */
        void onClickBetView(int betPosition, long betCoin);

        void onClickRecharge();

    }
}
