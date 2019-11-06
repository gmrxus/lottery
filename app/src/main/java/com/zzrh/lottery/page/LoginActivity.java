package com.zzrh.lottery.page;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zzrh.lottery.R;
import com.zzrh.lottery.base.BaseActivity;
import com.zzrh.lottery.ui.FocusButton;
import com.zzrh.lottery.ui.FocusImageView;
import com.zzrh.lottery.util.http.HttpUtil;
import com.zzrh.lottery.util.http.Urls;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends BaseActivity {
  private static final String TAG = "LoginActivity";

  private FocusButton btnBack;
  private EditText etTel;
  private EditText etPassword;
  private FocusButton btnRegister;
  private FocusButton btnLogin;
  private FocusImageView ivEye;

  private boolean isVisibility = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    initView();
  }

  private void initView() {
    btnBack = findViewById(R.id.btn_back);
    etTel = findViewById(R.id.et_tel);
    etPassword = findViewById(R.id.et_password);
    btnRegister = findViewById(R.id.btn_register);
    btnLogin = findViewById(R.id.btn_login);
    ivEye = findViewById(R.id.iv_eyes);
    btnBack.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    btnRegister.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        go2Activity(RegisterActivity.class);
      }
    });
    btnLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        RequestBody body = new FormBody.Builder()
            .add("tel", etTel.getText().toString())
            .add("password", etPassword.getText().toString())
            .build();
        HttpUtil.postAsync(Urls.BASE_URL, body, new Callback() {
          @Override
          public void onFailure(Call call, IOException e) {
            //登录失败
          }

          @Override
          public void onResponse(Call call, Response response) throws IOException {
            Gson gson = new Gson();
            JsonObject object = JsonParser.parseString(response.body().string()).getAsJsonObject();
            String code = object.get("code").getAsString();
            if ("0000".equals(code)) {
              //登录成功
            } else {
              //登录失败
            }
          }
        });
      }
    });
    ivEye.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        isVisibility = !isVisibility;
        ivEye.setImageDrawable(ContextCompat.getDrawable(LoginActivity.this,
            isVisibility
                ? R.drawable.ic_eye_on_orange
                : R.drawable.ic_eye_off_orange));
        ivEye.invalidate();
        if(isVisibility){
          //如果选中，显示密码
          etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }else{
          //否则隐藏密码
          etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
      }
    });

    ivEye.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
        int imgRes = isVisibility ? R.drawable.ic_eye_on : R.drawable.ic_eye_off;
        int imgResOrange = isVisibility ? R.drawable.ic_eye_on_orange : R.drawable.ic_eye_off_orange;
        ivEye.setImageDrawable(ContextCompat.getDrawable(LoginActivity.this, hasFocus ? imgResOrange : imgRes));
      }
    });
  }
}
