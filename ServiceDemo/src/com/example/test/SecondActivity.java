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

public class SecondActivity extends Activity implements OnClickListener{
	
	
	private MinaService ms = null;
	private Button b1,b2,b3,b4,b5,b6;
	private TextView textView;
	private EditText editText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
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
			case R.id.button1:{
				Intent i  = new Intent();  
	            i.setClass(SecondActivity.this, MinaService.class);  
	            startService(i);
				break;
			}
			case R.id.button2:{
				Intent i  = new Intent();  
	            i.setClass(SecondActivity.this, MinaService.class);  
	            stopService(i);
				break;
			}
			case R.id.button3:{
				bindService(new Intent(SecondActivity.this,    
                        MinaService.class), mConnection, Context.BIND_AUTO_CREATE); 
				break;
			}
			case R.id.button4:{
				unbindService(mConnection); 
				break;
			}
			case R.id.button5:{
				Intent i  = new Intent();  
	            i.setClass(SecondActivity.this, MainActivity.class); 
	            startActivity(i);
	            
				break;
			}
			case R.id.button6:{
				textView.setText(ms.getMsg());
				ms.setMsg(editText.getText().toString());
				break;
			}
		}
	}
	
	private ServiceConnection mConnection = new ServiceConnection(){

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			 ms = ((MinaService.LocalBinder)service).getService();
			 Toast.makeText(SecondActivity.this, "connect",
                     Toast.LENGTH_SHORT).show(); 
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			ms = null;
			Toast.makeText(SecondActivity.this, "cutdown",
                    Toast.LENGTH_SHORT).show(); 
		}
		
	};


}
