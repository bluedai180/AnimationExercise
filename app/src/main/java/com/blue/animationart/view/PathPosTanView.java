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

public class PathPosTanView extends View implements View.OnClickListener {

    private Path mPath;
    // 存放取出点的具体坐标
    private float[] mPos;
    //　当前曲线的运动趋势即横纵坐标
    private float[] mTan;
    private Paint mPaint;
    private PathMeasure mPathMeasure;
    private ValueAnimator mValueAnim;
    private float mCurrentValue;

    public PathPosTanView(Context context) {
        super(context);
    }

    public PathPosTanView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        // 绘制一个圆形
        mPath.addCircle(0, 0, 200, Path.Direction.CW);

        mPathMeasure = new PathMeasure();
        mPathMeasure.setPath(mPath, false);
        // 初始化数组，横纵坐标一共两个
        mPos = new float[2];
        mTan = new float[2];

        setOnClickListener(this);

        mValueAnim = ValueAnimator.ofFloat(0, 1);
        mValueAnim.setDuration(3000);
        mValueAnim.setInterpolator(new LinearInterpolator());
        mValueAnim.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mCurrentValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
    }

    public PathPosTanView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 第一个参数是运动的轨迹长度，后面两个参数接收获取的值
        mPathMeasure.getPosTan(mCurrentValue * mPathMeasure.getLength(), mPos, mTan);
        // 获取路径上点的切线角度
        float degree = (float) (Math.atan2(mTan[1], mTan[0]) * 180 / Math.PI);
        // 将画布锁定
        canvas.save();
        // 移动画布
        canvas.translate(400, 400);
        // 绘制路线
        canvas.drawPath(mPath, mPaint);
        // 绘制在运动轨迹上的圆
        canvas.drawCircle(mPos[0], mPos[1], 10, mPaint);
        // 旋转画布角度
        canvas.rotate(degree);
        // 绘制切线
        canvas.drawLine(0, -200, 300, -200, mPaint);
        // 画布释放
        canvas.restore();
    }

    @Override
    public void onClick(View view) {
        mValueAnim.start();
    }
}
