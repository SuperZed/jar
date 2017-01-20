package com.plugin.library.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Zed on 2016/10/8.
 */

public abstract class BaseFragment extends Fragment {

    public FragmentActivity mActivity;
    protected boolean isVisible;//是否可见
    private boolean isPrepared;//是否准备好
    private boolean isFirst = true;//是否为第一次加载

    /**
     * 此方法可以得到上下文对象
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 当Activity初始化之后可以在这里进行一些数据的初始化操作
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirst) {
            return;
        }
        initEvent();
        initData();
        isFirst = false;
    }

    /**
     * 不可见时
     */
    protected void onInvisible() {

    }

    /**
     * 返回一个需要展示的View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mActivity = getActivity();
        View view = inflateView(inflater, container, savedInstanceState);
        initView(view);
        return view;
    }

    /**
     * 子类可以复写此方法初始化事件
     */
    protected void initEvent() {

    }

    /**
     * 子类实现此抽象方法返回View进行展示
     *
     * @return
     */
    public abstract View inflateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState);

    /**
     * 初始化控件
     */
    protected abstract void initView(View view);

    /**
     * 子类在此方法中实现数据的初始化
     */
    public abstract void initData();

}
