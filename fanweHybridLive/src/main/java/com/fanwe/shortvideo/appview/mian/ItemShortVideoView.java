package com.fanwe.shortvideo.appview.mian;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.CircleImageView;
import com.fanwe.live.R;
import com.fanwe.live.appview.BaseAppView;
import com.fanwe.live.utils.GlideUtil;
import com.fanwe.shortvideo.model.ShortVideoModel;

/**
 * 小视频列表itemView
 *
 * @author wxy
 */
public class ItemShortVideoView extends BaseAppView {

    private ImageView iv_bg_image;
    private CircleImageView iv_head_image;
    private TextView tv_content;
    private TextView tv_praise_count;
    private ShortVideoModel model;

    public ItemShortVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ItemShortVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ItemShortVideoView(Context context) {
        super(context);
        init();
    }

    protected void init() {
        setContentView(R.layout.item_live_short_video_view);

        iv_bg_image = find(R.id.iv_bg_image);
        tv_content = find(R.id.tv_content);
        iv_head_image = find(R.id.iv_head_img);
        tv_praise_count = find(R.id.tv_praise_count);
    }

    public ShortVideoModel getModel() {
        return model;
    }

    public void setModel(ShortVideoModel model) {
        this.model = model;
        if (model != null) {
            SDViewUtil.setVisible(this);
            GlideUtil.load(model.getSv_img()).into(iv_bg_image);
            GlideUtil.load(model.getHead_image()).into(iv_head_image);
            Drawable drawable;
            if (model.getIs_praise().equals("1")) {
                drawable =getResources().getDrawable(R.drawable.praise_red_heart);
            }else{
                drawable =getResources().getDrawable(R.drawable.praise_hollow_heart);
            }
            drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
            tv_praise_count.setCompoundDrawables(drawable,null,null,null);
            SDViewBinder.setTextView(tv_content, model.getSv_content());
            SDViewBinder.setTextView(tv_praise_count, model.getCount_praise());

        }
    }

}
