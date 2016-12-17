package com.xu.ximageloader.cache;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.xu.ximageloader.core.XImageLoaderRequest;

/**
 * Created by Xu on 2016/10/23.
 */

public class MemoryCache implements ImageCache {

    private LruCache<String, Bitmap> mMemoryCache;

    public MemoryCache() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 4;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };
    }

    @Override
    public void put(XImageLoaderRequest request, Bitmap bitmap) {
        if (bitmap != null) {
            mMemoryCache.put(request.getImageUrl(), bitmap);
            Log.i("memorycache-put", "key:" + request.getImageUrl() + "value:" + bitmap.toString());
        }
    }

    @Override
    public Bitmap get(XImageLoaderRequest request) {
        if (mMemoryCache.get(request.getImageUrl()) != null) {
            Log.i("memorycache-get", "key:" + request.getImageUrl() + "value:" + mMemoryCache.get(request.getImageUrl()).toString());
        } else {
            Log.i("memorycache-get", "key:" + request.getImageUrl() + "value:" + "null");
        }
        return mMemoryCache.get(request.getImageUrl());
    }

    @Override
    public void remove(XImageLoaderRequest request) {
        mMemoryCache.remove(request.getImageUrl());
    }
}
