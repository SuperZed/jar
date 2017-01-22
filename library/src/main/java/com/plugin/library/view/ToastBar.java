package com.plugin.library.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by xie on 2016/8/25.
 */
public class ToastBar {

    private final static int CLOSE = 0;
    private final static int ANIM_DURATION = 800;
    private final static int SHOW_DURATION = 2500;
    private final static int BACKGROUND_COLOR = Color.parseColor("#E6434344");
    private final static int TEXT_SIZE = 15;

    private final Context mContext;
    private RelativeLayout mToastView;
    private WindowManager mWindowManager;
    private LinearLayout mParentLayout;
    private TextView mToastText;
    private View view;

    private ToastBar(Context context) {
        //使用applicationContext保证Activity跳转时Toast不会消失
        this.mContext = context.getApplicationContext();
    }

    public static ToastBar with(Context context) {
        return new ToastBar(context);
    }

    public void show(String toast) {
        showHeaderToast(toast, SHOW_DURATION);
    }

    public void showInButtom(View view, String toast) {
        this.view = view;
        showHeaderToast(toast, SHOW_DURATION);
    }

    public void show(String toast, int duration) {
        showHeaderToast(toast, duration);
    }

    public void showInButtom(View view, String toast, int duration) {
        this.view = view;
        showHeaderToast(toast, duration);
    }

    private synchronized void showHeaderToast(String toast, int duration) {
        initView();
        setText(toast);
        setHeaderViewInAnim();
        //自动关闭
        mHeaderToastHandler.sendEmptyMessageDelayed(CLOSE, duration);
    }

    /**
     * 添加进入动画
     */
    private void setHeaderViewInAnim() {
//      ObjectAnimator.ofFloat(mToastView, "rotationX", -90, 0).setDuration(ANIM_DURATION).start();
//      ObjectAnimator.ofFloat(mToastView, "alpha", 0, 1).setDuration(ANIM_DURATION * 3 / 2).start();
        ObjectAnimator.ofFloat(mToastView, "translationY", -700, 0).setDuration(ANIM_DURATION).start();
    }

    private void setText(String toast) {
        mToastText.setText(toast);
    }

    private void initView() {
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        //为mHeaderToastView添加parent使其能够展示动画效果
        mParentLayout = new LinearLayout(mContext);

        mToastView = new RelativeLayout(mContext);
        mToastView.setBackgroundColor(BACKGROUND_COLOR);

        mToastText = new TextView(mContext);
        mToastText.setGravity(Gravity.CENTER);
        mToastText.setTextColor(Color.WHITE);
        mToastText.setTextSize(TEXT_SIZE);
        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        layoutParams.setMargins(0, dp2px(6), 0, dp2px(6));
        mToastText.setLayoutParams(layoutParams);

        mToastView.addView(mToastText);

        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        wmParams.gravity = Gravity.CENTER | Gravity.TOP;
        wmParams.x = 0;
        wmParams.y = view == null ? 0 : view.getBottom();
        wmParams.format = PixelFormat.TRANSLUCENT;
        wmParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        mParentLayout.addView(mToastView);
        mWindowManager.addView(mParentLayout, wmParams);
    }

    private Handler mHeaderToastHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CLOSE:
                    animDismiss();
                    break;
                default:
                    Log.e("ToastBar", "no selection matches");
                    break;
            }
        }
    };

    /**
     * 消失动画
     */
    private void animDismiss() {
        if (null == mParentLayout || null == mParentLayout.getParent()) {
            //如果linearLayout已经被从wm中移除，直接return
            return;
        }
//      ObjectAnimator.ofFloat(mToastView, "rotationX", 0, -90).setDuration(ANIM_DURATION).start();
//      ObjectAnimator.ofFloat(mToastView, "alpha", 1, 0).setDuration(ANIM_DURATION * 3 / 2).start();
        ObjectAnimator anim = ObjectAnimator.ofFloat(mToastView, "translationY", 0, -700);
        anim.setDuration(ANIM_DURATION);
        anim.start();
        anim.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                dismiss();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     * 移除
     */
    private void dismiss() {
        if (null != mParentLayout && null != mParentLayout.getParent()) {
            mWindowManager.removeView(mParentLayout);
        }
    }

    private int dp2px(float dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

}
