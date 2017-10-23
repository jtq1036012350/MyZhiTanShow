package com.iguitar.xiaoxiaozhitan.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import com.iguitar.xiaoxiaozhitan.ui.view.MyProgressDialog;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;


/**
 * Created by xpf on 2016/9/19.
 */
public class DownloadUtil {
    private static final String BASE_PATH = Environment.getExternalStorageDirectory().getPath() + "/XiaoXiaoZhiTan.apk";
    private Activity activity;
    private MyProgressDialog progressDialog;
//    private ProgressDialog progressDialog;
    private HttpHandler downhandler;
    private File file;
    private String updateContent;

    public DownloadUtil(Activity activity, String updateContent) {
        this.activity = activity;
        this.updateContent = updateContent;
    }

    public void StartDownload(String url) {
        if (TextUtils.isEmpty(url)) {
//            Toast.makeText(activity, "暂无下载链接", Toast.LENGTH_SHORT).show();
            CommonUtil.showToast(activity, "暂无下载链接");
            return;
        }
        file = new File(BASE_PATH);
        if (file.exists()) {
            file.delete();
        }
        progressDialog = new MyProgressDialog(activity);
        progressDialog.setTitle("更新提示");
        if (TextUtils.isEmpty(updateContent)) {
            progressDialog.setMessage("正在下载...");
        } else {
            progressDialog.setMessage(updateContent);
        }
        progressDialog.show();
        HttpUtils http = new HttpUtils();
        /**
         第一个参数：网络下载位置
         第二个参数：下载保存位置
         第三个参数：如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
         第四个参数：如果从请求返回信息中获取到文件名，下载完成后自动重命名。
         第五个参数：下载的监听
         */
        http.download(url, BASE_PATH, true, false, new RequestCallBack<File>() {
            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                progressDialog.setMax((int) total);
                progressDialog.setProgress((int) current);
            }

            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                showUpdataDialog();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                CommonUtil.showToast(activity, "下载失败，稍后再试");
                progressDialog.dismiss();
            }
        });
    }

    private void showUpdataDialog() {
        AlertUtil.showOneMessageInstallDialog(activity, activity, "安装", updateContent, new AlertUtil.GetOneMessageCallBack() {
            @Override
            public void onPositive() {
                InstallApk(file);
            }
        });
//        AlertDialog.Builder builer = new AlertDialog.Builder(activity,R.style.dialogNoBg);
////        builer.setMessage(updateContent + "\n是否安装?");
////        builer.setMessage("是否安装");
//
//        LayoutInflater inflater = activity.getLayoutInflater();
//        View convertView = inflater.inflate(R.layout.install_tips_layout, null);
//        TextView tv_message = (TextView) convertView.findViewById(R.id.tv_message);
//        tv_message.setText(updateContent);
//        tv_message.setMovementMethod(new ScrollingMovementMethod());
//        Button btn_install = (Button) convertView.findViewById(R.id.btn_install);
//        btn_install.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                InstallApk(file);
//
//            }
//        });
//        builer.setView(convertView);
//
////        builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                if (file != null) {
//                }
//            }
//        });
//
//        builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });

//        AlertDialog dialog = builer.create();
//        dialog.show();
    }

    public void CanceldownLoad() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        if (downhandler != null) {
            downhandler.cancel();
        }
    }

    public HttpHandler getDownhandler() {
        return downhandler;
    }

    private void InstallApk(File file) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        activity.startActivity(intent);
        activity.finish();
    }

    public String GetCode() {
        int currentVersionCode = 0;
        PackageManager manager = activity.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(activity.getPackageName(), 0);
            currentVersionCode = info.versionCode; // 版本号
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return currentVersionCode + "";
    }
}
