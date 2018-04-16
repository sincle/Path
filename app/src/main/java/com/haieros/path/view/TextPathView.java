package com.haieros.path.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.haieros.path.R;

/**
 * Created by Kang on 2018/4/13.
 */

public class TextPathView extends View {

    private String mText;
    private Context mContext;
    private Paint mPaint;
    private Path mTextPath;

    public TextPathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(context,attrs);
        initPaint();
        initPathText();
    }

    private void initPaint() {
        mPaint = new Paint();
        // 抗锯齿
        mPaint.setAntiAlias(true);
        //设置画笔颜色
        mPaint.setColor(Color.RED);
        //设置描边
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        //笔帽
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    private void initPathText() {
        mTextPath = new Path();
        mPaint.getTextPath(mText,0,mText.length(),0,mPaint.getTextSize(),mTextPath);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        this.mContext = context;
        if (null != attrs) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.path_text_view);
            mText = typedArray.getString(R.styleable.path_text_view_path_text);
            typedArray.recycle();
        }
    }
}
