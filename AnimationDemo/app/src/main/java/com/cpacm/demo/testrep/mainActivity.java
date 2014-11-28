package com.cpacm.demo.testrep;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class mainActivity extends Activity{

    private ListView lv;
    private String[] array = new String[]{"TweenAnimation","FrameAnimation","ObjectAnimator"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView)findViewById(R.id.listView);
        lv.setAdapter(new ArrayAdapter<String>(this,R.layout.item,array));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        Intent intent = new Intent();
                        intent.setClass(mainActivity.this,TweenAnimationActivity.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);//系统自带的切换动画
                        break;
                    case 1:
                        Intent intent2 = new Intent();
                        intent2.setClass(mainActivity.this,FrameAnimationActivity.class);
                        startActivity(intent2);
                        //如果这个地方不想用自己的，可以直接调安卓提供的动画，如下：
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                        break;
                    case 2:
                        Intent intent3 = new Intent();
                        intent3.setClass(mainActivity.this,ObjectAnimatorActivity.class);
                        startActivity(intent3);
                        break;
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
