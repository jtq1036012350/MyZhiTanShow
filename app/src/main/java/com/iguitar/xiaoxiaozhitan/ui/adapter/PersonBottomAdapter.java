package com.iguitar.xiaoxiaozhitan.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iguitar.xiaoxiaozhitan.MyApplication;
import com.iguitar.xiaoxiaozhitan.R;
import com.iguitar.xiaoxiaozhitan.ui.activity.LoginActivity;
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

    private  OnUpdateClickListener onUpdateClickListener;
    public interface OnUpdateClickListener{
        void OnUpdate();
    }

    public void setOnUpdateClickListener(OnUpdateClickListener onUpdateClickListener){
        this.onUpdateClickListener = onUpdateClickListener;
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
            viewHolder.ll_qq_first = (LinearLayout) view.findViewById(R.id.ll_qq_first);
            viewHolder.ll_qq_second = (LinearLayout) view.findViewById(R.id.ll_qq_second);
            viewHolder.ll_qq_third = (LinearLayout) view.findViewById(R.id.ll_qq_third);
            viewHolder.tv_user_name = (TextView) view.findViewById(R.id.tv_user_name);
            viewHolder.ll_share = (LinearLayout) view.findViewById(R.id.ll_share);
            viewHolder.ll_exit = (LinearLayout) view.findViewById(R.id.ll_exit);
            viewHolder.ll_update = (LinearLayout) view.findViewById(R.id.ll_update);
            viewHolder.tv_version = (TextView) view.findViewById(R.id.tv_version);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tv_version.setText("版本： "+CommonUtil.getAPPVersion(context));

        viewHolder.ll_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) context).finish();
                Intent intent = new Intent();
                intent.setClass((Activity) context, LoginActivity.class);
                ((Activity) context).startActivity(intent);
            }
        });
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

        viewHolder.ll_qq_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtil.joinQQGroup((Activity) context, "eaC2K4rJSE5vb5txpYyIK3rePKByY0jn")) {
                } else {
                    CommonUtil.CopyToClipBoard((Activity) context, "422068207");
                    CommonUtil.showTopToast((Activity) context, "QQ群号码粘贴到剪贴板成功！");
                }
            }
        });
        viewHolder.ll_qq_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtil.joinQQGroup((Activity) context, "DTRHxYW05u5SrUah5AJPPKLEzPPEEpQz")) {
                } else {
                    CommonUtil.CopyToClipBoard((Activity) context, "518544404");
                    CommonUtil.showTopToast((Activity) context, "QQ群号码粘贴到剪贴板成功！");
                }
            }
        });
        viewHolder.ll_qq_third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtil.joinQQGroup((Activity) context, "s5tLhM4zdLjiY-FbEdvBai2wlrcuU3D7")) {
                } else {
                    CommonUtil.CopyToClipBoard((Activity) context, "607455254");
                    CommonUtil.showTopToast((Activity) context, "QQ群号码粘贴到剪贴板成功！");
                }
            }
        });

        viewHolder.ll_share.setOnClickListener(new View.OnClickListener() {
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
        viewHolder.ll_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onUpdateClickListener!=null){
                    onUpdateClickListener.OnUpdate();
                }
            }
        });
        String url = (String) MyApplication.getMap("profile_image_url");
        ArrayList<String> myPhotoList = (ArrayList<String>) MyPhotoUtil.getPhotoMap();
        if (myPhotoList != null && myPhotoList.size() != 0 && myPhotoList.get(0) != null) {
            Glide.with((Activity) context)
                    .load(myPhotoList.get(0))
                    .placeholder(R.mipmap.myicon)
                    .dontAnimate()
                    .into(viewHolder.iv_user);
        } else if (!"".equals(url)){
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
        private ImageView iv_user;
        private LinearLayout ll_qq_first;
        private LinearLayout ll_qq_second;
        private LinearLayout ll_qq_third;
        private TextView tv_user_name;
        private LinearLayout ll_share;
        private LinearLayout ll_exit;
        private LinearLayout ll_update;
        private TextView tv_version;
    }
}
