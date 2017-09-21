package com.iguitar.xiaoxiaozhitan.ui.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import com.andview.refreshview.XRefreshView;
import com.iguitar.xiaoxiaozhitan.R;
import com.iguitar.xiaoxiaozhitan.databinding.FragmentStoreBinding;
import com.iguitar.xiaoxiaozhitan.ui.base.BaseFragment;
import com.iguitar.xiaoxiaozhitan.ui.view.MyWebViewClient;
import com.iguitar.xiaoxiaozhitan.utils.AlertUtil;
import com.iguitar.xiaoxiaozhitan.utils.CommonUtil;
import com.iguitar.xiaoxiaozhitan.utils.ConstantUtil;

/**
 * 商城Fragment
 * Created by Jiang on 2017/4/13.
 */

public class StoreFragment extends BaseFragment {
    private FragmentStoreBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = View.inflate(getActivity(), R.layout.fragment_store, null);
//        return view;

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lazyLoad();
    }

    private void openTaobaoShopping() {
        if (CommonUtil.checkPackage(mActivity, "com.taobao.taobao")) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri uri = Uri.parse(ConstantUtil.storeUrl);
            intent.setData(uri);
            startActivity(intent);
        }
    }


    /**
     * 初始化布局
     */
    private void initViews() {

        loadUrl();

        if (CommonUtil.checkPackage(mActivity, "com.taobao.taobao")) {
            binding.ivBtn.setVisibility(View.VISIBLE);
            AlertUtil.showAlertInfoDialog(mActivity, mActivity, "检测到您已经安装了淘宝APP，建议直接点击右下角的按钮跳转到淘宝APP!", "确定", "取消", new AlertUtil.AlertCallBack() {
                @Override
                public void onPositive() {
                    openTaobaoShopping();
                }

                @Override
                public void onNegative() {

                }
            });
//            CommonUtil.showTopToast(mActivity, "检测到您已经安装了淘宝APP，建议直接点击右下角的按钮跳转到淘宝APP!");
        }
        binding.ivBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTaobaoShopping();
            }
        });

        //相关下拉刷新的设置
        binding.mRefreshView.setPullLoadEnable(false);
        binding.mRefreshView.setAutoLoadMore(false);
//        mRefreshView.setAutoRefresh(true);

        binding.mRefreshView.setXRefreshViewListener(new XRefreshView.XRefreshViewListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onRefresh(boolean isPullDown) {
                if (isPullDown) {
                    binding.wvStore.loadUrl(binding.wvStore.getUrl());
                }
            }

            @Override
            public void onLoadMore(boolean isSilence) {

            }

            @Override
            public void onRelease(float direction) {

            }

            @Override
            public void onHeaderMove(double headerMovePercent, int offsetY) {

            }
        });

    }

    /**
     * 加载淘宝店
     */
    private void loadUrl() {
        binding.wvStore.getSettings().setJavaScriptEnabled(true);
        MyWebViewClient myWebViewClient = new MyWebViewClient(mActivity, binding.mRefreshView);
        binding.wvStore.setWebViewClient(myWebViewClient);
        myWebViewClient.setonSuccessLoadListener(new MyWebViewClient.OnSuccessLoadListener() {
            @Override
            public void onSuccessLoad(int flag) {
                binding.wvStore.goBack();
            }
        });
        //启用支持javascript
        binding.wvStore.getSettings().setDomStorageEnabled(true);
        binding.wvStore.getSettings().setJavaScriptEnabled(true);
        binding.wvStore.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        binding.wvStore.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//js和android交互
        binding.wvStore.getSettings().setDomStorageEnabled(true);//开启DOM storage API功能
        binding.wvStore.getSettings().setSupportZoom(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.wvStore.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //允许缓存
        binding.wvStore.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        binding.wvStore.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && binding.wvStore.canGoBack()) {
                    binding.wvStore.goBack(); //goBack()表示返回WebView的上一页面
                    return true;
                }
                return false;
            }
        });
        binding.wvStore.loadUrl(ConstantUtil.storeUrl);
    }

    /**
     * 初始化数据
     */
    private void initDatas() {

    }

    @Override
    public void onStop() {
        super.onStop();
//        binding.wvStore.stopLoading();
//        binding.wvStore.clearCache(true);
//        // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
//        binding.wvStore.getSettings().setJavaScriptEnabled(false);
//        binding.wvStore.clearHistory();
//        binding.wvStore.clearView();
//        binding.wvStore.removeAllViews();
//        binding.wvStore.destroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.wvStore.stopLoading();
        binding.wvStore.clearCache(true);
        // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
        binding.wvStore.getSettings().setJavaScriptEnabled(false);
        binding.wvStore.clearHistory();
        binding.wvStore.clearView();
        binding.wvStore.removeAllViews();
        binding.wvStore.destroy();
    }

    @Override
    protected void lazyLoad() {
        initDatas();
        initViews();
    }
}
