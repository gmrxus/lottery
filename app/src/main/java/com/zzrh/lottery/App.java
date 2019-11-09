package com.zzrh.lottery;

import android.app.Application;

import com.zzrh.lottery.util.ToastUtils;

import org.xutils.x;

public class App extends Application {
  public static long baseCountTime = 0;
  @Override
  public void onCreate() {
    super.onCreate();
    x.Ext.init(this);
    ToastUtils.init(this);
  }


}
