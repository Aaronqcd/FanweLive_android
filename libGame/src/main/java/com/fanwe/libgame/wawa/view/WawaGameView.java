package com.fanwe.libgame.wawa.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanwe.games.R;
import com.fanwe.libgame.model.GameBetCoinsOptionModel;
import com.fanwe.libgame.view.BaseGameView;
import com.fanwe.libgame.wawa.WawaManager;
import com.fanwe.libgame.wawa.adapter.AutoPollAdapter;
import com.fanwe.libgame.wawa.model.WawaItemModel;

import java.math.BigDecimal;
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
    private Context mContext;

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
        this.mContext=context;
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

        // 1先下来
        final int lineLocation[] = new int[2];
        wawa_line.getLocationInWindow(lineLocation);
        final int recycle2Location[] = new int[2];
        mRecyclerView2.getLocationInWindow(recycle2Location);
        ValueAnimator animator = ValueAnimator.ofFloat(lineLocation[1], recycle2Location[1] - mRecyclerView2.getHeight() / 2);
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentY = (float) animation.getAnimatedValue();
                ViewGroup.LayoutParams params = wawa_line.getLayoutParams();
                params.height = (int) currentY;
                wawa_line.setLayoutParams(params);
                wawa_stub.setTranslationY(currentY - wawa_stub.getHeight() / 2);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                int position = getCurrentViewIndex();
                int firstItemPosition = mLineManager.findFirstVisibleItemPosition();
                if (position - firstItemPosition >= 0) {
                    View view = mRecyclerView2.getChildAt((position - firstItemPosition) % list.size());
                    AutoPollAdapter.BaseViewHolder viewHolder = (AutoPollAdapter.BaseViewHolder) mRecyclerView2.getChildViewHolder(view);
                    viewHolder.sort_icon.setVisibility(View.GONE);
//                            adapter1.notifyDataSetChanged();
                }
                bottom_view.setVisibility(View.VISIBLE);
                bottom_iv.setImageResource(list.get(position % list.size()).wawaDrawable);
                swingAnimation(bottom_view, recycle2Location[1] - mRecyclerView2.getHeight() / 2, lineLocation[1]);
                ValueAnimator animator1 = ValueAnimator.ofFloat(recycle2Location[1] - mRecyclerView2.getHeight() / 2, lineLocation[1]);
                animator1.setDuration(2000);
                animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float currentY = (float) animation.getAnimatedValue();
                        ViewGroup.LayoutParams params = wawa_line.getLayoutParams();
                        params.height = (int) currentY;
                        wawa_line.setLayoutParams(params);
                        wawa_stub.setTranslationY(currentY - wawa_stub.getHeight() / 2);
//                                if(is) {
//                                    final int bottomLocation[] = new int[2];
//                                    bottom_view.getLocationInWindow(bottomLocation);
//                                    TranslateAnimation trananimation = new TranslateAnimation(0, 0, currentY,1000f);
//                                    trananimation.setDuration(1500);
//                                    AlphaAnimation alphaAnimation=new AlphaAnimation(1f,0f);
//                                    alphaAnimation.setDuration(1500);
//                                    AnimationSet set=new AnimationSet(true);
//                                    set.addAnimation(trananimation);
//                                    set.addAnimation(alphaAnimation);
//                                    bottom_view.startAnimation(set);
//                                }

                    }
                });
                animator1.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        adapter1.notifyDataSetChanged();
                        final int stubLocation[] = new int[2];
                        wawa_stub.getLocationInWindow(stubLocation);
                        Point startPosition=new Point(stubLocation[0],stubLocation[1]);
                        int coinLocation[]=new int[2];
                        img_coin.getLocationInWindow(coinLocation);
                        Point endPosition=new Point(coinLocation[0],coinLocation[1]);

                        CoinImageView coinImageView = new CoinImageView(mContext);
                        coinImageView.setStartPosition(startPosition);
                        coinImageView.setEndPosition(endPosition);
//                        WindowManager wm = (WindowManager) mContext.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
//                        ViewGroup rootView = (ViewGroup) wm.getWindow().getDecorView();
                        rootView.addView(coinImageView);
                        coinImageView.startBeizerAnimation();


                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animator1.start();
