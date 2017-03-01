package com.cpacm.core.mvp;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author: cpacm
 * @date: 2017/2/16
 * @desciption: 可取消订阅的 rxpresenter,防止rxjava引起的内存泄露
 */

public abstract class RxPresenter<T extends BaseView> implements BasePresenter<T> {
    protected T view;
    protected CompositeDisposable compositeDisposable;

    protected void unDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    protected void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    @Override
    public void attachView(T view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
        unDisposable();
    }
}
