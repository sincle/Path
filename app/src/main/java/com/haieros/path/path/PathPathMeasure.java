package com.haieros.path.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Kang on 2018/4/17.
 */

public class PathPathMeasure extends View {

    private final Paint mPaint;
    private final Path mPath;

    public PathPathMeasure(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(getWidth() / 2, getHeight() / 2);          // 平移坐标系

        mPath.moveTo(0,200);
//        mPath.lineTo(200,200);
//        mPath.lineTo(200,-200);
//        mPath.lineTo(-200,-200);
//        mPath.lineTo(-200,200);
//        mPath.close();
        mPath.addRect(-200, -200, 200, 200, Path.Direction.CW);
        Path dst = new Path();
        dst.lineTo(-300,-300);// 创建用于存储截取后内容的 Path
        PathMeasure measure = new PathMeasure(mPath, false);         // 将 Path 与 PathMeasure 关联

        // 截取一部分存入dst中，并使用 moveTo 保持截取得到的 Path 第一个点的位置不变
        // false  从原点开始
        measure.getSegment(200, 600, dst, true);

        canvas.drawPath(dst, mPaint);                        // 绘制 dst
    }
}
