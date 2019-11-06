package com.zzrh.lottery.page;

import androidx.appcompat.widget.AppCompatCheckBox;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rabtman.wsmanager.WsManager;
import com.rabtman.wsmanager.listener.WsStatusListener;
import com.zzrh.lottery.R;
import com.zzrh.lottery.base.BaseActivity;
import com.zzrh.lottery.util.QrCodeUtil;
import com.zzrh.lottery.util.ToastUtils;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import okio.ByteString;

public class RegisterActivity extends BaseActivity {

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
    ivQrCode.setImageBitmap(QrCodeUtil.createQRCodeBitmap("https://www.baidu.com",160,160));
    tvXieyi.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        go2Activity(TextActivity.class);
      }
    });

    btnSubmit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String tel = etTel.getText().toString();
        String password = etPassword.getText().toString();
        String passwordQ = etPasswordQ.getText().toString();
        String name = etName.getText().toString();
        String cardId = etCardId.getText().toString();
        String code = etCode.getText().toString();
        if (password.isEmpty() || !password.equals(passwordQ)) {
          ToastUtils.show(R.string.passwordTips);
          return;
        }
      }
    });


  }

  @Override
  protected void onResume() {
    super.onResume();
    webSocket();

  }
  private void webSocket(){
    OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
        .pingInterval(15, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build();
    wsManager = new WsManager.Builder(this)
        .wsUrl("ws://localhost:2333/")
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
      public void onMessage(String text) {
        super.onMessage(text);
      }

      @Override
      public void onMessage(ByteString bytes) {
        super.onMessage(bytes);
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
