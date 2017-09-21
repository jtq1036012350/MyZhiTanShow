package com.iguitar.xiaoxiaozhitan.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.daimajia.slider.library.SliderLayout;
import com.iguitar.xiaoxiaozhitan.R;
import com.iguitar.xiaoxiaozhitan.model.MainListJavaBean;
import com.iguitar.xiaoxiaozhitan.utils.CommonUtil;

import java.util.List;

/**
 * 首页RecyclerView 的Adapter
 * Created by Jiang on 2017-09-21.
 */

public class MyVideoRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MainListJavaBean> mainListJavaBeanList;
    private Context context;
    private LayoutInflater inflater;
    private OnSetTopViewData onSetTopViewData;

    //设置数据的回调接口
    public interface OnSetTopViewData {
        void OnSetTopViewData(SliderLayout sliderLayout, LinearLayout ll_guid_point);
    }

    public void settopViewData(OnSetTopViewData onSetTopViewData) {
        this.onSetTopViewData = onSetTopViewData;
    }


    //顶部栏目：
    private final int TYPE1 = 0;

    public MyVideoRecyclerViewAdapter(Context context, List<MainListJavaBean> mainListJavaBeanList) {
        this.context = context;
        this.mainListJavaBeanList = mainListJavaBeanList;
        //别忘了初始化inflater
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TYPE1:
                holder = new ViewHolderTop(View.inflate(context, R.layout.fragment_item_top_layout, null));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == -1) {
            return;
        }
        switch (viewType) {
            case TYPE1:
                ((ViewHolderTop) holder).setData();
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE1;
        }
        return -1;
    }

    public class ViewHolderTop extends RecyclerView.ViewHolder {
        private SliderLayout sliderLayout;
        private LinearLayout ll_guid_point;

        public ViewHolderTop(View itemView) {
            super(itemView);
            sliderLayout = (SliderLayout) itemView.findViewById(R.id.slider_view);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(CommonUtil.getDisplayWidth((Activity) context), 200);
            sliderLayout.setLayoutParams(layoutParams);
            ll_guid_point = (LinearLayout) itemView.findViewById(R.id.ll_guid_point);
        }

        public void setData() {
            if (onSetTopViewData != null) {
                onSetTopViewData.OnSetTopViewData(sliderLayout, ll_guid_point);
            }
        }
    }

}
