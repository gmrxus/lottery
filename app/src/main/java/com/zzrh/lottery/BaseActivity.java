package com.zzrh.lottery;

import android.content.Intent;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;

import com.zzrh.lottery.App;

public class BaseActivity extends AppCompatActivity {
  private CountDownTimer codeCountDown;


  protected void go2Activity(Class activityClass) {
    startActivity(new Intent(this, activityClass));
  }

  protected void startCountDownTimer(long timeLong) {
    if (codeCountDown != null) {
      codeCountDown.cancel();
    }
    codeCountDown = new CountDownTimer(timeLong, 1000) {
      @Override
      public void onTick(long millisUntilFinished) {
        App.baseCountTime = millisUntilFinished;
        onTickCallback(millisUntilFinished);
      }

      @Override
      public void onFinish() {
        App.baseCountTime = 0;
        onFinishCallback();
      }
    }.start();
  }

  protected void onTickCallback(long millisUntilFinished) {

  }

  protected void onFinishCallback() {

  }


  protected CountDownTimer getCountDownTimer() {
    return codeCountDown;
  }
}
