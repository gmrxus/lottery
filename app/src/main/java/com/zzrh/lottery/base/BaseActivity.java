package com.zzrh.lottery.base;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.zzrh.lottery.MainActivity;

public class BaseActivity extends AppCompatActivity {
  protected void go2Activity(Class activityClass) {
    startActivity(new Intent(this, activityClass));
  }
}
