package com.example.dhrushit.popularmovies;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by dHRUSHIT on 10/6/2016.
 */
public class MyImageView extends ImageView {
    public MyImageView(Context context){
        super(context);
    }

    public MyImageView(Context context,AttributeSet attributeSet){
        super(context,attributeSet);
    }

    MyImageView(Context context,AttributeSet attributeSet,int defStyle){
        super(context,attributeSet,defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(),getMeasuredHeight());
    }

    
}
