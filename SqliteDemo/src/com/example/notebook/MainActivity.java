package com.example.notebook;

import java.util.ArrayList;


import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {

	private TextView tv;
	public static final int EDIT_STATE = 1;
	public static final int ALERT_STATE = 2;
	private ListViewAdapter adapter;// 数据源对象
	private DBManager dm = null;// 数据库管理对象
	private Cursor cursor = null;
    private Button addRecordButton;//新增
	private Button deleteRecordButton;//删除
	private Button modifyRecordButton;//修改
	private int pos=-1;
	private Intent intent = new Intent();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ListView listView=(ListView)this.findViewById(R.id.lv);
		addRecordButton = (Button)this.findViewById(R.id.newbn);
		modifyRecordButton = (Button)this.findViewById(R.id.editbn);
		deleteRecordButton = (Button)this.findViewById(R.id.delbn);
		tv = (TextView)this.findViewById(R.id.tv);
		dm = new DBManager(this);//数据库操作对象
		initAdapter();//初始化
		listView.setAdapter(adapter);//自动为id为list的ListView设置适配器
		//设置按钮监听器
        addRecordButton.setOnClickListener(new AddRecordListener());//新增
        deleteRecordButton.setOnClickListener(new DeleteRecordListener());//删除
        modifyRecordButton.setOnClickListener(new ModifyRecordListener());//修改
        listView.setOnItemClickListener 
        ( 
           new AdapterView.OnItemClickListener()  
           { 
               public void onItemClick(AdapterView adapterView, View view,int position, long id) 
               { 
            	// TODO Auto-generated method stub		
//       			Log.v("position", position+"");
//       			Log.v("id", id+"");
       			
       			cursor.moveToPosition(position);
       			pos=position;
       			tv.setText(cursor.getString(cursor.getColumnIndex("title")));
       			
       			//intent.putExtra("state", EDIT_STATE);
       			intent.putExtra("id", cursor.getInt(cursor.getColumnIndex("_id")));
       			intent.putExtra("title", cursor.getString(cursor.getColumnIndex("title")));
       			intent.putExtra("time", cursor.getString(cursor.getColumnIndex("time")));
       			intent.putExtra("content", cursor.getString(cursor.getColumnIndex("content")));
       			intent.putExtra("picture", cursor.getString(cursor.getColumnIndex("pic")));
       		//	cursor.close();
       			dm.close();
       			
       			//intent.setClass(MainActivity.this, NoteActivity.class);
       			//MainActivity.this.startActivity(intent);
               } 
           } 
        ); 
        
	}
	
	
	//初始化数据源
		public void initAdapter(){
	    	
	    	dm.open();//打开数据库操作对象
	    	
	    	cursor = dm.selectAll();//获取所有数据
	    	
	    	cursor.moveToFirst();//将游标移动到第一条数据，使用前必须调用
	    	
	    	int count = cursor.getCount();//个数
	    	ArrayList<String> contents = new ArrayList<String>();//图片的所有集合
	    	ArrayList<String> imgs = new ArrayList<String>();//图片的所有集合
	    	ArrayList<String> items = new ArrayList<String>();//标题的所有集合
	    	ArrayList<String> times = new ArrayList<String>();//时间的所有集合
	    	for(int i= 0; i < count; i++){
	    		contents.add(cursor.getString(cursor.getColumnIndex("content")));
	    		imgs.add(cursor.getString(cursor.getColumnIndex("pic")));
	    		items.add(cursor.getString(cursor.getColumnIndex("title")));
	    		times.add(cursor.getString(cursor.getColumnIndex("time")));
	    		cursor.moveToNext();//将游标指向下一个
	    	}
	   // 	cursor.close();
	    	dm.close();//关闭数据操作对象
	    	adapter = new ListViewAdapter(this,contents,imgs,items,times);//创建数据源
	    }
		
		@Override
		protected void onDestroy() {//销毁Activity之前，所做的事
			// TODO Auto-generated method stub
			cursor.close();//关闭游标
			super.onDestroy();
		}
		
		
		public class AddRecordListener implements OnClickListener{
			public void onClick(View v) {
				// TODO Auto-generated method stub
				intent.putExtra("state", EDIT_STATE);
				intent.setClass(MainActivity.this, NoteActivity.class);
				MainActivity.this.startActivity(intent);
			}
			
		}
		public class ModifyRecordListener implements OnClickListener{

			public void onClick(View v) {
				intent.putExtra("state", ALERT_STATE);
				intent.setClass(MainActivity.this, NoteActivity.class);
				MainActivity.this.startActivity(intent);
				// TODO Auto-generated method stub
				
			}
			
		}
		
		public class DeleteRecordListener implements OnClickListener{

			public void onClick(View v) {
				// TODO Auto-generated method stub
				try{
					if(pos!=-1){
					dm.open();
					cursor.moveToPosition(pos);
					int i = dm.delete(cursor.getInt(cursor.getColumnIndex("_id")));//删除数据
					dm.close();
					adapter.removeListItem(pos);//删除数据
					adapter.notifyDataSetChanged();//通知数据源，数据已经改变，刷新界面
					
				//	Log.v("show", "chenggong1" + i);
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}
				
			}
			
		}
		

}
