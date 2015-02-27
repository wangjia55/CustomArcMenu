package com.jacob.arcmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * Package : com.jacob.arcmenu
 * Author : jacob
 * Date : 15-2-27
 * Description : 这个类是用来xxx
 */
public class ArcMenu extends ViewGroup implements View.OnClickListener {

    private static final int POS_LEFT_TOP = 0;
    private static final int POS_RIGHT_TOP = 1;
    private static final int POS_LEFT_BOTTOM = 2;
    private static final int POS_RIGHT_BOTTOM = 3;

    private Position mPosition = Position.POS_RIGHT_BOTTOM;
    private OnMenuClickListener onMenuClickLisetener;


    enum Position {
        POS_LEFT_TOP,
        POS_RIGHT_TOP,
        POS_LEFT_BOTTOM,
        POS_RIGHT_BOTTOM
    }

    private Status mStatus = Status.CLOSE;

    enum Status {
        CLOSE, OPEN
    }

    private int mRadius = 100;


    public interface OnMenuClickListener {
        public void onclick(View v, int position);
    }

    public void setOnMenuClickListener(OnMenuClickListener listener) {
        this.onMenuClickLisetener = listener;
    }

    public ArcMenu(Context context) {
        this(context, null);
    }

    public ArcMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());

        TypedArray typedArray = context.getResources().obtainAttributes(attrs, R.styleable.ArcMenu);
        int pos = typedArray.getInt(R.styleable.ArcMenu_position, 0);
        switch (pos) {
            case POS_LEFT_TOP:
                mPosition = Position.POS_LEFT_TOP;
                break;
            case POS_RIGHT_TOP:
                mPosition = Position.POS_RIGHT_TOP;
                break;
            case POS_LEFT_BOTTOM:
                mPosition = Position.POS_LEFT_BOTTOM;
                break;
            case POS_RIGHT_BOTTOM:
                mPosition = Position.POS_RIGHT_BOTTOM;
                break;
        }

        mRadius = (int) typedArray.getDimension(R.styleable.ArcMenu_radius,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics()));
        typedArray.recycle();
        Log.e("TAG", mPosition + "--" + mRadius);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            setCorePosition();
            setMenuPosition();
        }
    }

    /**
     * 设置菜单的位置
     */
    private void setMenuPosition() {
        int count = getChildCount();

        for (int i = 1; i < count; i++) {
            View child = getChildAt(i);
            child.setVisibility(GONE);
            int cl = (int) (mRadius * Math.sin((Math.PI / 2 / (count - 2)) * (i - 1)));
            int ct = (int) (mRadius * Math.cos((Math.PI / 2 / (count - 2)) * (i - 1)));

            int cw = child.getMeasuredWidth();
            int ch = child.getMeasuredHeight();

            if (mPosition == Position.POS_RIGHT_BOTTOM || mPosition == Position.POS_RIGHT_TOP) {
                cl = getMeasuredWidth() - cw - cl;
            }

            if (mPosition == Position.POS_LEFT_BOTTOM || mPosition == Position.POS_RIGHT_BOTTOM) {
                ct = getMeasuredHeight() - ch - ct;
            }

//            Log.e("TAG", child + "***" + cl + "**" + ct + "**" + angle);
            child.layout(cl, ct, cl + cw, ct + ch);
        }

    }

    /**
     * 确定主按钮图标的位置
     */
    private void setCorePosition() {
        View coreView = getChildAt(0);
        int mt = 0;
        int ml = 0;

        switch (mPosition) {
            case POS_LEFT_TOP:
                mt = 0;
                ml = 0;
                break;
            case POS_RIGHT_TOP:
                mt = 0;
                ml = getMeasuredWidth() - coreView.getMeasuredWidth();
                break;
            case POS_LEFT_BOTTOM:
                mt = getMeasuredHeight() - coreView.getMeasuredHeight();
                ml = 0;
                break;
            case POS_RIGHT_BOTTOM:
                mt = getMeasuredHeight() - coreView.getMeasuredHeight();
                ml = getMeasuredWidth() - coreView.getMeasuredWidth();
                break;
        }

        int mr = ml + coreView.getMeasuredWidth();
        int mb = mt + coreView.getMeasuredHeight();

        coreView.layout(ml, mt, mr, mb);

        coreView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Log.e("TAG", "onclick");
        rotateCoreView(v, 0, 360, 300);
        toggleButton(300);
    }

    private void rotateCoreView(View v, int start, int end, int duration) {
        RotateAnimation rotateAnimation = new RotateAnimation(start, end, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(duration);
        rotateAnimation.setFillAfter(true);
        v.startAnimation(rotateAnimation);
    }

    private void toggleButton(int druation) {
        int count = getChildCount();
        for (int i = 1; i < count; i++) {
            final View child = getChildAt(i);
            child.setVisibility(View.VISIBLE);
            final int index = i;
            child.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onMenuClickLisetener != null) {
                        onMenuClickLisetener.onclick(child, index);
                        showSelectAnim(index);
                        changeStatus();
                    }
                }
            });

            int xflag = 1;
            int yflag = 1;

            int cl = (int) (mRadius * Math.sin((Math.PI / 2 / (count - 2)) * (i - 1)));
            int ct = (int) (mRadius * Math.cos((Math.PI / 2 / (count - 2)) * (i - 1)));

            if (mPosition == Position.POS_LEFT_TOP || mPosition == Position.POS_LEFT_BOTTOM) {
                xflag = -1;
            }

            if (mPosition == Position.POS_LEFT_TOP || mPosition == Position.POS_RIGHT_TOP) {
                yflag = -1;
            }

            if (mStatus == Status.CLOSE) {
                AnimationSet animationSet = new AnimationSet(true);
                TranslateAnimation translateAnimation = new TranslateAnimation(xflag * cl, 0f, yflag * ct, 0f);
                translateAnimation.setFillAfter(true);
                translateAnimation.setDuration(druation);

                RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setDuration(druation);
                rotateAnimation.setFillAfter(false);

                animationSet.addAnimation(rotateAnimation);
                animationSet.addAnimation(translateAnimation);
                animationSet.setStartOffset(i * 20);

                animationSet.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        child.setVisibility(VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                child.startAnimation(animationSet);
            } else {
                AnimationSet animationSet = new AnimationSet(true);

                TranslateAnimation translateAnimation = new TranslateAnimation(0f, xflag * cl, 0f, yflag * ct);
                translateAnimation.setFillAfter(true);
                translateAnimation.setDuration(druation);

                RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setDuration(druation);
                rotateAnimation.setFillAfter(false);

                animationSet.addAnimation(rotateAnimation);
                animationSet.addAnimation(translateAnimation);
                animationSet.setStartOffset(i * 20);
                child.startAnimation(animationSet);

                animationSet.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        child.setVisibility(GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }


        }


        changeStatus();

    }

    private void showSelectAnim(int index) {
        int count = getChildCount();

        for (int i = 1; i < count; i++) {
            final View child = getChildAt(i);
            Log.e("TAG", index + "");
            if (index == i) {
                AnimationSet animationSet = new AnimationSet(true);
                ScaleAnimation scaleAnimation = new ScaleAnimation(0, 3f, 0, 3f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF,
                        0.5f);
                AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
                animationSet.setDuration(300);
                animationSet.addAnimation(scaleAnimation);
                animationSet.addAnimation(alphaAnimation);
                animationSet.setFillAfter(true);
                child.startAnimation(animationSet);

            } else {
                AnimationSet animationSet = new AnimationSet(true);
                Animation anim = new ScaleAnimation(1.0f, 0f, 1.0f, 0f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF,
                        0.5f);
                animationSet.setDuration(300);
                animationSet.setFillAfter(true);
                animationSet.addAnimation(anim);
                child.startAnimation(animationSet);
            }
        }
    }

    public void changeStatus() {
        mStatus = (mStatus == Status.CLOSE ? Status.OPEN : Status.CLOSE);
    }
}
