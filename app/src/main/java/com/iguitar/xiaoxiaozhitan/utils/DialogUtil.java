package com.iguitar.xiaoxiaozhitan.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iguitar.xiaoxiaozhitan.R;
import com.iguitar.xiaoxiaozhitan.ui.view.MyDialog;


public class DialogUtil {

    private static MyDialog myDialog;
    private static Dialog dialog;

    /**
     * 得到自定义的progressDialog
     *
     * @param context
     * @param msg     文字显示
     * @return
     */
    public static Dialog createLoadingDialog(Context context, String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_loading_view);// 加载布局
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
        tipTextView.setText(msg);// 设置加载信息

        Dialog loadingDialog = new Dialog(context, R.style.MyDialogStyle);// 创建自定义样式dialog
        loadingDialog.setCancelable(true); // 是否可以按返回键消失
        loadingDialog.setCanceledOnTouchOutside(false); //点击加载框以外的区域
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        loadingDialog.show();
        return loadingDialog;
    }

    // 通用
    public static MyDialog createUniversalDialog(Context context, View layout) {
        if (((Activity) context).isFinishing()) {
            return null;
        }
        if (dialog == null) {
            myDialog = new MyDialog(context, layout, R.style.mydialog,
                    MyDialog.CENTER, MyDialog.WRAP_CONTENT, MyDialog.WRAP_CONTENT);
        } else {
            myDialog.setContentView(layout);
        }
        myDialog.show();

        return myDialog;
    }

    public static void dismiss() {
        if (myDialog != null && myDialog.isShowing()) {
            myDialog.dismiss();
            myDialog = null;
        }
    }

    public static Dialog createDialog(Context context, Activity activity, View view, int styleId) {
        if (dialog != null) {
            dialog.dismiss();
        }
        dialog = new Dialog(activity, styleId);
        dialog.setCancelable(true);
        dialog.setContentView(view);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (DeviceUtil.getMetricsWidth(context) - 80); // 设置宽度
        dialog.getWindow().setAttributes(lp);
        return dialog;
    }

}
