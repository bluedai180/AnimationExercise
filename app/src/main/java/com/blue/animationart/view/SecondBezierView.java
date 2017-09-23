package com.blue.animationart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by huanggecheng on 2017/9/6.
 */

public class SecondBezierView extends View {
    private float mStartPointX;
    private float mStartPointY;
    private float mEndPointX;
    private float mEndPointY;

    private float mFlagPointX;
    private float mFlagPointY;

    private Path mPath;
    private Paint mPaintBezier;
    private Paint mPaintFlag;

    public SecondBezierView(Context context) {
        super(context);
    }
    public SecondBezierView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaintBezier = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBezier.setStrokeWidth(8);
        mPaintBezier.setStyle(Paint.Style.STROKE);

        mPaintFlag = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintFlag.setStrokeWidth(3);
        mPaintFlag.setStyle(Paint.Style.STROKE);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStartPointX = w / 4;
        mStartPointY = h / 2 - 200;
        mEndPointX = w * 3 / 4;
        mEndPointY = h / 2 - 200;

        mFlagPointX = w / 2;
        mFlagPointY = h - 400;

        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath.reset();
        mPath.moveTo(mStartPointX, mStartPointY);
        mPath.quadTo(mFlagPointX, mFlagPointY, mEndPointX, mEndPointY);
        canvas.drawPoint(mStartPointX, mStartPointY, mPaintFlag);
        canvas.drawPoint(mEndPointX, mEndPointY, mPaintFlag);
        canvas.drawPoint(mFlagPointX, mFlagPointY, mPaintFlag);
        canvas.drawLine(mStartPointX, mStartPointY, mFlagPointX, mFlagPointY, mPaintFlag);
        canvas.drawLine(mEndPointX, mEndPointY, mFlagPointX, mFlagPointY, mPaintFlag);
        canvas.drawPath(mPath, mPaintBezier);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mFlagPointX = event.getX();
                mFlagPointY = event.getY();
                invalidate();
                break;
        }
        return true;
    }
}
