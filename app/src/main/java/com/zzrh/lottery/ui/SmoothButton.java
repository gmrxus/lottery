package com.zzrh.lottery.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatButton;

import com.zzrh.lottery.R;

public class SmoothButton extends AppCompatButton {

  public SmoothButton(Context context) {
    this(context, null);
  }

  public SmoothButton(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public SmoothButton(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setup(attrs);
  }

  private void setup(AttributeSet attrs) {
    getContext().obtainStyledAttributes(attrs, R.styleable.smooth_button);


  }
}
