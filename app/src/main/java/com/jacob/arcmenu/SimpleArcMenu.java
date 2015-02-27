package com.jacob.arcmenu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Package : com.jacob.arcmenu
 * Author : jacob
 * Date : 15-2-27
 * Description : 这个类是用来xxx
 */
public class SimpleArcMenu extends ViewGroup {


    public SimpleArcMenu(Context context) {
        this(context, null);
    }

    public SimpleArcMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleArcMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            positionMainMenu();
            positionChildMenu();
        }
    }

    private void positionChildMenu() {
        View mainView = getChildAt(0);
        View childView1 = getChildAt(1);
        int cl1 = (getMeasuredWidth() - childView1.getMeasuredWidth() - mainView.getMeasuredWidth()) / 2 + mainView.getMeasuredWidth();
        int ct1 = (getMeasuredHeight() - childView1.getMeasuredWidth()) / 2;

        int cr1 = cl1 + childView1.getMeasuredWidth();
        int cb1 = ct1 + childView1.getMeasuredHeight();
        childView1.layout(cl1, ct1, cr1, cb1);

        View childView2 = getChildAt(2);
        int radius = (int) ((mainView.getMeasuredWidth() + childView2.getMeasuredWidth()) / 2 * Math.sin(45));
        int ct2 = (int) (mainView.getY() - (radius - mainView.getHeight() / 2));
        int cl2 = (int) (mainView.getX() + mainView.getMeasuredWidth() / 2 + (radius - childView2.getMeasuredWidth() / 2));

        int cr2 = cl2 + childView2.getMeasuredWidth();
        int cb2 = ct2 + childView2.getMeasuredHeight();
        childView2.layout(cl2, ct2, cr2, cb2);

        View childView3 = getChildAt(3);


        int ct3 = (int) (ct2+((mainView.getMeasuredHeight()+childView3.getMeasuredHeight())*1.0/2/Math.sin(45)));

        int cr3 = cl2 + childView3.getMeasuredWidth();
        int cb3 = ct3 + childView3.getMeasuredHeight();
        childView3.layout(cl2, ct3, cr3, cb3);
    }

    private void positionMainMenu() {
        View mainView = getChildAt(0);
        View childView = getChildAt(1);

        int ml = (getMeasuredWidth() - childView.getMeasuredWidth() - mainView.getMeasuredWidth()) / 2;
        int mt = (getMeasuredHeight() - mainView.getMeasuredWidth()) / 2;

        int mr = ml + mainView.getMeasuredWidth();
        int mb = mt + mainView.getMeasuredHeight();

        mainView.layout(ml, mt, mr, mb);
    }
}
