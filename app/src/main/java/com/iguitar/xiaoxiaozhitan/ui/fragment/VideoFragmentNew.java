package com.iguitar.xiaoxiaozhitan.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.andview.refreshview.XRefreshView;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.iguitar.xiaoxiaozhitan.MyApplication;
import com.iguitar.xiaoxiaozhitan.R;
import com.iguitar.xiaoxiaozhitan.api.ApiInerface;
import com.iguitar.xiaoxiaozhitan.databinding.FragmentVideoNewBinding;
import com.iguitar.xiaoxiaozhitan.model.MainListJavaBean;
import com.iguitar.xiaoxiaozhitan.model.MessageEvent;
import com.iguitar.xiaoxiaozhitan.model.VideoBottomBean;
import com.iguitar.xiaoxiaozhitan.ui.activity.PlayListActivity;
import com.iguitar.xiaoxiaozhitan.ui.activity.VideoActivity;
import com.iguitar.xiaoxiaozhitan.ui.adapter.MyVideoRecyclerViewAdapter;
import com.iguitar.xiaoxiaozhitan.ui.base.BaseFragment;
import com.iguitar.xiaoxiaozhitan.utils.CommonUtil;
import com.iguitar.xiaoxiaozhitan.utils.LogUtil;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 视频Fragment
 * Created by Jiang on 2017/4/13.
 */

public class VideoFragmentNew extends BaseFragment {
    private FragmentVideoNewBinding binding;
    //    private TextView tv_tittle;
//    private ImageButton button;
    //    private ArrayList<String> videoList;
//    private ArrayList<Integer> videoCover;
    private SliderLayout sliderView;

