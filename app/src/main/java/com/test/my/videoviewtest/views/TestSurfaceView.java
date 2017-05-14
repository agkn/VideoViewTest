package com.test.my.videoviewtest.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;


/**
 * @author Andrey Kulik (Andrey.Kulik@ascom.com).
 */

public class TestSurfaceView extends SurfaceView {

    private static final String TAG = "TestSurface";

    public TestSurfaceView(Context context) {
        super(context);
        init();
    }

    public TestSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public TestSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }
    private void init() {
        Log.v(TAG, "Surface is creating.");
        //setBackgroundColor(0);
    }

    @Override
    protected void onAttachedToWindow() {
        Log.v(TAG, "onAttachedToWindow.");
        super.onAttachedToWindow();
        postDelayed(new Runnable() {
            @Override
            public void run() {
                TestSurfaceView.this.setBackgroundColor(0);
            }
        }, 10000);
    }

    @Override
    protected void onDetachedFromWindow() {
        Log.v(TAG, "onDetachedFromWindow.");
        super.onDetachedFromWindow();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.v(TAG, "onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.v(TAG, "onLayout(" + changed + ", " + left + ", " + top + ", " + right + ", " + bottom + ")");
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.v(TAG, "onDraw");
        super.onDraw(canvas);
    }

    @Override
    public void draw(Canvas canvas) {
        Log.v(TAG, "draw");
        super.draw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Log.v(TAG, "dispatchDraw");
        super.dispatchDraw(canvas);
    }
}
