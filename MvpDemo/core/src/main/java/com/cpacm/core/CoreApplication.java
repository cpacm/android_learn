package com.cpacm.core;

import android.app.Activity;
import android.app.Application;

import java.util.HashSet;


/**
 * Global application
 *
 * @Auther: cpacm
 * @Date: 2017/02/04
 */
public abstract class CoreApplication extends Application {

    /**
     * application singleton
     */
    private static CoreApplication instance;

    /**
     * 运用set来保存每一个activity
     * activity set
     */
    private HashSet<Activity> activities;

    public static CoreApplication getInstance() {
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //TODO 解除注释
        //CrashHandler crashHandler = CrashHandler.getInstance();
        //crashHandler.init(getApplicationContext());
        instance = this;
    }

    /**
     * 添加一个activity到列表中<br/>
     * add Activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activities == null) {
            activities = new HashSet<>();
        }
        activities.add(activity);
    }

    /**
     * 从列表中删除一个activity<br/>
     * remove Activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (activities != null) {
            activities.remove(activity);
        }
    }

    /**
     * 关闭list内的每一个activity<br/>
     * close all activity
     */
    public void closeAllActivity() {
        if (activities != null) {
            synchronized (activities) {
                for (Activity activity : activities) {
                    if (activity != null)
                        activity.finish();
                }
                activities.clear();
            }
        }
    }

    /**
     * 关闭list内的每一个activity并且退出应用<br/>
     * close all activity and exit app
     */
    public void exit() {
        closeAllActivity();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

}
