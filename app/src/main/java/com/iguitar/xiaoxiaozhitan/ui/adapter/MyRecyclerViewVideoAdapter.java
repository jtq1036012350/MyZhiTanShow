package com.iguitar.xiaoxiaozhitan.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iguitar.xiaoxiaozhitan.R;
import com.iguitar.xiaoxiaozhitan.model.VideoUrls;
import com.squareup.picasso.Picasso;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class MyRecyclerViewVideoAdapter extends RecyclerView.Adapter<MyRecyclerViewVideoAdapter.MyViewHolder> {

    //    int[] videoIndexs = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
//    private String url;
    private Context context;
//    public static final String TAG = "MyRecyclerViewVideoAdapter";

    private List<VideoUrls> videoUrlsList;

    public MyRecyclerViewVideoAdapter(Context context,List<VideoUrls> videoUrlsList) {
        this.context = context;
        this.videoUrlsList = videoUrlsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_videoview, parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        Log.i(TAG, "onBindViewHolder [" + holder.jcVideoPlayer.hashCode() + "] position=" + position);

        holder.jcVideoPlayer.setUp(
                videoUrlsList.get(position).getUrl(), JCVideoPlayer.SCREEN_LAYOUT_LIST,
                videoUrlsList.get(position).getDescription());
        Picasso.with(holder.jcVideoPlayer.getContext())
                .load(videoUrlsList.get(position).getImageUrl())
                .placeholder(R.mipmap.myicon_mini)
                .fit()
                .into(holder.jcVideoPlayer.thumbImageView);
        holder.tv_description.setText(videoUrlsList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return videoUrlsList==null?0:videoUrlsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private JCVideoPlayerStandard jcVideoPlayer;
        private TextView tv_description;
        public MyViewHolder(View itemView) {
            super(itemView);
            jcVideoPlayer = (JCVideoPlayerStandard) itemView.findViewById(R.id.videoplayer);
            tv_description = (TextView) itemView.findViewById(R.id.tv_description);
        }
    }

}
