package com.plugin.library.permissions;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.util.Log;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 权限管理
 */
class PermissionsChecker {
    private static final String TAG = "PermissionsChecker";
    private static final int REQUEST_CODE_PERMISSION = 0x38;
    private static final int REQUEST_CODE_SETTING = 0x39;
    private Context mContext;
    private Activity mActivity;
    private PermissionsSettings mPermissionsSettings;
    private OnPermissionsListener mCallback;
    private final List<String> mDeniedPermissions = new LinkedList<>();
    private final Set<String> mManifestPermissions = new HashSet<>(1);

    PermissionsChecker(Context context) {
        mContext = context;
        getManifestPermissions();
    }

    /**
     * 获取所需的权限
     */
    private synchronized void getManifestPermissions() {
        PackageInfo packageInfo = null;
        try {
            packageInfo = mContext.getPackageManager().getPackageInfo(
                    mContext.getPackageName(), PackageManager.GET_PERMISSIONS);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo != null) {
            String[] permissions = packageInfo.requestedPermissions;
            if (permissions != null) {
                for (String perm : permissions) {
                    mManifestPermissions.add(perm);
                }
            }
        }
    }

    /**
     * 开始请求
     *
     * @param options
     * @param acpListener
     */
    synchronized void request(PermissionsSettings options, OnPermissionsListener acpListener) {
        mCallback = acpListener;
        mPermissionsSettings = options;
        checkSelfPermission();
    }

    /**
     * 检查权限
     */
    private synchronized void checkSelfPermission() {
        mDeniedPermissions.clear();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Log.i(TAG, "版本在6.0以下");
            if (mCallback != null) {
                mCallback.onGranted();
            }
            onDestroy();
            return;
        }
        String[] permissions = mPermissionsSettings.getPermissions();
        for (String permission : permissions) {
            //检查申请的权限是否在 AndroidManifest.xml 中
            if (mManifestPermissions.contains(permission)) {
                int checkSelfPermission = checkSelfPermission(mContext, permission);
                Log.i(TAG, "checkSelfPermission = " + checkSelfPermission);
                //如果权限是拒绝状态，则装入拒绝集合中
                if (checkSelfPermission == PackageManager.PERMISSION_DENIED) {
                    mDeniedPermissions.add(permission);
                }
            }
        }
        //如果权限全部获得，回调onGranted
        if (mDeniedPermissions.isEmpty()) {
            Log.i(TAG, "mDeniedPermissions.isEmpty()");
            if (mCallback != null) {
                mCallback.onGranted();
            }
            onDestroy();
            return;
        }
        startAcpActivity();
    }

    /**
     * 检查权限授权状态
     *
     * @param context
     * @param permission
     * @return
     */

    private int checkSelfPermission(Context context, String permission) {
        try {
            final PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            int targetSdkVersion = info.applicationInfo.targetSdkVersion;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (targetSdkVersion >= Build.VERSION_CODES.M) {
                    Log.i(TAG, "targetSdkVersion >= Build.VERSION_CODES.M");
                    return ContextCompat.checkSelfPermission(context, permission);
                } else {
                    return PermissionChecker.checkSelfPermission(context, permission);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return ContextCompat.checkSelfPermission(context, permission);
    }

    /**
     * 启动处理权限过程的 Activity
     */
    private synchronized void startAcpActivity() {
        Log.i("xie", "startAcpActivity");
        Intent intent = new Intent(mContext, ShowActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    /**
     * 检查权限是否存在拒绝不再提示
     *
     * @param activity
     */
    synchronized void checkRequestPermissionRationale(Activity activity) {
        mActivity = activity;
        boolean shouldShowRational = false;
        //如果拒绝集合中不为空，则提示申请理由提示框，否则直接向系统请求权限
        for (String permission : mDeniedPermissions) {
            shouldShowRational = shouldShowRational || ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permission);

        }
        Log.i(TAG, "shouldShowRational = " + shouldShowRational);
        String[] permissions = mDeniedPermissions.toArray(new String[mDeniedPermissions.size()]);
        if (shouldShowRational) {
            showRationalDialog(permissions);
        } else {
            requestPermissions(permissions);
        }
    }

    /**
     * 申请理由对话框
     *
     * @param permissions
     */
    private synchronized void showRationalDialog(final String[] permissions) {
        new AlertDialog.Builder(mActivity)
                .setTitle(mPermissionsSettings.getDialogTitle())
                .setMessage(mPermissionsSettings.getRationalMessage())
                .setPositiveButton(mPermissionsSettings.getRationalBtnText(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermissions(permissions);
                    }
                }).show();
    }

    /**
     * 向系统请求权限
     *
     * @param permissions
     */
    private synchronized void requestPermissions(String[] permissions) {
        ActivityCompat.requestPermissions(mActivity, permissions, REQUEST_CODE_PERMISSION);
    }

    /**
     * 响应向系统请求权限结果
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 同意的权限
     */
    synchronized void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION:
                LinkedList<String> grantedPermissions = new LinkedList<>();
                LinkedList<String> deniedPermissions = new LinkedList<>();
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        grantedPermissions.add(permission);
                    } else {
                        deniedPermissions.add(permission);
                    }

                }
                //全部允许才回调 onGranted 否则只要有一个拒绝都回调 onDenied
                if (!grantedPermissions.isEmpty() && deniedPermissions.isEmpty()) {
                    if (mCallback != null) {
                        mCallback.onGranted();
                    }
                    onDestroy();
                } else if (!deniedPermissions.isEmpty()) {
                    showDeniedDialog(deniedPermissions);
                }
                break;
        }
    }

    /**
     * 拒绝权限提示框
     *
     * @param permissions 权限集合
     */
    private synchronized void showDeniedDialog(final List<String> permissions) {
        new AlertDialog.Builder(mActivity)
                .setTitle(mPermissionsSettings.getDialogTitle())
                .setMessage(mPermissionsSettings.getDeniedMessage())
                .setCancelable(false)
                .setNegativeButton(mPermissionsSettings.getDeniedCloseBtn(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (mCallback != null) {
                            mCallback.onDenied(permissions);
                        }
                        onDestroy();
                    }
                })
                .setPositiveButton(mPermissionsSettings.getDeniedSettingBtn(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startSetting();
                    }
                }).show();
    }

    /**
     * 摧毁本库的 ShowActivity
     */
    private void onDestroy() {
        if (mActivity != null) mActivity.finish();
    }

    /**
     * 跳转到设置界面
     */
    private void startSetting() {
        if (CompatOs.isMIUI()) {
            Intent intent = CompatOs.getSettingIntent(mActivity);
            if (CompatOs.isIntentAvailable(mActivity, intent)) {
                mActivity.startActivityForResult(intent, REQUEST_CODE_SETTING);
            }
        } else {
            try {
                Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        .setData(Uri.parse("package:" + mActivity.getPackageName()));
                mActivity.startActivityForResult(intent, REQUEST_CODE_SETTING);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
                try {
                    Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                    mActivity.startActivityForResult(intent, REQUEST_CODE_SETTING);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 响应设置权限返回结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    synchronized void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mCallback == null || mPermissionsSettings == null
                || requestCode != REQUEST_CODE_SETTING) {
            onDestroy();
            return;
        }
        checkSelfPermission();
    }
}
