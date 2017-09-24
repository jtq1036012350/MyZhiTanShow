package com.iguitar.xiaoxiaozhitan.ui.fragment;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.iguitar.xiaoxiaozhitan.R;
import com.iguitar.xiaoxiaozhitan.api.ApiInerface;
import com.iguitar.xiaoxiaozhitan.databinding.FragmentPersonalNewBinding;
import com.iguitar.xiaoxiaozhitan.model.VersionInfo;
import com.iguitar.xiaoxiaozhitan.ui.adapter.PersonBottomAdapter;
import com.iguitar.xiaoxiaozhitan.ui.base.BaseFragment;
import com.iguitar.xiaoxiaozhitan.utils.CommonUtil;
import com.iguitar.xiaoxiaozhitan.utils.ConstantUtil;
import com.iguitar.xiaoxiaozhitan.utils.DownloadUtil;
import com.iguitar.xiaoxiaozhitan.utils.LogUtil;
import com.iguitar.xiaoxiaozhitan.utils.PrompUtil;
import com.joker.api.Permissions4M;
import com.joker.api.wrapper.ListenerWrapper;
import com.joker.api.wrapper.Wrapper;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;

import me.iwf.photopicker.utils.MyPhotoUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 个人中心Fragment
 * Created by Jiang on 2017/4/13.
 */

