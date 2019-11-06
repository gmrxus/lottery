package com.zzrh.lottery.ui;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class FocusTextLine extends AppCompatTextView {
  public FocusTextLine(Context context) {
    super(context);
  }

  public FocusTextLine(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public FocusTextLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
    super.onFocusChanged(focused, direction, previouslyFocusedRect);
    if (focused) {
      getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
      getPaint().setAntiAlias(true);
    } else {
      getPaint().setFlags(Paint.DITHER_FLAG);
      getPaint().setAntiAlias(true);
    }
  }
}
