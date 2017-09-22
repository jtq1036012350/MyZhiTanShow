package com.iguitar.xiaoxiaozhitan.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

    Fragment current_fragment;

    private TextView tv_tittle;
    private ImageButton button;

    private RelativeLayout rl_title;

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
                    startFragmentAdd(index);
                }


            });
        }
//        binding.civVideo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startFragmentAdd(2);
//                changeUI(2);
//            }
//        });
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

        startFragmentAdd(2);
        //默认选择第一个条目
        setEnable(binding.mainBottomeSwitcherContainer.getChildAt(2), false);
    }

    // fragment的切换
    private void startFragmentAdd(int index) {
        switch (index) {
            case 0:
                initTitle("淘宝", false);
                break;
            case 1:
                initTitle("学习中心", true);
                break;
            case 2:
                initTitle("视频", true);
                break;
            case 3:
                initTitle("聊天机器人", true);
                break;
            case 4:
                initTitle("个人中心", true);
                break;
        }
        Fragment fragment = fragments.get(index);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        if (current_fragment == null) {
            fragmentTransaction.add(R.id.main_fragment_container, fragment).commit();
            current_fragment = fragment;
        }
        if (current_fragment != fragment) {
            // 先判断是否被add过
            if (!fragment.isAdded()) {
                // 隐藏当前的fragment，add下一个到Activity中
                fragmentTransaction.hide(current_fragment)
                        .add(R.id.main_fragment_container, fragment).commit();
            } else {
                // 隐藏当前的fragment，显示下一个
                fragmentTransaction.hide(current_fragment).show(fragment)
                        .commit();
            }
            current_fragment = fragment;
        }
    }

    //切换Fragment(会重新加载页面)
    private void changeFragments(int index) {
        Fragment fragment = fragments.get(index);
        if (fragments.get(index).isAdded()) {

        }
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

    /**
     * 修改标题统一方法
     *
     * @param title 标题
     * @param flag  true：展示布局 fals:隐藏布局
     */
    private void initTitle(String title, boolean flag) {
        tv_tittle = (TextView) findViewById(R.id.tv_top_title);
        tv_tittle.setText("视频");

        rl_title = (RelativeLayout) findViewById(R.id.rl_title);
        if (!flag) {
            rl_title.setVisibility(View.GONE);
        } else {
            rl_title.setVisibility(View.VISIBLE);
        }
        button = (ImageButton) findViewById(R.id.btn_back);
        button.setVisibility(View.GONE);
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
