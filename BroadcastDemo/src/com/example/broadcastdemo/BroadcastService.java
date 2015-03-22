package com.example.broadcastdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class BroadcastService extends Service{

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		//开启服务时会首先调用该方法
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//根据每次intent传过来的信息进行判断来显示不同信息
		switch(intent.getFlags()){
			case 1:{
				Toast.makeText(getApplicationContext(), "静态注册", Toast.LENGTH_SHORT).show();
				break;
			}
			case 2:{
				Toast.makeText(getApplicationContext(), "动态注册", Toast.LENGTH_SHORT).show();
				break;
			}
			case 3:{
				Toast.makeText(getApplicationContext(), "普通广播", Toast.LENGTH_SHORT).show();
				break;
			}
			case 4:{
				Toast.makeText(getApplicationContext(), "有序广播", Toast.LENGTH_SHORT).show();
				break;
			}
		}
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		// 停止service后会调用此方法
		Log.d("TEST", "destroy");
		super.onDestroy();
	}

}
