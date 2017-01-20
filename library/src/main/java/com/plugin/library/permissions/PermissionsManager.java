package com.plugin.library.permissions;

import android.content.Context;

/**
 * 权限检查
 */
public class PermissionsManager {

    private static PermissionsManager mInstance;
    private PermissionsChecker mPermissionsManager;

    public static PermissionsManager getInstance(Context context) {
        if (mInstance == null)
            synchronized (PermissionsManager.class) {
                if (mInstance == null) {
                    mInstance = new PermissionsManager(context);
                }
            }
        return mInstance;
    }

    private PermissionsManager(Context context) {
        mPermissionsManager = new PermissionsChecker(context.getApplicationContext());
    }

    /**
     * 开始请求
     *
     * @param permissionsSettings
     * @param listener
     */
    public void request(PermissionsSettings permissionsSettings, OnPermissionsListener listener) {
        if (permissionsSettings == null) new NullPointerException("PermissionsSettings is null...");
        if (listener == null) new NullPointerException("OnPermissionsListener is null...");
        mPermissionsManager.request(permissionsSettings, listener);
    }

    PermissionsChecker getManager() {
        return mPermissionsManager;
    }
}
