package com.zzrh.lottery.page;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zzrh.lottery.R;

public class TextActivity extends AppCompatActivity {

  private TextView tvUp;
  private TextView tvDwon;
  private Button btnBack;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_text);
    initView();
  }

  private void initView() {
    btnBack = findViewById(R.id.btn_back);
    tvUp = findViewById(R.id.tv_up);
    tvDwon = findViewById(R.id.tv_down);
    btnBack.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }
}
