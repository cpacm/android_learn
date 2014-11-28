package com.cpacm.demo.testrep;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;


public class TweenAnimationActivity extends Activity implements View.OnClickListener {

    private ImageView av;
    private Button alpha,rotate,translate,scale;
    private AnimationSet mySet;
    private Button allAnimation,back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        mySet = new AnimationSet(true);
        av = (ImageView)findViewById(R.id.imageView2);
        alpha = (Button)findViewById(R.id.alpha);
        alpha.setOnClickListener(this);
        rotate = (Button)findViewById(R.id.rotate);
        rotate.setOnClickListener(this);
        translate = (Button)findViewById(R.id.translate);
        translate.setOnClickListener(this);
        scale = (Button)findViewById(R.id.scale);
        scale.setOnClickListener(this);
        allAnimation = (Button)findViewById(R.id.all);
        allAnimation.setOnClickListener(this);
        back = (Button)findViewById(R.id.back);
        back.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
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

    @Override
    public void onClick(View v) {
        Animation myAnimation = null;
        switch(v.getId()){
            case R.id.alpha:
                myAnimation = AlphaAnimation();
                av.startAnimation(myAnimation);//av是一个imageview
                break;
            case R.id.rotate:
                myAnimation = RotateAnimation();
                av.startAnimation(myAnimation);
                break;
            case R.id.translate:
                myAnimation = TranslateAnimation();
                av.startAnimation(myAnimation);
                break;
            case R.id.scale:
                myAnimation = ScaleAnimation();
                av.startAnimation(myAnimation);
                break;
            case R.id.all:
                AllAnimation();
                break;
            case R.id.back:
                this.finish();
                break;
        }
    }

    public Animation AlphaAnimation(){
        Animation myAnimation_Alpha;
        //透明度从0到1
        myAnimation_Alpha = new AlphaAnimation(0,1);
        //设置时间持续时间为 2000毫秒
        myAnimation_Alpha.setDuration(2000);
        return myAnimation_Alpha;
    }

    public Animation RotateAnimation(){
        Animation myAnimation_Rotate;
        //第一个参数fromDegrees为动画起始时的旋转角度
        //第二个参数toDegrees为动画旋转到的角度
        //第三个参数pivotXType为动画在X轴相对于物件位置类型
        //第四个参数pivotXValue为动画相对于物件的X坐标的开始位置
        //第五个参数pivotXType为动画在Y轴相对于物件位置类型
        //第六个参数pivotYValue为动画相对于物件的Y坐标的开始位置
        myAnimation_Rotate = new RotateAnimation(0.0f, +350.0f,
                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
        //动画插入器，可以自己定义动画的速度
        //加速-减速 动画插入器
        myAnimation_Rotate.setInterpolator(this,android.R.anim.accelerate_decelerate_interpolator);
        myAnimation_Rotate.setDuration(2000);
        return myAnimation_Rotate;
    }

    public Animation TranslateAnimation(){
        //TranslateAnimation(float fromXDelta, float toXDelta,
        //float fromYDelta, float toYDelta)
        //第一个参数fromXDelta为动画起始时 X坐标上的移动位置
        //第二个参数toXDelta为动画结束时 X坐标上的移动位置
        //第三个参数fromYDelta为动画起始时Y坐标上的移动位置
        //第四个参数toYDelta为动画结束时Y坐标上的移动位置
        Animation myAnimation_Translate;
        myAnimation_Translate=new TranslateAnimation(0.0f, -80.0f, 0.0f, 300.0f);
        myAnimation_Translate.setDuration(2000);
        myAnimation_Translate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                av.setTranslationX(av.getTranslationX()-80f);
                av.setTranslationY(av.getTranslationY()+300f);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return myAnimation_Translate;
    }

    public Animation ScaleAnimation(){
        Animation myAnimation_Scale;
        //第一个参数fromX为动画起始时 X坐标上的伸缩尺寸
        //第二个参数toX为动画结束时 X坐标上的伸缩尺寸
        //第三个参数fromY为动画起始时Y坐标上的伸缩尺寸
        //第四个参数toY为动画结束时Y坐标上的伸缩尺寸
        /*说明:
                    以上四种属性值
                    0.0表示收缩到没有
                    1.0表示正常无伸缩
                    值小于1.0表示收缩
                    值大于1.0表示放大
        */
        //第五个参数pivotXType为动画在X轴相对于物件位置类型
        //第六个参数pivotXValue为动画相对于物件的X坐标的开始位置
        //第七个参数pivotXType为动画在Y轴相对于物件位置类型
        //第八个参数pivotYValue为动画相对于物件的Y坐标的开始位置
        /*myAnimation_Scale = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
        myAnimation_Scale.setDuration(2000);*/

        //使用xml读取动画
        myAnimation_Scale = AnimationUtils.loadAnimation(this,R.anim.scale);
        return myAnimation_Scale;
    }

    public void AllAnimation(){
        mySet.addAnimation(AlphaAnimation());
        mySet.addAnimation(RotateAnimation());
        mySet.addAnimation(TranslateAnimation());
        mySet.addAnimation(ScaleAnimation());
        mySet.setDuration(3000);
        av.startAnimation(mySet);
    }

}
