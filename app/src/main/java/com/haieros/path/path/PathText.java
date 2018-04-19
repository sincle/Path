package com.haieros.path.path;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.LinearInterpolator;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Kang on 2018/4/13.
 */

public class PathText extends CoordinateView {

    private Paint paint;
    private String mText;
    private Path path;
    private PathMeasure pathMeasure;
    private float totalLength;
    private Paint mPathPaint;
    private Path mDest;
    private ArrayList<Float> childs;
    private PathMeasure destMeasure;
    private float otherLength = 0;
    private float destLength;
    private float more = 0f;
    private DecimalFormat mDec;
    private float nowLength;
    private ValueAnimator valueAnimator;
    private float animatedValue;

    public PathText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initPath() {

        mText = "OO";
        path = new Path();
        path.addCircle(0, 0, 200, Path.Direction.CW);
        paint.getTextPath(mText, 0, mText.length(), 0, paint.getTextSize(), path);
        pathMeasure = new PathMeasure();
        pathMeasure.setPath(path, false);
        childs = new ArrayList<>();
        totalLength = 0f;

        while (pathMeasure.nextContour()) {
            float length = pathMeasure.getLength();
            Log.e("kang", "length:" + length);
            totalLength += length;
            childs.add(length);
        }

        Log.e("kang", "totalLength:" + totalLength);
        mDest = new Path();
        destMeasure = new PathMeasure();
        initAnimator();
    }

    @Override
    public void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(30);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(400);

        mPathPaint = new Paint();
        mPathPaint.setColor(Color.BLACK);
        mPathPaint.setStrokeWidth(2);
        //mPathPaint.setTextSize(300);
        mPathPaint.setStyle(Paint.Style.STROKE);
        mDec = new DecimalFormat(".#");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawCircle(0,0,200,paint);
        canvas.drawText(mText, 0, 0, paint);
        //canvas.translate(0,200);
        canvas.drawPath(mDest, mPathPaint);
        drawHead(canvas);
    }

    private void drawHead(Canvas canvas) {
        float[] pos = new float[2];
        float[] tan = new float[2]; //tan X = tan[0] / tan[1]
        //获取指定distance距离的 tan 和 pos distance 在 每一段 path的范围(0,path.getLength()) 之间,所以此处要减去之前的 length和
        boolean posTan = pathMeasure.getPosTan(animatedValue * totalLength - otherLength, pos, tan);
        Log.e("kang", "result:" + posTan + ",pos[0]:" + pos[0] + ",pos[1]:" + pos[1] + "------:tan[0]:" + tan[0] + ",tan[1]:" + tan[1]);
        float degree = (float) ((float) (Math.atan2(tan[1], tan[0]) * 180) / Math.PI);
        Log.e("kang", "degree:" + degree);
        Path path = new Path();
        path.moveTo(pos[0], pos[1]);
        float x = (float) (150 * Math.cos(Math.atan2(tan[1], tan[0])));
        float y = (float) (150 * Math.sin(Math.atan2(tan[1], tan[0])));
        Log.e("kang", "x:" + x + ",y:" + y);
        path.lineTo(pos[0] + x, pos[1] + y);
        canvas.drawPath(path, mPathPaint);
    }

    public void initAnimator() {
        valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(10000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animatedValue = (float) animation.getAnimatedValue();
                anim(animatedValue);
                invalidate();
            }
        });
    }

    public void start() {
        mDest.reset();
        pathMeasure.setPath(path, true);
        clear();
        if (valueAnimator.isRunning()) {
            valueAnimator.cancel();
        }
        valueAnimator.start();
    }

    private void anim(float animatedValue) {
        //限制长度
        float limitLength = pathMeasure.getLength() + otherLength;
//        Log.e("kang1", "limitLength:" + limitLength);

        //浮点对比 精度一位 之后的忽略
        float desFloat = subString(destLength);
        float limitFLoat = subString(limitLength);
//        Log.e("kang", "desFloat:"+desFloat+",limitFLoat:"+limitFLoat);
        if (desFloat < limitFLoat) {
            float start = destLength - otherLength;
            float end = animatedValue * totalLength - otherLength;
//            Log.e("kang", "start:" + start + ",end:" + end + ",-------:" + (end - start)+"-------------:"+(limitLength - destLength));
            //切换 path 是 如果截取的path 长度 超过 目标
            if (limitLength - destLength < end - start) {
                more = (end - start) - (limitLength - destLength);
                end = start + limitLength - destLength;
                Log.i("kang", "最后一次需要补偿:" + more);
            }  //获取start - end 段内的path
//            Log.d("kang","start:"+start+",end:"+end+",result:"+(end - start));
            pathMeasure.getSegment(start, end, mDest, true);

            if (animatedValue == 1.0f) {
                //补偿最后一次
                pathMeasure.getSegment(0, more, mDest, true);
            }
        } else {
            //绘制后的 path段 总长度
            otherLength += pathMeasure.getLength();
//            Log.e("kang1", "otherLength:" + otherLength);
            //下一段path
            pathMeasure.nextContour();
            Log.i("kang", "每段结束后需要补偿:" + more + "");
            //补偿 进入else操作后 animatedValue变化导致的
            if (more != 0f) {
                pathMeasure.getSegment(0, more, mDest, true);
                more = 0f;
            }
        }
        //目标长度 可变 在这里更新
        destLength = getDestLength(mDest);
//        Log.i("kang", "destLength:" + destLength);
    }

    private float subString(float value) {
        String temp = String.valueOf(value);
        int index = temp.indexOf('.');
        String result = temp.substring(0, index + 2);
        return Float.parseFloat(result);
    }

    private float getDestLength(Path path) {
        int count = 0;
        destMeasure.setPath(path, false);
        float sum = 0.0f;
        while (destMeasure.nextContour()) {
            float length = destMeasure.getLength();
            sum += length;
            count++;
        }
//        Log.e("kang", "count:"+count);
        return sum;
    }

    public void clear() {
        mDest.reset();
        otherLength = 0.0f;
        more = 0.0f;
        destLength = 0.0f;
    }
}
