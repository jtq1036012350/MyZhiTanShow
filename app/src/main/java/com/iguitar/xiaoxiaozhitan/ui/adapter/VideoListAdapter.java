package com.iguitar.xiaoxiaozhitan.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.iguitar.xiaoxiaozhitan.R;
import com.iguitar.xiaoxiaozhitan.model.VideoListJavaBean;

import java.util.ArrayList;

/**
 * 播放列表Adapter
 * Created by Jiang on 2017/4/16.
 */

public class VideoListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<VideoListJavaBean> videoListJavaBeanList;

    public VideoListAdapter(Context mContext, ArrayList<VideoListJavaBean> videoListJavaBeanList) {
        this.mContext = mContext;
        this.videoListJavaBeanList = videoListJavaBeanList;
    }

    @Override
    public int getCount() {
        return videoListJavaBeanList == null?0:videoListJavaBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return videoListJavaBeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        VideoListJavaBean videoJavaBean = videoListJavaBeanList.get(i);
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_video_list_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_video_cover = (ImageView) convertView.findViewById(R.id.iv_video_cover);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        viewHolder.iv_video_cover.setImageResource(videoJavaBean.getVideoCover());
        Glide.with(mContext)
                .load(videoJavaBean.getVideoCover())
                .dontAnimate()
                .into(viewHolder.iv_video_cover);
        return convertView;
    }

    private static class ViewHolder {
        ImageView iv_video_cover;
    }

}
