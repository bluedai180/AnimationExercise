package com.blue.animationart;

import android.animation.TypeEvaluator;
import android.graphics.Point;
import android.graphics.PointF;

/**
 * Created by huanggecheng on 2017/9/13.
 */

public class BezierEvaluator implements TypeEvaluator<PointF> {
    private PointF mFlagPoint;
    public BezierEvaluator(PointF flagPoint) {
        mFlagPoint = flagPoint;
    }

    @Override
    public PointF evaluate(float v, PointF pointF, PointF t1) {
        return BezierUtil.CalculateBezierPointForQuadratic(v, pointF, mFlagPoint, t1);
    }
}
