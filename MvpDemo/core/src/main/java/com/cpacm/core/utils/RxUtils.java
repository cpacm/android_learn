package com.cpacm.core.utils;

import com.cpacm.core.model.ApiResponse;
import com.cpacm.core.model.http.ApiException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;

/**
 * @author: cpacm
 * @date: 2017/2/17
 * @desciption:
 */

public class RxUtils {

    /**
     * 子线程运行，主线程回调
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> io_main() {
        return new ObservableTransformer<T, T>() {

            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 返回数据统一处理
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<ApiResponse<T>, T> handleResult() {   //compose判断结果
        return new ObservableTransformer<ApiResponse<T>, T>() {
            @Override
            public Observable<T> apply(Observable<ApiResponse<T>> httpResponseObservable) {
                return httpResponseObservable.flatMap(new Function<ApiResponse<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(ApiResponse<T> tApiResponse) throws Exception {
                        if (tApiResponse.getResultCode() != 1000) {
                            return Observable.error(new ApiException("服务器返回error"));
                        } else {
                            return createData(tApiResponse.getReturnObject());
                        }
                    }
                });
            }
        };
    }

    /**
     * 生成Observable
     *
     * @param <T>
     * @return
     */
    public static <T> Observable<T> createData(final T t) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> e) throws Exception {
                e.onNext(t);
                e.onComplete();
            }
        });
    }

}
