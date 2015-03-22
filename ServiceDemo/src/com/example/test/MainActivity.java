package com.example.test;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{

	private MinaService ms = null;
	private Button b1,b2,b3,b4,b5,b6;
	private TextView textView;
	private EditText editText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//获得界面的控件
		textView = (TextView) findViewById(R.id.textView1);
		editText = (EditText) findViewById(R.id.editText1);
		b1 = (Button) findViewById(R.id.button1);
		b1.setOnClickListener(this);
		b2 = (Button) findViewById(R.id.button2);
		b2.setOnClickListener(this);
		b3 = (Button) findViewById(R.id.button3);
		b3.setOnClickListener(this);
		b4 = (Button) findViewById(R.id.button4);
		b4.setOnClickListener(this);
		b5 = (Button) findViewById(R.id.button5);
		b5.setOnClickListener(this);
		b6 = (Button) findViewById(R.id.button6);
		b6.setOnClickListener(this);
		Log.d("TEST","===初始化完成===");
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
			case R.id.button1:{//按钮一：用startService开启服务
				Intent i  = new Intent();  
	            i.setClass(MainActivity.this, MinaService.class);  
	            startService(i);
				break;
			}
			case R.id.button2:{//按钮二:停止服务
				Intent i  = new Intent();  
	            i.setClass(MainActivity.this, MinaService.class);  
	            stopService(i);
				break;
			}
			case R.id.button3:{//按钮三:用bindService来绑定Service和Activity
				bindService(new Intent(MainActivity.this,    
                        MinaService.class), mConnection, Context.BIND_AUTO_CREATE); 
				break;
			}
			case R.id.button4:{//取消绑定
				unbindService(mConnection); 
				break;
			}
			case R.id.button5:{//跳转到下一个Activity
				Intent i  = new Intent();  
	            i.setClass(MainActivity.this, SecondActivity.class); 
	            startActivity(i);
	            
				break;
			}
			case R.id.button6:{//显示Service里面的信息
				textView.setText(ms.getMsg());
				ms.setMsg(editText.getText().toString());
				break;
			}
		}
	}
	
	
	public void show(String str){
		Toast.makeText(this, str, Toast.LENGTH_LONG).show();
	}
	
	private ServiceConnection mConnection = new ServiceConnection(){

		
		/**
		 * 绑定Service和Activity时会用到这个函数，所以可以在这里获取到Service的引用对象
		 * @see android.content.ServiceConnection#onServiceConnected(android.content.ComponentName, android.os.IBinder)
		 **/
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			//获取Service的引用对象
			 ms = ((MinaService.LocalBinder)service).getService();
			 Toast.makeText(MainActivity.this, "connect",
                     Toast.LENGTH_SHORT).show(); 
		}

		/**
		 * 这个函数是在绑定异常时调用，平时不会使用到这个函数
		 * @see android.content.ServiceConnection#onServiceDisconnected(android.content.ComponentName)
		 **/
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			ms = null;
			Toast.makeText(MainActivity.this, "cutdown",
                    Toast.LENGTH_SHORT).show(); 
		}
		
	};
	
}
