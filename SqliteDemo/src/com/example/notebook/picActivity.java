package com.example.notebook;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class picActivity extends Activity {
	private String filePath;
	private int id = -1;
	private String titleText = "";
	private String contentText = "";
	private String timeText = "";
	private String picText = "";
	private Button b;	//图片搜索按钮
	private Button com;
	private int state = -1;
	private ListView lv;//图片名称存放ListView
	private ImageView iv;//ImageView对象
	ArrayList<File> al=new ArrayList<File>(); //文件存放列表
	
    @Override  
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pic_list);
        
        
        com=(Button)this.findViewById(R.id.complete);
        b=(Button)this.findViewById(R.id.btpss);//图片搜索按钮
        lv=(ListView)this.findViewById(R.id.lvtplb);//图片名称存放ListView 
        iv=(ImageView)this.findViewById(R.id.iv);//ImageView对象
        com.setOnClickListener(new CompleteListener());
		File[] files=new File("/sdcard/DCIM").listFiles(); 
		for(File f:files)
		{
			String fileName=f.getName();
			String[] fs=fileName.split("\\.");
			if(fs.length==2)
			{
				String hz=fs[1];
				if(hz.equals("jpg")||hz.equals("png"))
				{  
					al.add(f);
				}
			}			
		}
        
        b.setOnClickListener//图片搜索按钮监听器
        (
        	new OnClickListener()
        	{
				@Override
				public void onClick(View v) {						 
		       		BaseAdapter ba=new BaseAdapter()//创建适配器
	            	{
	        			@Override
	        			public int getCount() {
	        				return al.size();
	        			}

	        			@Override
	        			public Object getItem(int position) {
	        				return null;
	        			}

	        			@Override
	        			public long getItemId(int position) {				
	        				return 0;
	        			}

	        			@Override
	        			public View getView(int arg0, View arg1, ViewGroup arg2) {
	        				LinearLayout ll=new LinearLayout(picActivity.this);
	        				ll.setOrientation(LinearLayout.VERTICAL);//竖直排列
	        				ll.setPadding(5, 5, 5, 5);//留白				
	        				TextView tv=new TextView(picActivity.this);//初始化TextView
	        				tv.setTextColor(Color.BLACK);//设置字体颜色							
	        				tv.setText(al.get(arg0).getName());//添加文件名称
	        			    tv.setGravity(Gravity.LEFT);//左对齐
	        			    tv.setTextSize(18);//字体大小  			
	        			    ll.addView(tv);//LinearLayout添加TextView
	        				return ll;
	        			}	            		
	            	};
	            	lv.setAdapter(ba);//设置适配器
	            	
	            	lv.setOnItemClickListener//设置选中菜单的监听器
	            	(
	          			new OnItemClickListener()
	          			{
	        					@Override
	        					public void onItemClick(AdapterView<?> arg0, View arg1,
	        							int arg2, long arg3) {
	        						File curfile=al.get(arg2);//获取点击图片文件
	        						filePath=curfile.getPath();
	        						Bitmap bm=BitmapFactory.decodeFile(filePath);
	        						iv.setImageBitmap(bm);
	        					}
	          			}
	            	);
										
				}        		
        	}
        );
    }
    
    public class CompleteListener implements OnClickListener{

    	public void onClick(View v) {
    		Intent intent = getIntent();
    		id = intent.getIntExtra("id",0);
    		titleText = intent.getStringExtra("title");
    		contentText = intent.getStringExtra("content");
    		picText = intent.getStringExtra("picture");
    		state = intent.getIntExtra("state", 1);//参数名，默认值
    		picText=filePath;
    		Intent intents = new Intent();
    		intents.putExtra("id",id);
			intents.putExtra("title", titleText);
			intents.putExtra("content", contentText);
			intents.putExtra("picture", picText);
			intents.putExtra("state", state);
			intents.putExtra("flag", 1);
    		intents.setClass(picActivity.this, NoteActivity.class);
    		picActivity.this.startActivity(intents);
    		//保存完毕
    	}
    	
    }


}
