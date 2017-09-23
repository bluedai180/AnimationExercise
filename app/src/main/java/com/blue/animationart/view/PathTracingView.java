package com.blue.animationart.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by huanggecheng on 2017/9/19.
 */

public class PathTracingView extends View {

    // 截取path的输出
    private Path mDst;
    private Paint mPaint;
    // 用于绘图的原始Path
    private Path mPath;
    // 获取Path的长度
    private float mLength;
    private float mAnimValue;
    // 测量的工具类
    private PathMeasure mPathMeasure;


    public PathTracingView(Context context) {
        super(context);
    }

    public PathTracingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);

        mPath = new Path();
        mDst = new Path();

        // 划一个圆
        mPath.addCircle(400, 400, 100, Path.Direction.CW);
        mPathMeasure = new PathMeasure();
        // 关联Path，由于画出的圆已经是闭合的了，所以true和false都无关紧要了
        mPathMeasure.setPath(mPath, true);

        mLength = mPathMeasure.getLength();

        // 从0到100%变化
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAnimValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }

    public PathTracingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mDst.reset();
        // 避免Android上硬件加速的bug，在调用getSegment方法时，对mDst进行lineTo操作
        mDst.lineTo(0, 0);

        // 终点坐标从0到100%变化
        float stop = mLength * mAnimValue;
        // 在前半段start为0，后半段快速向stop靠拢
        float start = (float) (stop - ((0.5 - Math.abs(mAnimValue - 0.5)) * mLength));

        // 获取截取片段
        mPathMeasure.getSegment(start, stop, mDst, true);
        canvas.drawPath(mDst, mPaint);
    }
}
