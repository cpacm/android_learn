package com.example.notebook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;


public class DBHelper extends SQLiteOpenHelper{
	private static final int VERSION=1;
	/** 
     * 在SQLiteOpenHelper的子类当中，必须有该构造函数 
     * @param context   上下文对象 
     * @param name      数据库名称 
     * @param factory 
     * @param version   当前数据库的版本，值必须是整数并且是递增的状态 
     */
	public DBHelper(Context context,String name,CursorFactory factory,int version){
		super(context,name,factory,version);
	}
	public DBHelper(Context context, String name, int version){  
        this(context,name,null,version);  
    }  
  
    public DBHelper(Context context, String name){  
        this(context,name,VERSION);  
    }
	@Override
	public void onCreate(SQLiteDatabase db) {
		 // TODO Auto-generated method stub  
        System.out.println("create a database");  
        //execSQL用于执行SQL语句  
        db.execSQL("create table notebook(_id integer primary key autoincrement,pic varchar(50),title varchar(20),content text,time varchar)");
		
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		System.out.println("upgrade a database");
	}  

}

