package com.blue.animationart.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by huanggecheng on 2017/9/6.
 */

public class WaveBezierView extends View implements View.OnClickListener {

    private Path mPath;
    private Paint mPaintBezier;
    private int mWaveCount;

    private int mWaveLength;
    private int mScreenHeight, mScreenWidth;
    private int mCenterY;

    private ValueAnimator mValueAnimator;
    private int moffset;


    public WaveBezierView(Context context) {
        super(context);
    }

    public WaveBezierView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaintBezier = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBezier.setColor(Color.BLUE);
        mPaintBezier.setStrokeWidth(8);
        mPaintBezier.setStyle(Paint.Style.FILL_AND_STROKE);
        mWaveLength = 800;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPath = new Path();
        setOnClickListener(this);
        mScreenHeight = h;
        mScreenWidth = w;
        mCenterY = h / 2 + 400;
        mWaveCount = (int) Math.round(mScreenWidth / mWaveLength + 1.5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(-mWaveLength + moffset, mCenterY);
        for (int i = 0; i < mWaveCount; i++) {
            mPath.quadTo(-mWaveLength * 3 / 4 + i * mWaveLength + moffset, mCenterY + 60, -mWaveLength / 2 + i * mWaveLength + moffset, mCenterY);
            mPath.quadTo(-mWaveLength / 4 + i * mWaveLength + moffset, mCenterY - 60, i * mWaveLength + moffset, mCenterY);
        }
        mPath.lineTo(mScreenWidth, mScreenHeight);
        mPath.lineTo(0, mScreenHeight);
        mPath.close();
        canvas.drawPath(mPath, mPaintBezier);
    }

    @Override
    public void onClick(View view) {
        mValueAnimator = ValueAnimator.ofInt(0, mWaveLength);
        mValueAnimator.setDuration(1000);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                moffset = (int) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        mValueAnimator.start();
    }
}
