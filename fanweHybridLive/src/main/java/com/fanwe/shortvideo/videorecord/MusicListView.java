package com.fanwe.shortvideo.videorecord;

import android.content.Context;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.fanwe.library.utils.SDNotification;
import com.fanwe.library.utils.SDPackageUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.R;

import org.xutils.common.Callback;
import org.xutils.common.util.FileUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Link on 2016/9/12.
 */
public class MusicListView extends ListView {

    private MusicListAdapter adapter;
    public MusicListAdapter getAdapter(){
        return adapter;
    }
    public MusicListView(Context context){
        super(context);
        init(context);
    }
    public MusicListView(Context context, AttributeSet attrs){
        super(context,attrs);
        init(context);
    }
    private void init(Context context){
        this.setChoiceMode(CHOICE_MODE_SINGLE);

    }
    public void setupList(LayoutInflater inflater){
//        SimpleAdapter adapter = new SimpleAdapter(mContext,getData(),R.layout.audio_ctrl_music_item,
//                new String[]{"name","duration"},
//                new int[]{R.id.xml_music_item_name,R.id.xml_music_item_duration});
        adapter = new MusicListAdapter(inflater, new ArrayList<TCAudioControl.MediaEntity>());
        setAdapter(adapter);
    }

    static public class ViewHolder{
        ImageView selected;
        TextView name;
        TextView duration;
        TextView tv_download;
    }

}


class MusicListAdapter extends BaseAdapter{
    private Context mContext;
    List<TCAudioControl.MediaEntity> mData = null;
    private LayoutInflater mInflater;

    MusicListAdapter(LayoutInflater inflater, List<TCAudioControl.MediaEntity> list){
        mInflater = inflater;
        mData = list;
    }

    public void setData(List<TCAudioControl.MediaEntity> data){
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MusicListView.ViewHolder holder;
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.audio_ctrl_music_item,null);
            holder = new MusicListView.ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.xml_music_item_name);
            holder.duration = (TextView) convertView.findViewById(R.id.xml_music_item_duration);
            holder.tv_download = (TextView) convertView.findViewById(R.id.tv_download);
//            holder.selected = (ImageView) convertView.findViewById(R.id.music_item_selected);
            convertView.setTag(holder);
        }
        else{
            holder = (MusicListView.ViewHolder)convertView.getTag();
        }
        holder.name.setText(mData.get(position).title);
        holder.duration.setText(mData.get(position).singer);
//        holder.selected.setVisibility(mData.get(position).state == 1 ? View.VISIBLE : View.GONE);
        holder.tv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDownload(position);
            }
        });
        return convertView;
    }

    /**
     * 是否正在下载中
     */
    private static boolean isDownloading;
    /**
     * 开始下载
     */
    private void startDownload(int position)
    {
        isDownloading = true;

        File dir = null;
        String dirName = x.app().getPackageName();
        if (FileUtil.existsSdcard())
        {
            dir = new File(Environment.getExternalStorageDirectory(), dirName);
        } else
        {
            dir = new File(Environment.getDataDirectory(), dirName);
        }
        String path = dir.getAbsolutePath() + File.separator + "songcaoliang" + ".mp3";

        // 1.获取下载地址http://music.baidu.com/data/music/links?songIds=****(121353608 --》待完成
        //2. 下载 地址文件

        RequestParams params = new RequestParams("http://zhangmenshiting.qianqian.com/data2/music/b008dff3c9df62831582ddea13f64921/569080852/569080829104400128.mp3?xcode=114c502913a46c066b73a0f684021956");
        params.setSaveFilePath(path);
        params.setAutoRename(false);
        params.setAutoResume(false);

        x.http().get(params, new Callback.ProgressCallback<File>()
        {
//"http://qukufile2.qianqian.com/data2/lrc/ef80d282b94f37e92bee6e5b9b417124/569080826/569080826.lrc",
            @Override
            public void onError(Throwable arg0, boolean arg1)
            {
                isDownloading = false;
                SDToast.showToast("下载失败");
            }

            @Override
            public void onCancelled(CancelledException e)
            {
                isDownloading = false;
            }

            @Override
            public void onFinished()
            {
                isDownloading = false;
            }

            @Override
            public void onSuccess(File file)
            {
                isDownloading = false;
                SDToast.showToast("下载完成");
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading)
            {
                int progress = (int) ((current * 100) / (total));
            }

            @Override
            public void onWaiting()
            {

            }

            @Override
            public void onStarted()
            {
            }
        });
    }


}
