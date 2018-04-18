package com.haieros.path.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Kang on 2018/4/17.
 */

public abstract class CoordinateView extends View {

    private Paint mCoordPaint;

    public CoordinateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        initPaint();
        initPath();
        mCoordPaint = new Paint();
        mCoordPaint.setStrokeWidth(1);
        mCoordPaint.setColor(Color.BLACK);
        mCoordPaint.setStyle(Paint.Style.STROKE);
    }

    protected abstract void initPath();

    public abstract void initPaint();

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(getWidth()/2,getHeight()/2);
        canvas.drawLine(-1000,0,1000,0,mCoordPaint);
        canvas.drawLine(0,-1000,0,1000,mCoordPaint);
    }
}
