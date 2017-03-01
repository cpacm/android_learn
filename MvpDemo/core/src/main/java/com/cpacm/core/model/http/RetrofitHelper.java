package com.cpacm.core.model.http;

import com.cpacm.core.utils.FileUtils;
import com.cpacm.core.utils.SystemParamsUtils;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author: cpacm
 * @date: 2017/2/15
 * @desciption: retrofit 初始化
 */

public class RetrofitHelper {


    private static RetrofitHelper ourInstance;
    private Apis apis;
    private OkHttpClient okHttpClient = null;


    public static RetrofitHelper getInstance() {
        if (ourInstance == null)
            ourInstance = new RetrofitHelper();
        return ourInstance;
    }

    private RetrofitHelper() {
        init();
    }

    public void init() {
        initOkHttp();
        apis = getApiService(HttpUtils.BASEURL, Apis.class);
    }

    private void initOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        File cacheFile = new File(FileUtils.getNetCacheDir());
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!SystemParamsUtils.isNetworkConnected()) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (SystemParamsUtils.isNetworkConnected()) {
                    int maxAge = 0;
                    // 有网络时, 不缓存, 最大保存时长为0
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };
        // 一些静态的header
        Interceptor apikey = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = addHeaders(chain.request());
                return chain.proceed(request);
            }
        };
        //设置统一的请求头部参数
        builder.addInterceptor(apikey);
        //设置缓存
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        okHttpClient = builder.build();
    }

    /**
     * 添加头部
     *
     * @param request
     * @return
     */
    private Request addHeaders(Request request) {
        Request.Builder builder = request.newBuilder();

        builder.addHeader("Connection", "Keep-Alive")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Accept-Language", "zh-CN,en-US");
        return builder.build();
    }

    private <T> T getApiService(String baseUrl, Class<T> clz) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(clz);
    }

    public Apis getApis() {
        return apis;
    }
}
