package com.zzrh.lottery.page;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rabtman.wsmanager.WsManager;
import com.rabtman.wsmanager.listener.WsStatusListener;
import com.zzrh.lottery.MainActivity;
import com.zzrh.lottery.R;
import com.zzrh.lottery.BaseActivity;
import com.zzrh.lottery.common.SPKey;
import com.zzrh.lottery.ui.FocusButton;
import com.zzrh.lottery.ui.FocusImageView;
import com.zzrh.lottery.util.QrCodeUtil;
import com.zzrh.lottery.util.SPUtil;
import com.zzrh.lottery.util.ToastUtils;
import com.zzrh.lottery.util.http.HttpUtil;
import com.zzrh.lottery.util.http.Urls;


import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okio.ByteString;

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
  public static final int DIALOG_ORDER = 2;
  public static final int DIALOG_HELP = 3;
  private TextView tvAccount;
  private ImageView ivQrcode;
  private String qrCodeUrl;
  private String huihuaId = UUID.randomUUID().toString();
  private String androidId;
  private WsManager wsManager;
  private boolean isLogin = false;
  private Dialog dialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    androidId = Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);
    getView();
  }

  @Override
  protected void onResume() {
    super.onResume();
    loginCheck();

  }

  private void loginCheck() {
    String token = (String) SPUtil.get(this, SPKey.token, "");
    if (token.isEmpty()) {
      SPUtil.put(HomeActivity.this, SPKey.account, "");
      btnTopRight.setText(R.string.login);
      btnTopLeft.setVisibility(View.VISIBLE);
      tvAccount.setVisibility(View.GONE);
      isLogin = false;
    } else {

      String account = (String) SPUtil.get(this, SPKey.account, "");
      btnTopRight.setText(R.string.sing_out);
      btnTopLeft.setVisibility(View.GONE);
      tvAccount.setText(account);
      tvAccount.setVisibility(View.VISIBLE);
      isLogin = true;
    }
  }

  private void getView() {
    btnTc = findViewById(R.id.btn_left);
    btnFc = findViewById(R.id.btn_right);
    btnTopLeft = findViewById(R.id.btn_top_left);
    btnTopRight = findViewById(R.id.btn_top_right);
    rlLogin = findViewById(R.id.rl_qrcode_login);
    rlRegist = findViewById(R.id.rl_qrcode_regist);
    rlHelp = findViewById(R.id.rl_qrcode_help);
    tvAccount = findViewById(R.id.tv_account);
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
        if (!isLogin) {
          go2Activity(LoginActivity.class);
        } else {
          isLogin = false;
          SPUtil.put(HomeActivity.this, SPKey.token, "");
          loginCheck();
        }
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
        String token = (String) SPUtil.get(HomeActivity.this, SPKey.token, "");
        if (token.isEmpty()) {
          ToastUtils.show("请登录后再操作");
          return;
        }
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
    dialog = new Dialog(this, R.style.MyQrCodeDialog);
    View view = getLayoutInflater().inflate(R.layout.qrcode_dialog, null);
    TextView tv = view.findViewById(R.id.tv);
    FocusButton btnClose = view.findViewById(R.id.btn_close);
    ivQrcode = view.findViewById(R.id.iv_qrcode);
    dialog.setContentView(view);
    switch (dialogType) {
      case DIALOG_LOGIN:
        tv.setText(R.string.qrcode_login_hint);
        loadQrcodeUrl(DIALOG_LOGIN + "");
        break;
      case DIALOG_REGIST:
        loadQrcodeUrl(DIALOG_LOGIN + "");
        tv.setText(R.string.qrcode_regist_hint);
        break;
      case DIALOG_HELP:
        loadQrcodeUrl(DIALOG_LOGIN + "");
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

  private void loadQrcodeUrl(String type) {
    FormBody body = new FormBody.Builder()
        .add("type", type)
        .build();
    HttpUtil.postAsync(Urls.GET_QRCODE, body, new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
        Log.e(TAG, "onFailure: ", e);
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {
        String string = response.body().string();
        Log.d(TAG, "onResponse: " + string);
        JsonElement element = JsonParser.parseString(string);
        if (!element.isJsonObject()) {
          ToastUtils.show(string);
          return;
        } else {
          String code = element.getAsJsonObject().get("code").getAsString();
          if (!"0000".equals(code)) {
            ToastUtils.show(string);
          } else {
            qrCodeUrl = androidId + "_" + huihuaId;
            runOnUiThread(new Runnable() {
              @Override
              public void run() {
                ivQrcode.setImageBitmap(QrCodeUtil.createQRCodeBitmap(qrCodeUrl, 300, 300));
                startWebSocket();

              }
            });
          }
        }
      }
    });
  }

  private void startWebSocket() {

    OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
        .pingInterval(15, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build();
    wsManager = new WsManager.Builder(this)
        .wsUrl(Urls.WEB_SOCKET + androidId + "/" + huihuaId)
        .needReconnect(true)
        .client(okHttpClient)
        .build();
    wsManager.startConnect();
    wsManager.setWsStatusListener(new WsStatusListener() {
      @Override
      public void onOpen(Response response) {
        super.onOpen(response);
      }

      @Override
      public void onMessage(final String text) {
        super.onMessage(text);
        Log.d(TAG, "onMessage: " + text);
        //{"data":{"token":"YTliMjZiZmM1MzEzNGMxNmJjMDUwZGU5OTZmNDc0ZGN8MTU3MzAxMTg1Mzc2MjA4NDE="},"type":"SUCCESS"}
        JsonElement jsonElement = JsonParser.parseString(text);
        if (!jsonElement.isJsonObject()) {
          runOnUiThread(new Runnable() {
            @Override
            public void run() {
              ToastUtils.show(text);
            }
          });
          return;
        }
        JsonObject object = jsonElement.getAsJsonObject();
        String type = object.get("type").getAsString();
        if (!"SUCCESS".equals(type)) {
          runOnUiThread(new Runnable() {
            @Override
            public void run() {
              ToastUtils.show(text);
            }
          });
          return;
        }
        JsonObject data = object.get("data").getAsJsonObject();
        String token = data.get("token").getAsString();
        String tel = data.get("tel").getAsString();
        String userId = data.get("userId").getAsString();
        SPUtil.put(HomeActivity.this, SPKey.token, token);
        SPUtil.put(HomeActivity.this, SPKey.account, tel);
        SPUtil.put(HomeActivity.this, SPKey.userId, userId);


        dialog.cancel();
        loginCheck();

      }

      @Override
      public void onMessage(ByteString bytes) {
        super.onMessage(bytes);
        Log.d(TAG, "onMessage: " + bytes);

      }

      @Override
      public void onReconnect() {
        super.onReconnect();
      }

      @Override
      public void onClosing(int code, String reason) {
        super.onClosing(code, reason);
      }

      @Override
      public void onClosed(int code, String reason) {
        super.onClosed(code, reason);
      }

      @Override
      public void onFailure(Throwable t, Response response) {
        super.onFailure(t, response);
      }
    });
  }

}
