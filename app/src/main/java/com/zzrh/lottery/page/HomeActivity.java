package com.zzrh.lottery.page;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zzrh.lottery.MainActivity;
import com.zzrh.lottery.R;
import com.zzrh.lottery.base.BaseActivity;
import com.zzrh.lottery.entity.BtnImgEntity;

import java.util.List;

public class HomeActivity extends BaseActivity {
  String packageName = "com.zzrh.fucai_test";

  private Button btnTc;
  private Button btnFc;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    getView();
  }

  private void getView() {
    btnTc = findViewById(R.id.btn_left);
    btnFc = findViewById(R.id.btn_right);
    btnTc.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        go2Activity(MainActivity.class);
      }
    });
    btnFc.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        openOtherApp(packageName);
      }
    });
  }

  private void openOtherApp(String packageName) {
    PackageInfo pi = null;
    try {
      pi = getPackageManager().getPackageInfo(packageName, 0);
      Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
      resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
      resolveIntent.setPackage(pi.packageName);
      List<ResolveInfo> apps = getPackageManager().queryIntentActivities(resolveIntent, 0);
      ResolveInfo ri = apps.iterator().next();
      if (ri != null) {
        packageName = ri.activityInfo.packageName;
        String className = ri.activityInfo.name;
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cn = new ComponentName(packageName, className);
        intent.setComponent(cn);
        startActivity(intent);
      }
    } catch (Exception e) {
    }
  }
}
