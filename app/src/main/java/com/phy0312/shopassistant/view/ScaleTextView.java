package com.phy0312.shopassistant.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * description: 点击可缩放textview<br/>
 * author: dingdj<br/>
 * date: 2014/11/24<br/>
 */
public class ScaleTextView extends TextView {

    /**
     * 是否被点击*/
    boolean isOnTouchScaleState=false;
    /**
     * 点击地缩小的系数
     * */
    float onTouchScale=0.9f;

    public ScaleTextView(Context context) {
        super(context);
    }

    public ScaleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScaleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if(isOnTouchScaleState){
            isOnTouchScaleState = false;
            canvas.scale(onTouchScale, onTouchScale, this.getWidth()/2, this.getHeight()/2);
        }
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                isOnTouchScaleState=true;
                this.invalidate();
                break;
            case MotionEvent.ACTION_UP:
                isOnTouchScaleState=false;
                this.invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
                isOnTouchScaleState=false;
                this.invalidate();
                break;
            default:
                break;

        }
        return super.onTouchEvent(event);
    }

}
