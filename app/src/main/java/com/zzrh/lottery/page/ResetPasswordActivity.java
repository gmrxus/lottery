package com.zzrh.lottery.page;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.zzrh.lottery.R;
import com.zzrh.lottery.ui.FocusButton;

public class ResetPasswordActivity extends AppCompatActivity {

  private FocusButton btnBack;
  private FocusButton btnSubmit;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_reset_password);
    initView();
  }

  private void initView() {
    btnBack = findViewById(R.id.btn_back);
    btnSubmit = findViewById(R.id.btn_submit);
    btnBack.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    btnSubmit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

      }
    });
  }
}
