package com.example.test;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MinaService extends Service{
	private String msg = "service bind success";
	
	private final IBinder mBinder = new LocalBinder();

	@Override
	public IBinder onBind(Intent intent) {
		Log.d("TEST", "onbind");
		//用于绑定Activity
		return mBinder;
	}

	@Override
	public void onCreate() {
		//开启服务时会首先调用该方法
		Log.d("TEST", "onCreate");
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//start service时会在onCreate后调用该方法
		Log.d("TEST", "start");
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		// 停止service后会调用此方法
		Log.d("TEST", "destroy");
		super.onDestroy();
	}

	@Override
	public boolean onUnbind(Intent intent) {
		//取消Activity与service绑定时要调用此方法
		Log.d("TEST", "onunbind");
		return super.onUnbind(intent);
	}

	@Override
	public void onRebind(Intent intent) {
		// 重新绑定时调用此方法
		Log.d("TEST", "onrebind");
		super.onRebind(intent);
	}

	
	/**
	 * @author cpacm
	 * 通过Binder返回service的引用到Activity中
	 */
	public class LocalBinder extends Binder {
		MinaService getService() {
			return MinaService.this;
        }
	}
	
	//普通方法，证明service在后台运行
	public String getMsg(){
		return msg;
	}
	public void setMsg(String m){
		this.msg = m;
	}
	
}
