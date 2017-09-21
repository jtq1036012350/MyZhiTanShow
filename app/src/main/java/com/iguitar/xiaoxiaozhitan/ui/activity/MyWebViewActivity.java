package com.iguitar.xiaoxiaozhitan.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.widget.ImageButton;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.iguitar.xiaoxiaozhitan.R;
import com.iguitar.xiaoxiaozhitan.databinding.ActivityWebviewLayoutBinding;
import com.iguitar.xiaoxiaozhitan.ui.base.BaseActivity;
import com.iguitar.xiaoxiaozhitan.ui.view.MyWebViewClient;
import com.iguitar.xiaoxiaozhitan.utils.AlertUtil;
import com.iguitar.xiaoxiaozhitan.utils.CommonUtil;
import com.iguitar.xiaoxiaozhitan.utils.ConstantUtil;

/**
 * WebViewActivity
 * Created by Jiang on 2017/4/17.
 */

public class MyWebViewActivity extends BaseActivity {
    private ActivityWebviewLayoutBinding binding;
    private String url;
    private String title;
    private TextView tv_tittle;
    private ImageButton imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_webview_layout);
        initDatas();
        initViews();
    }

    private void initDatas() {
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
    }

    private void initViews() {
        tv_tittle = (TextView) findViewById(R.id.tv_top_title);
        tv_tittle.setText(title);

        imgBack = (ImageButton) findViewById(R.id.btn_back);
        imgBack.setVisibility(View.VISIBLE);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishMyActivity();
            }
        });

        binding.wvMy.getSettings().setJavaScriptEnabled(true);
        MyWebViewClient myWebViewActivity = new MyWebViewClient(this, binding.mRefreshView);
        binding.wvMy.setWebChromeClient(new WebChromeClient());
        binding.wvMy.setWebViewClient(myWebViewActivity);
        myWebViewActivity.setonSuccessLoadListener(new MyWebViewClient.OnSuccessLoadListener() {
            @Override
            public void onSuccessLoad(int flag) {
                binding.wvMy.loadUrl(url);
            }
        });

        binding.wvMy.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        //启用支持javascript
        binding.wvMy.getSettings().setDomStorageEnabled(true);
        binding.wvMy.getSettings().setJavaScriptEnabled(true);

        binding.wvMy.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//js和android交互

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.wvMy.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //允许缓存
        binding.wvMy.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

//        binding.wvMy.getSettings().setDomStorageEnabled(true);//开启DOM storage API功能
        binding.wvMy.getSettings().setSupportZoom(true);
//
//        binding.wvMy.getSettings().setUserAgentString("User-Agent:iPhone");//设置用户代理，一般不用
        binding.wvMy.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        binding.wvMy.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && binding.wvMy.canGoBack()) {
                    binding.wvMy.goBack(); //goBack()表示返回WebView的上一页面
                    return true;
                }
                return false;
            }
        });
        binding.wvMy.loadUrl(url);

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
                    binding.wvMy.loadUrl(binding.wvMy.getUrl());
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

        if ("快手".equals(title)) {
            //判断是否直接可以跳转
            if (CommonUtil.checkPackage(this, ConstantUtil.packKuaiShou)) {
                AlertUtil.showAlertInfoDialog(this, this, "检测到您已经安装了快手APP，建议直接点击右下角的按钮跳转到快手APP!", "确定", "取消", new AlertUtil.AlertCallBack() {
                    @Override
                    public void onPositive() {
                        openUrl(ConstantUtil.roomKuaiShou, ConstantUtil.packKuaiShou);
                    }

                    @Override
                    public void onNegative() {

                    }
                });
            }
        } else if ("熊猫直播".equals(title)) {
            //判断是否直接可以跳转
            if (CommonUtil.checkPackage(this, ConstantUtil.packXiongMao)) {
                AlertUtil.showAlertInfoDialog(this, this, "检测到您已经安装了熊猫直播APP，建议直接点击右下角的按钮跳转到熊猫直播APP!", "确定", "取消", new AlertUtil.AlertCallBack() {
                    @Override
                    public void onPositive() {
                        openUrl(ConstantUtil.roomXiongMao, ConstantUtil.packXiongMao);
                    }

                    @Override
                    public void onNegative() {

                    }
                });
            }
        }


    }

    /**
     * 打开特定的地址
     *
     * @param url         地址
     * @param packageName 包名
     */
    private void openUrl(String url, String packageName) {
        if (CommonUtil.checkPackage(this, packageName)) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri uri = Uri.parse(url);
            intent.setData(uri);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.wvMy.stopLoading();
        binding.wvMy.clearCache(true);
        // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
        binding.wvMy.getSettings().setJavaScriptEnabled(false);
        binding.wvMy.clearHistory();
        binding.wvMy.clearView();
        binding.wvMy.removeAllViews();
        binding.wvMy.destroy();
    }
}