    private MyVideoRecyclerViewAdapter myVideoRecyclerViewAdapter;
    //上半部分主要的条目信息集合
    private List<MainListJavaBean> mainListJavaBeenList;
    //底部主要的条目信息集合
    private List<VideoBottomBean> bottomListJavaBeanList;

//    private ArrayList<Integer> bottomImageUrls;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video_new, container, false);
        return binding.getRoot();

//        View view = View.inflate(getActivity(), R.layout.fragment_video, null);
//        return view;
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

//        initLoopRotarySwitchView();
        myVideoRecyclerViewAdapter = new MyVideoRecyclerViewAdapter(mActivity, mainListJavaBeenList, bottomListJavaBeanList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        myVideoRecyclerViewAdapter.settopViewData(new MyVideoRecyclerViewAdapter.OnSetTopViewData() {
            @Override
            public void OnSetTopViewData(SliderLayout sliderLayout, LinearLayout ll_guid_point) {
                sliderView = sliderLayout;
                initLoopRotarySwitchView(sliderLayout, ll_guid_point);
            }
        });
        binding.recyclerView.setAdapter(myVideoRecyclerViewAdapter);

        //相关下拉刷新的设置
        binding.mRefreshView.setPullLoadEnable(false);
        binding.mRefreshView.setAutoLoadMore(false);
//        mRefreshView.setAutoRefresh(true);

        binding.mRefreshView.setXRefreshViewListener(new XRefreshView.XRefreshViewListener() {
            @Override
            public void onRefresh() {
                getDataFromServer();
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

    /**
     * 初始化轮播图
     */
    private void initLoopRotarySwitchView(final SliderLayout sliderView, final LinearLayout ll_guid_point) {
//        binding.mLoopRotarySwitchView
//                .setAutoRotation(true)//是否自动切换
//                .setAutoScrollDirection(LoopRotarySwitchView.AutoScrollDirection.left)//切换方向
//                .setAutoRotationTime(2000);//自动切换的时间  单位毫秒
//
////        binding.mLoopRotarySwitchView.setScaleX(5);
//
//        binding.mLoopRotarySwitchView.setLoopRotationX(-28);
//
//        binding.mLoopRotarySwitchView.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(int fragment_voice_item, View view) {
//                LogUtil.d("info", "点击了" + fragment_voice_item);
//            }
//        });
//
//        binding.mLoopRotarySwitchView.setOnItemSelectedListener(new OnItemSelectedListener() {
//            @Override
//            public void selected(int fragment_voice_item, View view) {
//                LogUtil.d("info", "选择了" + fragment_voice_item);
//                binding.tvReflection.setText(videoList.get(fragment_voice_item));
//            }
//        });

        sliderView.removeAllSliders();

        for (int i = 0; i < mainListJavaBeenList.size(); i++) {
//            DefaultSliderView：只有图片，没有文字描述
//            TextSliderView：有图片，有文字描述
            TextSliderView textSliderView = new TextSliderView(MyApplication.getContext());
            textSliderView.description(mainListJavaBeenList.get(i).getCoverName());
            textSliderView.image(mainListJavaBeenList.get(i).getImageUrl());
            textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
//                    CommonUtil.showTopToast(mActivity, videoList.get(binding.sliderView.getCurrentPosition()));
//                    int x = binding.sliderView.getCurrentPosition();
//                    int y = mainListJavaBeenList.get(binding.sliderView.getCurrentPosition()).getMainJavaBeanArrayList().get(0).getVideoListJavaBeen().size();
                    if (mainListJavaBeenList.get(sliderView.getCurrentPosition()).getMainJavaBeanArrayList().size() == 1) {
//                        Intent intent = new Intent(mActivity, VideoActivity.class);
//                        intent.putExtra("videoList", playListMainJavaBeanArrayList.get(binding.sliderView.getCurrentPosition()).getVideoListJavaBeen());
//                        startActivity(intent);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("videoList", mainListJavaBeenList.get(sliderView.getCurrentPosition()).getMainJavaBeanArrayList().get(0).getVideoListJavaBeen());
                        bundle.putString("title", mainListJavaBeenList.get(sliderView.getCurrentPosition()).getCoverName());
                        startMyActivity(VideoActivity.class, bundle);
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("playList", mainListJavaBeenList.get(sliderView.getCurrentPosition()).getMainJavaBeanArrayList());
                        bundle.putString("title", mainListJavaBeenList.get(sliderView.getCurrentPosition()).getCoverName());
                        startMyActivity(PlayListActivity.class, bundle);
                    }
                }
            });
            sliderView.addSlider(textSliderView);
        }

        sliderView.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                binding.tvReflection.setText(videoList.get(position));
                createPoint(ll_guid_point, position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        binding.sliderView.setSliderTransformDuration(1200,new LinearInterpolator());
//        binding.sliderView.setDuration(2000);
        sliderView.setPresetTransformer(SliderLayout.Transformer.Tablet);
//        sliderView.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
//        sliderView.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderView.setCustomAnimation(new DescriptionAnimation());
        sliderView.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
        createPoint(ll_guid_point, 0);

    }

    /**
     * 创建红点
     *
     * @param pos
     */
    private void createPoint(LinearLayout llGuidPoint, int pos) {
        llGuidPoint.removeAllViews();
        for (int i = 0; i < mainListJavaBeenList.size(); i++) {
            // 创建灰点
            ImageView point = new ImageView(mActivity);
            if (pos == i) {
                point.setBackgroundResource(R.drawable.guide_point_black);
            } else {
                point.setBackgroundResource(R.drawable.guide_point_nomal);
            }
            // 设置宽高
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
            if (i != 0) {
                params.leftMargin = 10;
            }
            point.setLayoutParams(params);
            // 添加到线性容器中
            llGuidPoint.addView(point);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
//        binding.mLoopRotarySwitchView.clearDisappearingChildren();
        if (sliderView != null) {
            sliderView.stopAutoCycle();
        }
    }

    @Override
    protected void lazyLoad() {
//        initDatas();
//        initView();
        if (mainListJavaBeenList == null) {
            getDataFromServer();
        } else {
            initView();
        }
    }

    // This method will be called when a MessageEvent is posted
    @Subscribe
    public void onMessageEvent(MessageEvent event) {
//        Toast.makeText(getActivity(), event.message+"aa", Toast.LENGTH_SHORT).show();
        if (2 == event.getIndex()) {
            getDataFromServer();
        }
    }


    /**
     * 从服务端获取数据
     */
    public void getDataFromServer() {
        binding.mRefreshView.startRefresh();
        ApiInerface studyInfo = retrofit.create(ApiInerface.class);
        Call<List<MainListJavaBean>> call = studyInfo.getVideoTopInfo();
        call.enqueue(new Callback<List<MainListJavaBean>>() {
            @Override
            public void onResponse(Call<List<MainListJavaBean>> call, Response<List<MainListJavaBean>> response) {
                binding.mRefreshView.stopRefresh();
                if (response.isSuccessful()) {
                    mainListJavaBeenList = response.body();
                    getBottomDataFromServer();
                    LogUtil.e("infoooo", "normalGet:" + response.body() + "");
                }
            }

            @Override
            public void onFailure(Call<List<MainListJavaBean>> call, Throwable t) {
                binding.mRefreshView.stopRefresh();
                CommonUtil.showTopToast(mActivity, "获取视频页面顶部数据失败！");
                LogUtil.e("infoooo", "normalGet:" + t.toString() + "");
            }
        });
    }

    /**
     * 获取视频页面底部数据
     */
    public void getBottomDataFromServer() {
        binding.mRefreshView.startRefresh();
        ApiInerface studyInfo = retrofit.create(ApiInerface.class);
        Call<List<VideoBottomBean>> call = studyInfo.getVideoBottomInfo();
        call.enqueue(new Callback<List<VideoBottomBean>>() {
            @Override
            public void onResponse(Call<List<VideoBottomBean>> call, Response<List<VideoBottomBean>> response) {
                binding.mRefreshView.stopRefresh();
                if (response.isSuccessful()) {
                    bottomListJavaBeanList = response.body();
                    initView();
                    LogUtil.e("infoooo", "normalGet:" + response.body() + "");
                }
            }

            @Override
            public void onFailure(Call<List<VideoBottomBean>> call, Throwable t) {
                binding.mRefreshView.stopRefresh();
                CommonUtil.showTopToast(mActivity, "获取视频页面底部数据失败！");
                LogUtil.e("infoooo", "normalGet:" + t.toString() + "");
            }
        });
    }
}
