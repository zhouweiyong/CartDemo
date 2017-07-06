package com.vst.cartdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zwy on 2017/7/5.
 * email:16681805@qq.com
 */

public class CartActionView extends View implements View.OnTouchListener {
    private Paint borderPaint;
    private RectF outRectF;
    private Rect increaseRect;
    private Rect reduceRect;
    private int outStrokeWidth = 2;
    private int roundCorner = 2;
    private int signStrokeWidth = 4;
    private Path cutLinePath;
    private Paint innerPressPaint;
    private RectF increaseInnerRectF;
    private boolean isIncreasePress;
    private boolean isReducePress;
    private boolean isCartNumPress;
    private RectF reduceInnerRectF;
    private Path reducePaht;
    private Paint normalSignPaint;
    private int cartNum = 200;
    private String cartNumStr = "200";
    private Path increasePath;
    private Paint nothingSignPaint;
    private Paint textPaint;
    private float textSize;
    private int textY;
    private Rect cartNumRect;
    private Paint cartNumPressPaint;
    private OnCartActionListener mOnCartActionListener;

    public void setOnCartActionListener(OnCartActionListener mOnCartActionListener) {
        this.mOnCartActionListener = mOnCartActionListener;
    }

    public CartActionView(Context context) {
        this(context, null);
    }

    public CartActionView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CartActionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {

        borderPaint = new Paint();
        borderPaint.setColor(Color.parseColor("#a0a0a0"));
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setAntiAlias(true);
        borderPaint.setStrokeWidth(outStrokeWidth);

        innerPressPaint = new Paint();
        innerPressPaint.setColor(Color.parseColor("#f0f0f0"));
        innerPressPaint.setStyle(Paint.Style.FILL);
        innerPressPaint.setAntiAlias(true);

        normalSignPaint = new Paint();
        normalSignPaint.setColor(Color.parseColor("#a0a0a0"));
        normalSignPaint.setStyle(Paint.Style.STROKE);
        normalSignPaint.setAntiAlias(true);
        normalSignPaint.setStrokeWidth(signStrokeWidth);

        nothingSignPaint = new Paint();
        nothingSignPaint.setColor(Color.parseColor("#dbdbdb"));
        nothingSignPaint.setStyle(Paint.Style.STROKE);
        nothingSignPaint.setAntiAlias(true);
        nothingSignPaint.setStrokeWidth(signStrokeWidth);


        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.parseColor("#323232"));
        textSize = 22 * (context.getResources().getDisplayMetrics().scaledDensity) + 0.5f;
        textPaint.setTextSize(textSize);

        cartNumPressPaint = new Paint();
        cartNumPressPaint.setColor(Color.RED);
        cartNumPressPaint.setStyle(Paint.Style.STROKE);
        cartNumPressPaint.setAntiAlias(true);
        cartNumPressPaint.setStrokeWidth(outStrokeWidth);

