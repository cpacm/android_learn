package com.cpacm.core.mvp;

/**
 * @author: cpacm
 * @date: 2017/2/16
 * @desciption:
 */

public interface BasePresenter<T extends BaseView> {
    void attachView(T view);
    void detachView();
}
