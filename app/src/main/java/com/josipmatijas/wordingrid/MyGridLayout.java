package com.josipmatijas.wordingrid;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.GridLayout;

public class MyGridLayout extends GridLayout {
    private static final String TAG = MyGridLayout.class.getSimpleName();

    public MyGridLayout(Context context) {
        super(context);
    }

    public MyGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyGridLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
