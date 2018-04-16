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

public class PathSecBezierCurve extends View {

    private Paint mPaint;
    private Path mPath;
    private Point point;

    public PathSecBezierCurve(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        initPaint();
        initPath();
        start();
    }


    private void start() {
        point = new Point(50, 50);
        final int[] a = {50};
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){

                    point.set(50, a[0]);
                    postInvalidate();
                    if(a[0] < 1900) {
                        a[0] = a[0]+10;
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }

    private void initPath() {

        mPath = new Path();
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
        mPath.moveTo(50, 1000);
        mPath.lineTo(point.x, point.y);
        mPath.lineTo(1400, 1000);
        mPath.moveTo(50, 1000);
        mPath.quadTo(point.x, point.y, 1400, 1000);
        canvas.drawPath(mPath, mPaint);
    }
}
