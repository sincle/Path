package com.haieros.path.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by Kang on 2018/4/16.
 */

public class PathFillType extends AbView {

    private Path mPath1;

    public PathFillType(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void start() {

    }

    @Override
    protected void initPath() {
        mPath = new Path();
        mPath1 = new Path();
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPath.addCircle(300,200,100, Path.Direction.CW);
        mPath.addCircle(200,200,100, Path.Direction.CW);
        // 默认取相交区域
        mPath.setFillType(Path.FillType.WINDING);
       // mPath.setFillType(Path.FillType.EVEN_ODD);
        mPath.setFillType(Path.FillType.INVERSE_WINDING);
       // mPath.setFillType(Path.FillType.INVERSE_EVEN_ODD);
        canvas.drawPath(mPath,mPaint);
    }
}
