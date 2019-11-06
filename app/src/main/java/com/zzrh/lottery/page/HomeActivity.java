package com.zzrh.lottery.page;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzrh.lottery.MainActivity;
import com.zzrh.lottery.R;
import com.zzrh.lottery.base.BaseActivity;
import com.zzrh.lottery.ui.FocusButton;
import com.zzrh.lottery.ui.FocusImageView;

import java.util.List;

public class HomeActivity extends BaseActivity {
  private static final String TAG = "HomeActivity";

  String fucai = "com.zzrh.fucai_test";
  String ticai = "cn.wasu.ott.bgctv";

  private FocusImageView btnTc;
  private FocusImageView btnFc;
  private FocusButton btnTopLeft;
  private FocusButton btnTopRight;
  private RelativeLayout rlLogin;
  private RelativeLayout rlRegist;
  private RelativeLayout rlHelp;
  private RelativeLayout rlResetPassword;
  public static final int DIALOG_LOGIN = 0;
  public static final int DIALOG_REGIST = 1;
  public static final int DIALOG_HELP = 2;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    getView();
    WindowManager mWindowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
    DisplayMetrics metrics = new DisplayMetrics();
    mWindowManager.getDefaultDisplay().getMetrics(metrics);
    int width = metrics.widthPixels;//获取到的是px，像素，绝对像素，需要转化为dpi
    int height = metrics.heightPixels;
    Log.e(TAG, "onCreate: width:" + width);
    Log.e(TAG, "onCreate: height:" + height);
  }

  private void getView() {
    btnTc = findViewById(R.id.btn_left);
    btnFc = findViewById(R.id.btn_right);
    btnTopLeft = findViewById(R.id.btn_top_left);
    btnTopRight = findViewById(R.id.btn_top_right);
    rlLogin = findViewById(R.id.rl_qrcode_login);
    rlRegist = findViewById(R.id.rl_qrcode_regist);
    rlHelp = findViewById(R.id.rl_qrcode_help);
    rlResetPassword = findViewById(R.id.rl_reset_password);

    btnTc.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        go2Activity(MainActivity.class);
      }
    });
    btnFc.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        openOtherApp(fucai);

      }
    });
    btnTc.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        openOtherApp(ticai);
      }
    });
    btnTopRight.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        go2Activity(LoginActivity.class);
      }
    });
    btnTopLeft.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        go2Activity(RegisterActivity.class);
      }
    });
    rlResetPassword.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        go2Activity(ResetPasswordActivity.class);
      }
    });
    rlLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        createDialog(DIALOG_LOGIN);
      }
    });
    rlRegist.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        createDialog(DIALOG_REGIST);
      }
    });
    rlHelp.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        createDialog(DIALOG_HELP);
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

  private void createDialog(int dialogType) {
    final Dialog dialog = new Dialog(this, R.style.MyQrCodeDialog);
    View view = getLayoutInflater().inflate(R.layout.qrcode_dialog, null);
    TextView tv = view.findViewById(R.id.tv);
    FocusButton btnClose = view.findViewById(R.id.btn_close);
    ImageView ivQrcode = view.findViewById(R.id.iv_qrcode);
    dialog.setContentView(view);
    switch (dialogType) {
      case DIALOG_LOGIN:
        tv.setText(R.string.qrcode_login_hint);
        break;
      case DIALOG_REGIST:
        tv.setText(R.string.qrcode_regist_hint);
        break;
      case DIALOG_HELP:
        tv.setText(R.string.qrcode_help_hint);
        break;
    }
    btnClose.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dialog.cancel();
      }
    });

    //获取到当前Activity的Window
    Window dialog_window = dialog.getWindow();
    //获取到LayoutParams
    WindowManager.LayoutParams dialog_window_attributes = dialog_window.getAttributes();
    //设置宽度
    dialog_window_attributes.width = RelativeLayout.LayoutParams.MATCH_PARENT;
    //设置高度
    dialog_window_attributes.height = RelativeLayout.LayoutParams.MATCH_PARENT;
    dialog_window.setAttributes(dialog_window_attributes);

    dialog.show();

  }

  private void  startWebSocket(){
    ChatWebSocket.getChartWebSocket().sen
  }

}
