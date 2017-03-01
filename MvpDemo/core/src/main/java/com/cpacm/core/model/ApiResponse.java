package com.cpacm.core.model;

/**
 * @author: cpacm
 * @date: 2017/2/16
 * @desciption:
 */

public class ApiResponse<T> {
    private int resultCode;
    private T returnObject;
    private Object ruturnInfo;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public T getReturnObject() {
        return returnObject;
    }

    public void setReturnObject(T returnObject) {
        this.returnObject = returnObject;
    }

    public Object getRuturnInfo() {
        return ruturnInfo;
    }

    public void setRuturnInfo(Object ruturnInfo) {
        this.ruturnInfo = ruturnInfo;
    }
}
