package com.example.demo;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.plugin.library.swipeback.ActivityHelper;

/**
 * Created by xiezh on 2017/1/20.
 */

public class App extends Application {

    private static App instance;
    public static Handler handler = new Handler(Looper.getMainLooper());
    private static Context mContext;
    private ActivityHelper helper;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = getApplicationContext();
        registerActivityLifecycleCallbacks(helper = new ActivityHelper());
    }
    /**
     * application对象
     */
    public static App getInstance() {
        return instance;
    }

    public static Context getContext() {
        return mContext;
    }

    public ActivityHelper getActivityHelper() {
        return helper;
    }
}
