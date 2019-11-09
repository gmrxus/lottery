package com.zzrh.lottery.page;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zzrh.lottery.R;
import com.zzrh.lottery.common.SPKey;
import com.zzrh.lottery.ui.EyeCheckBox;
import com.zzrh.lottery.ui.FocusButton;
import com.zzrh.lottery.util.SPUtil;
import com.zzrh.lottery.util.ToastUtils;
import com.zzrh.lottery.util.http.HttpUtil;
import com.zzrh.lottery.util.http.Urls;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

public class ResetPasswordActivity extends AppCompatActivity {
  private static final String TAG = "ResetPasswordActivity";

  private FocusButton btnBack;
  private FocusButton btnSubmit;
  private EditText etOld;
  private EditText etPassword;
  private EditText etPasswordQ;
  private String oldPassword;
  private String password;
  private EyeCheckBox cbEyeOldPassword;
  private EyeCheckBox cbEyePassword;
  private EyeCheckBox cbEyePasswordQ;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_reset_password);
    initView();
  }

  private void initView() {
    btnBack = findViewById(R.id.btn_back);
    btnSubmit = findViewById(R.id.btn_submit);
    etOld = findViewById(R.id.et_old);
    etPassword = findViewById(R.id.et_password);
    etPasswordQ = findViewById(R.id.et_password_q);
    cbEyeOldPassword = findViewById(R.id.cb_eye_old_password);
    cbEyePassword = findViewById(R.id.cb_eye_password);
    cbEyePasswordQ = findViewById(R.id.cb_eye_password_q);
    btnBack.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    btnSubmit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        oldPassword = etOld.getText().toString();
        password = etPassword.getText().toString();
        String passwordQ = etPasswordQ.getText().toString();
        if (oldPassword.isEmpty() || password.isEmpty() || passwordQ.isEmpty()) {
          ToastUtils.show("请重新输入");
          return;
        }
        if (!checkPassword(password, passwordQ)) {
          ToastUtils.show("请重新验证密码");
          return;
        }
        resetPassword();
      }
    });
    cbEyeOldPassword.setOnCheckedChangeListener(new EyeCheckBox.OnCheckedChangeListener() {
      @Override
      public void checkedListener(boolean isCheck) {
        etOld.setTransformationMethod(isCheck ? PasswordTransformationMethod.getInstance() : HideReturnsTransformationMethod.getInstance());
      }
    });
    cbEyePassword.setOnCheckedChangeListener(new EyeCheckBox.OnCheckedChangeListener() {
      @Override
      public void checkedListener(boolean isCheck) {
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

  private void resetPassword() {
    FormBody body = new FormBody.Builder()
        .add("userId", (String) SPUtil.get(ResetPasswordActivity.this, SPKey.userId, ""))
        .add("origialPassword", oldPassword)
        .add("newPassword", password)
        .build();
    HttpUtil.postAsync(Urls.RESET_PASSWORD, body, new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
        Log.e(TAG, "onFailure: ", e);
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
        } else {
          runOnUiThread(new Runnable() {
            @Override
            public void run() {
              ToastUtils.show(R.string.scusess);
//              SPUtil.clear(ResetPasswordActivity.this);
              finish();
            }
          });
        }
      }
    });
  }

  private boolean checkPassword(String password, String passwordQ) {
    if (password.equals(passwordQ)) {
      return true;
    }
    return false;
  }
}
