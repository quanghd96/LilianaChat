package com.quang.lilianachat.ui.wheelview.transformer;

import android.graphics.drawable.Drawable;

import com.quang.lilianachat.ui.wheelview.WheelView;


public interface WheelSelectionTransformer {
    void transform(Drawable drawable, WheelView.ItemState itemState);
}
