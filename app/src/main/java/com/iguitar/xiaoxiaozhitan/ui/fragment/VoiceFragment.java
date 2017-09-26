package com.iguitar.xiaoxiaozhitan.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andview.refreshview.XRefreshView;
import com.iguitar.xiaoxiaozhitan.MyApplication;
import com.iguitar.xiaoxiaozhitan.R;
import com.iguitar.xiaoxiaozhitan.api.ApiInerface;
import com.iguitar.xiaoxiaozhitan.databinding.FragmentVoiceBinding;
import com.iguitar.xiaoxiaozhitan.model.Conversation;
import com.iguitar.xiaoxiaozhitan.model.MessageEvent;
import com.iguitar.xiaoxiaozhitan.model.MyConversionBean;
import com.iguitar.xiaoxiaozhitan.model.VoiceUtils;
import com.iguitar.xiaoxiaozhitan.ui.adapter.MyRecognizerDialogListener;
import com.iguitar.xiaoxiaozhitan.ui.adapter.MyVoiceAdapter;
import com.iguitar.xiaoxiaozhitan.ui.base.BaseFragment;
import com.iguitar.xiaoxiaozhitan.utils.CommonUtil;
import com.iguitar.xiaoxiaozhitan.utils.LogUtil;
import com.iguitar.xiaoxiaozhitan.utils.PrompUtil;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 直播Fragment
 * Created by Jiang on 2017/4/13.
 */

public class VoiceFragment extends BaseFragment {
    private FragmentVoiceBinding binding;
    private VoiceUtils voiceUtils;
    private List<Conversation> listData;
    private List<MyConversionBean> myConversionBeanList;
    private MyVoiceAdapter adapter;
//    private OnSelectVoiceFragmentListener onSelectVoiceFragmentListener;

//    private TextView tv_top_title;
//    private ImageButton btn_back;

//    public interface OnSelectVoiceFragmentListener {
//        void onSeleceVoiceFragment();
//    }

//    public void setOnSelectVoiceFragmentListener(OnSelectVoiceFragmentListener onSelectVoiceFragmentListener) {
//        this.onSelectVoiceFragmentListener = onSelectVoiceFragmentListener;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listData = (List<Conversation>) MyApplication.getMap("voice");
        if (listData == null) {
            listData = new ArrayList<>();
        }
    }

    // This method will be called when a MessageEvent is posted
    @Subscribe
    public void onMessageEvent(MessageEvent event) {
//        Toast.makeText(getActivity(), event.message+"aa", Toast.LENGTH_SHORT).show();
        if (4 == event.getIndex()) {
            onGetDataStrinngs();
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_voice, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lazyLoad();
    }


    @Override
    protected void lazyLoad() {
        initDatas();
        initViews();
    }

    /**
     * 初始化界面
     */
    private void initViews() {
        binding.btnSpesk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voiceUtils.listen(new MyRecognizerDialogListener(mActivity,listData, binding.lvVoice, adapter, voiceUtils, myConversionBeanList));
            }
        });

        //相关下拉刷新的设置
        binding.mRefreshView.setPullLoadEnable(false);
        binding.mRefreshView.setAutoLoadMore(false);
//        mRefreshView.setAutoRefresh(true);

        binding.mRefreshView.setXRefreshViewListener(new XRefreshView.XRefreshViewListener() {
            @Override
            public void onRefresh() {
                onGetDataStrinngs();
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
        onGetDataStrinngs();
        voiceUtils = new VoiceUtils(mActivity);
        adapter = new MyVoiceAdapter(mActivity, listData);
        binding.lvVoice.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void scrollToBottom() {
        if (adapter != null) {
//            int a = CommonUtil.getTotalHeightofListView(binding.lvVoice);
//            binding.lvVoice.scrollBy(0,CommonUtil.getTotalHeightofListView(binding.lvVoice));
            binding.lvVoice.setSelection(binding.lvVoice.getBottom());
        }
    }

    /**
     * 查看版本信息
     */
    private void onGetDataStrinngs() {
        binding.mRefreshView.startRefresh();
        PrompUtil.startProgressDialog(mActivity, "加载中");
        ApiInerface userBiz = retrofit.create(ApiInerface.class);
        Call<List<MyConversionBean>> call = userBiz.getVoiceReturn();
        call.enqueue(new Callback<List<MyConversionBean>>() {
            @Override
            public void onResponse(Call<List<MyConversionBean>> call, Response<List<MyConversionBean>> response) {
                PrompUtil.stopProgressDialog("");
                binding.mRefreshView.stopRefresh();
                if (response.isSuccessful()) {
                    LogUtil.e("infoooo", "normalGet:" + response.body() + "");
                    myConversionBeanList = response.body();
                    adapter.notifyDataSetChanged();;
                } else {
                    LogUtil.e("infoooo", "normalGet:" + response.body() + "");
                }
            }

            @Override
            public void onFailure(Call<List<MyConversionBean>> call, Throwable t) {
                PrompUtil.stopProgressDialog("");
                binding.mRefreshView.stopRefresh();
                CommonUtil.showTopToast(mActivity, "获取数据失败！");
                LogUtil.e("infoooo", "normalGet:" + t.toString() + "");
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        MyApplication.putMap("voice", listData);
    }
}
