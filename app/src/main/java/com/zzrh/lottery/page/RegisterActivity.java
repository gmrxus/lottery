package com.zzrh.lottery.page;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatCheckBox;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rabtman.wsmanager.WsManager;
import com.zzrh.lottery.App;
import com.zzrh.lottery.R;
import com.zzrh.lottery.BaseActivity;
import com.zzrh.lottery.ui.EyeCheckBox;
import com.zzrh.lottery.util.QrCodeUtil;
import com.zzrh.lottery.util.ToastUtils;
import com.zzrh.lottery.util.http.HttpUtil;
import com.zzrh.lottery.util.http.Urls;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

public class RegisterActivity extends BaseActivity {
  private static final String TAG = "RegisterActivity";

  private AppCompatCheckBox ivCheck;
  boolean icCheck = true;
  private Button btnBack;
  private EditText etTel;
  private EditText etPassword;
  private EditText etPasswordQ;
  private EditText etName;
  private EditText etCardId;
  private EditText etCode;
  private TextView tvCodeTitle;
  private Button btnSubmit;
  private ImageView ivQrCode;
  private TextView tvXieyi;
  private WsManager wsManager;
  private String qrCodeUrl = "";
  private long pageCountDown = 0;
  private CountDownTimer pageCountDownTimer;
  private EyeCheckBox cbEyePassword;
  private EyeCheckBox cbEyePasswordQ;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);
    initView();
  }

  private void initView() {
    ivQrCode = findViewById(R.id.iv_qrcode);
    ivCheck = findViewById(R.id.iv_check);
    btnBack = findViewById(R.id.btn_back);
    etTel = findViewById(R.id.et_tel);
    etPassword = findViewById(R.id.et_password);
    etPasswordQ = findViewById(R.id.et_password_q);
    etName = findViewById(R.id.et_name);
    etCardId = findViewById(R.id.et_cardid);
    etCode = findViewById(R.id.et_code);
    tvCodeTitle = findViewById(R.id.tv_code_title);
    tvXieyi = findViewById(R.id.tv_xieyi);
    btnSubmit = findViewById(R.id.btn_submit);
    cbEyePassword = findViewById(R.id.cb_eye_password);
    cbEyePasswordQ = findViewById(R.id.cb_eye_password_q);
    btnBack.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

    ivCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        btnSubmit.setEnabled(isChecked);
        btnSubmit.setBackgroundResource(isChecked ? R.drawable.selector_bg_main_btn : R.drawable.selector_of_enable);
      }
    });


    btnSubmit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String password = etPassword.getText().toString();
        String passwordQ = etPasswordQ.getText().toString();
        if (password.isEmpty() || !password.equals(passwordQ)) {
          ToastUtils.show(R.string.passwordTips);
          return;
        }
        register();
      }
    });
    tvCodeTitle.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        getCheckCode();
      }
    });
    cbEyePassword.setOnCheckedChangeListener(new EyeCheckBox.OnCheckedChangeListener() {
      @Override
      public void checkedListener(boolean isCheck) {
        Log.e(TAG, "checkedListener: " + isCheck);
        //如果选中，显示密码
        etPassword.setTransformationMethod(isCheck ? PasswordTransformationMethod.getInstance() : HideReturnsTransformationMethod.getInstance());
      }
    });
    cbEyePasswordQ.setOnCheckedChangeListener(new EyeCheckBox.OnCheckedChangeListener() {
      @Override
      public void checkedListener(boolean isCheck) {
        etPasswordQ.setTransformationMethod(isCheck ? PasswordTransformationMethod.getInstance() : HideReturnsTransformationMethod.getInstance());

      }
    });

  }

  private void register() {
    String tel = etTel.getText().toString();
    String password = etPassword.getText().toString();
    String name = etName.getText().toString();
    String cardId = etCardId.getText().toString();
    String code = etCode.getText().toString();
    FormBody body = new FormBody.Builder()
        .add("tel", tel)
        .add("password", password)
        .add("realName", name)
        .add("cardId", cardId)
        .add("checkCode", code)
        .build();
    HttpUtil.postAsync(Urls.register, body, new Callback() {
      @Override
      public void onFailure(Call call, final IOException e) {
        Log.e(TAG, "onFailure: ", e);
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            ToastUtils.show(e.getMessage());
          }
        });
      }

      @Override
      public void onResponse(Call call, Response response) {
        try {
          final String string = response.body().string();
          JsonElement element = JsonParser.parseString(string);
          if (!element.isJsonObject()) {
            runOnUiThread(new Runnable() {
              @Override
              public void run() {
                ToastUtils.show(string);

              }
            });
            return;
          }
          final JsonObject object = element.getAsJsonObject();
          String code = object.get("code").getAsString();
          if (!"0000".equals(code)) {
            runOnUiThread(new Runnable() {
              @Override
              public void run() {
                ToastUtils.show(string);

              }
            });
            return;
          }
          runOnUiThread(new Runnable() {
            @Override
            public void run() {
              ToastUtils.show(object.get("msg").getAsString());
              finish();
            }
          });
        } catch (final IOException e) {
          e.printStackTrace();
          runOnUiThread(new Runnable() {
            @Override
            public void run() {
              ToastUtils.show(e.getMessage());
            }
          });
        }
      }
    });
  }

  @Override
  protected void onResume() {
    super.onResume();
//    webSocket();
    loadRegisterQrcode();
//    CountDownTimer countDownTimer = getCountDownTimer();
//    if (null != countDownTimer) {
//      countDownTimer.cancel();
//      countDownTimer = null;
//    }
//    countdown();
    loadBaseCountDownTimer();

  }

  private void loadRegisterQrcode() {
    FormBody body = new FormBody.Builder()
        .add("type", "1")
        .build();
    HttpUtil.postAsync(Urls.GET_QRCODE, body, new Callback() {
      @Override
      public void onFailure(Call call, final IOException e) {
        Log.e(TAG, "onFailure: ", e);
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            ToastUtils.show(e.getMessage());
          }
        });
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {
        final String string = response.body().string();
        JsonElement element = JsonParser.parseString(string);
        if (!element.isJsonObject()) {
          runOnUiThread(new Runnable() {
            @Override
            public void run() {
              ToastUtils.show(string);

            }
          });
          return;
        }
        JsonObject object = element.getAsJsonObject();
        String code = object.get("code").getAsString();
        if (!"0000".equals(code)) {
          runOnUiThread(new Runnable() {
            @Override
            public void run() {
              ToastUtils.show(string);

            }
          });
          return;
        }
        qrCodeUrl = element.getAsJsonObject().get("data").getAsJsonObject().get("qrCode").getAsString();
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            ivQrCode.setImageBitmap(QrCodeUtil.createQRCodeBitmap(qrCodeUrl, 300, 300));
          }
        });
      }
    });
  }

  private void getCheckCode() {

    String tel = etTel.getText().toString();
    FormBody body = new FormBody.Builder()
        .add("tel", tel)
        .build();
    HttpUtil.postAsync(Urls.getCheckCode, body, new Callback() {
      @Override
      public void onFailure(Call call, final IOException e) {
        Log.e(TAG, "onFailure: ", e);
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            ToastUtils.show(e.getMessage());
          }
        });
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {
        final String string = response.body().string();
        JsonElement element = JsonParser.parseString(string);
        if (!element.isJsonObject()) {
          runOnUiThread(new Runnable() {
            @Override
            public void run() {
              ToastUtils.show(string);

            }
          });
          return;
        }
        final JsonObject object = element.getAsJsonObject();
        String code = object.get("code").getAsString();
        if (!"0000".equals(code)) {
          runOnUiThread(new Runnable() {
            @Override
            public void run() {
              ToastUtils.show(string);

            }
          });
          return;
        }
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            ToastUtils.show(object.get("msg").getAsString());
//            clickCountdown(5 * 60 * 1000);
            pageCountDownTimer();

          }
        });
      }
    });
  }

  @Override
  protected void onTickCallback(long millisUntilFinished) {

//    tvCodeTitle.setEnabled(false);
//    String value = String.valueOf((int) millisUntilFinished / 1000);
//    tvCodeTitle.setText("获取验证码(" + value + ")");
//    pageCountDown = millisUntilFinished;
//
//    Log.e(TAG, "onTickCallback: " + this.toString());
//    Log.e(TAG, "onTickCallback: " + millisUntilFinished);
  }

  @Override
  protected void onFinishCallback() {
//    pageCountDown = 0;
//    tvCodeTitle.setText("获取验证码");
//    tvCodeTitle.setEnabled(true);
  }


  private void loadBaseCountDownTimer() {
    long countTime = App.baseCountTime;
    if (countTime == 0) {
      tvCodeTitle.setText("获取验证码");
      tvCodeTitle.setEnabled(true);
      return;
    }
    tvCodeTitle.setEnabled(false);
    pageCountDownTimer = new CountDownTimer(App.baseCountTime, 1000) {

      @Override
      public void onTick(long millisUntilFinished) {
        String value = String.valueOf((int) millisUntilFinished / 1000);
        tvCodeTitle.setText("获取验证码(" + value + ")");
        pageCountDown = millisUntilFinished;
      }

      @Override
      public void onFinish() {
        pageCountDown = 0;
        tvCodeTitle.setText("获取验证码");
        tvCodeTitle.setEnabled(true);
      }
    }.start();
  }

  private void pageCountDownTimer() {
    pageCountDownTimer = null;
    tvCodeTitle.setEnabled(false);
    pageCountDownTimer = new CountDownTimer(5 * 60 * 1000, 1000) {

      @Override
      public void onTick(long millisUntilFinished) {
        String value = String.valueOf((int) millisUntilFinished / 1000);
        tvCodeTitle.setText("获取验证码(" + value + ")");
        pageCountDown = millisUntilFinished;
      }

      @Override
      public void onFinish() {
        pageCountDown = 0;
        tvCodeTitle.setText("获取验证码");
        tvCodeTitle.setEnabled(true);
      }
    }.start();
  }


  @Override
  protected void onPause() {
    super.onPause();
    if (pageCountDownTimer != null) {

      pageCountDownTimer.cancel();
    }
    startCountDownTimer(pageCountDown);
  }
}
