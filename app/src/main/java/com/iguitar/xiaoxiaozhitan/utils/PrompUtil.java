package com.iguitar.xiaoxiaozhitan.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

public class PrompUtil {

    private static Dialog dialog = null;

    public static void startProgressDialog(Context context, String title) {

        if (!((Activity) context).isFinishing()) {
            if (dialog != null)
                dialog.dismiss();
            dialog = DialogUtil.createLoadingDialog(context, title);
        }
//        if (!((Activity) context).isFinishing()) {
//            if (dialog == null) {
//                dialog = DialogUtil.createLoadingDialog(context, title);
//            } else if (!dialog.isShowing()) {
//                dialog.show();
//            }
//        }
    }

    public static void stopProgressDialog(String title) {
        if (dialog != null)
            dialog.dismiss();
        dialog = null;
    }

}
