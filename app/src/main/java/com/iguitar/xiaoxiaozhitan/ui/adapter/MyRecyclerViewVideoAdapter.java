package com.iguitar.xiaoxiaozhitan.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iguitar.xiaoxiaozhitan.R;
import com.iguitar.xiaoxiaozhitan.utils.CommonUtil;
import com.squareup.picasso.Picasso;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class MyRecyclerViewVideoAdapter extends RecyclerView.Adapter<MyRecyclerViewVideoAdapter.MyViewHolder> {

    //    int[] videoIndexs = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    private String url;
    private Context context;
//    public static final String TAG = "MyRecyclerViewVideoAdapter";

    public MyRecyclerViewVideoAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        http:
//192.168.1.107:8080/XiaoXiao/Server/videos/lvxing.f4v
        url = "http://"+ CommonUtil.getIP(context) + "/XiaoXiao/Server/videos/teshimaaoi.mp4";
//        url =  "http://video.jiecao.fm/8/17/bGQS3BQQWUYrlzP1K4Tg4Q__.mp4";
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_videoview, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        Log.i(TAG, "onBindViewHolder [" + holder.jcVideoPlayer.hashCode() + "] position=" + position);

        holder.jcVideoPlayer.setUp(
                url, JCVideoPlayer.SCREEN_LAYOUT_LIST,
                "意义");
        Picasso.with(holder.jcVideoPlayer.getContext())
                .load(R.mipmap.anbuzhenming)
                .into(holder.jcVideoPlayer.thumbImageView);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        JCVideoPlayerStandard jcVideoPlayer;

        public MyViewHolder(View itemView) {
            super(itemView);
            jcVideoPlayer = (JCVideoPlayerStandard) itemView.findViewById(R.id.videoplayer);
        }
    }

}
