package com.example.broadcastdemo;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;


public class MainActivity extends Activity {

	private HotReceiver receiver;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//动态注册广播
		//新建一个广播对象
	    receiver = new HotReceiver();  
	    //新建一个intent管理机制，（功能是对组件进行过滤，只获取需要的消息）
		IntentFilter filter = new IntentFilter(); 
		//添加白名单（只获取该动作的信息）
		filter.addAction("android.intent.action.HOT_BROADCAST");          
		//与广播绑定，进行注册
		registerReceiver(receiver, filter);
	}

	@Override
	protected void onDestroy() {
		//取消注册，一定要记得，不然系统会报错误
		unregisterReceiver(receiver);
		stopService(new Intent("android.intent.action.BroadcastService"));
		super.onDestroy();
	}

}
