package com.iguitar.xiaoxiaozhitan.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.iguitar.xiaoxiaozhitan.R;
import com.iguitar.xiaoxiaozhitan.api.ApiInerface;
import com.iguitar.xiaoxiaozhitan.databinding.FragmentStudyBinding;
import com.iguitar.xiaoxiaozhitan.model.StudyJavaBean;
import com.iguitar.xiaoxiaozhitan.ui.activity.MyWebViewActivity;
import com.iguitar.xiaoxiaozhitan.ui.adapter.FragmentStudyAdapter;
import com.iguitar.xiaoxiaozhitan.ui.base.BaseFragment;
import com.iguitar.xiaoxiaozhitan.utils.CommonUtil;
import com.iguitar.xiaoxiaozhitan.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 学习平台Fragment
 * Created by Jiang on 2017/4/13.
 */

public class StudyPlatformFragment extends BaseFragment {
    private FragmentStudyBinding binding;
    private List<StudyJavaBean> studyJavaBeanArrayList;
    private ArrayList<String> title;
    private ArrayList<String> urls;
    private ArrayList<String> description;
    private ArrayList<Integer> images;
    private ArrayList<Integer> isMore;
    private FragmentStudyAdapter adapter;
    private TextView tv_tittle;
    private ImageButton button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = View.inflate(getActivity(), R.layout.fragment_study, null);
//        return view;
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_study, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lazyLoad();
    }

    @Override
    public void onResume() {
        super.onResume();
        initTitle();
    }

    public void refreshStudyPlatform(){
        getDataFromServer();
    }
    /**
     * 初始化页面
     */
    private void initView() {
        //相关下拉刷新的设置
        binding.mRefreshView.setPullLoadEnable(false);
        binding.mRefreshView.setAutoLoadMore(false);
//        mRefreshView.setAutoRefresh(true);

        binding.mRefreshView.setXRefreshViewListener(new XRefreshView.XRefreshViewListener() {
            @Override
            public void onRefresh() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        binding.mRefreshView.stopRefresh();
//                    }
//                }, 1000);
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

        adapter = new FragmentStudyAdapter(mActivity, studyJavaBeanArrayList);
        binding.fragStudyLv.setAdapter(adapter);

        binding.fragStudyLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!"".equals(studyJavaBeanArrayList.get(i).getUrl())) {
                    Bundle bundle = new Bundle();
                    bundle.putString("url", studyJavaBeanArrayList.get(i).getUrl());
                    bundle.putString("title", studyJavaBeanArrayList.get(i).getTitle());
                    startMyActivity(MyWebViewActivity.class, bundle);
                }
            }
        });
    }

    private void initTitle() {
        tv_tittle = (TextView) mActivity.findViewById(R.id.tv_top_title);
        tv_tittle.setText("学习平台");

        button = (ImageButton) mActivity.findViewById(R.id.btn_back);
        button.setVisibility(View.GONE);
    }

    @Override
    protected void lazyLoad() {
//                initDatas();
//        initView();
        if (studyJavaBeanArrayList == null) {
            getDataFromServer();
        } else {
            initView();
        }

    }


    public void getDataFromServer() {
        binding.mRefreshView.startRefresh();
        ApiInerface studyInfo = retrofit.create(ApiInerface.class);
        Call<List<StudyJavaBean>> call = studyInfo.getStudyInfo();
        call.enqueue(new Callback<List<StudyJavaBean>>() {
            @Override
            public void onResponse(Call<List<StudyJavaBean>> call, Response<List<StudyJavaBean>> response) {
                if (response.isSuccessful()) {
                    binding.mRefreshView.stopRefresh();
                    studyJavaBeanArrayList = response.body();
                    initView();
                    LogUtil.e("infoooo", "normalGet:" + response.body() + "");
                }
            }

            @Override
            public void onFailure(Call<List<StudyJavaBean>> call, Throwable t) {
                binding.mRefreshView.stopRefresh();
                CommonUtil.showTopToast(mActivity, "获取学习模块数据失败！");
                LogUtil.e("infoooo", "normalGet:" + t.toString() + "");
            }
        });
    }
}
