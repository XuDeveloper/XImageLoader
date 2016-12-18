package com.xu.ximageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;

import com.xu.ximageloader.core.XImageLoaderRequest;

/**
 * Created by Xu on 2016/12/13.
 */

public class DoubleCache implements ImageCache {

    private DiskCache mDiskCache;
    private MemoryCache mMemoryCache;

    public DoubleCache(Context context) {
        mDiskCache = new DiskCache(context);
        mMemoryCache = new MemoryCache();
    }

    @Override
    public void put(XImageLoaderRequest request, Bitmap bitmap) {
        mMemoryCache.put(request, bitmap);
        mDiskCache.put(request, bitmap);
    }

    @Override
    public Bitmap get(XImageLoaderRequest request) {
        Bitmap bitmap = mMemoryCache.get(request);
        if (bitmap != null) {
            return bitmap;
        } else {
            bitmap = mDiskCache.get(request);
            if (bitmap != null) {
                saveBitmapIntoMemory(request, bitmap);
            }
        }
        return bitmap;
    }

    @Override
    public void remove(XImageLoaderRequest request) {
        mMemoryCache.remove(request);
        mDiskCache.remove(request);
    }

    private void saveBitmapIntoMemory(XImageLoaderRequest key, Bitmap bitmap) {
        // 如果Value从disk中读取,那么存入内存缓存
        if (bitmap != null) {
            mMemoryCache.put(key, bitmap);
        }
    }
}
