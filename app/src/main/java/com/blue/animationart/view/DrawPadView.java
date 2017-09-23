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

public class DrawPadView extends View {

    private Path mPath;
    private Paint mPaint;
    private float mX;
    private float mY;

    public DrawPadView(Context context) {
        super(context);
    }

    public DrawPadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(8);
        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float x1 = event.getX();
                float y1 = event.getY();
                float cx = (x1 + mX) / 2;
                float cy = (y1 + mY) / 2;
                mPath.quadTo(mX, mY, cx, cy);
                mX = x1;
                mY = y1;
                break;
            case MotionEvent.ACTION_DOWN:
                mPath.reset();
                mX = event.getX();
                mY = event.getY();
                mPath.moveTo(mX, mY);
                break;
        }
        invalidate();
        return true;
    }
}
