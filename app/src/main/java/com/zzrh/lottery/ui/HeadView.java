package com.zzrh.lottery.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.zzrh.lottery.R;

public class HeadView extends RelativeLayout {

  private boolean isShowIcon;
  private String leftText;
  private boolean isShowRightBottom;
  private boolean isShowMidText;
  private boolean isShowUserText;
  private String rightBottomText;
  private ImageView iv;
  private TextView tvUser;
  private TextView tv;
//  private TextView tvMid;
  private FocusButton btn;

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
    isShowMidText = typedArray.getBoolean(R.styleable.HeadView_isShowRightBottom, false);
    isShowUserText = typedArray.getBoolean(R.styleable.HeadView_isShowUserTv, false);
    typedArray.recycle();

  }

  private void initView(Context context) {
    LayoutInflater.from(context).inflate(R.layout.item_head_view, this, true);
    iv = findViewById(R.id.iv);
    tv = findViewById(R.id.tv);
    tvUser = findViewById(R.id.tv_user);
//    tvMid = findViewById(R.id.tv_mid);
    btn = findViewById(R.id.btn);
//    tvMid.setVisibility(isShowMidText ? VISIBLE : GONE);
    iv.setVisibility(isShowIcon ? VISIBLE : GONE);
    tvUser.setVisibility(isShowUserText ? VISIBLE : GONE);
    btn.setVisibility(isShowRightBottom ? VISIBLE : GONE);
    tv.setText(leftText);
    btn.setText(rightBottomText);
    btn.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        rightListener.rightBtnClick(v);
      }
    });
  }

  private OnRightBtnClickListener rightListener;
  private OnLeftBtnClickListener leftListener;

  public void setRightClickListener(OnRightBtnClickListener listener) {
    this.rightListener = listener;
  }

  public interface OnRightBtnClickListener {
    void rightBtnClick(View v);


  }

  public interface OnLeftBtnClickListener {
    void liftBtnClick(View v);


  }
}
