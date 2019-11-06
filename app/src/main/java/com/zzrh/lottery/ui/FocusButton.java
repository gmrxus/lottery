package com.zzrh.lottery.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatButton;

import com.zzrh.lottery.util.AnimatorUtil;

public class FocusButton extends AppCompatButton {
  private static final String TAG = "FocusButton";

  public FocusButton(Context context) {
    super(context);
  }

  public FocusButton(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public FocusButton(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
    super.onFocusChanged(focused, direction, previouslyFocusedRect);
    AnimatorUtil.setViewAnimator(this, focused);

  }
}
