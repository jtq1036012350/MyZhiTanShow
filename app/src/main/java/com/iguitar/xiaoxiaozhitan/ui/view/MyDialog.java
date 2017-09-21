package com.iguitar.xiaoxiaozhitan.ui.view;

import android.app.ActionBar.LayoutParams;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 *
 */

public class MyDialog extends Dialog {

    public static final int BOTTOM = 2;
    public static final int TOP = 0;
    public static final int CENTER = 1;
    public static final int WRAP_CONTENT=0;
    public static final int MATCH_CONTENT=1;

    private int screenWidth;

    private int screenHeight;

    public MyDialog(Context context, View layout, int style, int position) {
        super(context, style);
        this.setCanceledOnTouchOutside(true);
    }

    /**
     * 指定宽高
     *
     * @param context
     * @param width
     * @param height
     * @param layout
     * @param style
     * @param position
     */
    public MyDialog(Context context, int width, int height, View layout, int style, int position) {
        super(context, style);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        if (position == TOP) {
            params.gravity = Gravity.TOP;
        } else if (position == CENTER) {
            params.gravity = Gravity.CENTER;
        } else if (position == BOTTOM) {
            params.gravity = Gravity.BOTTOM;
        }
        params.width = width;
        params.height = height;

        window.setAttributes(params);
        this.setCanceledOnTouchOutside(false);
    }

    /**
     * 宽高使用比例
     *
     * @param context
     * @param layout
     * @param style
     * @param position 对话框显示的位置 上中下
     * @param scaleWidth 比例宽度
     * @param scaleHeight 比例高度
     */
    public MyDialog(Context context, View layout, int style, int position, int scaleWidth, int scaleHeight) {
        super(context, style);
        getScreen();
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        if (position == TOP) {
            params.gravity = Gravity.TOP;
        } else if (position == CENTER) {
            params.gravity = Gravity.CENTER;
        } else if (position == BOTTOM) {
            params.gravity = Gravity.BOTTOM;
        }
        if (scaleWidth == 0) {
            params.width = (int) (screenWidth * 0.9);
        } else if (scaleWidth == 1) {
            params.width = LayoutParams.MATCH_PARENT;
        } else {
            params.width = (int) (screenWidth * ((float) scaleWidth / 10));
        }
        if (scaleHeight == 0) {
            params.height = LayoutParams.WRAP_CONTENT;
        } else if (scaleHeight == 1) {
            params.height = LayoutParams.MATCH_PARENT;
        } else {
            params.height = (int) (screenHeight * ((float) scaleHeight / 10));
        }
        window.setAttributes(params);
        this.setCanceledOnTouchOutside(false);
    }

    private void getScreen(){
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        screenWidth = wm.getDefaultDisplay().getWidth();
        screenHeight = wm.getDefaultDisplay().getHeight();
    }

}
