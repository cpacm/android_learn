package com.example.broadcastdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class HotReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		//String msg = intent.getStringExtra("msg");  
		intent = new Intent("android.intent.action.BroadcastService"); 
		intent.addFlags(2);
		context.startService(intent);
		Log.d("TEST","¶¯Ì¬×¢²á");
	}

}
