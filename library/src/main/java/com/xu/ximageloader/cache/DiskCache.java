package com.xu.ximageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.xu.ximageloader.core.XImageLoaderRequest;
import com.xu.ximageloader.util.FileUtils;
import com.xu.ximageloader.util.Util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import libcore.io.DiskLruCache;

/**
 * Created by Xu on 2016/11/15.
 */

public class DiskCache implements ImageCache {

    private static final long DISK_CACHE_SIZE = 1024 * 1024 * 50;

    private DiskLruCache mDiskLruCache;
    private Context mContext;

    public DiskCache(Context context) {
        this.mContext = context;
        try {
            File diskCacheDir = FileUtils.getDiskCacheDir(mContext, "bitmap");
            if (!diskCacheDir.exists()) {
                diskCacheDir.mkdirs();
            }
            if (FileUtils.getUsableSpace(diskCacheDir) > DISK_CACHE_SIZE) {
                mDiskLruCache = DiskLruCache.open(diskCacheDir, Util.getAppVersion(context), 1, DISK_CACHE_SIZE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void put(XImageLoaderRequest request, Bitmap bitmap) {
        if (bitmap != null) {
            DiskLruCache.Editor editor = null;
            try {
                String imageUrlMd5 = Util.hashKeyForDisk(request.getImageUrl());
                editor = mDiskLruCache.edit(imageUrlMd5);
                if (editor != null) {
                    OutputStream outputStream = editor.newOutputStream(0);
                    if (FileUtils.writeBitmapToDisk(bitmap, outputStream)) {
                        // 写入disk缓存
                        Log.i("diskcache-put", "write in disk");
                        editor.commit();
                    } else {
                        editor.abort();
                    }
//                if (editor != null) {
//                    OutputStream outputStream = editor.newOutputStream(0);
//                    if (FileUtils.downloadUrlToStream(request.getInputStream(), outputStream)) {
//                        editor.commit();
//                    } else {
//                        editor.abort();
//                    }
//                    Util.closeQuietly(outputStream);
                }
                mDiskLruCache.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i("diskcache-put", "key:" + Util.hashKeyForDisk(request.getImageUrl()) + " value:" + bitmap.toString());
        }
    }

    @Override
    public Bitmap get(XImageLoaderRequest request) {
        Bitmap bitmap = null;
        InputStream is = null;
        try {
            String imageUrlMd5 = Util.hashKeyForDisk(request.getImageUrl());
            DiskLruCache.Snapshot snapShot = mDiskLruCache.get(imageUrlMd5);
            if (snapShot != null) {
                is = snapShot.getInputStream(0);
                bitmap = BitmapFactory.decodeStream(is);
            }
            if (bitmap != null) {
                Log.i("diskcache-get", "key:" + Util.hashKeyForDisk(request.getImageUrl()) + " value:" + bitmap.toString());
            } else {
                Log.i("diskcache-get", "key:" + Util.hashKeyForDisk(request.getImageUrl()) + " value: null");
            }
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Util.closeQuietly(is);
        }
        return null;
    }

    @Override
    public void remove(XImageLoaderRequest request) {
        try {
            String key = Util.hashKeyForDisk(request.getImageUrl());
            mDiskLruCache.remove(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
