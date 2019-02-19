package com.iguitar.xiaoxiaozhitan.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.iguitar.xiaoxiaozhitan.MyApplication;
import com.iguitar.xiaoxiaozhitan.R;
import com.iguitar.xiaoxiaozhitan.api.ApiInerface;
import com.iguitar.xiaoxiaozhitan.databinding.ActivityLoginBinding;
import com.iguitar.xiaoxiaozhitan.model.MessageEvent;
import com.iguitar.xiaoxiaozhitan.model.UserInfo;
import com.iguitar.xiaoxiaozhitan.model.UserReturnBean;
import com.iguitar.xiaoxiaozhitan.model.VersionInfo;
import com.iguitar.xiaoxiaozhitan.ui.base.BaseActivity;
import com.iguitar.xiaoxiaozhitan.ui.view.ClearEditText;
import com.iguitar.xiaoxiaozhitan.utils.CommonUtil;
import com.iguitar.xiaoxiaozhitan.utils.DownloadUtil;
import com.iguitar.xiaoxiaozhitan.utils.LogUtil;
import com.iguitar.xiaoxiaozhitan.utils.MDFiveUtil;
import com.iguitar.xiaoxiaozhitan.utils.PrompUtil;
import com.lidroid.xutils.ViewUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * description: 登录页面
 * autour: Jiang
 * date: 2017/6/19 15:13
 * update: 2017/6/19
 * version:
 */

public class LoginActivity extends BaseActivity {
    private ActivityLoginBinding binding;
    private UserInfo userInfo;
    private String username;
    private String password;
    private boolean isUpdate;
    private UserInfo loginInfo;
    //true : 可以跳转 false：不能跳转
    private boolean canStartActivity = false;
    //记录已经授权的权限数目
//    private int permissionCount;
    //PDA条目集合
//    private ArrayList<String> pdaItems;
    //已经被选中的PDA
//    private String pdaSelected;
    //权限状态（缓存）
    private boolean isPermissioned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
//        ViewUtil.initSystemBar(this, R.color.colorLoginBlue);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
//        startMyActivity(MyVideoActivity.class,null);
        isUpdate = getIntent().getBooleanExtra("isUpdate", false);
        loginInfo = CommonUtil.getLoginInfo(LoginActivity.this);
//        pdaItems = new ArrayList<>();
        //展示当前版本信息
        if (!TextUtils.isEmpty(CommonUtil.getAPPVersion(LoginActivity.this))) {
            binding.tvVersion.setText("版本:" + CommonUtil.getAPPVersion(LoginActivity.this));
        }
        //加载图片
//        String url = "http://" + CommonUtil.getIP(LoginActivity.this) + Constant.IMAGE_NETWORK_LOGIN;
        Glide.with(LoginActivity.this)
                .load(R.mipmap.myicon)
//                .placeholder(R.mipmap.winning)
                .dontAnimate()
                .into(binding.imgIcon);
        //六院评审修改
        getLoginInfo();
        initListener();
        isPermissioned = CommonUtil.getCurrentPermission(LoginActivity.this);

//        int maxMemory = ((int) Runtime.getRuntime().maxMemory()) / 1024 / 1024;
//        // 应用程序已获得内存
//        long totalMemory = ((int) Runtime.getRuntime().totalMemory()) / 1024 / 1024;
//        // 应用程序已获得内存中未使用内存
//        long freeMemory = ((int) Runtime.getRuntime().freeMemory()) / 1024 / 1024;
//        System.out.println("---> maxMemory=" + maxMemory + "M,totalMemory=" + totalMemory
//                + "M,freeMemory=" + freeMemory + "M");

        binding.editName.requestFocus();

