package com.iguitar.xiaoxiaozhitan.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iguitar.xiaoxiaozhitan.R;
import com.iguitar.xiaoxiaozhitan.model.PlayListMainJavaBean;

import java.util.ArrayList;

/**
 * 播放列表第一级Adapter
 * Created by Jiang on 2017/4/16.
 */

public class PlayListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<PlayListMainJavaBean> playListJavaBeanList;

    public PlayListAdapter(Context mContext, ArrayList<PlayListMainJavaBean> playListJavaBeanList) {
        this.mContext = mContext;
        this.playListJavaBeanList = playListJavaBeanList;
    }

    @Override
    public int getCount() {
        return playListJavaBeanList == null?0:playListJavaBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return playListJavaBeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        PlayListMainJavaBean playJavaBean = playListJavaBeanList.get(i);
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_video_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_video = (ImageView) convertView.findViewById(R.id.iv_video);
            viewHolder.tv_video_des = (TextView) convertView.findViewById(R.id.tv_video_des);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        viewHolder.iv_video.setImageResource(playJavaBean.getVideoCover());
        Glide.with(mContext)
                .load(playJavaBean.getVideoCover())
                .dontAnimate()
                .into(viewHolder.iv_video );
        viewHolder.tv_video_des.setText(playJavaBean.getCoverName());
        return convertView;
    }

    private static class ViewHolder {
        ImageView iv_video;
        TextView tv_video_des;
    }

}
