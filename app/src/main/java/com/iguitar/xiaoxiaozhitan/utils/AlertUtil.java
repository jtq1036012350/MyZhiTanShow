package com.iguitar.xiaoxiaozhitan.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.iguitar.xiaoxiaozhitan.R;

/**
 * 弹框Util
 */

public class AlertUtil {

    private static Dialog dialog = null;
    //只有一个按钮的Dialog的回调接口
    public interface OnShowMessageCallBack {
        void onPositive();
    }

    public static void startProgressDialog(Context context, String title) {
        if (dialog != null)
            dialog.dismiss();
        dialog = DialogUtil.createLoadingDialog(context, title);
    }

    public static void stopProgressDialog(String title) {
        if (dialog != null)
            dialog.dismiss();

        dialog = null;
    }

    //带有输入框的Dialog
    public static void showAlertRefuseReasonDialog(final Context context, final Activity activity, String message, final GetMessageCallBack callback) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_refusereason_alert, null);
        final Dialog dialog = DialogUtil.createDialog(context, activity, view, R.style.mydialog);
        dialog.setContentView(view);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_bindcard_cancel);
        Button btn_submit = (Button) view.findViewById(R.id.btn_bindcard_submit);
        TextView tv_msg = (TextView) view.findViewById(R.id.tv_bindcard_msg);
        final EditText et_bindcard = (EditText) view.findViewById(R.id.et_bindcard);
        tv_msg.setText(message);

        btn_submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                if (callback != null) {
                    callback.onNegative(dialog);
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    //   dialog.dismiss();
                }
                if (callback != null) {
                    callback.onPositive(et_bindcard.getText().toString());
                    activity.finish();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    //带有输入框的Dialog的回调接口
    public interface GetMessageCallBack {
        void onPositive(String message);

        void onNegative(Dialog dialog);
    }

    //带有输入框的Dialog的回调接口
    public interface GetOneMessageCallBack {
        void onPositive();
    }

    //带有确定和取消按钮的Dialog
    public static void showAlertInfoDialog(Context context, Activity activity, String message, String leftButtonText, String rightButtonText, final AlertCallBack callback) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_alert, null);
        final Dialog dialog = DialogUtil.createDialog(context, activity, view, R.style.mydialog);
        dialog.setContentView(view);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        Button btn_submit = (Button) view.findViewById(R.id.btn_submit);

        btn_submit.setText(leftButtonText);
        btn_cancel.setText(rightButtonText);

        TextView tv_msg = (TextView) view.findViewById(R.id.tv_msg);
        tv_msg.setText(message);
        btn_submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                if (callback != null) {
                    callback.onPositive();
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                if (callback != null) {
                    callback.onNegative();
                }
            }
        });
        dialog.show();
    }

    //Alert回调方法
    public interface AlertCallBack {
        public void onPositive();

        public void onNegative();
    }

    //只带有一个按钮的弹框
    public static void showOneMessageDialog(Context context, Activity activity, String msg) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_onemessage_ok, null);
        final Dialog dialog = DialogUtil.createDialog(context, activity, view, R.style.mydialog);
        dialog.setContentView(view);
        Button btn_conf = (Button) view.findViewById(R.id.btn_confirm);
        TextView tv_msg = (TextView) view.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        btn_conf.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

            }
        });
        dialog.show();
    }

    //只带有一个按钮的弹框
    public static void showOneMessageInstallDialog(Context context, Activity activity, String buttonText, String msg, final AlertUtil.GetOneMessageCallBack getOneMessageCallBack) {
        View view = LayoutInflater.from(context).inflate(R.layout.install_tips_layout, null);
        final Dialog dialog = DialogUtil.createDialog(context, activity, view, R.style.mydialog);
        dialog.setContentView(view);
        Button btn_install = (Button) view.findViewById(R.id.btn_install);
        btn_install.setText(buttonText);
        TextView tv_message = (TextView) view.findViewById(R.id.tv_message);
        tv_message.setText(msg);
        btn_install.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                    getOneMessageCallBack.onPositive();
                }

            }
        });
        dialog.show();
    }

    //只带有一个按钮的弹框带有标题
    public static void showOneMessageWithTittleDialog(Context context, Activity activity, String msg, String tittle) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_onemessage_with_tittle_ok, null);
        final Dialog dialog = DialogUtil.createDialog(context, activity, view, R.style.mydialog);
        dialog.setContentView(view);
        Button btn_conf = (Button) view.findViewById(R.id.btn_confirm);
        TextView tv_msg = (TextView) view.findViewById(R.id.tv_msg);
        TextView tv_tittle = (TextView) view.findViewById(R.id.tv_tittle);
        tv_tittle.setText(tittle);
        tv_msg.setText(msg);
        btn_conf.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

            }
        });
        dialog.show();
    }

    /**
     * 弹框提示（只有一条提示信息的弹框）
     *
     * @param context
     * @param title    标题
     * @param content  内容
     * @param btn_text 按钮文字
     * @param callBack 回调
     */
    public static void showOneMessageDialog(Context context, String title, String content, String btn_text, final OnShowMessageCallBack callBack) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_new_message, null);
        Button btnSure = (Button) dialogView.findViewById(R.id.btn_sure);
        Button btnClose = (Button) dialogView.findViewById(R.id.btn_close);
        TextView tvTitle = (TextView) dialogView.findViewById(R.id.tv_title);
        TextView tvContent = (TextView) dialogView.findViewById(R.id.tv_content);
        if (content.contains("ConnectException")) {
            content = "网络错误，请检查网络连接！";
        }
        tvTitle.setText(title);
        tvContent.setText(content);
        btnSure.setText(btn_text);
        tvContent.setMovementMethod(new ScrollingMovementMethod());
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtil.dismiss();
                if (callBack != null) {
                    callBack.onPositive();
                }
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtil.dismiss();
            }
        });
        DialogUtil.createUniversalDialog(context, dialogView);
    }

}
