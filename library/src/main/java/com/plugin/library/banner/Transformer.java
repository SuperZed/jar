package com.plugin.library.banner;

import android.support.v4.view.ViewPager.PageTransformer;

import com.plugin.library.banner.transformer.AccordionTransformer;
import com.plugin.library.banner.transformer.CubeInTransformer;
import com.plugin.library.banner.transformer.CubeOutTransformer;
import com.plugin.library.banner.transformer.DefaultTransformer;
import com.plugin.library.banner.transformer.DepthPageTransformer;
import com.plugin.library.banner.transformer.FlipHorizontalTransformer;
import com.plugin.library.banner.transformer.FlipVerticalTransformer;
import com.plugin.library.banner.transformer.TabletTransformer;
import com.plugin.library.banner.transformer.ZoomInTransformer;
import com.plugin.library.banner.transformer.ZoomOutSlideTransformer;
import com.plugin.library.banner.transformer.ZoomOutTranformer;


public class Transformer {
    public static Class<? extends PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends PageTransformer> ZoomOut = ZoomOutTranformer.class;
    public static Class<? extends PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
}
