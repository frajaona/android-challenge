package com.farmdrop.challenge.producers.ui;


import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {

    @Nullable
    private final Drawable mDivider;

    public SimpleDividerItemDecoration(@NonNull Resources resources, @DrawableRes int dividerDrawable) {
        mDivider = ResourcesCompat.getDrawable(resources, dividerDrawable, null);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mDivider != null) {
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                int left = parent.getPaddingLeft() + child.getPaddingLeft();
                int right = parent.getWidth() - parent.getPaddingRight() - child.getPaddingRight();

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }
}
