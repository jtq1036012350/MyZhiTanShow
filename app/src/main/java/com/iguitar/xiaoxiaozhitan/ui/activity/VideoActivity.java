package com.iguitar.xiaoxiaozhitan.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.iguitar.xiaoxiaozhitan.R;
import com.iguitar.xiaoxiaozhitan.databinding.ActivityVideoBinding;
import com.iguitar.xiaoxiaozhitan.model.VideoListJavaBean;
import com.iguitar.xiaoxiaozhitan.ui.adapter.VideoListAdapter;
import com.iguitar.xiaoxiaozhitan.ui.base.BaseActivity;

import java.util.ArrayList;

/**
 * 视频页面（可点击跳转）
 */
public class VideoActivity extends BaseActivity {
    private ActivityVideoBinding binding;
    //视频集合
    private ArrayList<VideoListJavaBean> videoListJavaBeenList;
    private TextView tv_title;
    private ImageButton btn_back;
    private VideoListAdapter playListAdapter;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_video);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video);
        tv_title = (TextView) findViewById(R.id.tv_top_title);
        title = getIntent().getStringExtra("title");
        tv_title.setText(title);

        btn_back = (ImageButton) findViewById(R.id.btn_back);
        btn_back.setVisibility(View.VISIBLE);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishMyActivity();
            }
        });

        initDatas();
        initViews();
    }

    /**
     * 初始化数据
     */
    private void initDatas() {
        videoListJavaBeenList = (ArrayList<VideoListJavaBean>) getIntent().getSerializableExtra("videoList");
    }

    /**
     * 初始化布局
     */
    private void initViews() {
        playListAdapter = new VideoListAdapter(this,videoListJavaBeenList);
        binding.lvVideo.setAdapter(playListAdapter);
        binding.lvVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                startMyActivity();
                Bundle bundle = new Bundle();
                bundle.putString("url",videoListJavaBeenList.get(position).getUrl());
                bundle.putString("title","视频播放");
                startMyActivity(MyWebViewActivity.class,bundle);
            }
        });
        //相关下拉刷新的设置
        binding.mRefreshView.setPullLoadEnable(false);
        binding.mRefreshView.setAutoLoadMore(false);
//        mRefreshView.setAutoRefresh(true);

        binding.mRefreshView.setXRefreshViewListener(new XRefreshView.XRefreshViewListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.mRefreshView.stopRefresh();
                    }
                }, 1000);
            }

            @Override
            public void onRefresh(boolean isPullDown) {

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
}
