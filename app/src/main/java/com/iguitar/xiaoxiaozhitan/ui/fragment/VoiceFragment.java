package com.iguitar.xiaoxiaozhitan.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.iguitar.xiaoxiaozhitan.MyApplication;
import com.iguitar.xiaoxiaozhitan.R;
import com.iguitar.xiaoxiaozhitan.databinding.FragmentVoiceBinding;
import com.iguitar.xiaoxiaozhitan.model.Conversation;
import com.iguitar.xiaoxiaozhitan.model.VoiceUtils;
import com.iguitar.xiaoxiaozhitan.ui.adapter.MyRecognizerDialogListener;
import com.iguitar.xiaoxiaozhitan.ui.adapter.MyVoiceAdapter;
import com.iguitar.xiaoxiaozhitan.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 直播Fragment
 * Created by Jiang on 2017/4/13.
 */

public class VoiceFragment extends BaseFragment {
    private FragmentVoiceBinding binding;
    private VoiceUtils voiceUtils;
    private List<Conversation> listData;
    private MyVoiceAdapter adapter;

    private TextView tv_top_title;
    private ImageButton btn_back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listData = (List<Conversation>) MyApplication.getMap("voice");
        if (listData == null) {
            listData = new ArrayList<>();
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

    private void initViews() {
        tv_top_title = (TextView) mActivity.findViewById(R.id.tv_top_title);
        btn_back = (ImageButton) mActivity.findViewById(R.id.btn_back);

        btn_back.setVisibility(View.GONE);
        tv_top_title.setText("语音机器人");

        binding.btnSpesk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voiceUtils.listen(new MyRecognizerDialogListener(listData, binding.lvVoice, adapter, voiceUtils));
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initDatas() {
        voiceUtils = new VoiceUtils(mActivity);
        adapter = new MyVoiceAdapter(mActivity, listData);
        binding.lvVoice.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyApplication.putMap("voice", listData);
    }
}
