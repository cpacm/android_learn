package com.example.notebook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;



public class NoteActivity extends Activity {
	
	public static final int EDIT_STATE = 1;
	public static final int ALERT_STATE = 2;
	private ImageView pics;//增加
	private Button complete;//完成
	private EditText title;
	private EditText content;
	private DBManager dm = null;
	private int state = -1;
	private int flag = -1;
	private int id = -1;
	private String titleText = "";
	private String contentText = "";
	private String timeText = "";
	private String picText = "";

	
	protected void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_note);
		
		Intent intent = getIntent();
		state = intent.getIntExtra("state", EDIT_STATE);//参数名，默认值
		flag = intent.getIntExtra("flag", 0);//参数名，默认值
		pics = (ImageView)findViewById(R.id.image);
		complete = (Button)findViewById(R.id.editComplete);
		title = (EditText)findViewById(R.id.editTitle);
		content = (EditText)findViewById(R.id.editContent);
		
		//设置监听
		pics.setOnClickListener(new PictureCompleteListener());
		complete.setOnClickListener(new EditCompleteListener());
		content.setOnTouchListener(new OnTouchListener(){
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			content.setSelection(content.getText().toString().length());
//			Editable ea = content.getText();
//			Selection.setSelection(ea,ea.length());
			return false;
			}
		});	
		if(state == ALERT_STATE||flag==1){//修改状态,赋值控件
		id = intent.getIntExtra("id",0);
		titleText = intent.getStringExtra("title");
		contentText = intent.getStringExtra("content");
		timeText = intent.getStringExtra("time");
		picText = intent.getStringExtra("picture");
		Bitmap bitmap = getLoacalBitmap(picText);
		pics.setImageBitmap(bitmap);
		title.setText(titleText);
		content.setText(contentText);
		}
		dm = new DBManager(this);
	}
	
	public static Bitmap getLoacalBitmap(String url) {
		// TODO Auto-generated method stub
		 try {
             FileInputStream fis = new FileInputStream(url);
             return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片        

          } catch (FileNotFoundException e) {
             e.printStackTrace();
             return null;
        }
	}
	/**
	 * 监听完成按钮
	 * @author mao
	 *
	 */
	public class EditCompleteListener implements OnClickListener{

		public void onClick(View v) {
			// TODO Auto-generated method stub
			titleText = title.getText().toString();
			contentText = content.getText().toString();
//			
//			Log.v(t, t);
//			Log.v(c, c);
			try{
				dm.open();
				if(state == EDIT_STATE)//新增状态
					dm.insert(titleText, contentText,picText);
				if(state == ALERT_STATE)//修改状态
					dm.update(id, titleText, contentText, picText);
				dm.close();
				
			}catch(Exception ex){
				ex.printStackTrace();
			}
			Intent intent = new Intent();
			intent.setClass(NoteActivity.this, MainActivity.class);
			NoteActivity.this.startActivity(intent);
			
			
			//保存完毕
		}
		
	}
	
	public class PictureCompleteListener implements OnClickListener{

		public void onClick(View v) {
			// TODO Auto-generated method stub
//			
//			Log.v(t, t);
//			Log.v(c, c);
			Intent intents = new Intent();
			intents.putExtra("id",id);
			intents.putExtra("title", title.getText().toString());
			intents.putExtra("content", content.getText().toString());
			intents.putExtra("state", state);
			intents.setClass(NoteActivity.this, picActivity.class);
			NoteActivity.this.startActivity(intents);
			//保存完毕
		}
		
	}

}
