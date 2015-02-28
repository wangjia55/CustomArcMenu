package com.jacob.arcmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * Package : com.jacob.arcmenu
 * Author : jacob
 * Date : 15-2-27
 * Description : 这个类是用来xxx
 */
public class LeMenu extends ViewGroup implements View.OnClickListener {
    private int distance = 100;
    private Status status = Status.CLOSE;

    enum Status {
        OPEN, CLOSE
    }

    private OnLeMenuClickListener menuClickListener;

    public interface OnLeMenuClickListener {
        void onLeMenuClick(View view, int position);
    }

    public void setOnMenuClickListener(OnLeMenuClickListener listener) {
        menuClickListener = listener;
    }

    public LeMenu(Context context) {
        this(context, null);
    }

    public LeMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LeMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LeMenu);
        distance = (int) typedArray.getDimension(R.styleable.LeMenu_distance,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100
                        , getResources().getDisplayMetrics()));
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            setClosePosition();
            setMenuPosition();
        }
    }

    private void setMenuPosition() {
        int count = getChildCount();
        int w = getMeasuredWidth() / (count - 1);
        for (int i = 1; i < count; i++) {
            View child = getChildAt(i);
            child.setVisibility(GONE);
            int cl = w * (i - 1) + (w - child.getMeasuredWidth()) / 2;
            int ct = getMeasuredHeight() - child.getMeasuredHeight() - distance;

            int cw = child.getMeasuredWidth();
            int ch = child.getMeasuredHeight();
            child.layout(cl, ct, cl + cw, ct + ch);
        }
    }

    private void setClosePosition() {
        View child = getChildAt(0);
        int cl = (getMeasuredWidth() - child.getMeasuredWidth()) / 2;
        int ct = getMeasuredHeight() - child.getMeasuredHeight();
        child.layout(cl, ct, cl + child.getMeasuredWidth(), ct + child.getMeasuredHeight());
        child.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        toggleMenu(300);
        changeStatus();
    }


    private void toggleMenu(int duration) {
        int count = getChildCount();
        for (int i = 1; i < count; i++) {
            final View child = getChildAt(i);
            final int index = i;
            child.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (menuClickListener != null) {
                        menuClickListener.onLeMenuClick(child, index);
                    }
                    showSelectAnim(v, index);
                }


            });
            if (status == Status.CLOSE) {
                //open
                TranslateAnimation translateAnimation = new TranslateAnimation(0f, 0f, getMeasuredHeight() - child.getY(), 0f);
                translateAnimation.setDuration(duration);
                translateAnimation.setFillAfter(true);
                switch (i) {
                    case 1:
                        translateAnimation.setStartOffset(50 * 2);
                        break;
                    case 2:
                        translateAnimation.setStartOffset(30 * 2);
                        break;
                    case 3:
                        translateAnimation.setStartOffset(80 * 2);
                        break;
                    case 4:
                        translateAnimation.setStartOffset(75 * 2);
                        break;
                }

                child.startAnimation(translateAnimation);

            } else {
                //close
                TranslateAnimation translateAnimation = new TranslateAnimation(0f, 0f, 0f, getMeasuredHeight() - child.getY());
                translateAnimation.setDuration(duration);
                translateAnimation.setFillAfter(true);
//                translateAnimation.setStartOffset(i*25);
                switch (i) {
                    case 1:
                        translateAnimation.setStartOffset(50 * 2);
                        break;
                    case 2:
                        translateAnimation.setStartOffset(30 * 2);
                        break;
                    case 3:
                        translateAnimation.setStartOffset(80 * 2);
                        break;
                    case 4:
                        translateAnimation.setStartOffset(70 * 2);
                        break;
                }
                child.startAnimation(translateAnimation);
            }
        }

    }

    private void showSelectAnim(View v, int index) {
        int count = getChildCount();
        for (int i = 1; i < count; i++) {
            View child = getChildAt(i);
            if (i == index) {
                ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 2f, 1f, 2f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF,
                        0.5f);
                scaleAnimation.setDuration(300);
                child.startAnimation(scaleAnimation);

            } else {
                TranslateAnimation translateAnimation = new TranslateAnimation(0f, 0f, 0f, getMeasuredHeight() - child.getY());
                translateAnimation.setDuration(300);
                translateAnimation.setFillAfter(true);
//                translateAnimation.setStartOffset(i*25);
                switch (i) {
                    case 1:
                        translateAnimation.setStartOffset(50 * 2);
                        break;
                    case 2:
                        translateAnimation.setStartOffset(30 * 2);
                        break;
                    case 3:
                        translateAnimation.setStartOffset(80 * 2);
                        break;
                    case 4:
                        translateAnimation.setStartOffset(70 * 2);
                        break;
                }
                child.startAnimation(translateAnimation);
            }
        }
        changeStatus();
    }

    private void changeStatus() {
        status = (status == Status.CLOSE ? Status.OPEN : Status.CLOSE);
    }
}
