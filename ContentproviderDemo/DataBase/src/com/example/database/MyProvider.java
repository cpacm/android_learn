package com.example.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MyProvider extends ContentProvider {

	private DBHelper dh = null;// 数据库管理对象
	private SQLiteDatabase db;//获取其中的数据库
	//UriMatcher:Creates the root node of the URI tree.
	//按照官方解释，UriMatcher是一颗Uri的树，然后利用addURI()方法往里面添加枝干，通过match()函数来查找枝干。
	private static final UriMatcher MATCHER = new UriMatcher(
			UriMatcher.NO_MATCH);
	//设定匹配码
	private static final int NOTEBOOK = 1;
	private static final int NOTEBOOKS = 2;
	static {
		//添加枝干，并给它们加上唯一的匹配码，以方便查找
		//如果match()方法匹配content://com.example.database/notebook路径，返回匹配码为1
		MATCHER.addURI("com.example.database", "notebook", NOTEBOOKS);
		//如果match()方法匹配content://com.example.database/notebook/#路径，返回匹配码为2
		//其中#号为通配符。
		MATCHER.addURI("com.example.database", "notebook/#", NOTEBOOK);
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		dh = new DBHelper(this.getContext(),"note.db");// 数据库操作对象
		db = dh.getReadableDatabase();
		return false;
	}

	/** 
	 * 查询，返回Cursor
	 **/
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		//通过match函数，获取匹配码
		switch (MATCHER.match(uri)) {
		case NOTEBOOKS:
			//返回数据库操作的结果
			return db.query("notebook", projection, selection, selectionArgs,
					null, null, sortOrder);
		case NOTEBOOK:
			//因为添加 了id,所以要把id加到where条件中
			long id = ContentUris.parseId(uri);
			String where = "_id=" + id;
			if (selection != null && !"".equals(selection)) {
				where = selection + " and " + where;
			}
			return db.query("notebook", projection, where, selectionArgs, null,
					null, sortOrder);
		default:
			throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
		}
	}

	//获取Uri的类型
	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		switch (MATCHER.match(uri)) {  
        case NOTEBOOKS:  
            return "com.example.Database.all/notebook";  
  
        case NOTEBOOK:  
            return "com.example.Database.item/notebook";  
  
        default:  
            throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());  
        }
	}

	//插入数据
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		switch (MATCHER.match(uri)) {  
        case NOTEBOOKS:  
        	//调用数据库的插入功能
            // 特别说一下第二个参数是当title字段为空时，将自动插入一个NULL。  
            long rowid = db.insert("notebook", "title", values);  
            Uri insertUri = ContentUris.withAppendedId(uri, rowid);// 得到代表新增记录的Uri  
            this.getContext().getContentResolver().notifyChange(uri, null);  
            return insertUri;  
  
        default:  
            throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());  
        }
	}

	//删除数据
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		int count;
		switch (MATCHER.match(uri)) {  
        case NOTEBOOKS:  
            count = db.delete("notebook", selection, selectionArgs);  
            return count;  
  
        case NOTEBOOK:  
            long id = ContentUris.parseId(uri);  
            String where = "_id=" + id;  
            if (selection != null && !"".equals(selection)) {  
                where = selection + " and " + where;  
            }  
            count = db.delete("notebook", where, selectionArgs);  
            return count;  
  
        default:  
            throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());  
        }
	}

	//更新数据
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		int count = 0;  
        switch (MATCHER.match(uri)) {  
        case NOTEBOOKS:  
            count = db.update("notebook", values, selection, selectionArgs);  
            return count;  
        case NOTEBOOK:  
            long id = ContentUris.parseId(uri);  
            String where = "_id=" + id;  
            if (selection != null && !"".equals(selection)) {  
                where = selection + " and " + where;  
            }  
            count = db.update("notebook", values, where, selectionArgs);  
            return count;  
        default:  
            throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());  
        } 
	}

}
