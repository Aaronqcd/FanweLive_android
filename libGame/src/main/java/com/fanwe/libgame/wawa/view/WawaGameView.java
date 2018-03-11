package com.fanwe.libgame.wawa.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.fanwe.games.R;
import com.fanwe.libgame.wawa.adapter.AutoPollAdapter;

/**
 * Created by wxy on 2018/3/10.
 */

public class WawaGameView extends FrameLayout {
    public static int[] advance_drawable = new int[]{
            R.drawable.prizeitem_01, R.drawable.prizeitem_02,
            R.drawable.prizeitem_03, R.drawable.prizeitem_04,
            R.drawable.prizeitem_05, R.drawable.prizeitem_06,
            R.drawable.prizeitem_07, R.drawable.prizeitem_08,
            R.drawable.prizeitem_09, R.drawable.prizeitem_010
    };
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
    private RadioGroup radioGroup;

    public WawaGameView(@NonNull Context context) {
        this(context, null);
    }

    public WawaGameView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WawaGameView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_wawa_game, this);
        initView();
        initAnimation();
    }

    private void initView() {
        rootView = (RelativeLayout) findViewById(R.id.rootView);
        start_grab_animation = (ImageView) findViewById(R.id.start_grab_animation);
        wawa_line = (ImageView) findViewById(R.id.wawa_line);
        select_coin_bg = (ImageView) findViewById(R.id.select_coin_bg);
        bottom_iv = (ImageView) findViewById(R.id.bottom_iv);
        wawa_stub = (ImageView) findViewById(R.id.wawa_stub);
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
                }
            }
        });

        mRecyclerView = (AutoPollRecyclerView) findViewById(R.id.rv_recycleView);
        mRecyclerView.setXY(2, 2);
        mRecyclerView2 = (AutoPollRecyclerView) findViewById(R.id.rv_recycleView2);
        mRecyclerView2.setXY(-2, -2);
        AutoPollAdapter adapter = new AutoPollAdapter(getContext(), advance_drawable, true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(adapter);
        adapter1 = new AutoPollAdapter(getContext(), advance_drawable, false);
        mLineManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        mRecyclerView2.setLayoutManager(mLineManager);
        mRecyclerView2.setAdapter(adapter1);
        if (true) //保证itemCount的总个数宽度超过屏幕宽度->自己处理
        {
            mRecyclerView.start();
            mRecyclerView2.start();
        }
    }
    private void initAnimation() {

        start_grab_animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // 1先下来
                ValueAnimator animator2 = ValueAnimator.ofFloat( 0f,  rootView.getHeight()- mRecyclerView2.getHeight()-radioGroup.getHeight());
                animator2.setTarget(wawa_stub);
                animator2.setDuration(2000);
                animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float currentValue = (float) animation.getAnimatedValue();
                        wawa_stub.setTranslationY(currentValue);
                        ViewGroup.LayoutParams params=wawa_line.getLayoutParams();
                        params.height=(int)currentValue;
                        wawa_line.setLayoutParams(params);
                    }
                });
                animator2.start();

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
        });

    }
}
