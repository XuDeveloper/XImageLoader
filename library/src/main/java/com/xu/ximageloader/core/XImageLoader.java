package com.xu.ximageloader.core;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.xu.ximageloader.cache.DiskCache;
import com.xu.ximageloader.cache.DoubleCache;
import com.xu.ximageloader.cache.MemoryCache;
import com.xu.ximageloader.config.XImageLoaderConfig;
import com.xu.ximageloader.util.Util;

/**
 * Created by Xu on 2016/10/17.
 */

public class XImageLoader {

    private Context mContext;

    private XImageLoader(Context context) {
        mContext = context;
    }

    public static void verifyStoragePermissions(Activity activity) {
        Util.verifyStoragePermissions(activity);
    }

    public static XImageLoader build(Context context) {
        return new XImageLoader(context);
    }

    public XImageLoaderRequest imageview(ImageView imageView) {
        return imageview(false, false, imageView);
    }

    public XImageLoaderRequest imageview(boolean isMemoryCache, boolean isDiskCache, ImageView imageView) {
        XImageLoaderConfig config = new XImageLoaderConfig();
        boolean isCache = isMemoryCache | isDiskCache;
        config.setIsCache(isCache);
        if (isMemoryCache) {
            config.setCache(new MemoryCache());
        }
        if (isDiskCache) {
            config.setCache(new DiskCache(mContext));
        }
        if (isMemoryCache && isDiskCache) {
            config.setCache(new DoubleCache(mContext));
        }
        return new XImageLoaderRequest(mContext, config, imageView);
    }

    public XImageLoaderRequest imageview(boolean isDoubleCache, ImageView imageView) {
        XImageLoaderConfig config = new XImageLoaderConfig();
        if (isDoubleCache) {
            config.setIsCache(isDoubleCache);
            config.setCache(new DoubleCache(mContext));
        }
        return new XImageLoaderRequest(mContext, config, imageView);
    }

    public XImageLoaderRequest imageview(XImageLoaderConfig config, ImageView imageView) {
        return new XImageLoaderRequest(mContext, config, imageView);
    }


}