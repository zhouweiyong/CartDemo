package com.vst.cartdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by zwy on 2017/7/3.
 * email:16681805@qq.com
 */

public class SwipeLayout extends HorizontalScrollView {

    private ViewGroup mContentView;
    private ViewGroup mSwipeMenuView;
    private boolean isFirst = true;
    private int width;
    private SwipeRecyclerView swipeRecyclerView;
    private SwipeRecyclerView swipeRecyclerView1;


    public SwipeLayout(Context context) {
        this(context, null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.i("zwy","SwipeLayout");
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.setHorizontalScrollBarEnabled(false);
        this.setVerticalScrollBarEnabled(false);
        this.setFocusableInTouchMode(true);
        this.setFocusable(true);
        try {
            LinearLayout mWrapperView = (LinearLayout) getChildAt(0);
            mContentView = (ViewGroup) mWrapperView.getChildAt(0);
            mSwipeMenuView = (ViewGroup) mWrapperView.getChildAt(1);
            mContentView.getLayoutParams().width = w;
            swipeRecyclerView = (SwipeRecyclerView) this.getParent();
            swipeRecyclerView.addSwipeLayout(this);

            Log.i("zwy",">>>>add");
        } catch (Exception e) {
            e.printStackTrace();
        }
        measureChildren(w, h);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        if (changed) {
//            this.setHorizontalScrollBarEnabled(false);
//            this.setVerticalScrollBarEnabled(false);
//            this.setFocusableInTouchMode(true);
//            this.setFocusable(true);
//            try {
//                swipeRecyclerView = (SwipeRecyclerView) this.getParent().getParent();
//                swipeRecyclerView.addSwipeLayout(this);
//                width = r - l;
//                LinearLayout mWrapperView = (LinearLayout) getChildAt(0);
//                mContentView = (ViewGroup) mWrapperView.getChildAt(0);
//                mSwipeMenuView = (ViewGroup) mWrapperView.getChildAt(1);
//                mContentView.getLayoutParams().width = width;
//                Log.i("zwy",">>>>add");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            measureChildren(width, b - t);
//        }
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                if (getScrollX() < mSwipeMenuView.getWidth() / 2) {
                    smoothScrollTo(0, 0);
                } else {
                    swipeRecyclerView.closeAllSwipeLayout();
                    smoothScrollTo(mSwipeMenuView.getWidth(), 0);
                }
                return true;
        }

        return super.onTouchEvent(ev);

    }

    public void close() {
        this.smoothScrollTo(0, 0);
    }

}
