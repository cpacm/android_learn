package com.example.broadcastdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ColdReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		//跳转到service中
		intent = new Intent("android.intent.action.BroadcastService"); 
		intent.addFlags(1);
		//开启service
		context.startService(intent);
		//日志打印
		Log.d("TEST","静态注册");
	}

}
