package com.cpacm.mvpdemo.ui.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cpacm.core.model.bean.SplashImageBean;
import com.cpacm.mvpdemo.R;
import com.cpacm.mvpdemo.ui.BaseActivity;

/**
 * @author: cpacm
 * @date: 2017/2/16
 * @desciption: 启动界面
 */

public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View {

    private ImageView bgView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        bgView = (ImageView) findViewById(R.id.background);
        presenter.getSplashData();
    }

    @Override
    public void setPresenter() {
        presenter = new SplashPresenter();
    }

    @Override
    public void showSplash(SplashImageBean bean) {
        Glide.with(this)
                .load(bean.getImageUrl())
                .into(bgView);
        Toast.makeText(this, bean.toString(), Toast.LENGTH_SHORT).show();
    }

}
