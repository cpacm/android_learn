package com.cpacm.demo.testrep;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.cpacm.demo.testrep.R;

public class FrameAnimationActivity extends Activity implements View.OnClickListener{

    private ImageView iv;
    private Button b1,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_animation);
        iv = (ImageView)findViewById(R.id.imageView);
        b1 = (Button)findViewById(R.id.start);
        b1.setOnClickListener(this);
        b2 = (Button)findViewById(R.id.end);
        b2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.start:
                iv.setBackgroundResource(R.drawable.frame_animation);
                AnimationDrawable anim = (AnimationDrawable) iv.getBackground();
                anim.start();
                //runFrame();
                break;
            case R.id.end:
                AnimationDrawable anim2 = (AnimationDrawable) iv.getBackground();
                if (anim2.isRunning()) { //如果正在运行,就停止
                    anim2.stop();
                }
                break;
        }
    }

    public void runFrame() {
        //完全编码实现的动画效果
        AnimationDrawable anim = new AnimationDrawable();
        for (int i = 1; i <= 4; i++) {
            //根据资源名称和目录获取R.java中对应的资源ID
            int id = getResources().getIdentifier("p" + i, "drawable", getPackageName());
            //根据资源ID获取到Drawable对象
            Drawable drawable = getResources().getDrawable(id);
            //将此帧添加到AnimationDrawable中
            anim.addFrame(drawable, 500);
        }
        anim.setOneShot(false); //设置为loop
        //api15以前
        iv.setBackgroundDrawable(anim);  //将动画设置为ImageView背景
        //api16以后
        //iv.setBackground(anim);
        anim.start();   //开始动画
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.frame_animation, menu);
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
