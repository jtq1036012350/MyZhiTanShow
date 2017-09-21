package com.iguitar.xiaoxiaozhitan.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.iguitar.xiaoxiaozhitan.R;
import com.iguitar.xiaoxiaozhitan.databinding.ActivityMainBinding;
import com.iguitar.xiaoxiaozhitan.ui.base.BaseActivity;
import com.iguitar.xiaoxiaozhitan.ui.fragment.LiveFragment;
import com.iguitar.xiaoxiaozhitan.ui.fragment.PersonalFragment;
import com.iguitar.xiaoxiaozhitan.ui.fragment.StoreFragment;
import com.iguitar.xiaoxiaozhitan.ui.fragment.StudyPlatformFragment;
import com.iguitar.xiaoxiaozhitan.ui.fragment.VideoFragmentNew;
import com.iguitar.xiaoxiaozhitan.ui.fragment.VoiceFragment;
import com.iguitar.xiaoxiaozhitan.utils.CommonUtil;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    //用来存放主界面的Fragemnt
    private ArrayList<Fragment> fragments = null;
    //返回键按下的间隔时间
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        CommonUtil.showToast(getApplicationContext(), "登陆成功！");
        //初始化数据
        initDatas();
        //初始化布局
        initViews();
    }

    //初始化布局
    private void initViews() {
        int count = binding.mainBottomeSwitcherContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            FrameLayout childAt = (FrameLayout) binding.mainBottomeSwitcherContainer.getChildAt(i);
            childAt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = binding.mainBottomeSwitcherContainer.indexOfChild(view);
                    changeUI(index);
                    changeFragments(index);
                }


            });
        }
        binding.civVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments(2);
                changeUI(2);
            }
        });
    }



    //初始化数据
    private void initDatas() {
        fragments = new ArrayList<>();

        //添加Fragnment
        fragments.add(new StoreFragment());
        fragments.add(new StudyPlatformFragment());
        fragments.add(new VideoFragmentNew());
        fragments.add(new LiveFragment());
        fragments.add(new VoiceFragment());
        fragments.add(new PersonalFragment());

        changeFragments(2);
        //默认选择第一个条目
        setEnable(binding.mainBottomeSwitcherContainer.getChildAt(2), false);
    }

    //切换Fragment
    private void changeFragments(int index) {
        Fragment fragment = fragments.get(index);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_container, fragment)
                .commit();
    }

    //通过点击的时候来改变底部导航栏的UI
    private void changeUI(int index) {
//        ToastUtil.showText(MainActivity.this, index + "");
//        if (index == 2) {
//            binding.civVideo.setImageResource(R.mipmap.video_on);
//        } else {
//            binding.civVideo.setImageResource(R.mipmap.video_off);
//        }
        int childCount = binding.mainBottomeSwitcherContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (i == index) {
                setEnable(binding.mainBottomeSwitcherContainer.getChildAt(i), false);
            } else {
                setEnable(binding.mainBottomeSwitcherContainer.getChildAt(i), true);
            }
        }
    }


    /**
     * 将每个Item中的所用控件状态一同改变
     * 由于我们处理一个通用的代码，那么Item可能会有很多层，所以我们需要使用递归
     *
     * @param item
     * @param b
     */
    private void setEnable(View item, boolean b) {
        item.setEnabled(b);
        if (item instanceof ViewGroup) {
            int childCount = ((ViewGroup) item).getChildCount();
            for (int i = 0; i < childCount; i++) {
                setEnable(((ViewGroup) item).getChildAt(i), b);
            }
        }
    }

    //按下两次返回键退出应用
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Object mHelperUtils;
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();

            } else {
                finishMyActivity();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
