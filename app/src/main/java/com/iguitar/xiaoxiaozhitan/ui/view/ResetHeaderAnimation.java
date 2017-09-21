package com.iguitar.xiaoxiaozhitan.ui.view;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;

/**
 * Created by Jiang on 2017/5/8.
 */
public class ResetHeaderAnimation extends Animation {

    private View view;

    private int targetHeight;

    private int originalHeight;

    private int totalValue;

    public ResetHeaderAnimation(View view, int targetHeight) {
        this.view = view;
        this.targetHeight = targetHeight;
        originalHeight = view.getHeight();
        totalValue = targetHeight - originalHeight;//240-340
        setDuration(500);
        setInterpolator(new OvershootInterpolator());
    }


    /**
     * @param interpolatedTime(0-1)标识动画执行的百分比或者进度
     * @param t
     *
     *
     * time:0 - 0.5 - 1
     * value:10 - 60 - 110
     *
     */
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        int newHeight = (int) (originalHeight + totalValue* interpolatedTime);
        view.getLayoutParams().height = newHeight;
        view.requestLayout();
    }
}
