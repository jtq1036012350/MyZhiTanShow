package com.iguitar.xiaoxiaozhitan.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iguitar.xiaoxiaozhitan.R;
import com.iguitar.xiaoxiaozhitan.api.ApiInerface;
import com.iguitar.xiaoxiaozhitan.databinding.ActivityNetworkBinding;
import com.iguitar.xiaoxiaozhitan.ui.base.BaseActivity;
import com.iguitar.xiaoxiaozhitan.ui.view.ClearEditText;
import com.iguitar.xiaoxiaozhitan.utils.CommonUtil;
import com.iguitar.xiaoxiaozhitan.utils.LogUtil;
import com.iguitar.xiaoxiaozhitan.utils.PrompUtil;
import com.iguitar.xiaoxiaozhitan.utils.StringUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * description: 网络设置
 * autour: zhuoxl
 * date: 2017/6/19 15:13
 * update: 2017/6/19
 * version:
 */
public class NetworkActivity extends BaseActivity {

    private ActivityNetworkBinding binding;

    private StringBuffer ipAddress;

    private String ip;
    private String port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_network);
        ((TextView)findViewById(R.id.tv_top_title)).setText("网络设置");
        String ipPort = CommonUtil.getIP(this);


        //加载图片
//        String url = "http://" + CommonUtil.getIP(NetworkActivity.this) + Constant.IMAGE_NETWORK_LOGIN;
        Glide.with(NetworkActivity.this)
                .load(R.mipmap.myicon)
//                .placeholder(R.mipmap.myicon)
                .dontAnimate()
                .into(binding.imgIcon);

        if (!TextUtils.isEmpty(ipPort)) {
            if (ipPort.contains(":")) {

                ip = ipPort.substring(0, ipPort.indexOf(":"));
                port = ipPort.substring(ipPort.indexOf(":") + 1, ipPort.length());
                binding.editIp.setText(ip);
                binding.editPort.setText(port);
            } else {
                ip = ipPort;
                binding.editIp.setText(ip);
                binding.editPort.setText("");
            }

            if (!"".equals(binding.editIp.getText().toString().trim())) {
                binding.editIp.setSelection(binding.editIp.getText().toString().trim().length());
            }

            if (!"".equals(binding.editPort.getText().toString().trim())) {
                binding.editPort.setSelection(binding.editPort.getText().toString().trim().length());
            }
        }

        //IP地址的变更监听
        binding.editIp.setOnChangeListener(new ClearEditText.OnChangeCallBack() {
            @Override
            public void changeResult() {
                String ipPort = CommonUtil.getIP(NetworkActivity.this);
                if (!ipPort.equals(binding.editIp.getText().toString().trim())
                        && !"".equals(binding.editPort.getText().toString().trim())) {
//                    binding.editPort.setText("");
                }
            }
        });
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equals(binding.editIp.getText().toString().trim())) {
                    CommonUtil.showToast(getApplicationContext(), "请输入IP地址！");
                    return;
                } else if ("".equals(binding.editPort.getText().toString().trim())) {
                    CommonUtil.showToast(getApplicationContext(), "请输入端口号！");
                    return;
                }

                getAddress();
                onTestIP(ipAddress.toString());
            }
        });

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToLogin();
                finish();
            }
        });
    }

    /**
     * 网络测试
     *
     * @param ip ip地址
     */
    private void onTestIP(final String ip) {
        String url = "http://" + ip + "/XiaoXiao/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build();
        ApiInerface userBiz = retrofit.create(ApiInerface.class);
        Call<Object> call = userBiz.getVersionReturn();
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
                    CommonUtil.saveIp(NetworkActivity.this, ipAddress.toString());
                    CommonUtil.saveFirstLogin(NetworkActivity.this, true);
                    CommonUtil.showToast(NetworkActivity.this, "网络连接成功！");
                    backToLogin();
                    finish();
                } else {
                    CommonUtil.showTopToast(NetworkActivity.this, "网络连接失败！");
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                PrompUtil.stopProgressDialog("");
                CommonUtil.showTopToast(NetworkActivity.this, "网络连接失败！");
                LogUtil.e("infoooo", "normalGet:" + t.toString() + "");
            }
        });
    }


    public void getAddress() {
        ipAddress = new StringBuffer();
        String ip = binding.editIp.getText().toString().trim();
        String port = binding.editPort.getText().toString().trim();
        if (!ip.equals("")) {
            if (!StringUtils.isIp(ip)) {
                CommonUtil.showToast(getApplicationContext(), "IP地址地址格式不正确！");
                return;
            }
            ipAddress.append(ip.trim());
            if (!port.equals("")) {
                ipAddress.append(":" + port.trim());
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { //按下的如果是BACK，同时没有重复
            backToLogin();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void backToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}
