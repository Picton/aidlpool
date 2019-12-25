package com.xiaowei.aidlsercer.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
 import com.tope.aidlserver.IMdmQueryTokenAidlInterface;
import com.tope.aidlserver.IMdmUserAidlInterface;
import com.tope.aidlserver.ITPBinderPool;

/**
 * aidl提供对外服务
 *@作者 xiaowei
 *@创建日期 2019/12/21 15:26
 */
public class IMdmService extends Service {
  public IMdmService() {
  }

  @Override
  public IBinder onBind(Intent intent) {//绑定服务执行
     return   binderPool.asBinder();
  }
  ITPBinderPool binderPool = new ITPBinderPool.Stub() {
    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

    }

    @Override
    public IBinder queryBinder(int binderCode){
      switch (binderCode){
        case 1://token
          return iMdmQueryTokenAidlInterface.asBinder();
        case 2://用户
          return iMdmUserInfoAidlInterface.asBinder();
      }
      return null;
    }
  };
  IMdmQueryTokenAidlInterface iMdmQueryTokenAidlInterface = new IMdmQueryTokenAidlInterface.Stub(){
    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

    }

    @Override
    public String getToken(boolean isRefresh) throws RemoteException {
      Log.d("this","getToken");

      return "获取token";
    }

  };

  IMdmUserAidlInterface iMdmUserInfoAidlInterface = new IMdmUserAidlInterface.Stub(){
    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

    }
    @Override
    public String getUserInfo() throws RemoteException {
      Log.d("this","getUserInfo");

      return "获取用户信息";
    }

    @Override
    public void quitLogin() throws RemoteException {
      Log.d("this","quitLogin");
//      Toast.makeText(IMdmService.this,"quitLogin",Toast.LENGTH_SHORT).show();
    }
  };
}
