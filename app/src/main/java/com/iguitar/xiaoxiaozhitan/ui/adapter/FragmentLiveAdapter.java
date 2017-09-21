package com.iguitar.xiaoxiaozhitan.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iguitar.xiaoxiaozhitan.R;
import com.iguitar.xiaoxiaozhitan.model.LiveJavaBean;

import java.util.ArrayList;

/**
 * Created by Jiang on 2017/4/16.
 */

public class FragmentLiveAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<LiveJavaBean> liveJavaBeanArrayList;

    public FragmentLiveAdapter(Context mContext, ArrayList<LiveJavaBean> liveJavaBeanArrayList) {
        this.mContext = mContext;
        this.liveJavaBeanArrayList = liveJavaBeanArrayList;
    }

    @Override
    public int getCount() {
        return liveJavaBeanArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return liveJavaBeanArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        LiveJavaBean liveJavaBean = liveJavaBeanArrayList.get(i);
        if(convertView == null){
            convertView = View.inflate(mContext, R.layout.item_fragment_live_layout,null);
            viewHolder = new ViewHolder();
            viewHolder.tv_item_title = (TextView) convertView.findViewById(R.id.tv_item_title);
            viewHolder.tv_description = (TextView) convertView.findViewById(R.id.tv_url);
            viewHolder.iv_live_icon = (ImageView) convertView.findViewById(R.id.iv_live_icon);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_item_title.setText(liveJavaBean.getTitle());
        viewHolder.tv_description.setText(liveJavaBean.getDescription());
        if(i==0){
            viewHolder.iv_live_icon.setImageResource(R.mipmap.kuaishou);
        }else{
            viewHolder.iv_live_icon.setImageResource(R.mipmap.xiongmao);
        }

        return convertView;
    }

    private static class ViewHolder{
        TextView tv_item_title;
        TextView tv_description;
        ImageView iv_live_icon;
    }

}
