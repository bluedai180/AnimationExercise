package com.blue.animationart.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.blue.animationart.BezierEvaluator;

/**
 * Created by huanggecheng on 2017/9/13.
 */

public class PathBezierView extends View implements View.OnClickListener {

    private int mStartPointX, mStartPointY, mEndPointX, mEndPointY;
    private int mFlagPointX, mFlagPointY;

    private Path mPath;
    private Paint mPaintPath;
    private Paint mPaintCircle;

    private int mMovePaintX, mMovePaintY;

    public PathBezierView(Context context) {
        super(context);
    }

    public PathBezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPath = new Path();
        mPaintPath = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintPath.setStyle(Paint.Style.STROKE);
        mPaintPath.setStrokeWidth(8);

        mPaintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);

        mStartPointX = 100;
        mStartPointY = 100;
        mEndPointX = 600;
        mEndPointY = 600;
        mFlagPointX = 500;
        mFlagPointY = 0;
        mMovePaintX = mStartPointX;
        mMovePaintY = mStartPointY;
        setOnClickListener(this);
    }

    public PathBezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PathBezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mStartPointX, mStartPointY, 20, mPaintCircle);
        canvas.drawCircle(mEndPointX, mEndPointY, 20, mPaintCircle);

        canvas.drawCircle(mMovePaintX, mMovePaintY, 20, mPaintCircle);
        mPath.reset();
        mPath.moveTo(mStartPointX, mStartPointY);
        mPath.quadTo(mFlagPointX, mFlagPointY, mEndPointX, mEndPointY);
        canvas.drawPath(mPath, mPaintPath);
    }

    @Override
    public void onClick(View view) {
        BezierEvaluator evaluator = new BezierEvaluator(new PointF(mFlagPointX, mFlagPointY));
        ValueAnimator animator = ValueAnimator.ofObject(evaluator, new PointF(mStartPointX, mStartPointY), new PointF(mEndPointX, mEndPointY));
        animator.setDuration(600);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                PointF pointF = (PointF) valueAnimator.getAnimatedValue();
                mMovePaintX = (int) pointF.x;
                mMovePaintY = (int) pointF.y;
                invalidate();
            }
        });
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }
}
