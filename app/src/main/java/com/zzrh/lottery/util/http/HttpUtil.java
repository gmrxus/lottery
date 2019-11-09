package com.zzrh.lottery.util.http;

import android.util.Log;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
  private static final String TAG = "HttpUtil";
  private static int timOut = 30;
  private static OkHttpClient client = new OkHttpClient.Builder()
      .readTimeout(timOut, TimeUnit.SECONDS)
      .cookieJar(new CookieJar() {

        private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
          cookieStore.put(url.host(), cookies);
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
          List<Cookie> cookies = cookieStore.get(url.host());
          return cookies != null ? cookies : new ArrayList<Cookie>();
        }
      })
      .build();


  public static String get(String url) {
    //第一步获取okHttpClient对象

    String result = "";
    //第二步构建Request对象
    Request request = new Request.Builder()
        .url(url)
        .get()
        .build();
    //第三步构建Call对象
    Call call = client.newCall(request);
    //第四步:异步get请求
    try {
      Response execute = call.execute();
      result = execute.body().string();
    } catch (IOException e) {
      e.printStackTrace();
      Log.e(TAG, "get: ", e);
    }
    return result;
  }

  public static void getAsync(String url, Callback callback) {

    Request request = new Request.Builder()
        .url(url)
        .get()
        .build();
    Call call = client.newCall(request);
    call.enqueue(callback);

  }

  public static String post(String url, RequestBody requestBody) {
    String result = "";
    Request request = new Request.Builder()
        .url(url)
        .post(requestBody)
        .build();
    Call call = client.newCall(request);
    try {
      result = call.execute().body().string();

    } catch (IOException e) {
      e.printStackTrace();
      Log.e(TAG, "post: ", e);
    }
    return result;
  }

  public static void postAsync(String url, RequestBody requestBody, Callback callback) {
    Request request = new Request.Builder()
        .url(url)
        .post(requestBody)
        .build();
    Call call = client.newCall(request);
    call.enqueue(callback);
  }
}
