package com.plugin.library.permissions;

import java.util.List;

/**
 * 权限回调监听
 */
public interface OnPermissionsListener {
    /**
     * 同意
     */
    void onGranted();

    /**
     * 拒绝
     */
    void onDenied(List<String> permissions);
}
