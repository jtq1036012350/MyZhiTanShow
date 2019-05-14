package com.iguitar.xiaoxiaozhitan.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.iguitar.xiaoxiaozhitan.R;
import com.iguitar.xiaoxiaozhitan.model.MessageEvent;
import com.iguitar.xiaoxiaozhitan.ui.base.BaseActivity;
import com.iguitar.xiaoxiaozhitan.utils.CommonUtil;

import org.greenrobot.eventbus.Subscribe;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * 引导页Activity
 */
public class GuideActivity extends BaseActivity {
    private static final String TAG = GuideActivity.class.getSimpleName();
    private BGABanner mBackgroundBanner;
    private BGABanner mForegroundBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        setListener();
        processLogic();
    }

    @Subscribe
    public void onMessageEvent(MessageEvent event) {
//        Toast.makeText(getActivity(), event.message+"aa", Toast.LENGTH_SHORT).show();
//        if (2 == event.getIndex()) {
//            getDataFromServer();
//        }
    }

    private void initView() {
        setContentView(R.layout.activity_guide);
        mBackgroundBanner = (BGABanner) findViewById(R.id.banner_guide_background);
        mForegroundBanner = (BGABanner) findViewById(R.id.banner_guide_foreground);
    }

    private void setListener() {
        /**
         * 设置进入按钮和跳过按钮控件资源 id 及其点击事件
         * 如果进入按钮和跳过按钮有一个不存在的话就传 0
         * 在 BGABanner 里已经帮开发者处理了防止重复点击事件
         * 在 BGABanner 里已经帮开发者处理了「跳过按钮」和「进入按钮」的显示与隐藏
         */
        mForegroundBanner.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter, R.id.tv_guide_skip, new BGABanner.GuideDelegate() {
            @Override
            public void onClickEnterOrSkip() {
                CommonUtil.saveFirstGuide(GuideActivity.this, true);
                startActivity(new Intent(GuideActivity.this, NetworkActivity.class));
                finish();
            }
        });
    }

    /**
     * 设置处理图片
     */
    private void processLogic() {
        // 设置数据源
        mBackgroundBanner.setData(R.mipmap.guide_00, R.mipmap.guide_01, R.mipmap.guide_02);

        mForegroundBanner.setData(R.mipmap.guide_00_word, R.mipmap.guide_01_word, R.mipmap.guide_02_word);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 如果开发者的引导页主题是透明的，需要在界面可见时给背景 Banner 设置一个白色背景，避免滑动过程中两个 Banner 都设置透明度后能看到 Launcher
        mBackgroundBanner.setBackgroundResource(android.R.color.white);
    }
}