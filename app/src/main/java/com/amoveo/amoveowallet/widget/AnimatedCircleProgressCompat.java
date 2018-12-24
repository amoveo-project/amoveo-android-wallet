package com.amoveo.amoveowallet.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.amoveo.amoveowallet.R;

import static android.graphics.Paint.Style.STROKE;

public class AnimatedCircleProgressCompat extends View {

    private Paint mPaint;
    private float mStrokeWidth;
    private float mIntVelocity;
    private float mExtVelocity;
    private float mStartAngle;
    private float mSweepAngle;
    private boolean mPhase = true;

    public AnimatedCircleProgressCompat(Context context) {
        super(context);
        init(context, null);
    }

    public AnimatedCircleProgressCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AnimatedCircleProgressCompat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public AnimatedCircleProgressCompat(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mStrokeWidth = context.getResources().getDimension(R.dimen.size4);

        mPaint = new Paint();
        mPaint.setStyle(STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(context.getResources().getColor(R.color.black));

        mIntVelocity = 2.0f;
        mExtVelocity = 6.0f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mPhase) {
            mStartAngle = extractAngle(mStartAngle + mIntVelocity);
            mSweepAngle = extractAngle(mSweepAngle + (mExtVelocity - mIntVelocity));

            mPhase = 360 > mSweepAngle + (mExtVelocity - mIntVelocity);
        } else {
            mStartAngle = extractAngle(mStartAngle + mExtVelocity);
            mSweepAngle = extractAngle(mSweepAngle - (mExtVelocity - mIntVelocity));

            mPhase = 0 > mSweepAngle - (mExtVelocity - mIntVelocity);
        }

        float offset = mStrokeWidth / 2;

        canvas.drawArc(offset, offset, getWidth() - offset, getHeight() - offset, mStartAngle, mSweepAngle, false, mPaint);

        postDelayed(() -> {
            postInvalidate();
        }, 10);
    }

    private float extractAngle(float angle) {
        float result = angle;

        while (360 < result) {
            result -= 360;
        }

        return result;
    }
}