package com.xiaowei.aidlclient.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.xiaowei.aidlclient.bean.AidlMessageBean;
import com.tope.aidlserver.IMdmQueryTokenAidlInterface;
import com.tope.aidlserver.IMdmUserAidlInterface;
import com.tope.aidlserver.ITPBinderPool;

import java.lang.reflect.InvocationTargetException;


/**
 * aidl 工具类
 * @author xiaowei
  * @创建日期 2019/12/23 11:02
 */
public class MdmAidlHelper {
  public static final ActivityLifecycleImpl ACTIVITY_LIFECYCLE = new ActivityLifecycleImpl();
  @SuppressLint("StaticFieldLeak")
  private static Application sApplication;

  public static MdmAidlHelper getInstance() {//方法单例
    return Holder.instance;
  }

  private static class Holder {
    private static final MdmAidlHelper instance = new MdmAidlHelper();
  }

  /**
   * Init MdmAidlHelper.
   * <p>Init it in the class of Application.</p>
   */
  public void init() {
    init(getApplicationByReflect());
  }

  /**
   * Init utils.
   * <p>Init it in the class of Application.</p>
   *
   * @param app application
   */
  public void init(final Application app) {
    if (sApplication == null) {
      if (app == null) {
        sApplication = getApplicationByReflect();
      } else {
        sApplication = app;
      }
      sApplication.registerActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE);
    } else {
      if (app != null && app.getClass() != sApplication.getClass()) {
        sApplication.unregisterActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE);
        sApplication = app;
        sApplication.registerActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE);
      }
    }
  }

  /**
   * Return the context of Application object.
   *
   * @return the context of Application object
   */
  public Application getApp() {
    if (sApplication != null) return sApplication;
    Application app = getApplicationByReflect();
    init(app);
    return app;
  }

  /**
   * 获取用户信息
   *
   * @作者 xiaowei
   * @创建日期 2019/12/23 16:04
   */
  public AidlMessageBean getUserInfo() {
    AidlMessageBean aidlMessageBean = new AidlMessageBean();

    if (ACTIVITY_LIFECYCLE != null && ACTIVITY_LIFECYCLE.itpBinderPool != null) {
      try {
        IMdmUserAidlInterface iMdmQueryTokenAidlInterface = IMdmUserAidlInterface.Stub.asInterface(ACTIVITY_LIFECYCLE.itpBinderPool.queryBinder(2));
        aidlMessageBean.setCode(AidlMessageBean.AIDL_MESSAGE_CODE_OK);
        aidlMessageBean.setData(iMdmQueryTokenAidlInterface.getUserInfo());
        aidlMessageBean.setMessage("获取用户信息成功");

      } catch (RemoteException e) {
        e.printStackTrace();
        aidlMessageBean.setCode(AidlMessageBean.AIDL_MESSAGE_CODE_ERROR);
        aidlMessageBean.setMessage("获取数据");
      }
    } else {
      aidlMessageBean.setCode(AidlMessageBean.AIDL_MESSAGE_CODE_ERROR);
      aidlMessageBean.setMessage("服务器异常");
    }
    return aidlMessageBean;
  }

  /**
   * 获取token
   *
   * @param isRefresh 是否刷新
   * @作者 xiaowei
   * @创建日期 2019/12/23 16:04
   */
  public AidlMessageBean getToken(boolean isRefresh) {
    AidlMessageBean aidlMessageBean = new AidlMessageBean();

    if (ACTIVITY_LIFECYCLE != null && ACTIVITY_LIFECYCLE.itpBinderPool != null) {
      try {

        IMdmQueryTokenAidlInterface iMdmQueryTokenAidlInterface = IMdmQueryTokenAidlInterface.Stub.asInterface(ACTIVITY_LIFECYCLE.itpBinderPool.queryBinder(1));
        aidlMessageBean.setCode(AidlMessageBean.AIDL_MESSAGE_CODE_OK);
        aidlMessageBean.setData(iMdmQueryTokenAidlInterface.getToken(isRefresh));
        aidlMessageBean.setMessage("获取Token成功");

      } catch (RemoteException e) {
        e.printStackTrace();
        aidlMessageBean.setCode(AidlMessageBean.AIDL_MESSAGE_CODE_ERROR);
        aidlMessageBean.setMessage("获取数据");
      }
    } else {
      aidlMessageBean.setCode(AidlMessageBean.AIDL_MESSAGE_CODE_ERROR);
      aidlMessageBean.setMessage("服务器异常");
    }
    return aidlMessageBean;
  }

  /**
   * 退出登录
   *
   * @作者 xiaowei
   * @创建日期 2019/12/23 16:04
   */
  public AidlMessageBean quitLogin() {
    AidlMessageBean aidlMessageBean = new AidlMessageBean();

    if (ACTIVITY_LIFECYCLE != null && ACTIVITY_LIFECYCLE.itpBinderPool != null) {
      try {

        IMdmUserAidlInterface iMdmQueryTokenAidlInterface = IMdmUserAidlInterface.Stub.asInterface(ACTIVITY_LIFECYCLE.itpBinderPool.queryBinder(2));
        aidlMessageBean.setCode(AidlMessageBean.AIDL_MESSAGE_CODE_OK);
        iMdmQueryTokenAidlInterface.quitLogin();
        aidlMessageBean.setMessage("退出成功");

      } catch (RemoteException e) {
        e.printStackTrace();
        aidlMessageBean.setCode(AidlMessageBean.AIDL_MESSAGE_CODE_ERROR);
        aidlMessageBean.setMessage("获取数据");
      }
    } else {
      aidlMessageBean.setCode(AidlMessageBean.AIDL_MESSAGE_CODE_ERROR);
      aidlMessageBean.setMessage("服务器异常");
    }
    return aidlMessageBean;
  }

  /**
   * 反射获取 getApplication
   *
   * @作者 xiaowei
   * @创建日期 2019/12/24 11:52
   */
  private static Application getApplicationByReflect() {
    try {
      @SuppressLint("PrivateApi")
      Class<?> activityThread = Class.forName("android.app.ActivityThread");
      Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
      Object app = activityThread.getMethod("getApplication").invoke(thread);
      if (app == null) {
        throw new NullPointerException("u should init first");
      }
      return (Application) app;
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    throw new NullPointerException("u should init first");
  }


  /**
   * 监听 activity 生命周期
   *
   * @作者 xiaowei
   * @创建日期 2019/12/24 11:52
   */
  public static class ActivityLifecycleImpl implements Application.ActivityLifecycleCallbacks {
    public ITPBinderPool itpBinderPool;
    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
      @Override
      public void binderDied() {
        if (itpBinderPool != null) {
          itpBinderPool.asBinder().unlinkToDeath(this, 0);
          itpBinderPool = null;
          // 重连
          bindService();
        }
      }
    };

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
      bindService();
    }

    @Override
    public void onActivityStarted(Activity activity) {
      bindService();
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {/**/

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {/**/}

    @Override
    public void onActivityDestroyed(Activity activity) {
      // unBindService(activity);
    }

    private ServiceConnection conn = new ServiceConnection() {

      //绑定服务，回调onBind()方法
      @Override
      public void onServiceConnected(ComponentName name, IBinder service) {
        //Toast.makeText(Holder.instance.getApp(), "onServiceConnected", Toast.LENGTH_SHORT).show();
        try {
          itpBinderPool = ITPBinderPool.Stub.asInterface(service);
        } catch (Exception e) {
          e.printStackTrace();
          itpBinderPool = null;
        }
        if (null != itpBinderPool) {//重连机制
          try {
            itpBinderPool.asBinder().linkToDeath(deathRecipient, 0);
          } catch (RemoteException e) {
            e.printStackTrace();
          }
        }
      }

      @Override
      public void onServiceDisconnected(ComponentName name) {
        itpBinderPool = null;
       // Toast.makeText(Holder.instance.getApp(), "onServiceDisconnected", Toast.LENGTH_SHORT).show();

      }
    };
    /**
     *   绑定aidl 服务
     *@作者 xiaowei
     *@创建日期 2019/12/24 11:57
     */
    private void bindService() {
      if (itpBinderPool == null) {

        //绑定的时候服务端自动创建
        try {
          getApplicationByReflect().bindService(ServiceIntentUtil.createExplicitFromImplicitIntent(getApplicationByReflect(), new Intent("com.tope.aidlsercer"))
              , conn, Context.BIND_AUTO_CREATE);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }

    }
    /**
     *   解绑服务
     *@作者 xiaowei
     *@创建日期 2019/12/24 11:57
     */
    private void unBindService() {
      if(getApplicationByReflect()!=null && conn!=null){
        getApplicationByReflect().unbindService(conn);
      }
    }


  }


}
