package com.blue.animationart.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by huanggecheng on 2017/9/19.
 */

public class PathPaintView extends View {

    private Paint mPaint;
    private Path mPath;
    private float mLength;
    private float mAnimValue;
    private PathMeasure mPathMeasure;
    private PathEffect mPathEffect;


    public PathPaintView(Context context) {
        super(context);
    }

    public PathPaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);

        mPath = new Path();

        // 绘制一个三角形
        mPath.moveTo(100, 100);
        mPath.lineTo(100, 500);
        mPath.lineTo(400, 300);
        mPath.close();

        mPathMeasure = new PathMeasure();
        mPathMeasure.setPath(mPath, true);
        // 取出具体长度
        mLength = mPathMeasure.getLength();

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 0);
        valueAnimator.setDuration(2000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAnimValue = (float) valueAnimator.getAnimatedValue();
                // 设置画笔风格样式Dash
                // 将实线和虚线都设置为整个路径的长度，第二个参数是偏移量，从0到100%
                // 这样实线或者虚线会一点一点地挤开
                mPathEffect = new DashPathEffect(new float[]{mLength, mLength}, mLength * mAnimValue);
                mPaint.setPathEffect(mPathEffect);
                invalidate();
            }
        });
        valueAnimator.start();
    }

    public PathPaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
    }
}
