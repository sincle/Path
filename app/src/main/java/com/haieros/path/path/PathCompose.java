package com.haieros.path.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by Kang on 2018/4/16.
 */

public class PathCompose extends AbView {

    private Path mPath1;

    public PathCompose(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void start() {

    }

    @Override
    protected void initPath() {
        mPath = new Path();
        mPath1 = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        mPath.addRect(100,100,400,300, Path.Direction.CW);

        mPath1.addCircle(300,300,100, Path.Direction.CW);
        mPath.addPath(mPath1,100,0);
        canvas.drawPath(mPath,mPaint);
    }
}
