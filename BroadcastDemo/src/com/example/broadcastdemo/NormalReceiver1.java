package com.example.broadcastdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NormalReceiver1 extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		intent = new Intent("android.intent.action.BroadcastService"); 
		intent.addFlags(3);
		context.startService(intent);
		Log.d("TEST","∆’Õ®π„≤•1");
		abortBroadcast();
	}

}
