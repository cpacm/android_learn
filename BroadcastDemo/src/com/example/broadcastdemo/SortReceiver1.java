package com.example.broadcastdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SortReceiver1 extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		//String msg = intent.getStringExtra("msg");  
		intent = new Intent("android.intent.action.BroadcastService"); 
		intent.addFlags(4);
		context.startService(intent);
		Log.d("TEST","ÓÐÐò¹ã²¥1");
		abortBroadcast();
	}

}
