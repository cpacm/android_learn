package com.cpacm.mvpdemo.ui.splash;

import com.cpacm.core.model.bean.SplashImageBean;
import com.cpacm.core.mvp.BasePresenter;
import com.cpacm.core.mvp.BaseView;

/**
 * @author: cpacm
 * @date: 2017/2/17
 * @desciption:
 */

public interface SplashContract {

    interface View extends BaseView {
        void showSplash(SplashImageBean bean);
    }

    interface Presenter extends BasePresenter<View> {
        void getSplashData();
    }
}
