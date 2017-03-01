package com.cpacm.mvpdemo.ui.splash;

import android.util.Log;


import com.cpacm.core.model.ApiResponse;
import com.cpacm.core.model.bean.SplashImageBean;
import com.cpacm.core.model.http.Apis;
import com.cpacm.core.model.http.RetrofitHelper;
import com.cpacm.core.mvp.RxPresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: cpacm
 * @date: 2017/2/17
 * @desciption: 启动页
 */

public class SplashPresenter extends RxPresenter<SplashContract.View> implements SplashContract.Presenter {

    private Apis apis;

    public SplashPresenter() {
        apis = RetrofitHelper.getInstance().getApis();
    }

    @Override
    public void getSplashData() {
        Disposable disposable = apis.getStartImg("","")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<ApiResponse<SplashImageBean>>() {
                    @Override
                    public void accept(ApiResponse<SplashImageBean> splashImageBean) throws Exception {
                        view.showSplash(splashImageBean.getReturnObject());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("cpacm", throwable.toString());
                    }
                });
        addDisposable(disposable);
    }
}
