package com.zzrh.lottery.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.zzrh.lottery.R;

public class HeadView extends RelativeLayout {

  private boolean isShowIcon;
  private String leftText;
  private boolean isShowRightBottom;
  private String rightBottomText;
  private ImageView iv;
  private TextView tv;
  private Button btn;

  public HeadView(Context context) {
    this(context, null);
  }

  public HeadView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public HeadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initTypedView(context, attrs);
    initView(context);
  }

  private void initTypedView(Context context, AttributeSet attrs) {
    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeadView);
    isShowIcon = typedArray.getBoolean(R.styleable.HeadView_isShowIcon, false);
    leftText = typedArray.getString(R.styleable.HeadView_leftText);
    isShowRightBottom = typedArray.getBoolean(R.styleable.HeadView_isShowRightBottom, false);
    rightBottomText = typedArray.getString(R.styleable.HeadView_rightBottomText);
    typedArray.recycle();

  }

  private void initView(Context context) {
    LayoutInflater.from(context).inflate(R.layout.item_head_view, this, true);
    iv = findViewById(R.id.iv);
    tv = findViewById(R.id.tv);
    btn = findViewById(R.id.btn);
    iv.setVisibility(isShowIcon?VISIBLE:GONE);
    tv.setText(leftText);
    btn.setVisibility(isShowRightBottom?VISIBLE:GONE);
    btn.setText(rightBottomText);

  }
}
