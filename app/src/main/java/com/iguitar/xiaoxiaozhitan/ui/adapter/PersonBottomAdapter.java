package com.iguitar.xiaoxiaozhitan.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iguitar.xiaoxiaozhitan.MyApplication;
import com.iguitar.xiaoxiaozhitan.R;
import com.iguitar.xiaoxiaozhitan.utils.CommonUtil;

import java.util.ArrayList;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.utils.MyPhotoUtil;


/**
 * 个人中心底部的Adapter
 * Created by Jiang on 2017/5/8.
 */
public class PersonBottomAdapter extends BaseAdapter {

    private Context context;

    public PersonBottomAdapter(Context context, String url) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = View.inflate(context, R.layout.item_bottom_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_user = (ImageView) view.findViewById(R.id.iv_user);
            viewHolder.tv_first_qq_unit = (TextView) view.findViewById(R.id.tv_first_qq_unit);
            viewHolder.tv_second_qq_unit = (TextView) view.findViewById(R.id.tv_second_qq_unit);
            viewHolder.tv_third_qq_unit = (TextView) view.findViewById(R.id.tv_third_qq_unit);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.iv_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setShowGif(true)
                        .setPreviewEnabled(false)
                        .start((Activity) context, PhotoPicker.REQUEST_CODE);
            }
        });

        viewHolder.tv_first_qq_unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtil.joinQQGroup((Activity) context, "eaC2K4rJSE5vb5txpYyIK3rePKByY0jn")) {
                } else {
                    CommonUtil.CopyToClipBoard((Activity) context, "422068207");
                    CommonUtil.showTopToast((Activity) context, "QQ群号码粘贴到剪贴板成功！");
                }
            }
        });
        viewHolder.tv_second_qq_unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtil.joinQQGroup((Activity) context, "DTRHxYW05u5SrUah5AJPPKLEzPPEEpQz")) {
                } else {
                    CommonUtil.CopyToClipBoard((Activity) context, "518544404");
                    CommonUtil.showTopToast((Activity) context, "QQ群号码粘贴到剪贴板成功！");
                }
            }
        });
        viewHolder.tv_third_qq_unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtil.joinQQGroup((Activity) context, "s5tLhM4zdLjiY-FbEdvBai2wlrcuU3D7")) {
                } else {
                    CommonUtil.CopyToClipBoard((Activity) context, "607455254");
                    CommonUtil.showTopToast((Activity) context, "QQ群号码粘贴到剪贴板成功！");
                }
            }
        });

        String url = (String) MyApplication.getIconMap();
        ArrayList<String> myPhotoList = (ArrayList<String>) MyPhotoUtil.getPhotoMap();
        if (myPhotoList != null && myPhotoList.size() != 0 && myPhotoList.get(0) != null) {
            Glide.with((Activity) context)
                    .load(myPhotoList.get(0))
                    .placeholder(R.mipmap.myicon)
                    .dontAnimate()
                    .into(viewHolder.iv_user);
        } else if (!"".equals(url)) {
            Glide.with((Activity) context)
                    .load(url)
                    .placeholder(R.mipmap.myicon)
                    .dontAnimate()
                    .into(viewHolder.iv_user);
        } else {
            Glide.with((Activity) context)
                    .load(R.mipmap.myicon)
                    .dontAnimate()
                    .into(viewHolder.iv_user);
        }

        return view;
    }

    static class ViewHolder {
        ImageView iv_user;
        TextView tv_first_qq_unit;
        TextView tv_second_qq_unit;
        TextView tv_third_qq_unit;
    }
}
