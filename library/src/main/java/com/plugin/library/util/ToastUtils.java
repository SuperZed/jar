package com.plugin.library.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast工具类
 */
public class ToastUtils {

    private static Toast mToast;

    public static void showToast(Context context, String text) {
        if (null != mToast) {
            mToast.cancel();
            mToast = null;
        }
        mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
//        mToast.setView(createView(context,text));
        mToast.setText(text);
        mToast.show();
    }

//    private static View createView(Context context,String str){
//        View view = LayoutInflater.from(context).inflate(R.layout.layout_toast_view, null);
//        ((TextView) view.findViewById(R.id.text)).setText(str);
//        return view;
//    }

    public static void showToast(Context context, int textId) {
        if (null != mToast) {
            mToast.cancel();
            mToast = null;
        }
        mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        mToast.setText(textId);
        mToast.show();
    }

    public static void showToastLong(Context context, String text) {
        if (null != mToast) {
            mToast.cancel();
            mToast = null;
        }
        mToast = Toast.makeText(context, "", Toast.LENGTH_LONG);
        mToast.setText(text);
        mToast.show();
    }

    public static void showToastLong(Context context, int textId) {
        if (null != mToast) {
            mToast.cancel();
            mToast = null;
        }
        mToast = Toast.makeText(context, "", Toast.LENGTH_LONG);
        mToast.setText(textId);
        mToast.show();
    }

}
