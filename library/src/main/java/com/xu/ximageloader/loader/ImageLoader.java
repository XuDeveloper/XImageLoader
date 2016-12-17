package com.xu.ximageloader.loader;

import android.graphics.Bitmap;

import com.xu.ximageloader.core.XImageLoaderRequest;

/**
 * Created by Xu on 2016/10/23.
 */

public interface ImageLoader {

    Bitmap load(XImageLoaderRequest request);
}
