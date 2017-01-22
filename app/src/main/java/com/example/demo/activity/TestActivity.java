package com.example.demo.activity;

import android.os.Bundle;
import android.view.View;

import com.example.demo.BannerLoaderImage;
import com.example.demo.R;
import com.plugin.library.banner.Banner;
import com.plugin.library.banner.listener.OnBannerClickListener;
import com.plugin.library.view.ToastBar;
import com.plugin.library.view.switchbutton.SwitchIcon;
import com.plugin.library.view.titlebar.TitleBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiezh on 2017/1/20.
 */

public class TestActivity extends BaseSwipeActivity implements OnBannerClickListener {

    private List<String> imgUrl = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    Banner banner1;
    TitleBar mTitleBar;
    SwitchIcon mSwitchIcon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mTitleBar= (TitleBar) findViewById(R.id.title_bar);
        mTitleBar.setTitle("标题");

        mSwitchIcon = (SwitchIcon) findViewById(R.id.switchIcon);
        mSwitchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwitchIcon.switchState();
            }
        });
        imgUrl.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic1xjab4j30ci08cjrv.jpg");
        imgUrl.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
        imgUrl.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg");
        imgUrl.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg");
        titles.add("51巅峰钜惠");
        titles.add("十大星级品牌联盟，全场2折起");
        titles.add("生命不是要超越别人，而是要超越自己。");
        titles.add("嗨购5折不要停");
        //简单使用
        banner1 = (Banner) findViewById(R.id.banner1);
        banner1.setImages(imgUrl)
                .setImageLoader(new BannerLoaderImage())
                .setOnBannerClickListener(this).isAutoPlay(false)
                .start();
    }

    @Override
    public void OnBannerClick(int position) {

    }
}
