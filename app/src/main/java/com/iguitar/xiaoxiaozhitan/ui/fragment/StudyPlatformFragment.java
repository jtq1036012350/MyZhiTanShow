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
import com.google.gson.Gson;
import com.iguitar.xiaoxiaozhitan.R;
import com.iguitar.xiaoxiaozhitan.api.ApiInerface;
import com.iguitar.xiaoxiaozhitan.databinding.FragmentStudyBinding;
import com.iguitar.xiaoxiaozhitan.model.StudyJavaBean;
import com.iguitar.xiaoxiaozhitan.ui.activity.MyWebViewActivity;
import com.iguitar.xiaoxiaozhitan.ui.adapter.FragmentStudyAdapter;
import com.iguitar.xiaoxiaozhitan.ui.base.BaseFragment;
import com.iguitar.xiaoxiaozhitan.utils.ConstantUtil;
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

    /**
     * 初始化页面
     */
    private void initView() {
        tv_tittle = (TextView) mActivity.findViewById(R.id.tv_top_title);
        tv_tittle.setText("学习平台");

        button = (ImageButton) mActivity.findViewById(R.id.btn_back);
        button.setVisibility(View.GONE);
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
                if (!"".equals(urls.get(i))) {
                    Bundle bundle = new Bundle();
                    bundle.putString("url", urls.get(i));
                    bundle.putString("title", title.get(i));
                    startMyActivity(MyWebViewActivity.class, bundle);
                }
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initDatas() {
        studyJavaBeanArrayList = new ArrayList<>();
        title = new ArrayList<>();
        urls = new ArrayList<>();
        description = new ArrayList<>();
        images = new ArrayList<>();
        isMore = new ArrayList<>();

        title.add("优酷频道");
        title.add("腾讯课堂");
        title.add("百度传课");
        title.add("张弛小小指弹");
        title.add("指弹南通");
        title.add("快手");
        title.add("熊猫直播");

        images.add(R.mipmap.youku);
        images.add(R.mipmap.tengxunketang);
        images.add(R.mipmap.baiduchuanke);
        images.add(R.mipmap.xinlangweibo);
        images.add(R.mipmap.baidutieba);
        images.add(R.mipmap.kuaishou);
        images.add(R.mipmap.xiongmao);

        urls.add(ConstantUtil.youkuUrl);
        urls.add(ConstantUtil.tencentUrl);
        urls.add(ConstantUtil.baiduchuankeUrl);
        urls.add("");
        urls.add(ConstantUtil.nantongUrl);
        urls.add(ConstantUtil.kuaishouUrl);
        urls.add(ConstantUtil.pandaUrl);

        description.add("http://i.youku.com/xxzhitan");
        description.add("http://xxzt.ke.qq.com");
        description.add("http://www.chuanke.com/s4522102.html");
        description.add("指弹微博");
        description.add("指弹南通");

        description.add("指弹微博");
        description.add("指弹南通");

        isMore.add(1);
        isMore.add(1);
        isMore.add(1);
        isMore.add(0);
        isMore.add(0);
        isMore.add(0);
        isMore.add(0);

        for (int i = 0; i < 7; i++) {
            StudyJavaBean temp = new StudyJavaBean();
            temp.setTitle(title.get(i));
            temp.setDescription(description.get(i));
            temp.setIsMore(isMore.get(i));
            temp.setImage(images.get(i));
            studyJavaBeanArrayList.add(temp);
        }
        Gson gson = new Gson();
        String a = gson.toJson(studyJavaBeanArrayList);
        LogUtil.d("logoo", a);

    }

    @Override
    protected void lazyLoad() {
//        initDatas();
        getDataFromServer();
//        initView();
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
                LogUtil.e("infoooo", "normalGet:" + t.toString() + "");
            }
        });
    }
}
