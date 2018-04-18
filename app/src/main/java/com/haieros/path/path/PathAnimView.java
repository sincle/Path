package com.haieros.path.path;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by Kang on 2018/4/17.
 */

public class PathAnimView extends View {

    private Paint mPaint;
    private Path mPath;
    private PathMeasure mPathMeasure;
    private float mLengthSum;
    private Path dest;
    private PathMeasure mDestM;
    private int count;
    private Paint mIndiPaint;
    private float mLength;
    private float lastAnimatedValue = 0f;

    public PathAnimView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        initPaint();
    }

    private void initPaint() {
        //Paint
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(5);
        mPath = new Path();
        mPath.addCircle(0,0,200, Path.Direction.CCW);
        mPathMeasure = new PathMeasure();
        mPathMeasure.setPath(mPath,false);
        mLength = mPathMeasure.getLength();
        Log.e("kang", "length:"+mLength);
        dest = new Path();
        mDestM = new PathMeasure();

        mIndiPaint = new Paint();
        mIndiPaint.setStrokeWidth(2);
        mIndiPaint.setColor(Color.BLACK);
        mIndiPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //再绘制前景，mAnimPath不断变化，不断重绘View的话，就会有动画效果。
//        dest.moveTo(100,100);
//        dest.lineTo(100,200);
        canvas.translate(getWidth()/2,getHeight()/2);
        canvas.drawLine(-1000,0,1000,0,mIndiPaint);
        canvas.drawLine(0,-1000,0,1000,mIndiPaint);

        canvas.drawPath(dest, mPaint);
    }

    public void start() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(10000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                mPathMeasure.getSegment(mLength*lastAnimatedValue,animatedValue*mLength,dest,true);
                lastAnimatedValue = animatedValue;
                invalidate();
            }
        });
        valueAnimator.start();
    }
    public void clear(){
        dest.reset();
    }
}
