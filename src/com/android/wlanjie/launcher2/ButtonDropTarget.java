/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.wlanjie.launcher2;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.android.wlanjie.launcher.R;


/**
 * Implements a DropTarget.
 */
public class ButtonDropTarget extends TextView implements DropTarget, DragController.DragListener {

    protected final int mTransitionDuration;

    protected Launcher mLauncher;
    private int mBottomDragPadding;
    protected TextView mText;
    protected SearchDropTargetBar mSearchDropTargetBar;

    /** Whether this drop target is active for the current drag */
    protected boolean mActive;

    /** The paint applied to the drag view on hover */
    protected int mHoverColor = 0;

    public ButtonDropTarget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ButtonDropTarget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        System.out.println("ButtonDropTarget ButtonDropTarget");
        Resources r = getResources();
        mTransitionDuration = r.getInteger(R.integer.config_dropTargetBgTransitionDuration);
        mBottomDragPadding = r.getDimensionPixelSize(R.dimen.drop_target_drag_padding);
    }

    void setLauncher(Launcher launcher) {
        System.out.println("ButtonDropTarget setLauncher");
        mLauncher = launcher;
    }

    public boolean acceptDrop(DragObject d) {
        System.out.println("ButtonDropTarget acceptDrop");
        return false;
    }

    public void setSearchDropTargetBar(SearchDropTargetBar searchDropTargetBar) {
        System.out.println("ButtonDropTarget setSearchDropTargetBar");
        mSearchDropTargetBar = searchDropTargetBar;
    }

    protected Drawable getCurrentDrawable() {
        System.out.println("ButtonDropTarget getCurrentDrawable");
        Drawable[] drawables = getCompoundDrawablesRelative();
        for (int i = 0; i < drawables.length; ++i) {
            if (drawables[i] != null) {
                return drawables[i];
            }
        }
        return null;
    }

    public void onDrop(DragObject d) {
        System.out.println("ButtonDropTarget onDrop");
    }

    public void onFlingToDelete(DragObject d, int x, int y, PointF vec) {
        // Do nothing
        System.out.println("ButtonDropTarget onFlingToDelete");
    }

    public void onDragEnter(DragObject d) {
        System.out.println("ButtonDropTarget onDragEnter");
        d.dragView.setColor(mHoverColor);
    }

    public void onDragOver(DragObject d) {
        // Do nothing
        System.out.println("ButtonDropTarget onDragOver");
    }

    public void onDragExit(DragObject d) {
        d.dragView.setColor(0);
        System.out.println("ButtonDropTarget onDragExit");
    }

    public void onDragStart(DragSource source, Object info, int dragAction) {
        // Do nothing
        System.out.println("ButtonDropTarget onDragStart");
    }

    public boolean isDropEnabled() {
        System.out.println("ButtonDropTarget isDropEnabled");
        return mActive;
    }

    public void onDragEnd() {
        // Do nothing
        System.out.println("ButtonDropTarget onDragEnd");
    }

    @Override
    public void getHitRect(android.graphics.Rect outRect) {
        super.getHitRect(outRect);
        System.out.println("ButtonDropTarget getHitRect");
        outRect.bottom += mBottomDragPadding;
    }

    private boolean isRtl() {
        System.out.println("ButtonDropTarget isRtl");
        return (getLayoutDirection() == View.LAYOUT_DIRECTION_RTL);
    }

    Rect getIconRect(int viewWidth, int viewHeight, int drawableWidth, int drawableHeight) {
        System.out.println("ButtonDropTarget getIconRect");
        DragLayer dragLayer = mLauncher.getDragLayer();

        // Find the rect to animate to (the view is center aligned)
        Rect to = new Rect();
        dragLayer.getViewRectRelativeToSelf(this, to);

        final int width = drawableWidth;
        final int height = drawableHeight;

        final int left;
        final int right;

        if (isRtl()) {
            right = to.right - getPaddingRight();
            left = right - width;
        } else {
            left = to.left + getPaddingLeft();
            right = left + width;
        }

        final int top = to.top + (getMeasuredHeight() - height) / 2;
        final int bottom = top +  height;

        to.set(left, top, right, bottom);

        // Center the destination rect about the trash icon
        final int xOffset = (int) -(viewWidth - width) / 2;
        final int yOffset = (int) -(viewHeight - height) / 2;
        to.offset(xOffset, yOffset);

        return to;
    }

    @Override
    public DropTarget getDropTargetDelegate(DragObject d) {
        System.out.println("ButtonDropTarget getDropTargetDelegate");
        return null;
    }

    public void getLocationInDragLayer(int[] loc) {
        System.out.println("ButtonDropTarget getLocationInDragLayer");
        mLauncher.getDragLayer().getLocationInDragLayer(this, loc);
    }
}
