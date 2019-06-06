package com.example.unsplashtest;

import android.content.Context;
import android.media.Image;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.support.v7.widget.AppCompatImageView;

import java.util.jar.Attributes;

public class SquareImageView extends ImageView {


    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heigtMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY && heigtMode != MeasureSpec.EXACTLY) {
            int width = MeasureSpec.getSize(heightMeasureSpec);
            int heigt = width;
            if (heigtMode == MeasureSpec.AT_MOST) {
                heigt = Math.min(heigt, MeasureSpec.getSize(heightMeasureSpec));
            }
            setMeasuredDimension(width, heigt);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
