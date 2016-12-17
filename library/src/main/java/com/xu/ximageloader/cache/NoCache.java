package com.xu.ximageloader.cache;

import android.graphics.Bitmap;

import com.xu.ximageloader.core.XImageLoaderRequest;

/**
 * Created by Xu on 2016/12/13.
 */

public class NoCache implements ImageCache {
    @Override
    public void put(XImageLoaderRequest request, Bitmap bitmap) {

    }

    @Override
    public Bitmap get(XImageLoaderRequest request) {
        return null;
    }

    @Override
    public void remove(XImageLoaderRequest request) {

    }
}
