package com.zzrh.lottery.ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.zzrh.lottery.R;

public class EyeCheckBox extends FocusImageView {
  private Context context;
  private boolean isChecked = false;

  public EyeCheckBox(Context context) {
    super(context);
    this.context = context;
    init();

  }

  public EyeCheckBox(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    this.context = context;

    init();

  }

  public EyeCheckBox(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

    super(context, attrs, defStyleAttr);
    this.context = context;

    init();
  }


  private void init() {
    setEnabled(true);
    setFocusable(true);
    setClickable(true);
    this.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        isChecked = !isChecked;
        setImageDrawable(ContextCompat.getDrawable(context, isChecked ? R.drawable.select_bg_eye_off : R.drawable.select_bg_eye_on));
        listener.checkedListener(isChecked);
      }
    });

  }



  private OnCheckedChangeListener listener;

  public interface OnCheckedChangeListener {
    void checkedListener(boolean isCheck);
  }

  public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedListener) {
    listener = onCheckedListener;
  }
}
