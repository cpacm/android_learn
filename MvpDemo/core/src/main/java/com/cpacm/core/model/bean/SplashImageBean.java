package com.cpacm.core.model.bean;

/**
 * @author: cpacm
 * @date: 2017/2/16
 * @desciption: 启动页图片
 */

public class SplashImageBean {

    /**
     * imageUrl : http://xx.xx.com/14818e0cbba74d09ab33e7f79bd346aa_720x1280.jpg
     * timeStamp : 1462465683103
     * status : 1
     */

    private String imageUrl;
    private long timeStamp;
    private int status;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
