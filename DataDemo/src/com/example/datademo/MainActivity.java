package com.example.datademo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	
	 /** 存储后的文件路径：/data/data/<package name>/shares_prefs + 文件名.xml */ 
	public static final String PATH = "/data/data/com.example.datademo/shared_prefs/preferences.xml";
	private SharedPreferences userInfo;

	private Button button1;
	private Button button2;
	private Button button3;
	private Button button4;
	private Button button5;
	private Button button6;
	private Button button7;
	private Button button8;
	private Button button9;
	private Button button10;
	private TextView textView;
	private EditText editText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 获得界面的控件
		textView = (TextView) findViewById(R.id.textView1);
		editText = (EditText) findViewById(R.id.editText1);
		button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(this);
		button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(this);
		button3 = (Button) findViewById(R.id.button3);
		button3.setOnClickListener(this);
		button4 = (Button) findViewById(R.id.button4);
		button4.setOnClickListener(this);
		button5 = (Button) findViewById(R.id.button5);
		button5.setOnClickListener(this);
		button6 = (Button) findViewById(R.id.button6);
		button6.setOnClickListener(this);
		button7 = (Button) findViewById(R.id.button7);
		button7.setOnClickListener(this);
		button8 = (Button) findViewById(R.id.button8);
		button8.setOnClickListener(this);
		button9 = (Button) findViewById(R.id.button9);
		button9.setOnClickListener(this);
		button10 = (Button) findViewById(R.id.button10);
		button10.setOnClickListener(this);
		
		//在界面组件或服务组件中调用，构造应用默认的设置文件，默认文件名字为_preferences.xml
		//userInfo = PreferenceManager.getDefaultSharedPreferences(this);  
		//或获取指定名字的SharedPreferences对象  参数分别为存储的文件名和存储模式。
		userInfo = getSharedPreferences("preferences.xml", Activity.MODE_PRIVATE); 
		
		//读取数据，如果无法找到则会使用默认值
        String username = userInfo.getString("name", "未定义姓名");  
        String msg = userInfo.getString("msg", "未定义信息");
        //显示文本
        textView.setText(username+","+msg);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1: {
		    Log.d("TEST", "sdcard?"+FileUtil.isSdcardAvailable());
		    Log.d("TEST", "全部容量"+(float)FileUtil.getSDAllSizeKB()/1024/1024);
		    Log.d("TEST", "可用容量"+(float)FileUtil.getSDAvalibleSizeKB()/1024/1024);
		    Toast.makeText(this, "status", Toast.LENGTH_SHORT).show();
			break;
		}
		case R.id.button2: {
			Log.d("TEST", "example文件夹存在?"+FileUtil.isFileExist("example"));
		    Log.d("TEST", "创建forexample文件夹"+FileUtil.createFile("forexample"));
		    Toast.makeText(this, "IsFile", Toast.LENGTH_SHORT).show();
			break;
		}  
		case R.id.button3: {
			FileUtil.writeToSDCardFile("forexample", "test.txt",   
					editText.getText().toString(), "UTF-8", true);
			Toast.makeText(this, "WriteFile", Toast.LENGTH_SHORT).show();
			break;
		} 
		case R.id.button4: {
			textView.setText(FileUtil.ReadFromSDCardFile("forexample", "test.txt"));
			Toast.makeText(this, "ReadFile", Toast.LENGTH_SHORT).show();
			break;
		} 
		case R.id.button5: {
			writeFile("test2.txt",editText.getText().toString());
			Toast.makeText(this, "WritePrivateFile", Toast.LENGTH_SHORT).show();
			break;
		} 
		case R.id.button6: {
			textView.setText(readFile("test2.txt"));
			Toast.makeText(this, "ReadPrivateFile", Toast.LENGTH_SHORT).show();
			break;
		} 
		case R.id.button7: {
			//获得SharedPreferences的编辑器
			SharedPreferences.Editor editor = userInfo.edit();
			//将信息存入相应的键值中
			editor.putString("name", editText.getText().toString()).commit();
			Toast.makeText(this, "SetName", Toast.LENGTH_SHORT).show();
			break;
		} 
		case R.id.button8: {
			//获得SharedPreferences的编辑器
			SharedPreferences.Editor editor = userInfo.edit();
			//将信息存入相应的键值中ss
			editor.putString("msg", editText.getText().toString()).commit();
			Toast.makeText(this, "SetMessage", Toast.LENGTH_SHORT).show();
			break;
		} 
		case R.id.button9: {
			userInfo = getSharedPreferences("preferences.xml", Activity.MODE_PRIVATE);
			String username = userInfo.getString("name", "未定义姓名");  
		    String msg = userInfo.getString("msg", "未定义信息");
		    textView.setText(username+","+msg);
			Toast.makeText(this, "ShowMsg", Toast.LENGTH_SHORT).show();
			break;
		} 
		case R.id.button10: {
			textView.setText(print());
			Toast.makeText(this, "ShowXML", Toast.LENGTH_SHORT).show();
			break;
		} 
		}
	}
	
	public void writeFile(String fileName,String writestr){ 
		try{ 
		        FileOutputStream fout =openFileOutput(fileName,MODE_PRIVATE); 
		        byte [] bytes = writestr.getBytes(); 
		        fout.write(bytes); 
		        fout.close(); 
		      } 
		        catch(Exception e){ 
		        e.printStackTrace(); 
		       } 
		} 

		//读数据
	public String readFile(String fileName){ 
	  String res=""; 
	  try{ 
	         FileInputStream fin = openFileInput(fileName); 
	         int length = fin.available(); 
	         byte [] buffer = new byte[length]; 
	         fin.read(buffer);     
	         res = EncodingUtils.getString(buffer, "UTF-8"); 
	         fin.close();     
	     } 
	     catch(Exception e){ 
	         e.printStackTrace(); 
	     } 
	     return res; 
	}
	
	private String print() {  
        StringBuffer buff = new StringBuffer();  
        try {  
            BufferedReader reader = new BufferedReader(new InputStreamReader(  
                    new FileInputStream(PATH)));  
            String str;  
            while ((str = reader.readLine()) != null) {  
                buff.append(str + "/n");  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return buff.toString();  
    }

}
