package com.example.demo.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;

import com.example.demo.R;
import com.plugin.library.view.titlebar.TitleBar;


public abstract class BaseActivity extends BaseSwipeActivity {

    protected TitleBar mTitleBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 添加titlebar右边的action
     * @param action
     */
    protected void addAction(TitleBar.Action action) {
        mTitleBar.addAction(action);
    }

    /**
     * 添加titlebar右边的action集合
     * @param list
     */
    protected void addActionList(TitleBar.ActionList list) {
        mTitleBar.addActions(list);
    }

    /**
     * 设置状态栏是否透明
     * @param immersive
     */
    protected void setImmersiveStatusBar(boolean immersive) {
        mTitleBar.setImmersive(immersive);
    }

    /**
     * 设置标题颜色
     * @param color
     */
    protected void setTitleTextColor(int color) {
        mTitleBar.setTitleColor(color);
    }

    /**
     * 添加titlebar右边的action字体颜色
     * @param color
     */
    protected void setActionTextColor(int color) {
        mTitleBar.setActionTextColor(color);
    }

    /**
     * 设置布局
     *
     * @param layoutId
     */
    protected void setViewLayout(int layoutId) {
        setContentView(layoutId);
        mTitleBar = (TitleBar) findViewById(R.id.title_bar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
            mTitleBar.setImmersive(true);
        }
        mTitleBar.setLeftImageResource(R.drawable.icon_back);
        mTitleBar.setTitleColor(Color.WHITE);
        mTitleBar.setActionTextColor(Color.WHITE);
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLeftClick();
            }
        });
        initView();
    }

    /**
     * 设置title name
     */
    protected void setTitleName(String title) {
        mTitleBar.setTitle(title);
    }

    /**
     * 设置左边图标
     */
    protected void setLeftImageRes(int resId) {
        mTitleBar.setLeftImageResource(resId);
    }

    /**
     * 设置title name
     */
    protected void setTitleName(int resId) {
        mTitleBar.setTitle(resId);
    }

    /**
     * 初始化控件
     */

    protected abstract void initView();

    /**
     * 返回按钮事件
     */
    protected abstract void onLeftClick();

}
