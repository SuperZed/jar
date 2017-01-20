package com.example.demo;

import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.plugin.library.swipeback.SlideBackHelper;
import com.plugin.library.swipeback.SlideConfig;
import com.plugin.library.swipeback.callbak.OnSwipeListener;
import com.plugin.library.swipeback.widget.SlideBackLayout;

/**
 * Created by xiezh on 2017/1/20.
 */

public class BaseActivity extends AppCompatActivity {

    protected SlideBackLayout mSlideBackLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        mSlideBackLayout = SlideBackHelper.attach(this,
                // Activity栈管理工具
                App.getInstance().getActivityHelper(),
                // 参数的配置
                new SlideConfig.Builder()
                        // 屏幕是否旋转
                        .rotateScreen(false)
                        // 是否侧滑
                        .edgeOnly(true)
                        // 是否禁止侧滑
                        .lock(false)
                        // 侧滑的响应阈值，0~1，对应屏幕宽度*percent
                        .edgePercent(0.1f)
                        // 关闭页面的阈值，0~1，对应屏幕宽度*percent
                        .slideOutPercent(0.3f).create(),
                // 滑动的监听
                onSlideListener);
    }

    protected OnSwipeListener onSlideListener=new OnSwipeListener() {
        @Override
        public void onSlide(@FloatRange(from = 0.0, to = 1.0) float percent) {
        }

        @Override
        public void onOpen() {

        }

        @Override
        public void onClose() {
            onSwipeFinish();
        }
    };

    /**
     * 设置是否边缘滑动
     *
     * @param isEdgeOnly true:边缘滑动,flase:全屏滑动
     */
    public void edgeOnly(boolean isEdgeOnly) {
        mSlideBackLayout.edgeOnly(isEdgeOnly);
    }

    /**
     * 设置边缘响应的百分比
     *
     * @param percent 百分比
     */
    public void edgePercent(float percent) {
        mSlideBackLayout.setEdgeRangePercent(percent);
    }

    /**
     * 设置关闭页面的百分比
     *
     * @param percent 百分比
     */
    public void slideOutPercent(float percent) {
        mSlideBackLayout.setSlideOutRangePercent(percent);
    }

    /**
     * 滑动finish
     */
    protected void onSwipeFinish(){

    }
}
