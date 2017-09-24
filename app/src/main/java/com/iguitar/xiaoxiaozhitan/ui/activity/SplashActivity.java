package com.iguitar.xiaoxiaozhitan.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.iguitar.xiaoxiaozhitan.R;
import com.iguitar.xiaoxiaozhitan.api.ApiInerface;
import com.iguitar.xiaoxiaozhitan.model.UserInfo;
import com.iguitar.xiaoxiaozhitan.model.VersionInfo;
import com.iguitar.xiaoxiaozhitan.ui.base.BaseActivity;
import com.iguitar.xiaoxiaozhitan.utils.CommonUtil;
import com.iguitar.xiaoxiaozhitan.utils.ConstantUtil;
import com.iguitar.xiaoxiaozhitan.utils.LogUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.attr.data;

/**
 * 闪屏页面
 */
public class SplashActivity extends BaseActivity {
    private LinearLayout ll_welcome_bg;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        init();
    }

    /**
     * 初始化动画
     */
    private void init() {
        ll_welcome_bg = (LinearLayout) findViewById(R.id.ll_welcome_bg);

        // 旋转动画，0 ~ 360
        RotateAnimation rotateAnimation = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setFillAfter(true);

        // 缩放动画，从无到有
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                0, 1,
                0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setFillAfter(true);

        // 渐变动画，从无到有
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setFillAfter(true);

        // 创建动画集合
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(scaleAnimation);

        ll_welcome_bg.startAnimation(animationSet);

        // 监听动画
        animationSet.setAnimationListener(new MyAnimationListener());
    }

    class MyAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    String url = CommonUtil.getIP(SplashActivity.this);
                    onOperation();
                }
            }, 1000);

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

    }

    /**
     * 查看版本信息
     */
    private void onGetVersion() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUtil.internretUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build();
        ApiInerface userBiz = retrofit.create(ApiInerface.class);
        Call<Object> call = userBiz.getVersionReturn();
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
                    LogUtil.e("infoooo", "normalGet:" + response.body() + "");
                    Gson gson = new Gson();
                    VersionInfo versionInfo = gson.fromJson(response.body().toString(), VersionInfo.class);

                    //版本比对
                    if (Double.parseDouble(CommonUtil.getAPPVersion(SplashActivity.this)) < Integer.parseInt(versionInfo.getVersion())) {
                        onOperation();
                    } else {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class)
                                .putExtra("isUpdate", true).putExtra("version", data));
                        finish();
                    }
                } else {
                    LogUtil.e("infoooo", "normalGet:" + response.body() + "");
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                onOperation();
                LogUtil.e("infoooo", "normalGet:" + t.toString() + "");
            }
        });
    }

    private void onAutoLogin() {
//        PrompUtil.startProgressDialog(this, "加载中···");
//        ApiManager.Api api = apiCoreManager.getUserInfo(info.getUserName(), info.getPsw());
//        api.invoke(new NetworkEngine.Success<UserInfo>() {
//            @Override
//            public void callback(UserInfo data) {
//                if (data != null && !TextUtils.isEmpty(data.getLoginname())) {
//                    ApplicationUtil.put(WelcomeActivity.this, "UserInfo", data);
//                    startActivity(new Intent(WelcomeActivity.this, ChooseDepartmentActivity.class));
//                    finish();
//                } else {
//                    onLogin();
//                }
//            }
//        }, new NetworkEngine.Failure() {
//            @Override
//            public void callback(int i, String message, Map map) {
//                onLogin();
//                CommonUtil.showToast(WelcomeActivity.this, message);
//                PrompUtil.stopProgressDialog("");
//            }
//        }, new VolleyEngine.DefaultError(getApplicationContext()) {
//            @Override
//            public void callback(int code, String message, Map rawData) {
//                super.callback(code, message, rawData);
//                onLogin();
//                PrompUtil.stopProgressDialog("");
//            }
//        });
    }

    private void onOperation() {
        boolean IsFirst = CommonUtil.IsFirstLogin(SplashActivity.this);
        String ip = CommonUtil.getIP(SplashActivity.this);
        if (IsFirst && !TextUtils.isEmpty(ip)) {
            userInfo = CommonUtil.getLoginInfo(SplashActivity.this);
            if (userInfo != null && userInfo.isAuto() && !TextUtils.isEmpty(userInfo.getName())) {
                onAutoLogin();
            } else {
                onLogin();
            }
        } else {
            onConfig();
        }
    }

    private void onLogin() {
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();
    }

    private void onConfig() {
        startMyActivity(GuideActivity.class, null);
        finish();
    }

}
