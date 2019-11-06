package com.zzrh.lottery.ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.zzrh.lottery.R;
import com.zzrh.lottery.util.AnimatorUtil;

public class FocusImageView extends AppCompatImageView {
  public FocusImageView(Context context) {
    super(context);
    setFocusable(true);
  }

  public FocusImageView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    setFocusable(true);
  }

  public FocusImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setFocusable(true);
  }

  @Override
  protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
    super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    AnimatorUtil.setViewAnimator(this, gainFocus);

  }
}
