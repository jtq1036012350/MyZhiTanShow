package com.iguitar.xiaoxiaozhitan.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

/**
 * 加载进度弹框工具类
 *
 * @author Jiang
 */
public class PrompUtil {

    private static Dialog dialog = null;

    public static void startProgressDialog(Context context, String title) {

        if (!((Activity) context).isFinishing()) {
            if (dialog != null) {
                dialog.dismiss();
            }
            dialog = DialogUtil.createLoadingDialog(context, title);
        }
    }

    public static void stopProgressDialog(String text) {
        if (dialog != null) {
            dialog.dismiss();
        }
        dialog = null;
    }

}