        setOnTouchListener(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int leftPadding = getPaddingLeft();
        int topPadding = getPaddingTop();
        int rightPadding = getPaddingRight();
        int bottomPadding = getPaddingBottom();

        int startX = leftPadding + outStrokeWidth / 2;
        int startY = topPadding + outStrokeWidth / 2;
        int endX = w - rightPadding - outStrokeWidth / 2;
        int endY = h - bottomPadding - outStrokeWidth / 2;

        int realW = w - leftPadding - rightPadding;
        int realH = h - topPadding - bottomPadding;

        outRectF = new RectF(startX, startY, endX, endY);
        int perW = realW / 4;
        reduceRect = new Rect(startX, startY, startX + perW, endY);
        increaseRect = new Rect(startX + perW * 3, startY, endX, endY);
        reduceInnerRectF = new RectF(reduceRect);
        increaseInnerRectF = new RectF(increaseRect);
        cartNumRect = new Rect(reduceRect.right, reduceRect.top, increaseRect.left, increaseRect.bottom);

        cutLinePath = new Path();
        cutLinePath.moveTo(reduceRect.right, reduceRect.top);
        cutLinePath.lineTo(reduceRect.right, reduceRect.bottom);
        cutLinePath.moveTo(increaseRect.left, increaseRect.top);
        cutLinePath.lineTo(increaseRect.left, increaseRect.bottom);

        reducePaht = new Path();
        int rCenterX = reduceRect.left + perW / 2;
        int rCenterY = reduceRect.top + realH / 2;
        reducePaht.moveTo(rCenterX - perW / 4, rCenterY);
        reducePaht.lineTo(rCenterX + perW / 4, rCenterY);

        increasePath = new Path();
        int iCenterX = increaseRect.left + perW / 2;
        int iCenterY = increaseRect.top + realH / 2;
        increasePath.moveTo(iCenterX - perW / 4, iCenterY);
        increasePath.lineTo(iCenterX + perW / 4, iCenterY);
        increasePath.moveTo(iCenterX, iCenterY - realH / 4);
        increasePath.lineTo(iCenterX, iCenterY + realH / 4);

        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
        textY = (cartNumRect.bottom + cartNumRect.top - fontMetricsInt.bottom - fontMetricsInt.top) / 2;
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(outRectF, roundCorner, roundCorner, borderPaint);
        canvas.drawPath(cutLinePath, borderPaint);
        if (isReducePress)
            canvas.drawRoundRect(reduceInnerRectF, roundCorner, roundCorner, innerPressPaint);
        if (isIncreasePress)
            canvas.drawRoundRect(increaseInnerRectF, roundCorner, roundCorner, innerPressPaint);

        if (cartNum > 0) {
            canvas.drawPath(reducePaht, normalSignPaint);
        } else {
            canvas.drawPath(reducePaht, nothingSignPaint);
        }

        canvas.drawPath(increasePath, normalSignPaint);

        canvas.drawText(cartNumStr, cartNumRect.centerX(), textY, textPaint);

        if (isCartNumPress)
            canvas.drawRect(cartNumRect, cartNumPressPaint);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        Log.i("zwy", "x>>>" + y + " y>>>" + y);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (x > reduceRect.left && x < reduceRect.right && y > reduceRect.top && y < reduceRect.bottom && cartNum > 0)
                    isReducePress = true;
                if (x > increaseRect.left && x < increaseRect.right && y > increaseRect.top && y < increaseRect.bottom)
                    isIncreasePress = true;
                if (x > cartNumRect.left && x < cartNumRect.right && y > cartNumRect.top && y < cartNumRect.bottom)
                    isCartNumPress = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if (x < reduceRect.left || x > reduceRect.right || y < reduceRect.top || y > reduceRect.bottom)
                    isReducePress = false;
                if (x < increaseRect.left || x > increaseRect.right || y < increaseRect.top || y > increaseRect.bottom)
                    isIncreasePress = false;
                if (x < cartNumRect.left || x > cartNumRect.right || y < cartNumRect.top || y > cartNumRect.bottom)
                    isCartNumPress = false;
                break;
            case MotionEvent.ACTION_UP:
                if (isReducePress && mOnCartActionListener != null)
                    mOnCartActionListener.onReduce();

                if (isIncreasePress && mOnCartActionListener != null)
                    mOnCartActionListener.onIncrease();
                if (isCartNumPress && mOnCartActionListener != null)
                    mOnCartActionListener.onCartNumPress();

                isReducePress = false;
                isIncreasePress = false;
                isCartNumPress = false;
                break;
        }
        invalidate();
        return true;
    }

    public void setCartNum(int num) {
        this.cartNum = num;
        this.cartNumStr = String.valueOf(num);
        invalidate();
    }

    public interface OnCartActionListener {
        void onReduce();

        void onIncrease();

        void onCartNumPress();
    }
}
