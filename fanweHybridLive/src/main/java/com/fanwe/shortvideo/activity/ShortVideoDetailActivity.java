package com.fanwe.shortvideo.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fanwe.hybrid.activity.BaseActivity;
import com.fanwe.live.R;
import com.fanwe.live.utils.GlideUtil;
import com.fanwe.shortvideo.fragment.VideoDetailContainerFragment;
import com.fanwe.shortvideo.model.ShortVideoModel;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import org.xutils.view.annotation.ViewInject;

import java.util.List;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;


public class ShortVideoDetailActivity extends BaseActivity {
    private static String TAG = "ShortVideoDetailActivity";

    @ViewInject(R.id.vertical_viewpager)
    private VerticalViewPager mViewPager;
    private int mCurrentItem;
    private TXVodPlayer mVodPlayer;
    private TXCloudVideoView mVideoView;
    private RelativeLayout mRoomContainer;
    private PagerAdapter mPagerAdapter;
    private boolean mInit = false;
    private FrameLayout mFragmentContainer;
    private FragmentManager mFragmentManager;
    private List<ShortVideoModel> mVideoList;
    private VideoDetailContainerFragment mRoomFragment=VideoDetailContainerFragment.newInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_short_video_detail);
        getIntentData();
        initView();
        initListener();
        mViewPager.setCurrentItem(mCurrentItem);
        loadVideo(mVideoList.get(mCurrentItem).getSv_url());
        mRoomFragment.updateData(mVideoList.get(mCurrentItem));

    }

    private void initView() {
        mRoomContainer = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.view_room_container, null);
        mVideoView = (TXCloudVideoView) mRoomContainer.findViewById(R.id.texture_view);
        mFragmentContainer = (FrameLayout) mRoomContainer.findViewById(R.id.fragment_container);
        mFragmentManager = getSupportFragmentManager();

        //创建player对象
        mVodPlayer = new TXVodPlayer(getActivity());
        //关键player对象与界面view
        mVodPlayer.setPlayerView(mVideoView);
        mPagerAdapter = new PagerAdapter();
        mViewPager.setAdapter(mPagerAdapter);
    }

    private void initListener() {
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
                loadVideo(mVideoList.get(position).getSv_url());
                mRoomFragment.updateData(mVideoList.get(position));

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
                    loadVideoAndChatRoom(viewGroup);
                }
            }
        });
    }


    /**
     * @param viewGroup
     */
    @SuppressLint("ResourceType")
    private void loadVideoAndChatRoom(ViewGroup viewGroup) {
        //聊天室的fragment只加载一次，以后复用
        if (!mInit) {
            mFragmentManager.beginTransaction().add(mFragmentContainer.getId(), mRoomFragment).commitAllowingStateLoss();
            mInit = true;
        }
        viewGroup.addView(mRoomContainer);
    }

    private void loadVideo(String videoUrl) {

        mVodPlayer.stopPlay(true); // true代表清除最后一帧画面
        //创建player对象
        mVodPlayer = new TXVodPlayer(getActivity());
        //关键player对象与界面view
        mVodPlayer.setPlayerView(mVideoView);
        mVodPlayer.startPlay(videoUrl);
    }

    @Override
    protected void onPause() {
        super.onPause();
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

    public void getIntentData() {
        mCurrentItem=getIntent().getIntExtra("position",0);
        mVideoList = (List<ShortVideoModel>) getIntent().getSerializableExtra("video_list");

    }


    class PagerAdapter extends android.support.v4.view.PagerAdapter {

        @Override
        public int getCount() {
            return mVideoList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.view_room_item, null);
            ImageView anchor_img = (ImageView) view.findViewById(R.id.anchor_img);
            GlideUtil.load(mVideoList.get(position).getSv_img()).into(anchor_img);
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
