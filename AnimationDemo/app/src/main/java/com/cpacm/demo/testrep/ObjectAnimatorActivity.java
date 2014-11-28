package com.cpacm.demo.testrep;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;

public class ObjectAnimatorActivity extends Activity implements View.OnClickListener{

    private ImageView iv;
    private Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_animator);
        iv = (ImageView)findViewById(R.id.imageView2);
        b1 = (Button)findViewById(R.id.button);
        b1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button:
                //第一个参数是属性，即要变化的属性，下面是变化的值，可以防止多个数值
                PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X,100);
                PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 1f);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 730);
                PropertyValuesHolder pvhsX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1,2,1);
                PropertyValuesHolder pvhsY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1,2,1);
                PropertyValuesHolder pvhA = PropertyValuesHolder.ofFloat(View.ALPHA, 1);
                final ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(iv, pvhX, pvhY, pvhR, pvhsX, pvhsY, pvhA);
                animation.setDuration(2000);
                animation.setInterpolator(new OvershootInterpolator(0.9f));
                animation.start();
                break;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.object_animator, menu);
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
