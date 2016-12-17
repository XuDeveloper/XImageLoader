package com.xu.ximageloader.loader;

import android.graphics.Bitmap;
import android.util.Log;

import com.xu.ximageloader.core.XImageLoaderRequest;

/**
 * Created by Xu on 2016/12/13.
 */

public class NullImageLoader implements ImageLoader {

    private static final String TAG = "NullImageLoader";

    @Override
    public Bitmap load(XImageLoaderRequest request) {
        Log.e(TAG, "wrong schema, your image uri is :" + request.getImageUrl());
        return null;
    }
}
