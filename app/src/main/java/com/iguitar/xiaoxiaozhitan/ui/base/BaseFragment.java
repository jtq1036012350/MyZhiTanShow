package com.iguitar.xiaoxiaozhitan.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.iguitar.xiaoxiaozhitan.R;
import com.iguitar.xiaoxiaozhitan.utils.CommonUtil;
import com.iguitar.xiaoxiaozhitan.utils.ConstantUtil;

import org.greenrobot.eventbus.EventBus;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Description: Fragment的基类
 * Author: Jiang
 * Date:   2017/1/17
 */
public abstract class BaseFragment extends Fragment {
    public BaseActivity.ForResultCallBack forResultCallBack;
    protected Activity mActivity;
    protected boolean isVisible;
    public Retrofit retrofit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        String ipPortString = CommonUtil.getIP(mActivity);
        String url = "http://"+ipPortString+"/XiaoXiao/";
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build();
        EventBus.getDefault().register(this);
    }


    public interface ForResultCallBack {
        public void forResult(Intent data);
    }
    /**
     * 提供统一的关闭方式
     * <p>
     * （另一个Activity）
     */
    public void finishMyActivity() {
        mActivity.finish();
        mActivity.overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
    }

    /**
     * 提供统一的启动方式
     *
     * @param cls，intent（另一个Activity，intent携带的数据）
     */
    public void startMyActivity(Class<?> cls, Bundle mBundle) {
        Intent intent = new Intent(mActivity, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (mBundle != null) {
            intent.putExtras(mBundle);
        }
        mActivity.startActivity(intent);
        mActivity.overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
    }

    //封装startActivityForResult
    public void startMyActivityForResult(Class<?> cls, Bundle bundle, BaseActivity.ForResultCallBack forResultCallBack) {
        this.forResultCallBack = forResultCallBack;
        Intent intent = new Intent(mActivity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, ConstantUtil.FORRESULT);
        mActivity.overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
    }

    //封装startActivityForResult(这里为计量任务准备的，那里不知道为何会调用两次onSuccessScan)
    public void startMyActivityForResultClearTop(Class<?> cls, Bundle bundle, BaseActivity.ForResultCallBack forResultCallBack) {
        this.forResultCallBack = forResultCallBack;
        Intent intent = new Intent(mActivity, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, ConstantUtil.FORRESULT);
        mActivity.overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
    }

    //将数据传递给回调方法
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case ConstantUtil.FORRESULT:
                forResultCallBack.forResult(data);
                break;
        }
    }
    /**
     * 在这里实现Fragment数据的缓加载.
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible(){
        lazyLoad();
    }

    protected abstract void lazyLoad();

    protected void onInvisible(){}

    /**
     * 获取okhttp拦截器
     *
     * @return
     */
    public OkHttpClient getOkHttpClient() {
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("zcb", "OkHttp====Message:" + message);
            }
        });
        loggingInterceptor.setLevel(level);
        //定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient
                .Builder();
        //OkHttp进行添加拦截器loggingInterceptor
        httpClientBuilder.addInterceptor(loggingInterceptor);
        return httpClientBuilder.build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
