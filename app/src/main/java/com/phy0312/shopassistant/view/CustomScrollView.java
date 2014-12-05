package com.phy0312.shopassistant.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * description: 自定义滚动View<br/>
 * author: dingdj<br/>
 * date: 2014/12/5<br/>
 */
public class CustomScrollView extends ScrollView {

    private GestureDetector mGestureDetector = new GestureDetector(getContext(), new YScrollDetector());

    View.OnTouchListener mGestureListener;

    public CustomScrollView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        setFadingEdgeLength(0);
    }

    public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
        return (super.onInterceptTouchEvent(paramMotionEvent)) && (this.mGestureDetector.onTouchEvent(paramMotionEvent));
    }


    public boolean onScroll(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2) {
        return Math.abs(paramFloat2) > Math.abs(paramFloat1);
    }

    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        YScrollDetector() {
        }
    }
}