//                        down2UpRun2(wawa_stub);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
        // 2晃动===========================================
//                wawa_stub.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        //抓中了
//
//                    }
//                }, 0);
        // 3双飞 向上============================================

//                wawa_line.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        down2UpRun(wawa_line);
//                    }
//                }, 3000);
        wawa_stub.postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        }, 3000);







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
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();

    }
    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();

        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }


    public void swingAnimation(View v, int startY, int endY) {
        startShakeByPropertyAnim(v, 0.8f, 1.2f, 10f, 1000, startY, endY);
    }

    public int getCurrentViewIndex() {
        int firstVisibleItem = mLineManager.findFirstVisibleItemPosition();
        int lastVisibleItem = mLineManager.findLastVisibleItemPosition();

        double midleCount = (firstVisibleItem + lastVisibleItem) / 2;

        BigDecimal b = new BigDecimal(new Double(midleCount));
        int currentIndex = b.setScale(3, BigDecimal.ROUND_HALF_UP).intValue();

        return currentIndex;
    }
    ///sdgaa
//    public int getCurrentViewIndex() {
//        int firstVisibleItem = mLineManager.findFirstVisibleItemPosition();
//        int lastVisibleItem = mLineManager.findLastVisibleItemPosition();
//        int currentIndex = firstVisibleItem;
//        int lastHeight = 0;
//        for (int i = firstVisibleItem; i <= lastVisibleItem; i++) {
//            View view = mLineManager.getChildAt(i - firstVisibleItem);
//            if (null == view) {
//                continue;
//            }
//
//            int[] location = new int[2];
//            view.getLocationOnScreen(location);
//
//            Rect localRect = new Rect();
//            view.getLocalVisibleRect(localRect);
//
//            int showHeight = localRect.bottom - localRect.top;
//            if (showHeight > lastHeight) {
//                currentIndex = i;
//                lastHeight = showHeight;
//            }
//        }
//
//        if (currentIndex < 0) {
//            currentIndex = 0;
//        }
//
//        return currentIndex;
//    }

    private void startShakeByPropertyAnim(final View view, float scaleSmall, float scaleLarge, float shakeDegrees, long duration, int startY, int endY) {
        if (view == null) {
            return;
        }

        //先变小后变大
        PropertyValuesHolder scaleXValuesHolder = PropertyValuesHolder.ofKeyframe(View.SCALE_X,
                Keyframe.ofFloat(0f, 1.0f),
                Keyframe.ofFloat(0.25f, scaleSmall),
                Keyframe.ofFloat(0.5f, scaleLarge),
                Keyframe.ofFloat(0.75f, scaleLarge),
                Keyframe.ofFloat(1.0f, 1.0f)
        );
        PropertyValuesHolder scaleYValuesHolder = PropertyValuesHolder.ofKeyframe(View.SCALE_Y,
                Keyframe.ofFloat(0f, 1.0f),
                Keyframe.ofFloat(0.25f, scaleSmall),
                Keyframe.ofFloat(0.5f, scaleLarge),
                Keyframe.ofFloat(0.75f, scaleLarge),
                Keyframe.ofFloat(1.0f, 1.0f)
        );

        //先往左再往右
        PropertyValuesHolder rotateValuesHolder = PropertyValuesHolder.ofKeyframe(View.ROTATION,
                Keyframe.ofFloat(0f, 0f),
                Keyframe.ofFloat(0.1f, -shakeDegrees),
                Keyframe.ofFloat(0.2f, shakeDegrees),
                Keyframe.ofFloat(0.3f, -shakeDegrees),
                Keyframe.ofFloat(0.4f, shakeDegrees),
                Keyframe.ofFloat(0.5f, -shakeDegrees),
                Keyframe.ofFloat(0.6f, shakeDegrees),
                Keyframe.ofFloat(0.7f, -shakeDegrees),
                Keyframe.ofFloat(0.8f, shakeDegrees),
                Keyframe.ofFloat(0.9f, -shakeDegrees),
                Keyframe.ofFloat(1.0f, 0f));

        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(view, "translationY", 0, endY - startY);

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, scaleXValuesHolder, scaleYValuesHolder, rotateValuesHolder);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(2000);
        animatorSet.play(objectAnimator).with(objectAnimator1);
        animatorSet.start();
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
