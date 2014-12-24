package com.phy0312.shopassistant.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.phy0312.shopassistant.R;

/**
 * description: <br/>
 * author: dingdj<br/>
 * date: 2014/12/24<br/>
 */
public class LoadingProgressBar extends View {
    private static final long DEFAULT_DELAY = 150L;
    private static final int INCREMENT_PROGRESS_VALUE = 4;
    private RotateAnimation mAnimation;
    private int mDrawableHeight;
    private int mDrawableWidth;
    private Drawable mLoadingAbove;
    private Drawable mLoadingBehind;
    private int mProgress;
    private Rect mRect;

    public LoadingProgressBar(Context context) {
        this(context, null);
    }

    public LoadingProgressBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LoadingProgressBar(Context context, AttributeSet attributeSet, int param) {
        super(context, attributeSet, param);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.LoadingProgressBar, param, 0);
        this.mLoadingAbove = typedArray.getDrawable(R.styleable.LoadingProgressBar_drawableAbove);
        this.mLoadingBehind = typedArray.getDrawable(R.styleable.LoadingProgressBar_drawableBehind);
        this.mDrawableWidth = typedArray.getDimensionPixelSize(R.styleable.LoadingProgressBar_lpr_drawableWidth, this.mLoadingAbove.getIntrinsicWidth());
        this.mDrawableHeight = typedArray.getDimensionPixelSize(R.styleable.LoadingProgressBar_lpr_drawableHeight, this.mLoadingAbove.getIntrinsicHeight());
        typedArray.recycle();
        this.mRect = new Rect();
        this.mAnimation = new RotateAnimation();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mAnimation.startAnimation();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mAnimation.stopAnimation();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mLoadingBehind.setBounds(this.mRect);
        this.mLoadingBehind.draw(canvas);
        canvas.clipRect(0, this.mDrawableHeight - this.mProgress, this.mDrawableWidth, this.mDrawableHeight);
        this.mLoadingAbove.setBounds(this.mRect);
        this.mLoadingAbove.draw(canvas);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(this.mDrawableWidth, this.mDrawableHeight);
        this.mRect.top = 0;
        this.mRect.left = 0;
        this.mRect.bottom = this.mDrawableHeight;
        this.mRect.right = this.mDrawableWidth;
    }

    protected void onVisibilityChanged(View changedView, int visibility) {
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            this.mAnimation.stopAnimation();
            return;
        }
        this.mAnimation.startAnimation();
    }

    public void repaint() {
        if(this.mProgress == this.mDrawableHeight){
            this.mProgress = 0;
        }else{
            this.mProgress = INCREMENT_PROGRESS_VALUE + this.mProgress;
        }
        if (this.mProgress > this.mDrawableHeight) {
            this.mProgress = this.mDrawableHeight;
        }
        invalidate();
    }

    public void setDrawableHeight(int drawableHeight) {
        this.mDrawableHeight = drawableHeight;
    }

    public void setDrawableWidth(int drawableWidth) {
        this.mDrawableWidth = drawableWidth;
    }

    public void setLoadingAbove(Drawable loadingAbove, Drawable loadingBehind) {
        this.mLoadingAbove = loadingAbove;
        this.mLoadingBehind = loadingBehind;
    }

    public void setVisibility(int visibility) {
        if (getVisibility() != visibility) {
            if (visibility == View.GONE || visibility == View.INVISIBLE) {
                this.mAnimation.stopAnimation();
            } else {
                this.mAnimation.startAnimation();
            }
        }
    }

    private class RotateAnimation implements Runnable {

        private boolean isRunning;

        private RotateAnimation() {
        }

        public void run() {
            LoadingProgressBar.this.repaint();
            if (this.isRunning) {
                LoadingProgressBar.this.postDelayed(this, DEFAULT_DELAY);
                return;
            }
            LoadingProgressBar.this.removeCallbacks(this);
            LoadingProgressBar.this.postInvalidate();
        }

        public void startAnimation() {
            LoadingProgressBar.this.removeCallbacks(this);
            this.isRunning = true;
            LoadingProgressBar.this.post(this);
        }

        public void stopAnimation() {
            this.isRunning = false;
        }
    }
}

