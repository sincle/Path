package com.haieros.path.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Kang on 2018/4/16.
 */

public class PathThrBezierCurve extends View {

    private Paint mPaint;
    private Path mPath;
    private Point mPoint1;
    private Point mPoint2;
    private boolean isStart = false;

    public PathThrBezierCurve(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        initPaint();
        initPath();
    }


    private void start() {
        if(isStart) {
            return;
        }
        isStart = true;
        final int[] a = {50};
        final int[] b = {1000};
        new Thread(){
            public void run(){
                while (true){
                    try {
                        mPoint1.set(50, a[0]);
                        mPoint2.set(b[0],50);
                        postInvalidate();
                        if(a[0] < 1000) {
                            a[0] = a[0] + 10;
                            b[0] = b[0] - 10;
                        }
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private void initPath() {
        mPath = new Path();
        mPoint1 = new Point(50,50);
        mPoint2 = new Point(1000,50);
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int b = 0;
//        mPath.reset();
        mPath.moveTo(50, 1000);//开始点
        mPath.lineTo(mPoint1.x, mPoint1.y);//控制点1
        mPath.lineTo(mPoint2.x,mPoint2.y);//控制点2
        mPath.lineTo(1400, 1000);//结束点
        mPath.moveTo(50, 1000);
        mPath.cubicTo(mPoint1.x, mPoint1.y,mPoint2.x,mPoint2.y,1400, 1000);
        canvas.drawPath(mPath, mPaint);
        start();
    }
}
