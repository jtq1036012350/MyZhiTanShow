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
import com.iguitar.xiaoxiaozhitan.databinding.ActivityPlayListBinding;
import com.iguitar.xiaoxiaozhitan.model.PlayListMainJavaBean;
import com.iguitar.xiaoxiaozhitan.ui.adapter.PlayListAdapter;
import com.iguitar.xiaoxiaozhitan.ui.base.BaseActivity;
import com.iguitar.xiaoxiaozhitan.utils.ViewUtil;

import java.util.ArrayList;

/**
 * 播放列表Activity
 * @author Jiang
 */
public class PlayListActivity extends BaseActivity {
    private ActivityPlayListBinding binding;
    private ArrayList<PlayListMainJavaBean>  mainJavaBeanArrayList;
    private PlayListAdapter playListAdapter;
    private String title;
    private TextView tv_title;
    private ImageButton btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtil.initSystemBar(this, R.color.colorTitleBlack);
//        setContentView(R.layout.activity_play_list);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_play_list);
        initDatas();
        initViews();
    }

    /**
     * 初始化数据
     */
    private void initDatas() {
        mainJavaBeanArrayList = (ArrayList<PlayListMainJavaBean>) getIntent().getSerializableExtra("playList");
        title = getIntent().getStringExtra("title");
        tv_title = (TextView) findViewById(R.id.tv_top_title);
        tv_title.setText(title);

        btn_back = (ImageButton) findViewById(R.id.btn_back);
        btn_back.setVisibility(View.VISIBLE);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishMyActivity();
            }
        });
    }

    /**
     * 初始化布局
     */
    private void initViews() {
        playListAdapter= new PlayListAdapter(this,mainJavaBeanArrayList);
        binding.lvVideoList.setAdapter(playListAdapter);
        binding.lvVideoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("videoList", mainJavaBeanArrayList.get(position).getVideoListJavaBeen());
                bundle.putString("title",mainJavaBeanArrayList.get(position).getCoverName());
                startMyActivity(VideoActivity.class,bundle);
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
