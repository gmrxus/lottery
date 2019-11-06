package com.zzrh.lottery.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.FocusFinder;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import com.zzrh.lottery.util.AnimatorUtil;

public class FocusTextView extends AppCompatTextView {
  private int frame_width = 1;//边框宽度
  private boolean focused = false;

  public FocusTextView(Context context) {
    this(context, null);
  }

  public FocusTextView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public FocusTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
    super.onFocusChanged(focused, direction, previouslyFocusedRect);
//    AnimatorUtil.setViewAnimator(this, focused);
    this.focused = focused;

  }

  @Override
  public int getPaddingBottom() {
    return super.getPaddingBottom() + 14;
  }

  @Override
  public int getPaddingEnd() {
    return super.getPaddingEnd() + 14;
  }

  @Override
  public int getPaddingLeft() {
    return super.getPaddingLeft() + 14;
  }

  @Override
  public int getPaddingRight() {
    return super.getPaddingRight() + 14;
  }

  @Override
  public int getPaddingStart() {
    return super.getPaddingStart() + 14;
  }

  @Override
  public int getPaddingTop() {
    return super.getPaddingTop() + 14;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    if (focused) {

      Paint paint = new Paint();
      //  将边框设为黑色
      paint.setColor(Color.WHITE);
      //  画TextView的4个边
      canvas.drawLine(0, 0, this.getWidth() - frame_width, 0, paint);
      canvas.drawLine(0, 0, 0, this.getHeight() - frame_width, paint);
      canvas.drawLine(this.getWidth() - frame_width, 0, this.getWidth() - frame_width, this.getHeight() - frame_width, paint);
      canvas.drawLine(0, this.getHeight() - frame_width, this.getWidth() - frame_width, this.getHeight() - frame_width, paint);
      super.onDraw(canvas);
    }
  }
}