        onGetVersion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        CommonUtil.showToast(getApplicationContext(), "登陆成功！");
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            String url = data.get("profile_image_url");
            String name = data.get("name");
            MyApplication.putMap("profile_image_url", data.get("profile_image_url"));
            MyApplication.putMap("name", data.get("name"));
            startMyActivity(MainActivity.class, null);
            finish();
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            CommonUtil.showToast(getApplicationContext(), "登录失败！");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
//            Toast.makeText(getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
//            "profile_image_url" -> "http://q.qlogo.cn/qqapp/1106427116/F5AB14CC5C87C401D1259CBCA64ECC0C/100"

        }
    };

    private void initListener() {
        binding.relQq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canStartActivity) {
                    UMShareAPI.get(LoginActivity.this).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                } else {
                    CommonUtil.showTopToast(LoginActivity.this, "网络配置有误，请检查！");
                }
            }
        });
        binding.relNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginSuccessGo();
            }
        });
        binding.tvNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginSuccessGo();
            }
        });
        binding.tvNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginSuccessGo();
            }
        });
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //实际的
//                if (checkInput()) {
//                    onLogin();
//                }
                if (isPermissioned) {
                    onLogin();
                } else {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                        onLogin();
                    } else {
                        if (!checkInput()) {
                            return;
                        }
                        requestPermission(null, 0, Permission.READ_PHONE_STATE, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE);
                    }
                }
            }
        });
        binding.chbSave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    //清空密码
                    loginInfo.setName("");
                    loginInfo.setPassword("");
                    loginInfo.setRember(false);
                    //六院评审修改
                    CommonUtil.saveLoginInfo(LoginActivity.this, loginInfo);
                }
            }
        });


        binding.editName.setOnCancelListener(new ClearEditText.OnCancelCallBack() {
            @Override
            public void cancelResult() {
                binding.editPass.setText("");
            }
        });

        binding.editName.setOnChangeListener(new ClearEditText.OnChangeCallBack() {
            @Override
            public void changeResult() {
                if (loginInfo != null) {
                    if (loginInfo.getName() != null) {
                        if (!loginInfo.getName()
                                .equals(binding.editName.getText().toString().trim()) && !""
                                .equals(binding.editPass.getText().toString().trim())) {
                            binding.editPass.setText("");
                        }
                    }
                }
            }
        });

        binding.editPass.setOnCancelListener(new ClearEditText.OnCancelCallBack() {
            @Override
            public void cancelResult() {
//                binding.editName.setText("");
            }
        });
    }

    private void onDownLoad(VersionInfo versionInfo) {
        DownloadUtil loadUtil = new DownloadUtil(this, versionInfo.getContent());
        String url;
        url = "http://" + CommonUtil.getIP(this) + "/XiaoXiao/Update/XiaoXiaoZhiTan.apk";
        LogUtil.d("urlInfo", url);
        loadUtil.StartDownload(url);
    }

    // This method will be called when a MessageEvent is posted
    @Override
    public void onMessageEvent(MessageEvent event) {
//        Toast.makeText(this, event.message + login, Toast.LENGTH_SHORT).show();
    }

    private void getLoginInfo() {

        UserInfo loginInfo = CommonUtil.getLoginInfo(LoginActivity.this);
        binding.editName.setText(loginInfo.getName());
        binding.editPass.setText(loginInfo.getPassword());
        //设置焦点位置
        binding.editName.setSelection(binding.editName.getText().length());
        binding.editPass.setSelection(binding.editPass.getText().length());

        if (loginInfo.isRember()) {
            binding.chbSave.setChecked(true);
        }
    }

    private boolean checkInput() {

        username = binding.editName.getText().toString();
        password = binding.editPass.getText().toString();

//        //测试
//        if (username.equals("00") && password.equals("")) {
//            onLoginSuccess();
//        }
        if (TextUtils.isEmpty(username)) {
            CommonUtil.showToast(this, "请输入用户名");
            return false;
        }
        return true;

    }

    /**
     * 查看版本信息
     */
    private void onGetVersion() {
        PrompUtil.startProgressDialog(this, "加载中");
        ApiInerface userBiz = retrofit.create(ApiInerface.class);
        Call<Object> call = userBiz.getVersionReturn();
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                PrompUtil.stopProgressDialog("加载中");
                canStartActivity = true;
                if (response.isSuccessful()) {
                    CommonUtil.showTopToast(LoginActivity.this, "网络连接成功！");
                    LogUtil.e("infoooo", "normalGet:" + response.body() + "");
                    Gson gson = new Gson();
                    final VersionInfo versionInfo = gson.fromJson(response.body().toString(), VersionInfo.class);
//                    double i= Double.parseDouble(CommonUtil.getAPPVersion(LoginActivity.this));
                    //版本比对
                    if (CommonUtil.getCode(LoginActivity.this) < Integer.parseInt(versionInfo.getVersion())) {
                        requestPermission(null, 1, Permission.READ_PHONE_STATE, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE);
                    }
                } else {
                    canStartActivity = false;
                    LogUtil.e("infoooo", "normalGet:" + response.body() + "");
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                PrompUtil.stopProgressDialog("");
                canStartActivity = false;
                CommonUtil.showTopToast(LoginActivity.this, "检查更新失败！");
                LogUtil.e("infoooo", "normalGet:" + t.toString() + "");
            }
        });
    }

    /**
     * 登陆
     */
    private void onLogin() {
        PrompUtil.startProgressDialog(this, "加载中···");
//        //修改成POST方式
        String userName = binding.editName.getText().toString().trim();
        String userPass = binding.editPass.getText().toString().trim();

        onCheckLogin(userName, userPass);
//        UserInfo userInfo = new UserInfo();
//        userInfo.setName("00");
//        userInfo.setPassword("123");
//        userInfo.setRember(true);
//        if (userName.equals("00") && userPass.equals("123")) {
//            if (binding.chbSave.isChecked()) {
//                CommonUtil.saveLoginInfo(this, userInfo);
//            }
//            startMyActivity(MainActivity.class, null);
//            finish();
//        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PrompUtil.stopProgressDialog("");
    }

    /**
     * 登录访问
     */
    private void onCheckLogin(final String userName, final String userPass) {
        PrompUtil.startProgressDialog(this, "加载中");
        ApiInerface userBiz = retrofit.create(ApiInerface.class);
        Call<UserReturnBean> call = userBiz.getLoginInfo();
        call.enqueue(new Callback<UserReturnBean>() {
            @Override
            public void onResponse(Call<UserReturnBean> call, Response<UserReturnBean> response) {
                PrompUtil.stopProgressDialog("加载中");
                if (response.isSuccessful()) {
                    UserReturnBean userReturnBean = response.body();
                    String userPassMDFive = MDFiveUtil.GetMD5Code(userPass);
                    if (userName.equals(userReturnBean.getUsername()) && userPassMDFive.equals(userReturnBean.getUserpass())) {
                        if (binding.chbSave.isChecked()) {
                            UserInfo myUserInfo = new UserInfo();
                            myUserInfo.setName(userName);
                            myUserInfo.setPassword(userPass);
                            myUserInfo.setRember(true);
                            CommonUtil.saveLoginInfo(LoginActivity.this, myUserInfo);
                        }
                        startMyActivity(MainActivity.class, null);
                        finish();
                    }
                    LogUtil.e("infoooo", "normalGet:" + response.body() + "");
                }
            }

            @Override
            public void onFailure(Call<UserReturnBean> call, Throwable t) {
                PrompUtil.stopProgressDialog("");
                CommonUtil.showTopToast(LoginActivity.this, "");
                LogUtil.e("infoooo", "normalGet:" + t.toString() + "");
            }
        });
    }


    public void onLoginSuccess() {
        //保存密码
        saveLoginInfo();
        startMyActivity(MainActivity.class, null);
        finish();
    }

