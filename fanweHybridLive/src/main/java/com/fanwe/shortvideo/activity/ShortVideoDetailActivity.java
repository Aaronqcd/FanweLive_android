package com.fanwe.shortvideo.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fanwe.hybrid.activity.BaseActivity;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.live.R;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.shortvideo.fragment.RoomFragment;
import com.fanwe.shortvideo.model.ShortVideoDetailModel;
import com.fanwe.shortvideo.model.ShortVideoModel;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;


public class ShortVideoDetailActivity extends BaseActivity {
    private static String TAG = "ShortVideoDetailActivity";

    @ViewInject(R.id.vertical_viewpager)
    private VerticalViewPager mViewPager;
    private TXCloudVideoView mVideoView;
    private TXVodPlayer mVodPlayer;
    private static final int MESSAGE_ID_RECONNECTING = 0x01;
    private static final String DEFAULT_TEST_URL = "http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8";
    private static final String DEFAULT_TEST_URL1 = "http://1253918958.vod2.myqcloud.com/397c1fb2vodgzp1253918958/d8e0466b4564972819123307762/HFMIc1iVVHwA.mp4";

    private Toast mToast = null;
    private String mVideoPath = null;
    protected Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
        }
    };
    private RelativeLayout mRoomContainer;
    private PagerAdapter mPagerAdapter;
    private int mCurrentItem;
    private RoomFragment mRoomFragment = RoomFragment.newInstance();
    private boolean mInit = false;
    private FrameLayout mFragmentContainer;
    private FragmentManager mFragmentManager;
    private List<ShortVideoDetailModel.VideoDetail> listModel = new ArrayList<>();
    private ShortVideoModel model ;
    private List<String> mVideoUrls=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_short_video_detail);
        mRoomContainer = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.view_room_container, null);
        mFragmentContainer = (FrameLayout) mRoomContainer.findViewById(R.id.fragment_container);
        mVideoPath = DEFAULT_TEST_URL;
        mFragmentManager = getSupportFragmentManager();
        mVideoView = (TXCloudVideoView) mRoomContainer.findViewById(R.id.texture_view);

//        //创建player对象
        mVodPlayer = new TXVodPlayer(getActivity());
        //关键player对象与界面view
        mVodPlayer.setPlayerView(mVideoView);
        mPagerAdapter = new PagerAdapter();
        getIntentData();
//        requestData();
        generateUrls();
        mViewPager.setAdapter(mPagerAdapter);



        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e(TAG, "mCurrentId == " + position + ", positionOffset == " + positionOffset +
                        ", positionOffsetPixels == " + positionOffsetPixels);
                mCurrentItem = position;
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                loadVideo(position);
            }
        });

        mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                ViewGroup viewGroup = (ViewGroup) page;

                Log.e(TAG, "page.id == " + page.getId() + ", position == " + position);

                if ((position < 0 && viewGroup.getId() != mCurrentItem)) {
                    View roomContainer = viewGroup.findViewById(R.id.room_container);
                    if (roomContainer != null && roomContainer.getParent() != null && roomContainer.getParent() instanceof ViewGroup) {
                        ((ViewGroup) (roomContainer.getParent())).removeView(roomContainer);
                    }
                }
                // 满足此种条件，表明需要加载直播视频，以及聊天室了
                if (viewGroup.getId() == mCurrentItem && position == 0 ) {
                    if (mRoomContainer.getParent() != null && mRoomContainer.getParent() instanceof ViewGroup) {
                        ((ViewGroup) (mRoomContainer.getParent())).removeView(mRoomContainer);
                    }
                    loadVideoAndChatRoom(viewGroup, mCurrentItem);
                }
            }
        });

    }

    private void generateUrls() {
        for (int i = 0; i < 10; i++) {
            if(i % 2 == 0){
                mVideoUrls.add(DEFAULT_TEST_URL);
            }else{

                mVideoUrls.add(DEFAULT_TEST_URL1);
            }
        }
    }
    protected void requestData() {
        if(model == null ) return;
        CommonInterface.requestShortVideoDetails(model.getSv_id(), new AppRequestCallback<ShortVideoDetailModel>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if (actModel.isOk()) {
                    listModel = actModel.video;
//                        sortData(listModel);
                    mPagerAdapter = new PagerAdapter();
                    mViewPager.setAdapter(mPagerAdapter);

                    mCurrentItem = listModel.indexOf(model);
                    mViewPager.setCurrentItem(mCurrentItem);
                }
            }

            @Override
            protected void onFinish(SDResponse resp) {
                super.onFinish(resp);
            }
        });

//        requestCount++;
    }

    /**
     * @param viewGroup
     * @param currentItem
     */
    @SuppressLint("ResourceType")
    private void loadVideoAndChatRoom(ViewGroup viewGroup, int currentItem) {
        //聊天室的fragment只加载一次，以后复用
        if (!mInit) {
            mFragmentManager.beginTransaction().add(mFragmentContainer.getId(), mRoomFragment).commitAllowingStateLoss();
//            mFragmentManager.beginTransaction().setCustomAnimations(R.animator.slide_in_top,R.animator.slide_out_down);
            mInit = true;
        }
        loadVideo(currentItem);
        viewGroup.addView(mRoomContainer);
    }

    private void loadVideo(int position) {

        mVodPlayer.stopPlay(true); // true代表清除最后一帧画面
        //创建player对象
        mVodPlayer = new TXVodPlayer(getActivity());
        //关键player对象与界面view
        mVodPlayer.setPlayerView(mVideoView);
        mVodPlayer.startPlay(mVideoUrls.get(position));
    }

    @Override
    protected void onPause() {
        super.onPause();
        mToast = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVodPlayer.resume();
        mVideoView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVodPlayer.stopPlay(true); // true代表清除最后一帧画面
        mVideoView.onDestroy();
    }

    private void showToastTips(final String tips) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast != null) {
                    mToast.cancel();
                }
                mToast = Toast.makeText(ShortVideoDetailActivity.this, tips, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }

    public void getIntentData() {
        model= (ShortVideoModel) getIntent().getSerializableExtra("ShortVideoModel");

    }


    class PagerAdapter extends android.support.v4.view.PagerAdapter {

        @Override
        public int getCount() {
            return mVideoUrls.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.view_room_item, null);
//            ImageView anchor_img = (ImageView) view.findViewById(R.id.anchor_img);
//            anchor_img.setImageResource(resId[position]);
            view.setId(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(container.findViewById(position));
        }
    }
}
