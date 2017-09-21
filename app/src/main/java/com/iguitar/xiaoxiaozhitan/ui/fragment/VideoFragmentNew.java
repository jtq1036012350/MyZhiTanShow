package com.iguitar.xiaoxiaozhitan.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.gson.Gson;
import com.iguitar.xiaoxiaozhitan.MyApplication;
import com.iguitar.xiaoxiaozhitan.R;
import com.iguitar.xiaoxiaozhitan.databinding.FragmentVideoNewBinding;
import com.iguitar.xiaoxiaozhitan.model.MainListJavaBean;
import com.iguitar.xiaoxiaozhitan.model.PlayListMainJavaBean;
import com.iguitar.xiaoxiaozhitan.model.VideoBottomBean;
import com.iguitar.xiaoxiaozhitan.model.VideoListJavaBean;
import com.iguitar.xiaoxiaozhitan.ui.activity.PlayListActivity;
import com.iguitar.xiaoxiaozhitan.ui.activity.VideoActivity;
import com.iguitar.xiaoxiaozhitan.ui.adapter.MyVideoRecyclerViewAdapter;
import com.iguitar.xiaoxiaozhitan.ui.base.BaseFragment;
import com.iguitar.xiaoxiaozhitan.utils.ConstantUtil;
import com.iguitar.xiaoxiaozhitan.utils.LogUtil;
import com.iguitar.xiaoxiaozhitan.utils.MyLogUtil;
import com.iguitar.xiaoxiaozhitan.utils.PrompUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 视频Fragment
 * Created by Jiang on 2017/4/13.
 */

public class VideoFragmentNew extends BaseFragment {
    private FragmentVideoNewBinding binding;
    private TextView tv_tittle;
    private ImageButton button;
    private ArrayList<String> videoList;
    private ArrayList<Integer> videoCover;
    private SliderLayout sliderView;

    private MyVideoRecyclerViewAdapter myVideoRecyclerViewAdapter;
    //上半部分主要的条目信息集合
    private ArrayList<MainListJavaBean> mainListJavaBeenList;
    //底部主要的条目信息集合
    private List<VideoBottomBean> bottomListJavaBeanList;