//    public void onLoginFailure(String message) {
//        canStartActivity = false;
//        if (message.contains("ConnectException")) {
//            CommonUtil.showToast(this, "网络错误，请检查网络连接！");
//        } else if (message.contains("System.Data.Entity.Core")) {
//            CommonUtil.showToast(this, "数据库连接异常，请联系工作人员！");
//        } else {
//            CommonUtil.showToast(this, message);
//        }
//    }

//    public void onLoginError(String message) {
//        if (message.contains("ConnectException")) {
//            CommonUtil.showToast(this, "网络错误，请检查网络连接！");
//        } else if (message.contains("System.Data.Entity.Core")) {
//            CommonUtil.showToast(this, "数据库连接异常，请联系工作人员！");
//        } else {
//            CommonUtil.showToast(this, message);
//        }
//    }

    /**
     * 保存登陆信息
     */
    private void saveLoginInfo() {
        if (binding.chbSave.isChecked()) {
            CommonUtil.saveLoginInfo(LoginActivity.this, userInfo);
        }

    }

    private void onLoginSuccessGo() {
        Intent intent = new Intent(this, NetworkActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /**
     * 获取权限
     *
     * @param permissions
     * @param object
     * @param flag        0:全部权限 ，1：下载
     */
    private void requestPermission(final Object object, final int flag, String... permissions) {
        AndPermission.with(this)
                .permission(permissions)
                .rationale(new Rationale() {
                    @Override
                    public void showRationale(Context context, List<String> list, RequestExecutor requestExecutor) {
                        showAlertDialog(list);
                    }
                })
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        CommonUtil.showToast(LoginActivity.this, "权限获取成功！");
                        CommonUtil.saveCurrentPermission(LoginActivity.this, true);
                        if (0 == flag) {
                            onLogin();
                        } else if (1 == flag) {
                            onDownLoad((VersionInfo) object);
                        }
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        CommonUtil.showToast(LoginActivity.this, "权限获取失败！");
                        if (AndPermission.hasAlwaysDeniedPermission(LoginActivity.this, permissions)) {
                            showAlertDialog(permissions);
                        }
                    }
                })
                .start();
    }

    /**
     * 展示跳转到对话框
     */
    protected void showAlertDialog(@NonNull List<String> permissions) {
        List<String> permissionNames = Permission.transformText(this, permissions);
        String message = "用户您好，我们需要您开启设备权限" + TextUtils.join("\n", permissionNames) + "请点击前往设置页面!";
        new AlertDialog.Builder(LoginActivity.this)
                .setMessage(message)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                        Uri uri = Uri.fromParts("package", LoginActivity.this.getPackageName(), (String) null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }


}
