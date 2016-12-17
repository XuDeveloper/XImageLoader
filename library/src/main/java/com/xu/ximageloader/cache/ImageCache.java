package com.xu.ximageloader.cache;

import android.graphics.Bitmap;

import com.xu.ximageloader.core.XImageLoaderRequest;

/**
 * Created by Xu on 2016/10/17.
 */

public interface ImageCache {

    void put(XImageLoaderRequest request, Bitmap bitmap);
    Bitmap get(XImageLoaderRequest request);
    void remove(XImageLoaderRequest request);
}
