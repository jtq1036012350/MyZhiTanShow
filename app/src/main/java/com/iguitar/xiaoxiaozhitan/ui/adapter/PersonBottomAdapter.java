package com.iguitar.xiaoxiaozhitan.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iguitar.xiaoxiaozhitan.MyApplication;
import com.iguitar.xiaoxiaozhitan.R;
import com.iguitar.xiaoxiaozhitan.utils.CommonUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.ArrayList;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.utils.MyPhotoUtil;


/**
 * 个人中心底部的Adapter
 * Created by Jiang on 2017/5/8.
 */
public class PersonBottomAdapter extends BaseAdapter {
    private UMShareListener shareListener;
    private Context context;

    public PersonBottomAdapter(Context context, String url, UMShareListener shareListener) {
        this.context = context;
        this.shareListener = shareListener;
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
    public View getView(int position, final View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = View.inflate(context, R.layout.item_bottom_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_user = (ImageView) view.findViewById(R.id.iv_user);
            viewHolder.tv_first_qq_unit = (TextView) view.findViewById(R.id.tv_first_qq_unit);
            viewHolder.tv_second_qq_unit = (TextView) view.findViewById(R.id.tv_second_qq_unit);
            viewHolder.tv_third_qq_unit = (TextView) view.findViewById(R.id.tv_third_qq_unit);
            viewHolder.tv_user_name = (TextView) view.findViewById(R.id.tv_user_name);
            viewHolder.tv_share = (TextView) view.findViewById(R.id.tv_share);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        String userName = (String) MyApplication.getMap("name");
        if (!TextUtils.isEmpty(userName)) {
            viewHolder.tv_user_name.setText(userName);
        } else {

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

        viewHolder.tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UMImage thumb = new UMImage((Activity) context, R.mipmap.myicon);
                UMWeb web = new UMWeb("http://" + CommonUtil.getIP(context) + "/XiaoXiao/Update/XiaoXiaoZhiTan.apk");
                web.setThumb(thumb);
                web.setDescription("欢迎来使用小小指弹");
                web.setTitle("小小指弹");
                new ShareAction((Activity) context).withMedia(web).setPlatform(SHARE_MEDIA.QQ).setCallback(shareListener).share();
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
        } else if (!"".

                equals(url))

        {
            Glide.with((Activity) context)
                    .load(url)
                    .placeholder(R.mipmap.myicon)
                    .dontAnimate()
                    .into(viewHolder.iv_user);
        } else

        {
            Glide.with((Activity) context)
                    .load(R.mipmap.myicon)
                    .dontAnimate()
                    .into(viewHolder.iv_user);
        }

        return view;
    }


    static class ViewHolder {
        private ImageView iv_user;
        private TextView tv_first_qq_unit;
        private TextView tv_second_qq_unit;
        private TextView tv_third_qq_unit;
        private TextView tv_user_name;
        private TextView tv_share;
    }
}
