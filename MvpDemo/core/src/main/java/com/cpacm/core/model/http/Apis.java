package com.cpacm.core.model.http;

import com.cpacm.core.model.ApiResponse;
import com.cpacm.core.model.bean.SplashImageBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author: cpacm
 * @date: 2017/2/15
 * @desciption: api接口
 */

public interface Apis {

    /**
     * 启动页图片
     *
     * @return
     */
    @FormUrlEncoded
    @POST("getSplashImg")
    Observable<ApiResponse<SplashImageBean>> getStartImg(@Field("uId") String uid,
                                                         @Field("size") String size);



}
