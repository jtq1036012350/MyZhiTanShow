package com.iguitar.xiaoxiaozhitan.ui.base;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.iguitar.xiaoxiaozhitan.R;
import com.iguitar.xiaoxiaozhitan.utils.AlertUtil;
import com.iguitar.xiaoxiaozhitan.utils.ConstantUtil;
import com.iguitar.xiaoxiaozhitan.utils.ViewUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * 父类actvity
 * Created by jiang on 2016/9/23.
 */

public class BaseActivity extends FragmentActivity {
    private BaseActivity mActivity;
    public ForResultCallBack forResultCallBack;
    private OnSuccessSmsListener OnSuccessSmsListener;
    public Retrofit retrofit;

    public interface OnSuccessSmsListener {
        void OnSms(int flag);
    }

    public void setOnSmsListener(OnSuccessSmsListener OnSuccessSmsListener) {
        this.OnSuccessSmsListener = OnSuccessSmsListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtil.initSystemBar(this, R.color.colorTitleBlack);
        retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUtil.internretUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build();
        mActivity = this;
    }

    protected boolean isPermissionGranted(String permissionName, int questCode) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        //判断是否需要请求允许权限
        int hasPermision = checkSelfPermission(permissionName);
        if (hasPermision != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{permissionName}, questCode);
            return false;
        }
        return true;
    }

    protected boolean isPermissionsAllGranted(String[] permArray, int questCode) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        //获得批量请求但被禁止的权限列表
        List<String> deniedPerms = new ArrayList<String>();
        for (int i = 0; permArray != null && i < permArray.length; i++) {
            if (PackageManager.PERMISSION_GRANTED != checkSelfPermission(permArray[i])) {
                deniedPerms.add(permArray[i]);
            }
        }
        //进行批量请求
        int denyPermNum = deniedPerms.size();
        if (denyPermNum != 0) {
            requestPermissions(deniedPerms.toArray(new String[denyPermNum]), questCode);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length == 0) {
            return;
        }
        switch (requestCode) {
            case ConstantUtil.QUEST_CODE_LOCTION:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    popAlterDialog("位置", "位置信息权限被禁止，将导致定位失败。。是否开启该权限？(步骤：应用信息->权限->'勾选'位置)");
                } else {
                    showShortMsg("恭喜，用户已经授予位置权限");
                }
                break;
            case ConstantUtil.QUEST_CODE_CAMERA:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    popAlterDialog("相机", "摄像头使用权限被禁止，手电筒无法正常使用。是否开启该权限？(步骤：应用信息->权限->'勾选'相机)");
                } else {
                    showShortMsg("恭喜，用户已经授予相机权限");
                }
                break;
            case ConstantUtil.QUEST_CODE_SEND_SMS:
                if (OnSuccessSmsListener != null) {
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        OnSuccessSmsListener.OnSms(0);
                        popAlterDialog("短信", "发送短信权限被禁止，无法使用反馈/建议功能。是否开启该权限？(步骤：应用信息->权限->'勾选'短信)");
                    } else {
                        OnSuccessSmsListener.OnSms(1);
                        showShortMsg("恭喜，用户已经授予短信权限");
                    }
                }
                break;
            case ConstantUtil.QUEST_CODE_ALL:
                doPermissionAll(ConstantUtil.permArray, grantResults);
                break;
            case ConstantUtil.QUEST_CODE_CALL_PHONE:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    popAlterDialog("拨打电话", "拨打电话权限被禁止，无法使用拨打电话功能。是否开启该权限？(步骤：应用信息->权限->'勾选'电话)");
                } else {
                    showShortMsg("恭喜，用户已经授予所有权限");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
                break;
        }
    }

    private void doPermissionAll(String[] permissions, int[] grantResults) {
        int grantedPermNum = 0;
        int totalPermissons = permissions.length;
        int totalResults = grantResults.length;
        if (totalPermissons == 0 || totalResults == 0) {
            return;
        }
        Map<String, Integer> permResults = new HashMap<String, Integer>();
        //初始化Map容器，用于判断哪些权限被授予
        for (String perm : ConstantUtil.permArray) {
            permResults.put(perm, PackageManager.PERMISSION_DENIED);
        }
        //根据授权的数目和请求授权的数目是否相等来判断是否全部授予权限
        for (int i = 0; i < totalResults; i++) {
            permResults.put(permissions[i], grantResults[i]);
            if (permResults.get(permissions[i]) == PackageManager.PERMISSION_GRANTED) {
                grantedPermNum++;
            }
            Log.d("Debug", "权限：" + permissions[i] + "-->" + grantResults[i]);
        }
        if (grantedPermNum == totalPermissons) {
            //用于授予全部权限
        } else {
            showShortMsg("批量申请权限失败，将会影响正常使用！");
        }
    }

    private void showShortMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void popAlterDialog(final String msgFlg, String msgInfo) {
        AlertUtil.showAlertInfoDialog(BaseActivity.this, BaseActivity.this, "使用警告", "设置", "取消", new AlertUtil.AlertCallBack() {
            @Override
            public void onPositive() {
                //前往应用详情界面
                try {
                    Uri packUri = Uri.parse("package:" + getPackageName());
                    Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packUri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    BaseActivity.this.startActivity(intent);
                } catch (Exception e) {
                    showShortMsg("跳转失败");
                }
            }

            @Override
            public void onNegative() {

            }
        });
//        new AlertDialog.Builder(BaseActivity.this)
//                .setTitle("使用警告")
//                .setMessage(msgInfo)
//                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                })
//                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //前往应用详情界面
//                        try {
//                            Uri packUri = Uri.parse("package:" + getPackageName());
//                            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packUri);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            BaseActivity.this.startActivity(intent);
//                        } catch (Exception e) {
//                            showShortMsg("跳转失败");
//                        }
//                        dialog.dismiss();
//                    }
//                }).create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    //监听按下了返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finishMyActivity();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
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
    public void startMyActivityForResult(Class<?> cls, Bundle bundle, ForResultCallBack forResultCallBack) {
        this.forResultCallBack = forResultCallBack;
        Intent intent = new Intent(mActivity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, ConstantUtil.FORRESULT);
        mActivity.overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
    }

    //封装startActivityForResult(这里为计量任务准备的，那里不知道为何会调用两次onSuccessScan)
    public void startMyActivityForResultClearTop(Class<?> cls, Bundle bundle, ForResultCallBack forResultCallBack) {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case ConstantUtil.FORRESULT:
                forResultCallBack.forResult(data);
                break;
        }
    }

    public interface ForResultCallBack {
        void forResult(Intent data);
    }

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

}
