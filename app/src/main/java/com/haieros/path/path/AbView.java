package com.haieros.path.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Kang on 2018/4/16.
 */

public abstract class AbView extends View {

    public Paint mPaint;
    public Path mPath;
    public AbView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    protected void init(){
        initPaint();
        initPath();
        start();
    }

    protected abstract void start();

    protected abstract void initPath();

    protected void initPaint(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
