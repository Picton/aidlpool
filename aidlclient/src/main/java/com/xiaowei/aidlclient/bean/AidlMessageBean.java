package com.xiaowei.aidlclient.bean;

import java.io.Serializable;

/**
 *   基础数据bean
 *@作者 xiaowei
 *@创建日期 2019/12/23 16:05
 */

public class AidlMessageBean implements Serializable {
  public final static int  AIDL_MESSAGE_CODE_OK = 200;//成功
  public final static int  AIDL_MESSAGE_CODE_ERROR = 400;//失败
  private int code;//返回码
  private String message;//消息
  private String data;//返回数据

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }
}
