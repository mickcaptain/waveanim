package com.flyingwings.cn.myapplication;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Filedescription.
 *
 * @author mick
 * @date 2019/4/19 17:42
 */

public class WaveAnimView extends View implements Runnable {

    private int mMeasuredHeight;
    private int mMeasuredWidth;
    private PointF mCenterPoint;
    private int mRadis;
    private Paint mPaintCricleOne;
    private Paint mPaintCricleTwo;
    private Paint mPaintCricleThree;
    private ValueAnimator mValueAnimator;
    private int mCricleTwoRadis;
    private int mCricleThreeRadis;
    private int minRadis;
    private boolean mPause;
    private int mCricleOneRadis;

    private int paintOneAlpha;
    private int paintTwoAlpha;
    private int paintThreeAlpha;
    private int mDiff;
    private int mDiffAlpha;
    private float mAlphaUtil;

    // 初始化圆的透明度
    private final float alphaOne = 0.1f;
    private final float alphaTwo = 0.5f;
    private final float alphaThree = 0.8f;

    // 重复动画开始时的透明度
    private final float alphaStart = 0.8f;

    // 动画幅度
    private final int cell = 2;

    // 动画刷新时间
    private final int timeDiffUpdate = 50;


    public WaveAnimView(Context context) {
        this(context, null);
    }

    public WaveAnimView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveAnimView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMeasuredHeight = getMeasuredHeight();
        mMeasuredWidth = getMeasuredWidth();

        mRadis = Math.abs(Math.min(mMeasuredHeight, mMeasuredWidth)) / 2;

        Log.d("mick", " mMeasuredHeight : " + mMeasuredHeight + " , mMeasuredWidth : " + mMeasuredWidth + " , mRadis : " + mRadis);

        mCenterPoint = new PointF();
        mCenterPoint.x = mMeasuredWidth / 2;
        mCenterPoint.y = mMeasuredHeight / 2;


        mCricleOneRadis = mRadis;
        mCricleTwoRadis = mRadis - mRadis / 8;
        mCricleThreeRadis = mRadis - 3 * mRadis / 8;
        minRadis = mRadis / 2;

        mDiff = mRadis - minRadis;
        if (mDiff == 0) {
            return;
        }
        mDiffAlpha = paintThreeAlpha - paintOneAlpha;
        mAlphaUtil = mDiffAlpha / (float) mDiff;

    }

    private void init() {
        paintOneAlpha = (int) (alphaOne * 255);
        paintTwoAlpha = (int) (alphaTwo * 255);
        paintThreeAlpha = (int) (alphaThree * 255);


        mPaintCricleOne = new Paint();
        mPaintCricleOne.setColor(Color.parseColor("#5BD9C7"));
        mPaintCricleOne.setStyle(Paint.Style.FILL);
        mPaintCricleOne.setAlpha(paintOneAlpha);


        mPaintCricleTwo = new Paint();
        mPaintCricleTwo.setColor(Color.parseColor("#5BD9C7"));
        mPaintCricleTwo.setStyle(Paint.Style.FILL);
        mPaintCricleTwo.setAlpha(paintTwoAlpha);


        mPaintCricleThree = new Paint();
        mPaintCricleThree.setColor(Color.parseColor("#5BD9C7"));
        mPaintCricleThree.setStyle(Paint.Style.FILL);
        mPaintCricleThree.setAlpha(paintThreeAlpha);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, mCricleOneRadis, mPaintCricleOne);
        canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, mCricleTwoRadis, mPaintCricleTwo);
        canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, mCricleThreeRadis, mPaintCricleThree);
    }


    /**
     * 开动画
     * <p>
     */
    public void animStart() {
        postDelayed(this, timeDiffUpdate);


    }


    /**
     * 暂停动画
     */
    private void animPause() {
        mPause = true;
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
        }
    }

    @Override
    public void run() {
        mCricleOneRadis += cell;
        mCricleTwoRadis += cell;
        mCricleThreeRadis += cell;


        if (mCricleOneRadis >= mRadis) {
            mCricleOneRadis = minRadis;
            paintOneAlpha = (int) (alphaStart * 255);
        }

        if (mCricleTwoRadis >= mRadis) {
            mCricleTwoRadis = minRadis;
            paintTwoAlpha = (int) (alphaStart * 255);
        }

        if (mCricleThreeRadis >= mRadis) {
            mCricleThreeRadis = minRadis;
            paintThreeAlpha = (int) (alphaStart * 255);
        }

        paintOneAlpha -= cell * mAlphaUtil;
        paintTwoAlpha -= cell * mAlphaUtil;
        paintThreeAlpha -= cell * mAlphaUtil;


        mPaintCricleOne.setAlpha(paintOneAlpha);
        mPaintCricleTwo.setAlpha(paintTwoAlpha);
        mPaintCricleThree.setAlpha(paintThreeAlpha);

        Log.d("mick","cell * mAlphaUtil : "+(cell * mAlphaUtil)+" paintOneAlpha : "+paintOneAlpha+" , paintTwoAlpha : "+paintTwoAlpha+" , paintThreeAlpha : "+paintThreeAlpha);

        invalidate();
        postDelayed(WaveAnimView.this, timeDiffUpdate);
    }
}
