package com.zzrh.lottery.util.http;

//13554296038   123456
public interface Urls {
  String BASE_URL = "http://10.10.1.14:8080/lottery";
  String WEB_SOCKET = "ws://10.10.1.14:8080/lottery/websocket/";
  //  登录
  String LOGIN = BASE_URL + "/api/user/login";
  //获取二维码 0登录 1注册 2下单
  String GET_QRCODE = BASE_URL + "/api/app/createQrcode";
  //修改密码
  String RESET_PASSWORD = BASE_URL + "/api/user/updatePassword";
  //获取验证码
  String getCheckCode = BASE_URL + "/api/user/sendValidateCode";
  //注册
  String register = BASE_URL + "/api/user/regist";
}