public class PersonalFragment extends BaseFragment {
    private FragmentPersonalNewBinding binding;
    private TextView tv_tittle;
    private ImageButton btn_back;
    private PersonBottomAdapter personBottomAdapter;
    private View header;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = View.inflate(getActivity(), R.layout.fragment_personal, null);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_personal_new, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lazyLoad();
    }

    /**
     * 初始化页面
     */
    private void initView() {

        tv_tittle = (TextView) mActivity.findViewById(R.id.tv_top_title);
        tv_tittle.setText("个人中心");

        btn_back = (ImageButton) mActivity.findViewById(R.id.btn_back);
        btn_back.setVisibility(View.GONE);

        binding.lvParallax.setOverScrollMode(AbsListView.OVER_SCROLL_NEVER);

        header = View.inflate(mActivity, R.layout.layout_header, null);
        final ImageView parallaxView = (ImageView) header.findViewById(R.id.parallaxImageView);
        binding.lvParallax.addHeaderView(header);

        header.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                binding.lvParallax.setParallaxView(parallaxView);
                header.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

        ArrayList<String> myPhotoList = (ArrayList<String>) MyPhotoUtil.getPhotoMap();
        personBottomAdapter = new PersonBottomAdapter(mActivity, myPhotoList.get(0),shareListener);
        binding.lvParallax.setAdapter(personBottomAdapter);
        personBottomAdapter.setOnUpdateClickListener(new PersonBottomAdapter.OnUpdateClickListener() {
            @Override
            public void OnUpdate() {
                onGetVersion();
            }
        });
//        binding.tvFirstQqUnit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (CommonUtil.joinQQGroup(mActivity, "eaC2K4rJSE5vb5txpYyIK3rePKByY0jn")) {
//                } else {
//                    copyToClipBoard("422068207");
//                    CommonUtil.showTopToast(mActivity, "粘贴到剪贴板成功！");
//                }
//            }
//        });
//
//        binding.tvSecondQqUnit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (CommonUtil.joinQQGroup(mActivity, "DTRHxYW05u5SrUah5AJPPKLEzPPEEpQz")) {
//                } else {
//                    copyToClipBoard("518544404");
//                    CommonUtil.showTopToast(mActivity, "粘贴到剪贴板成功！");
//                }
//            }
//        });
//
//        binding.tvThirdQqUnit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (CommonUtil.joinQQGroup(mActivity, "s5tLhM4zdLjiY-FbEdvBai2wlrcuU3D7")) {
//                } else {
//                    copyToClipBoard("607455254");
//                    CommonUtil.showTopToast(mActivity, "粘贴到剪贴板成功！");
//                }
//            }
//        });
//
//        binding.ivUser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                PhotoPicker.builder()
//                        .setPhotoCount(1)
//                        .setShowCamera(true)
//                        .setShowGif(true)
//                        .setPreviewEnabled(false)
//                        .start(mActivity, PhotoPicker.REQUEST_CODE);
//
//
//            }
//        });

    }

    /**
     * 复制内容到剪贴板
     */
    private void copyToClipBoard(String text) {
        // 从API11开始android推荐使用android.content.ClipboardManager
        // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
        ClipboardManager cm = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(text);
    }

    @Override
    public void onResume() {
        super.onResume();
//        ArrayList<String> myPhotoList = (ArrayList<String>) MyPhotoUtil.getPhotoMap();
//        if (myPhotoList != null) {
//            Glide.with(mActivity)
//                    .load(myPhotoList.get(0))
//                    .placeholder(R.mipmap.myicon)
//                    .dontAnimate()
//                    .into(binding.ivUser);
//        }
        ArrayList<String> myPhotoList = (ArrayList<String>) MyPhotoUtil.getPhotoMap();
        personBottomAdapter.notifyDataSetChanged();
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(mActivity, "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(mActivity, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(mActivity, "取消了", Toast.LENGTH_LONG).show();

        }
    };

    private void onDownLoad(VersionInfo versionInfo) {
        DownloadUtil loadUtil = new DownloadUtil(mActivity, versionInfo.getContent());
        String url;
        url = "http://" + CommonUtil.getIP(mActivity) + "/XiaoXiao/Update/XiaoXiaoZhiTan.apk";
        LogUtil.d("urlInfo", url);
        loadUtil.StartDownload(url);
    }

    /**
     * 查看版本信息
     */
    private void onGetVersion() {
        ApiInerface userBiz = retrofit.create(ApiInerface.class);
        Call<Object> call = userBiz.getVersionReturn();
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
                    LogUtil.e("infoooo", "normalGet:" + response.body() + "");
                    Gson gson = new Gson();
                    final VersionInfo versionInfo = gson.fromJson(response.body().toString(), VersionInfo.class);
//                    double i= Double.parseDouble(CommonUtil.getAPPVersion(LoginActivity.this));
                    //版本比对
                    if (CommonUtil.getCode(mActivity) < Integer.parseInt(versionInfo.getVersion())) {
                        Permissions4M.get(mActivity)
                                .requestPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .requestCodes(ConstantUtil.WRITE_STOERAGE_CODE)
                                .requestListener(new ListenerWrapper.PermissionRequestListener() {
                                    @Override
                                    public void permissionGranted(int code) {
                                        onDownLoad(versionInfo);
                                    }

                                    @Override
                                    public void permissionDenied(int code) {
                                        CommonUtil.showTopToast(mActivity, "权限获取失败");
                                    }

                                    @Override
                                    public void permissionRationale(int code) {
                                        CommonUtil.showTopToast(mActivity, "权限获取失败");
                                    }
                                })
                                .requestPageType(Permissions4M.PageType.MANAGER_PAGE)
                                .requestPage(new Wrapper.PermissionPageListener() {
                                    @Override
                                    public void pageIntent(final Intent intent) {
                                        new AlertDialog.Builder(mActivity)
                                                .setMessage("您好，我们需要您开启读写存储权限申请：\n请点击前往设置页面\n")
                                                .setPositiveButton("前往设置页面", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        startActivity(intent);
                                                    }
                                                })
                                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                })
                                                .show();
                                    }
                                })
                                .request();
                    }
                } else {
                    CommonUtil.showTopToast(mActivity,"当前版本已经是最新版本，无需更新！");
                    LogUtil.e("infoooo", "normalGet:" + response.body() + "");
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                PrompUtil.stopProgressDialog("");
                CommonUtil.showTopToast(mActivity,"登录失败！");
                LogUtil.e("infoooo", "normalGet:" + t.toString() + "");
            }
        });
    }


    @Override
    protected void lazyLoad() {
        initView();
    }
}
