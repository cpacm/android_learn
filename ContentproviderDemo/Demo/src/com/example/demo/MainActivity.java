package com.example.demo;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

public class MainActivity extends Activity implements OnClickListener {

	private ListView listView;
	private SimpleCursorAdapter adapter;
	private Button button_query, button_insert, button_delete, button_update;
	private EditText editText_title, editText_content;
	private int CurItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		editText_title = (EditText) this.findViewById(R.id.editText1);
		editText_content = (EditText) this.findViewById(R.id.editText2);
		
		listView = (ListView) this.findViewById(R.id.listView1);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ListView lView = (ListView) parent;
				Cursor data = (Cursor) lView.getItemAtPosition(position);
				int _id = data.getInt(data.getColumnIndex("_id"));
				Toast.makeText(MainActivity.this, _id + "", Toast.LENGTH_SHORT)
						.show();
				CurItem = _id;
				editText_title.setText(data.getString(data.getColumnIndex("title")));
				editText_content.setText(data.getString(data.getColumnIndex("content")));
			}
		});
		button_query = (Button) this.findViewById(R.id.button1);
		button_query.setOnClickListener(this);
		button_insert = (Button) this.findViewById(R.id.button2);
		button_insert.setOnClickListener(this);
		button_delete = (Button) this.findViewById(R.id.button3);
		button_delete.setOnClickListener(this);
		button_update = (Button) this.findViewById(R.id.button4);
		button_update.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		//ContentResolver它是ContentProvider提供的一个接口，它能够调用ContentProvider里面的所有方法。
		ContentResolver contentResolver;
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1:
			contentResolver = getContentResolver();
			//Uri.parse()能将字符串转换成Uri格式。
			Uri selectUri = Uri.parse("content://com.example.database/notebook");
			Cursor cursor = contentResolver.query(selectUri, null, null, null,
					null);
			adapter = new SimpleCursorAdapter(this, R.layout.item, cursor,
					new String[] { "_id", "title", "content", "time" },
					new int[] { R.id.id, R.id.title, R.id.content, R.id.time },
					1);
			listView.setAdapter(adapter);
			break;
		case R.id.button2:
			contentResolver = getContentResolver();
			Uri insertUri = Uri
					.parse("content://com.example.database/notebook");
			ContentValues values = new ContentValues();
			values.put("title", editText_title.getText().toString());
			values.put("content", editText_content.getText().toString());
			values.put("time", System.currentTimeMillis());
			Uri uri = contentResolver.insert(insertUri, values);
			Toast.makeText(this, uri.toString() + "添加完成", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.button3:
			contentResolver = getContentResolver();
			Uri deleteUri = Uri
					.parse("content://com.example.database/notebook/"+CurItem);
			int d = contentResolver.delete(deleteUri, null,null);
			Toast.makeText(this, CurItem+"删除完成", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.button4:
			contentResolver = getContentResolver();
			Uri updateUri = Uri
					.parse("content://com.example.database/notebook/"+CurItem);
			ContentValues updatevalues = new ContentValues();
			updatevalues.put("title", editText_title.getText().toString());
			updatevalues.put("content", editText_content.getText().toString());
			updatevalues.put("time", System.currentTimeMillis());
			int u = contentResolver.update(updateUri, updatevalues,null,null);
			Toast.makeText(this, CurItem+"更新完成", Toast.LENGTH_SHORT)
					.show();
			break;
		}
	}
}
