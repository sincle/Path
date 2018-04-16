package com.haieros.path.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by Kang on 2018/4/16.
 */

public class PathArc extends AbView {
    public PathArc(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void start() {

    }

    @Override
    protected void initPath() {
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        mPath.lineTo(200,200);
        RectF rectF = new RectF(100, 100, 400, 400);

        mPath.arcTo(rectF,0,300,false);
        //mPath.addArc(rectF,0,300);
        canvas.drawPath(mPath,mPaint);
    }
}
