package com.vst.cartdemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by zwy on 2017/7/3.
 * email:16681805@qq.com
 */

public class SwipeRecyclerView extends RecyclerView {
    public ArrayList<SwipeLayout> swipeLayouts = new ArrayList<>();

    public SwipeRecyclerView(Context context) {
        super(context);
        setMotionEventSplittingEnabled(false);
    }

    public SwipeRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setMotionEventSplittingEnabled(false);
    }

    public SwipeRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setMotionEventSplittingEnabled(false);
    }

    public void addSwipeLayout(SwipeLayout swipeLayout) {
        if (swipeLayouts.indexOf(swipeLayout) == -1)
            swipeLayouts.add(swipeLayout);
    }

    public void closeAllSwipeLayout() {
        for (int i = 0; i < swipeLayouts.size(); i++) {
            if (swipeLayouts.get(i) != null)
                swipeLayouts.get(i).close();
        }
    }


    public void removeAllSwipeLayout() {
        swipeLayouts.clear();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        closeAllSwipeLayout();
        return super.onTouchEvent(e);
    }
}
