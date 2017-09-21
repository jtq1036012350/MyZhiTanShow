package com.iguitar.xiaoxiaozhitan.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.iguitar.xiaoxiaozhitan.ui.activity.MyWebViewActivity;
import com.iguitar.xiaoxiaozhitan.R;
import com.iguitar.xiaoxiaozhitan.databinding.FragmentLiveBinding;
import com.iguitar.xiaoxiaozhitan.model.LiveJavaBean;
import com.iguitar.xiaoxiaozhitan.ui.adapter.FragmentLiveAdapter;
import com.iguitar.xiaoxiaozhitan.ui.base.BaseFragment;
import com.iguitar.xiaoxiaozhitan.utils.ConstantUtil;

import java.util.ArrayList;

/**
 * 直播Fragment
 * Created by Jiang on 2017/4/13.
 */

public class LiveFragment extends BaseFragment {
    private FragmentLiveBinding binding;
    private ArrayList<LiveJavaBean> liveJavaBeanArrayList;
    private ArrayList<String> title;
    private ArrayList<String> description;
    private ArrayList<String> images;
    private ArrayList<String> urls;
    private FragmentLiveAdapter adapter;

    private TextView tv_tittle;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_live, container, false);
        return binding.getRoot();

//        View view = View.inflate(getActivity(), R.layout.fragment_live, null);
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
        tv_tittle = (TextView) mActivity.findViewById(R.id.tv_top_title);
        tv_tittle.setText("直播平台");

        adapter = new FragmentLiveAdapter(mActivity, liveJavaBeanArrayList);
        binding.fragentLiveLv.setAdapter(adapter);

        binding.fragentLiveLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("url",urls.get(i));
                bundle.putString("title",title.get(i));
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

    /**
     * 初始化数据
     */
    private void initDatas() {
        liveJavaBeanArrayList = new ArrayList<>();
        title = new ArrayList<>();
        description = new ArrayList<>();
        images = new ArrayList<>();

        title.add("快手");
        title.add("熊猫直播");

        description.add("49466328");
        description.add("264305");

        urls = new ArrayList<>();
        urls.add(ConstantUtil.kuaishouUrl);
        urls.add(ConstantUtil.pandaUrl);

        for (int i = 0; i < 2; i++) {
            LiveJavaBean temp = new LiveJavaBean();
            temp.setTitle(title.get(i));
            temp.setDescription(description.get(i));
            temp.setUrl(urls.get(i));
            liveJavaBeanArrayList.add(temp);
        }


    }

    @Override
    protected void lazyLoad() {
        initDatas();
        initView();
    }
}
