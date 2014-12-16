package com.phy0312.shopassistant.view.expandtextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.tools.StringUtils;

/**
 * description: 可缩放TextView<br/>
 * author: dingdongjin_91<br/>
 * date: 2014/12/16<br/>
 */
public class ExpandCollapseTextView extends FrameLayout
        implements View.OnClickListener {
    public static final String TAG = ExpandCollapseTextView.class.getSimpleName();

    private static final int DEFAULT_MAX_LINE = 2;
    private static final int DEFAULT_TEXT_SIZE = 15;
    private boolean isExpand;
    private boolean isExpandEnable = true;
    private boolean isInit;
    private Drawable mCollapseDrawable;
    private String mCollapseText;
    private int mCollapseTextColor;
    private TextView mContent;
    private TextView mExpandButton;
    private Drawable mExpandDrawable;
    private String mExpandText;
    private int mExpandTextColor;
    private int mGravity;
    private int mMaxLine;
    private int mPaddingBottom;
    private int mPaddingLeft;
    private int mPaddingRight;
    private int mPaddingTop;
    private int mSpan;

    public ExpandCollapseTextView(Context context) {
        this(context, null);
    }

    public ExpandCollapseTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandCollapseTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        Drawable defaultExpandDrawable = getResources().getDrawable(R.drawable.expand_icon);
        Drawable defaultCollapseDrawable = getResources().getDrawable(R.drawable.collapse_icon);
        String defaultExpandText = getContext().getString(R.string.expand_text);
        String defaultCollapseText = getContext().getString(R.string.collapse_text);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ExpandCollapseTextView, defStyleAttr, 0);

        if (typedArray.getBoolean(R.styleable.ExpandCollapseTextView_ShowDrawable, true)) {
            Drawable expandDrawable = typedArray.getDrawable(R.styleable.ExpandCollapseTextView_expandDrawable);
            if (expandDrawable == null) mExpandDrawable = defaultExpandDrawable;
            else mExpandDrawable = expandDrawable;

            Drawable collapseDrawable = typedArray.getDrawable(R.styleable.ExpandCollapseTextView_collapseDrawable);
            if (collapseDrawable == null) mCollapseDrawable = defaultCollapseDrawable;
            else mCollapseDrawable = collapseDrawable;

            int drawableWidth = typedArray.getDimensionPixelSize(R.styleable.ExpandCollapseTextView_drawableWidth, 0);
            int drawableHeight = typedArray.getDimensionPixelSize(R.styleable.ExpandCollapseTextView_drawableHeight, 0);

            if (drawableWidth == 0) {
                drawableWidth = mExpandDrawable.getMinimumWidth();
            }

            if (drawableHeight == 0) {
                drawableHeight = mExpandDrawable.getMinimumHeight();
            }

            mExpandDrawable.setBounds(0, 0, drawableWidth, drawableHeight);
            mCollapseDrawable.setBounds(0, 0, drawableWidth, drawableHeight);
        }

        mSpan = typedArray.getDimensionPixelSize(R.styleable.ExpandCollapseTextView_span, 0);

        String tmpExpandText = typedArray.getString(R.styleable.ExpandCollapseTextView_expandText);
        mExpandText = StringUtils.isEmpty(tmpExpandText) ? defaultExpandText : tmpExpandText;

        String tmpCollapseText = typedArray.getString(R.styleable.ExpandCollapseTextView_collapseText);
        mCollapseText = StringUtils.isEmpty(tmpCollapseText) ? defaultCollapseText : tmpCollapseText;

        String txtContent = typedArray.getString(R.styleable.ExpandCollapseTextView_text);
        this.mMaxLine = typedArray.getInteger(R.styleable.ExpandCollapseTextView_maxLine, DEFAULT_MAX_LINE);

        int drawPadding = typedArray.getDimensionPixelSize(R.styleable.ExpandCollapseTextView_drawablePadding, 0);

        int textSize = typedArray.getDimensionPixelSize(R.styleable.ExpandCollapseTextView_textSize, 0);
        if (textSize == 0) {
            textSize = DEFAULT_TEXT_SIZE;
        }

        int textColor = typedArray.getColor(R.styleable.ExpandCollapseTextView_textColor, Color.BLACK);

        this.mGravity = typedArray.getInt(R.styleable.ExpandCollapseTextView_expand_gravity, 0);
        this.mExpandTextColor = typedArray.getColor(R.styleable.ExpandCollapseTextView_expandTextColor, textColor);
        this.mCollapseTextColor = typedArray.getColor(R.styleable.ExpandCollapseTextView_collapseTextColor, textColor);

        this.mPaddingTop = getPaddingTop();
        this.mPaddingLeft = getPaddingLeft();
        this.mPaddingRight = getPaddingRight();
        this.mPaddingBottom = getPaddingBottom();

        TextView textView = new TextView(context);
        this.mContent = textView;
        this.mContent.setText(txtContent);
        this.mContent.setMaxLines(this.mMaxLine);
        this.mContent.setTextSize(0, textSize);

        addView(this.mContent, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView tv_expandButton = new TextView(context);
        this.mExpandButton = tv_expandButton;
        this.mExpandButton.setTextColor(this.mExpandTextColor);
        this.mExpandButton.setTextSize(0, textSize);
        addView(this.mExpandButton, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        this.mExpandButton.setCompoundDrawablePadding(drawPadding);
        this.mExpandButton.setOnClickListener(this);
        this.isExpand = false;

        boolean expandBold = typedArray.getBoolean(R.styleable.ExpandCollapseTextView_expandTextBold, true);
        if (expandBold) this.mExpandButton.setTypeface(Typeface.DEFAULT_BOLD);
        typedArray.recycle();
        changeState(this.isExpand);
    }


    private void changeState(boolean isExpand) {
        if (isExpand) {
            this.mContent.setMaxLines(Integer.MAX_VALUE);
            this.mExpandButton.setCompoundDrawables(null, null, this.mCollapseDrawable, null);
            this.mExpandButton.setText(this.mCollapseText);
            this.mExpandButton.setTextColor(this.mCollapseTextColor);
            return;
        } else {
            this.mContent.setMaxLines(this.mMaxLine);
            this.mExpandButton.setCompoundDrawables(null, null, this.mExpandDrawable, null);
            this.mExpandButton.setText(this.mExpandText);
            this.mExpandButton.setTextColor(this.mExpandTextColor);
        }
    }

    @Override
    public void onClick(View v) {
        this.isExpand = !this.isExpand;
        changeState(isExpand);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        getChildAt(0).measure(
                View.MeasureSpec.makeMeasureSpec(
                    View.MeasureSpec.getSize(widthMeasureSpec) - this.mPaddingLeft - this.mPaddingRight, MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, 0));
        getChildAt(1).measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));

        int height;
        if(isExpandEnable) {
            height = getChildAt(0).getMeasuredHeight() + getChildAt(1).getMeasuredHeight() + this.mPaddingTop + this.mPaddingBottom + this.mSpan;
        }else {
            height = getChildAt(0).getMeasuredHeight() + this.mPaddingTop + this.mPaddingBottom;
        }
        setMeasuredDimension(widthMeasureSpec, height);
    }



    public Drawable getCollapseDrawable() {
        return this.mCollapseDrawable;
    }

    public Drawable getExpandDrawable() {
        return this.mExpandDrawable;
    }

    public CharSequence getExpandText() {
        return this.mExpandButton.getText();
    }

    public int getGravity() {
        return this.mGravity;
    }

    public int getMaxLine() {
        return this.mMaxLine;
    }

    public int getSpan() {
        return this.mSpan;
    }

    public CharSequence getText() {
        return this.mContent.getText();
    }

    public String getmCollapseText() {
        return this.mCollapseText;
    }

    public void setPadding(int mPaddingLeft, int mPaddingTop, int mPaddingRight, int mPaddingBottom) {
        this.mPaddingTop = mPaddingTop;
        this.mPaddingLeft = mPaddingLeft;
        this.mPaddingRight = mPaddingRight;
        this.mPaddingBottom = mPaddingBottom;
        super.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
    }


    public void setCollapseDrawable(Drawable paramDrawable) {
        this.mCollapseDrawable = paramDrawable;
    }

    public void setCollapseText(int paramInt) {
        setCollapseText(getContext().getResources().getText(paramInt));
    }

    public void setCollapseText(CharSequence paramCharSequence) {
        this.mCollapseText = paramCharSequence.toString();
    }

    public void setExpandDrawable(Drawable paramDrawable) {
        this.mExpandDrawable = paramDrawable;
    }

    public void setExpandText(int paramInt) {
        setExpandText(getContext().getResources().getText(paramInt));
    }

    public void setExpandText(CharSequence paramCharSequence) {
        this.mExpandText = paramCharSequence.toString();
    }

    public void setGravity(int paramInt) {
        this.mGravity = paramInt;
    }

    public void setMaxLine(int paramInt) {
        this.mMaxLine = paramInt;
    }

    public void setSpan(int paramInt) {
        this.mSpan = paramInt;
    }

    public void setText(int resId) {
        setText(getContext().getResources().getText(resId));
    }

    public void setText(CharSequence text) {
        this.mContent.setText(text);
        this.isInit = false;
        requestLayout();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if(!this.isInit) {
            int lineCount = this.mContent.getLineCount();
            if(lineCount > this.mMaxLine) {
                this.mContent.setEllipsize(TextUtils.TruncateAt.END);
                this.isExpandEnable = true;
                this.isInit = true;
            }else{
                this.mContent.setMaxLines(lineCount);
                this.isExpandEnable = false;
            }
        }
        View expandText = getChildAt(0);
        expandText.layout(this.mPaddingLeft, this.mPaddingTop, this.mPaddingLeft+expandText.getMeasuredWidth(),
                this.mPaddingTop+expandText.getMeasuredHeight());
        int tmpRight;
        int tmpLeft;
        if(this.isExpandEnable) {
            View expandButton = getChildAt(1);
            if((0x5 & this.mGravity) == 0) {
                tmpRight = this.mPaddingLeft;
                tmpLeft = expandButton.getMeasuredWidth() + this.mPaddingLeft;
            }else{
                tmpRight = right - this.mPaddingLeft - getLeft();
                tmpLeft = tmpRight - expandButton.getMeasuredWidth();
            }
            int tmpTop = expandText.getMeasuredHeight() + this.mPaddingTop + this.mSpan;
            expandButton.layout(tmpLeft, tmpTop, tmpRight, tmpTop+expandButton.getMeasuredHeight());
        }
    }
}
