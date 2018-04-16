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
 * Created by Kang on 2018/4/13.
 */

public class PathText extends View {

    private Paint paint;
    private String mText = "KangKang";
    private Path path;

    public PathText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(200);

        path = new Path();

        //paint.getTextPath(mText,0,mText.length(),0,0,path);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        path.lineTo(0,100);
        path.lineTo(100,200);
        path.lineTo(200,0);
       canvas.drawPath(path,paint);
    }
}
