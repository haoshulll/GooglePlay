package com.myapp.googleplay.global;

import android.graphics.Bitmap;

import com.myapp.googleplay.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

/**
 * Created by haoshul on 2016/6/22.
 */
public interface ImageLoaderOptions {
    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.ic_default)//加载过程显示的图片
            .showImageForEmptyUri(R.drawable.ic_default)//url为空时显示什么图片
            .showImageOnFail(R.drawable.ic_default)//加载失败时显示什么图片
            .cacheInMemory(false)//是否内存缓存
            .cacheOnDisk(true)//是否硬盘缓存
            .considerExifParams(true)//会识别图片的方向信息.
            .imageScaleType(ImageScaleType.EXACTLY)//对图片进一步缩放
            .bitmapConfig(Bitmap.Config.RGB_565)//配置了图片的减小内存的颜色模式
            .displayer(new FadeInBitmapDisplayer(1500)).build();//渐渐显示的 加载动画
//					.displayer(new RoundedBitmapDisplayer(36)).build();//圆角加载


    DisplayImageOptions default_options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.ic_default)//加载过程显示的图片
            .showImageForEmptyUri(R.drawable.ic_default)//url为空时显示什么图片
            .showImageOnFail(R.drawable.ic_default)//加载失败时显示什么图片
            .cacheInMemory(false)//是否内存缓存
            .cacheOnDisk(true)//是否硬盘缓存
            .considerExifParams(true)//会识别图片的方向信息
            .imageScaleType(ImageScaleType.EXACTLY)//对图片进一步缩放
            .bitmapConfig(Bitmap.Config.RGB_565)//配置了图片的减小内存的颜色模式
            .displayer(new SimpleBitmapDisplayer()).build();//渐渐显示的 加载动画
//					.displayer(new RoundedBitmapDisplayer(36)).build();//圆角加载




    DisplayImageOptions round_options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.ic_default)//加载过程显示的图片
            .showImageForEmptyUri(R.drawable.ic_default)//url为空时显示什么图片
            .showImageOnFail(R.drawable.ic_default)//加载失败时显示什么图片
            .cacheInMemory(false)//是否内存缓存
            .cacheOnDisk(true)//是否硬盘缓存
            .considerExifParams(true)//会识别图片的方向信息
            .imageScaleType(ImageScaleType.EXACTLY)//对图片进一步缩放
            .bitmapConfig(Bitmap.Config.RGB_565)//配置了图片的减小内存的颜色模式
//            .displayer(new SimpleBitmapDisplayer()).build();//渐渐显示的 加载动画
            .displayer(new RoundedBitmapDisplayer(40)).build();//圆角加载


    DisplayImageOptions rawPhoto_options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.ic_default)//加载过程显示的图片
            .showImageForEmptyUri(R.drawable.ic_default)//url为空时显示什么图片
            .showImageOnFail(R.drawable.ic_default)//加载失败时显示什么图片
            .cacheInMemory(false)//是否内存缓存
            .cacheOnDisk(true)//是否硬盘缓存
            .considerExifParams(true)//会识别图片的方向信息
//            .imageScaleType(ImageScaleType.EXACTLY)//对图片进一步缩放
//            .bitmapConfig(Bitmap.Config.RGB_565)//配置了图片的减小内存的颜色模式
//            .displayer(new SimpleBitmapDisplayer()).build();//渐渐显示的 加载动画
            .displayer(new RoundedBitmapDisplayer(40)).build();//圆角加载
}
