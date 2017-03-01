package com.cpacm.core.model.http;

/**
 * @author: cpacm
 * @date: 2017/2/17
 * @desciption: 网络连接错误
 */

public class ApiException extends Exception {
    public ApiException(String msg) {
        super(msg);
    }
}
