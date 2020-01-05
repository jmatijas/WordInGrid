package com.josipmatijas.wordingrid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;

public class MyCardView extends CardView {
    private static final String TAG = MyCardView.class.getSimpleName();

    public MyCardView(@NonNull Context context) {
        super(context);
    }

    public MyCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        //int widthMode = MeasureSpec.getMode(widthSpec);
        int widthSize = MeasureSpec.getSize(widthSpec);
        //int heightMode = MeasureSpec.getMode(heightSpec);
        int heightSize = MeasureSpec.getSize(heightSpec);
        Log.d(TAG, "widthSize: " + widthSize + ", heightSize: " + heightSize);
        if(widthSize < heightSize) {
            int newDimSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
            super.onMeasure(newDimSpec, newDimSpec);
            //setMeasuredDimension(widthSize, widthSize);
        } else {
            int newDimSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
            super.onMeasure(newDimSpec, newDimSpec);
            //setMeasuredDimension(heightSize, heightSize);
        }
    }
}