    private ArrayList<Integer> bottomImageUrls;

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
     * 初始化数据
     */
    private void initDatas() {
        PrompUtil.startProgressDialog(mActivity, "加载中...");
        if (videoList == null) {
            videoList = new ArrayList<>();

            videoList.add("ACG教程合集");
            videoList.add("陈亮");
            videoList.add("岸部真明教程合集");
            videoList.add("押尾桑教程合集");
            videoList.add("郑成和教程合集");
        }

        if (videoCover == null) {
            videoCover = new ArrayList<>();

            videoCover.add(R.mipmap.acgwork);
            videoCover.add(R.mipmap.chen_liang);
            //--------------------------------------------------------------------------------------------------修改需求专辑图标--------------------------------------//
            videoCover.add(R.mipmap.anbuzhengming_new);
//            videoCover.add(R.mipmap.anbuzhenming);
            videoCover.add(R.mipmap.yaweisan_new);
//            videoCover.add(R.mipmap.yawei);
            videoCover.add(R.mipmap.zhengchenghe_new);
//            videoCover.add(R.mipmap.zhengchenghe);
        }

        if (mainListJavaBeenList == null) {
            mainListJavaBeenList = new ArrayList<>();
            //ACG教程集合
            ArrayList<PlayListMainJavaBean> playListMainJavaBeanArrayListACG = new ArrayList<>();
            PlayListMainJavaBean playListMainJavaBeanACG00 = new PlayListMainJavaBean();
            playListMainJavaBeanACG00.setCoverName("ACG教程第一期【火影忍者】");
            playListMainJavaBeanACG00.setVideoCover(R.mipmap.acg_00);

            PlayListMainJavaBean playListMainJavaBeanACG01 = new PlayListMainJavaBean();
            playListMainJavaBeanACG01.setCoverName("PM不会的在加群后输入【PM技巧】");
            playListMainJavaBeanACG01.setVideoCover(R.mipmap.acg_01);

            PlayListMainJavaBean playListMainJavaBeanACG02 = new PlayListMainJavaBean();
            playListMainJavaBeanACG02.setCoverName("刷弦部分在腾讯课堂搜索未闻花名");
            playListMainJavaBeanACG02.setVideoCover(R.mipmap.acg_02);

            VideoListJavaBean videoListJavaBeanACG0_00 = new VideoListJavaBean();
            videoListJavaBeanACG0_00.setUrl(ConstantUtil.urlThirtySix);
            videoListJavaBeanACG0_00.setVideoCover(R.mipmap.acg_00);

            VideoListJavaBean videoListJavaBeanACG0_01 = new VideoListJavaBean();
            videoListJavaBeanACG0_01.setUrl(ConstantUtil.urlThirtySeven);
            videoListJavaBeanACG0_01.setVideoCover(R.mipmap.acg_00);

            VideoListJavaBean videoListJavaBeanACG0_02 = new VideoListJavaBean();
            videoListJavaBeanACG0_02.setUrl(ConstantUtil.urlThirtyEight);
            videoListJavaBeanACG0_02.setVideoCover(R.mipmap.acg_00);

            VideoListJavaBean videoListJavaBeanACG0_03 = new VideoListJavaBean();
            videoListJavaBeanACG0_03.setUrl(ConstantUtil.urlThirtyNine);
            videoListJavaBeanACG0_03.setVideoCover(R.mipmap.acg_00);

            VideoListJavaBean videoListJavaBeanACG0_04 = new VideoListJavaBean();
            videoListJavaBeanACG0_04.setUrl(ConstantUtil.urlForty);
            videoListJavaBeanACG0_04.setVideoCover(R.mipmap.acg_00);


            ArrayList<VideoListJavaBean> videoListJavaBeanArrayListChenLiang0_00 = new ArrayList<>();
            videoListJavaBeanArrayListChenLiang0_00.add(videoListJavaBeanACG0_00);
            videoListJavaBeanArrayListChenLiang0_00.add(videoListJavaBeanACG0_01);
            videoListJavaBeanArrayListChenLiang0_00.add(videoListJavaBeanACG0_02);
            videoListJavaBeanArrayListChenLiang0_00.add(videoListJavaBeanACG0_03);
            videoListJavaBeanArrayListChenLiang0_00.add(videoListJavaBeanACG0_04);

            playListMainJavaBeanACG00.setVideoListJavaBeen(videoListJavaBeanArrayListChenLiang0_00);
            playListMainJavaBeanArrayListACG.add(playListMainJavaBeanACG00);


            VideoListJavaBean videoListJavaBeanACG1_00 = new VideoListJavaBean();
            videoListJavaBeanACG1_00.setUrl(ConstantUtil.urlFortyOne);
            videoListJavaBeanACG1_00.setVideoCover(R.mipmap.acg_01);

            VideoListJavaBean videoListJavaBeanACG1_01 = new VideoListJavaBean();
            videoListJavaBeanACG1_01.setUrl(ConstantUtil.urlFortyTwo);
            videoListJavaBeanACG1_01.setVideoCover(R.mipmap.acg_01);

            ArrayList<VideoListJavaBean> videoListJavaBeanArrayListChenLiang1_00 = new ArrayList<>();
            videoListJavaBeanArrayListChenLiang1_00.add(videoListJavaBeanACG1_00);
            videoListJavaBeanArrayListChenLiang1_00.add(videoListJavaBeanACG1_01);

            playListMainJavaBeanACG01.setVideoListJavaBeen(videoListJavaBeanArrayListChenLiang1_00);
            playListMainJavaBeanArrayListACG.add(playListMainJavaBeanACG01);


            VideoListJavaBean videoListJavaBeanACG2_00 = new VideoListJavaBean();
            videoListJavaBeanACG2_00.setUrl(ConstantUtil.urlFortyThree);
            videoListJavaBeanACG2_00.setVideoCover(R.mipmap.acg_02);

            VideoListJavaBean videoListJavaBeanACG2_01 = new VideoListJavaBean();
            videoListJavaBeanACG2_01.setUrl(ConstantUtil.urlFortyFour);
            videoListJavaBeanACG2_01.setVideoCover(R.mipmap.acg_02);


            VideoListJavaBean videoListJavaBeanACG2_02 = new VideoListJavaBean();
            videoListJavaBeanACG2_02.setUrl(ConstantUtil.urlFortyFive);
            videoListJavaBeanACG2_02.setVideoCover(R.mipmap.acg_02);

            VideoListJavaBean videoListJavaBeanACG2_03 = new VideoListJavaBean();
            videoListJavaBeanACG2_03.setUrl(ConstantUtil.urlFortySix);
            videoListJavaBeanACG2_03.setVideoCover(R.mipmap.acg_02);


            VideoListJavaBean videoListJavaBeanACG2_04 = new VideoListJavaBean();
            videoListJavaBeanACG2_04.setUrl(ConstantUtil.urlFortySeven);
            videoListJavaBeanACG2_04.setVideoCover(R.mipmap.acg_02);

            ArrayList<VideoListJavaBean> videoListJavaBeanArrayListChenLiang2_00 = new ArrayList<>();
            videoListJavaBeanArrayListChenLiang2_00.add(videoListJavaBeanACG2_00);
            videoListJavaBeanArrayListChenLiang2_00.add(videoListJavaBeanACG2_01);
            videoListJavaBeanArrayListChenLiang2_00.add(videoListJavaBeanACG2_02);
            videoListJavaBeanArrayListChenLiang2_00.add(videoListJavaBeanACG2_03);
            videoListJavaBeanArrayListChenLiang2_00.add(videoListJavaBeanACG2_04);

            playListMainJavaBeanACG02.setVideoListJavaBeen(videoListJavaBeanArrayListChenLiang2_00);
            playListMainJavaBeanArrayListACG.add(playListMainJavaBeanACG02);


            MainListJavaBean mainListJavaBeenACG = new MainListJavaBean();
            mainListJavaBeenACG.setMainJavaBeanArrayList(playListMainJavaBeanArrayListACG);
            mainListJavaBeenACG.setCoverName("ACG教程合集");
            mainListJavaBeenList.add(mainListJavaBeenACG);

            //陈亮的集合
            ArrayList<PlayListMainJavaBean> playListMainJavaBeanArrayListChenLiang = new ArrayList<>();
            PlayListMainJavaBean playListMainJavaBeanChenLiang = new PlayListMainJavaBean();
            playListMainJavaBeanChenLiang.setCoverName("");
            playListMainJavaBeanChenLiang.setVideoCover(-1);

            playListMainJavaBeanArrayListChenLiang.add(playListMainJavaBeanChenLiang);

            VideoListJavaBean videoListJavaBeanChenLiang = new VideoListJavaBean();
            videoListJavaBeanChenLiang.setUrl(ConstantUtil.urlFortyEight);
            videoListJavaBeanChenLiang.setVideoCover(R.mipmap.chen_liang);

            ArrayList<VideoListJavaBean> videoListJavaBeanArrayListChenLiang = new ArrayList<>();
            videoListJavaBeanArrayListChenLiang.add(videoListJavaBeanChenLiang);
            playListMainJavaBeanChenLiang.setVideoListJavaBeen(videoListJavaBeanArrayListChenLiang);
            MainListJavaBean mainListJavaBeenChenLiang = new MainListJavaBean();
            mainListJavaBeenChenLiang.setMainJavaBeanArrayList(playListMainJavaBeanArrayListChenLiang);
            mainListJavaBeenChenLiang.setCoverName("陈亮");
            mainListJavaBeenList.add(mainListJavaBeenChenLiang);

            //岸部真明的集合
            ArrayList<PlayListMainJavaBean> playListMainJavaBeanArrayListAnBu = new ArrayList<>();

            PlayListMainJavaBean playListMainJavaBeanAnBu = new PlayListMainJavaBean();
            playListMainJavaBeanAnBu.setCoverName("");
            playListMainJavaBeanAnBu.setVideoCover(-1);

            playListMainJavaBeanArrayListAnBu.add(playListMainJavaBeanAnBu);

            VideoListJavaBean videoListJavaBeanAnBu_00 = new VideoListJavaBean();
            videoListJavaBeanAnBu_00.setUrl(ConstantUtil.urlOne);
            videoListJavaBeanAnBu_00.setVideoCover(R.mipmap.anbu_00);

            VideoListJavaBean videoListJavaBeanAnBu_01 = new VideoListJavaBean();
            videoListJavaBeanAnBu_01.setUrl(ConstantUtil.urlTwo);
            videoListJavaBeanAnBu_01.setVideoCover(R.mipmap.anbu_01);

            VideoListJavaBean videoListJavaBeanAnBu_02 = new VideoListJavaBean();
            videoListJavaBeanAnBu_02.setUrl(ConstantUtil.urlThree);
            videoListJavaBeanAnBu_02.setVideoCover(R.mipmap.anbu_02);

            VideoListJavaBean videoListJavaBeanAnBu_03 = new VideoListJavaBean();
            videoListJavaBeanAnBu_03.setUrl(ConstantUtil.urlFour);
            videoListJavaBeanAnBu_03.setVideoCover(R.mipmap.anbu_03);

            VideoListJavaBean videoListJavaBeanAnBu_04 = new VideoListJavaBean();
            videoListJavaBeanAnBu_04.setUrl(ConstantUtil.urlFive);
            videoListJavaBeanAnBu_04.setVideoCover(R.mipmap.anbu_04);

            ArrayList<VideoListJavaBean> videoListJavaBeanArrayListAnBu = new ArrayList<>();
            videoListJavaBeanArrayListAnBu.add(videoListJavaBeanAnBu_00);
            videoListJavaBeanArrayListAnBu.add(videoListJavaBeanAnBu_01);
            videoListJavaBeanArrayListAnBu.add(videoListJavaBeanAnBu_02);
            videoListJavaBeanArrayListAnBu.add(videoListJavaBeanAnBu_03);
            videoListJavaBeanArrayListAnBu.add(videoListJavaBeanAnBu_04);


            playListMainJavaBeanAnBu.setVideoListJavaBeen(videoListJavaBeanArrayListAnBu);
            MainListJavaBean mainListJavaBeenAnBu = new MainListJavaBean();
            mainListJavaBeenAnBu.setMainJavaBeanArrayList(playListMainJavaBeanArrayListAnBu);
            mainListJavaBeenAnBu.setCoverName("岸部真明教程合集");
            mainListJavaBeenList.add(mainListJavaBeenAnBu);

            //押尾桑教程集合
            ArrayList<PlayListMainJavaBean> playListMainJavaBeanArrayListYWS = new ArrayList<>();
            PlayListMainJavaBean playListMainJavaBeanYWS00 = new PlayListMainJavaBean();
            playListMainJavaBeanYWS00.setCoverName("押尾am技巧初步讲解，后续会有更完善的视频");
            playListMainJavaBeanYWS00.setVideoCover(R.mipmap.yawei_00);

            PlayListMainJavaBean playListMainJavaBeanYWS01 = new PlayListMainJavaBean();
            playListMainJavaBeanYWS01.setCoverName("押尾桑系列指弹【Wind Song】");
            playListMainJavaBeanYWS01.setVideoCover(R.mipmap.yawei_01);

            PlayListMainJavaBean playListMainJavaBeanYWS02 = new PlayListMainJavaBean();
            playListMainJavaBeanYWS02.setCoverName("最重点弦部分在腾讯课堂搜索Fight");
            playListMainJavaBeanYWS02.setVideoCover(R.mipmap.yawei_04);

            PlayListMainJavaBean playListMainJavaBeanYWS03 = new PlayListMainJavaBean();
            playListMainJavaBeanYWS03.setCoverName("原版谱后日语注释部分讲解在腾讯课堂搜索Wings");
            playListMainJavaBeanYWS03.setVideoCover(R.mipmap.yawei_05);

            PlayListMainJavaBean playListMainJavaBeanYWS04 = new PlayListMainJavaBean();
            playListMainJavaBeanYWS04.setCoverName("注释的三吉他部分在腾讯课堂搜索Landscape");
            playListMainJavaBeanYWS04.setVideoCover(R.mipmap.yawei_06);

            PlayListMainJavaBean playListMainJavaBeanYWS05 = new PlayListMainJavaBean();
            playListMainJavaBeanYWS05.setCoverName("押尾桑系列指弹【Indigo Love】");
            playListMainJavaBeanYWS05.setVideoCover(R.mipmap.yawei_07);

            VideoListJavaBean videoListJavaBeanYWS0_00 = new VideoListJavaBean();
            videoListJavaBeanYWS0_00.setUrl(ConstantUtil.urlSix);
            videoListJavaBeanYWS0_00.setVideoCover(R.mipmap.yawei_00);

            ArrayList<VideoListJavaBean> videoListJavaBeanArrayListYWS0_00 = new ArrayList<>();
            videoListJavaBeanArrayListYWS0_00.add(videoListJavaBeanYWS0_00);

            playListMainJavaBeanYWS00.setVideoListJavaBeen(videoListJavaBeanArrayListYWS0_00);
            playListMainJavaBeanArrayListYWS.add(playListMainJavaBeanYWS00);


            VideoListJavaBean videoListJavaBeanYwS1_00 = new VideoListJavaBean();
            videoListJavaBeanYwS1_00.setUrl(ConstantUtil.urlSeven);
            videoListJavaBeanYwS1_00.setVideoCover(R.mipmap.yawei_01);

            VideoListJavaBean videoListJavaBeanYWS1_01 = new VideoListJavaBean();
            videoListJavaBeanYWS1_01.setUrl(ConstantUtil.urlEight);
            videoListJavaBeanYWS1_01.setVideoCover(R.mipmap.yawei_02);

            VideoListJavaBean videoListJavaBeanYWS1_02 = new VideoListJavaBean();
            videoListJavaBeanYWS1_02.setUrl(ConstantUtil.urlNine);
            videoListJavaBeanYWS1_02.setVideoCover(R.mipmap.yawei_03);

            ArrayList<VideoListJavaBean> videoListJavaBeanArrayListYWS1_00 = new ArrayList<>();
            videoListJavaBeanArrayListYWS1_00.add(videoListJavaBeanYwS1_00);
            videoListJavaBeanArrayListYWS1_00.add(videoListJavaBeanYWS1_01);
            videoListJavaBeanArrayListYWS1_00.add(videoListJavaBeanYWS1_02);

            playListMainJavaBeanYWS01.setVideoListJavaBeen(videoListJavaBeanArrayListYWS1_00);
            playListMainJavaBeanArrayListYWS.add(playListMainJavaBeanYWS01);


            VideoListJavaBean videoListJavaBeanYWS2_00 = new VideoListJavaBean();
            videoListJavaBeanYWS2_00.setUrl(ConstantUtil.urlTen);
            videoListJavaBeanYWS2_00.setVideoCover(R.mipmap.yawei_04);

            VideoListJavaBean videoListJavaBeanYWS2_01 = new VideoListJavaBean();
            videoListJavaBeanYWS2_01.setUrl(ConstantUtil.urlEleven);
            videoListJavaBeanYWS2_01.setVideoCover(R.mipmap.yawei_04);

            VideoListJavaBean videoListJavaBeanYWS2_02 = new VideoListJavaBean();
            videoListJavaBeanYWS2_02.setUrl(ConstantUtil.urlTweleve);
            videoListJavaBeanYWS2_02.setVideoCover(R.mipmap.yawei_04);

            VideoListJavaBean videoListJavaBeanYWS2_03 = new VideoListJavaBean();
            videoListJavaBeanYWS2_03.setUrl(ConstantUtil.urlThirteen);
            videoListJavaBeanYWS2_03.setVideoCover(R.mipmap.yawei_04);

            VideoListJavaBean videoListJavaBeanYES2_04 = new VideoListJavaBean();
            videoListJavaBeanYES2_04.setUrl(ConstantUtil.urlForteen);
            videoListJavaBeanYES2_04.setVideoCover(R.mipmap.yawei_04);

            ArrayList<VideoListJavaBean> videoListJavaBeanArrayListYWS2_00 = new ArrayList<>();
            videoListJavaBeanArrayListYWS2_00.add(videoListJavaBeanYWS2_00);
            videoListJavaBeanArrayListYWS2_00.add(videoListJavaBeanYWS2_01);
            videoListJavaBeanArrayListYWS2_00.add(videoListJavaBeanYWS2_02);
            videoListJavaBeanArrayListYWS2_00.add(videoListJavaBeanYWS2_03);
            videoListJavaBeanArrayListYWS2_00.add(videoListJavaBeanYES2_04);

            playListMainJavaBeanYWS02.setVideoListJavaBeen(videoListJavaBeanArrayListYWS2_00);
            playListMainJavaBeanArrayListYWS.add(playListMainJavaBeanYWS02);

            VideoListJavaBean videoListJavaBeanYWS3_00 = new VideoListJavaBean();
            videoListJavaBeanYWS3_00.setUrl(ConstantUtil.urlFifteen);
            videoListJavaBeanYWS3_00.setVideoCover(R.mipmap.yawei_05);

            VideoListJavaBean videoListJavaBeanYWS3_01 = new VideoListJavaBean();
            videoListJavaBeanYWS3_01.setUrl(ConstantUtil.urlSixteen);
            videoListJavaBeanYWS3_01.setVideoCover(R.mipmap.yawei_05);

            VideoListJavaBean videoListJavaBeanYWS3_02 = new VideoListJavaBean();
            videoListJavaBeanYWS3_02.setUrl(ConstantUtil.urlSeventeen);
            videoListJavaBeanYWS3_02.setVideoCover(R.mipmap.yawei_05);

            VideoListJavaBean videoListJavaBeanYWS3_03 = new VideoListJavaBean();
            videoListJavaBeanYWS3_03.setUrl(ConstantUtil.urlEighteen);
            videoListJavaBeanYWS3_03.setVideoCover(R.mipmap.yawei_05);

            VideoListJavaBean videoListJavaBeanYES3_04 = new VideoListJavaBean();
            videoListJavaBeanYES3_04.setUrl(ConstantUtil.urlNinteen);
            videoListJavaBeanYES3_04.setVideoCover(R.mipmap.yawei_05);

            ArrayList<VideoListJavaBean> videoListJavaBeanArrayListYWS3_00 = new ArrayList<>();
            videoListJavaBeanArrayListYWS3_00.add(videoListJavaBeanYWS3_00);
            videoListJavaBeanArrayListYWS3_00.add(videoListJavaBeanYWS3_01);
            videoListJavaBeanArrayListYWS3_00.add(videoListJavaBeanYWS3_02);
            videoListJavaBeanArrayListYWS3_00.add(videoListJavaBeanYWS3_03);
            videoListJavaBeanArrayListYWS3_00.add(videoListJavaBeanYES3_04);

            playListMainJavaBeanYWS03.setVideoListJavaBeen(videoListJavaBeanArrayListYWS3_00);
            playListMainJavaBeanArrayListYWS.add(playListMainJavaBeanYWS03);

            VideoListJavaBean videoListJavaBeanYWS4_00 = new VideoListJavaBean();
            videoListJavaBeanYWS4_00.setUrl(ConstantUtil.urlTwenty);
            videoListJavaBeanYWS4_00.setVideoCover(R.mipmap.yawei_06);

            VideoListJavaBean videoListJavaBeanYWS4_01 = new VideoListJavaBean();
            videoListJavaBeanYWS4_01.setUrl(ConstantUtil.urlTwentyOne);
            videoListJavaBeanYWS4_01.setVideoCover(R.mipmap.yawei_06);

            VideoListJavaBean videoListJavaBeanYWS4_02 = new VideoListJavaBean();
            videoListJavaBeanYWS4_02.setUrl(ConstantUtil.urlTwentyTwo);
            videoListJavaBeanYWS4_02.setVideoCover(R.mipmap.yawei_06);

            VideoListJavaBean videoListJavaBeanYWS4_03 = new VideoListJavaBean();
            videoListJavaBeanYWS4_03.setUrl(ConstantUtil.urlTwentyThree);
            videoListJavaBeanYWS4_03.setVideoCover(R.mipmap.yawei_06);

            VideoListJavaBean videoListJavaBeanYES4_04 = new VideoListJavaBean();
            videoListJavaBeanYES4_04.setUrl(ConstantUtil.urlTwentyFour);
            videoListJavaBeanYES4_04.setVideoCover(R.mipmap.yawei_06);

            ArrayList<VideoListJavaBean> videoListJavaBeanArrayListYWS4_00 = new ArrayList<>();
            videoListJavaBeanArrayListYWS4_00.add(videoListJavaBeanYWS4_00);
            videoListJavaBeanArrayListYWS4_00.add(videoListJavaBeanYWS4_01);
            videoListJavaBeanArrayListYWS4_00.add(videoListJavaBeanYWS4_02);
            videoListJavaBeanArrayListYWS4_00.add(videoListJavaBeanYWS4_03);
            videoListJavaBeanArrayListYWS4_00.add(videoListJavaBeanYES4_04);

            playListMainJavaBeanYWS04.setVideoListJavaBeen(videoListJavaBeanArrayListYWS4_00);
            playListMainJavaBeanArrayListYWS.add(playListMainJavaBeanYWS04);

            VideoListJavaBean videoListJavaBeanYWS5_00 = new VideoListJavaBean();
            videoListJavaBeanYWS5_00.setUrl(ConstantUtil.urlTwentyFive);
            videoListJavaBeanYWS5_00.setVideoCover(R.mipmap.yawei_07);

            VideoListJavaBean videoListJavaBeanYWS5_01 = new VideoListJavaBean();
            videoListJavaBeanYWS5_01.setUrl(ConstantUtil.urlTwentySix);
            videoListJavaBeanYWS5_01.setVideoCover(R.mipmap.yawei_07);

            VideoListJavaBean videoListJavaBeanYWS5_02 = new VideoListJavaBean();
            videoListJavaBeanYWS5_02.setUrl(ConstantUtil.urlTwentySeven);
            videoListJavaBeanYWS5_02.setVideoCover(R.mipmap.yawei_07);

            ArrayList<VideoListJavaBean> videoListJavaBeanArrayListYWS5_00 = new ArrayList<>();
            videoListJavaBeanArrayListYWS5_00.add(videoListJavaBeanYWS5_00);
            videoListJavaBeanArrayListYWS5_00.add(videoListJavaBeanYWS5_01);
            videoListJavaBeanArrayListYWS5_00.add(videoListJavaBeanYWS5_02);

            playListMainJavaBeanYWS05.setVideoListJavaBeen(videoListJavaBeanArrayListYWS5_00);
            playListMainJavaBeanArrayListYWS.add(playListMainJavaBeanYWS05);


            MainListJavaBean mainListJavaBeenYWS = new MainListJavaBean();
            mainListJavaBeenYWS.setMainJavaBeanArrayList(playListMainJavaBeanArrayListYWS);
            mainListJavaBeenYWS.setCoverName("押尾桑教程合集");
            mainListJavaBeenList.add(mainListJavaBeenYWS);

            //郑成河教程集合
            ArrayList<PlayListMainJavaBean> playListMainJavaBeanArrayListZCH = new ArrayList<>();
            PlayListMainJavaBean playListMainJavaBeanZCH00 = new PlayListMainJavaBean();
            playListMainJavaBeanZCH00.setCoverName("郑成河指弹第一期【River flows in you】");
            playListMainJavaBeanZCH00.setVideoCover(R.mipmap.zhengchenghe_00);

            PlayListMainJavaBean playListMainJavaBeanZCH01 = new PlayListMainJavaBean();
            playListMainJavaBeanZCH01.setCoverName("66小节-结束部分在腾讯课堂搜索Beatit");
            playListMainJavaBeanZCH01.setVideoCover(R.mipmap.zhengchenghe_01);

            PlayListMainJavaBean playListMainJavaBeanZCH02 = new PlayListMainJavaBean();
            playListMainJavaBeanZCH02.setCoverName("交替拨弦部分在腾讯课堂搜索加勒比海盗");
            playListMainJavaBeanZCH02.setVideoCover(R.mipmap.zhengchenghe_02);

            VideoListJavaBean videoListJavaBeanZCH0_00 = new VideoListJavaBean();
            videoListJavaBeanZCH0_00.setUrl(ConstantUtil.urlTwentyEight);
            videoListJavaBeanZCH0_00.setVideoCover(R.mipmap.zhengchenghe_00);

            VideoListJavaBean videoListJavaBeanZCH0_01 = new VideoListJavaBean();
            videoListJavaBeanZCH0_01.setUrl(ConstantUtil.urlTwentyNine);
            videoListJavaBeanZCH0_01.setVideoCover(R.mipmap.zhengchenghe_00);

            VideoListJavaBean videoListJavaBeanZCH0_02 = new VideoListJavaBean();
            videoListJavaBeanZCH0_02.setUrl(ConstantUtil.urlThirty);
            videoListJavaBeanZCH0_02.setVideoCover(R.mipmap.zhengchenghe_00);

            ArrayList<VideoListJavaBean> videoListJavaBeanArrayListZCH0_00 = new ArrayList<>();
            videoListJavaBeanArrayListZCH0_00.add(videoListJavaBeanZCH0_00);
            videoListJavaBeanArrayListZCH0_00.add(videoListJavaBeanZCH0_01);
            videoListJavaBeanArrayListZCH0_00.add(videoListJavaBeanZCH0_02);

            playListMainJavaBeanZCH00.setVideoListJavaBeen(videoListJavaBeanArrayListZCH0_00);
            playListMainJavaBeanArrayListZCH.add(playListMainJavaBeanZCH00);


            VideoListJavaBean videoListJavaBeanZCH1_00 = new VideoListJavaBean();
            videoListJavaBeanZCH1_00.setUrl(ConstantUtil.urlThirtyOne);
            videoListJavaBeanZCH1_00.setVideoCover(R.mipmap.zhengchenghe_01);

            VideoListJavaBean videoListJavaBeanZCH1_01 = new VideoListJavaBean();
            videoListJavaBeanZCH1_01.setUrl(ConstantUtil.urlThirtyTwo);
            videoListJavaBeanZCH1_01.setVideoCover(R.mipmap.zhengchenghe_01);

            VideoListJavaBean videoListJavaBeanZCH1_02 = new VideoListJavaBean();
            videoListJavaBeanZCH1_02.setUrl(ConstantUtil.urlThirtyThree);
            videoListJavaBeanZCH1_02.setVideoCover(R.mipmap.zhengchenghe_01);

            ArrayList<VideoListJavaBean> videoListJavaBeanArrayListZCH1_00 = new ArrayList<>();
            videoListJavaBeanArrayListZCH1_00.add(videoListJavaBeanZCH1_00);
            videoListJavaBeanArrayListZCH1_00.add(videoListJavaBeanZCH1_01);
            videoListJavaBeanArrayListZCH1_00.add(videoListJavaBeanZCH1_02);

            playListMainJavaBeanZCH01.setVideoListJavaBeen(videoListJavaBeanArrayListZCH1_00);
            playListMainJavaBeanArrayListZCH.add(playListMainJavaBeanZCH01);


            VideoListJavaBean videoListJavaBeanZCH2_00 = new VideoListJavaBean();
            videoListJavaBeanZCH2_00.setUrl(ConstantUtil.urlThirtyFour);
            videoListJavaBeanZCH2_00.setVideoCover(R.mipmap.zhengchenghe_02);

            VideoListJavaBean videoListJavaBeanZCH2_02 = new VideoListJavaBean();
            videoListJavaBeanZCH2_02.setUrl(ConstantUtil.urlThirtyFive);
            videoListJavaBeanZCH2_02.setVideoCover(R.mipmap.zhengchenghe_02);

            ArrayList<VideoListJavaBean> videoListJavaBeanArrayListZCH2_00 = new ArrayList<>();
            videoListJavaBeanArrayListZCH2_00.add(videoListJavaBeanZCH2_00);
            videoListJavaBeanArrayListZCH2_00.add(videoListJavaBeanZCH2_02);

            playListMainJavaBeanZCH02.setVideoListJavaBeen(videoListJavaBeanArrayListZCH2_00);
            playListMainJavaBeanArrayListZCH.add(playListMainJavaBeanZCH02);


            MainListJavaBean mainListJavaBeenZCH = new MainListJavaBean();
            mainListJavaBeenZCH.setMainJavaBeanArrayList(playListMainJavaBeanArrayListZCH);
            mainListJavaBeenZCH.setCoverName("郑成河教程合集");
            mainListJavaBeenList.add(mainListJavaBeenZCH);

        }

        bottomListJavaBeanList = new ArrayList<>();
        bottomImageUrls = new ArrayList<>();
        bottomImageUrls.add(R.mipmap.rumen);
        bottomImageUrls.add(R.mipmap.xiaobai);
        bottomImageUrls.add(R.mipmap.jinjie);
        bottomImageUrls.add(R.mipmap.dashen);

        for (int i = 0; i < bottomImageUrls.size(); i++) {
            VideoBottomBean temp = new VideoBottomBean();
            temp.setImageUrl(bottomImageUrls.get(i));
            bottomListJavaBeanList.add(temp);
        }
        Gson gson = new Gson();
        String a = gson.toJson(bottomListJavaBeanList);
        LogUtil.d("logoo", a);
        String b = gson.toJson(mainListJavaBeenList);
        MyLogUtil.LogShitou("logoo", b);

        PrompUtil.stopProgressDialog("加载中...");
    }

    /**
     * 初始化页面
     */
    private void initView() {
        tv_tittle = (TextView) mActivity.findViewById(R.id.tv_top_title);
        tv_tittle.setText("视频");

        button = (ImageButton) mActivity.findViewById(R.id.btn_back);
        button.setVisibility(View.GONE);

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

        for (int i = 0; i < videoList.size(); i++) {
//            DefaultSliderView：只有图片，没有文字描述
//            TextSliderView：有图片，有文字描述
            TextSliderView textSliderView = new TextSliderView(MyApplication.getContext());
            textSliderView.image(videoCover.get(i));
            textSliderView.description(videoList.get(i));
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
        sliderView.stopAutoCycle();
    }

    @Override
    protected void lazyLoad() {
        initDatas();
        initView();
    }
}
