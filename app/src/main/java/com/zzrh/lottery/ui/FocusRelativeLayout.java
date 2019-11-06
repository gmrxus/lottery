package com.zzrh.lottery.ui;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;

import com.zzrh.lottery.R;
import com.zzrh.lottery.util.AnimatorUtil;

public class FocusRelativeLayout extends RelativeLayout {
  Context context;

  public FocusRelativeLayout(Context context) {
    super(context);
    this.context = context;
  }

  public FocusRelativeLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.context = context;

  }

  public FocusRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    this.context = context;

  }

  @Override
  protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
    super.onFocusChanged(focused, direction, previouslyFocusedRect);
    AnimatorUtil.setViewAnimator(this, focused);
//    Drawable drawable = ContextCompat.getDrawable(context, R.drawable.shape_r_tea);
    if (focused) {
      this.setBackgroundResource(R.drawable.bg_text);
    } else {
      this.setBackgroundResource(0);
    }
  }

}
