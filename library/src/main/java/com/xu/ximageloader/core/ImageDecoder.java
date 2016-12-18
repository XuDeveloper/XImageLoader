package com.xu.ximageloader.core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * Created by Xu on 2016/10/23.
 */

public abstract class ImageDecoder {

    private static String TAG = "ImageDecoder";

    public abstract Bitmap decodeBitmapWithOptions(BitmapFactory.Options options);

    public final Bitmap decode(XImageLoaderRequest request) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        decodeBitmapWithOptions(options);
        options.inSampleSize = calculateInSampleSize(options, request);
        options.inJustDecodeBounds = false;
        return decodeBitmapWithOptions(options);
    }

    private int calculateInSampleSize(BitmapFactory.Options options, XImageLoaderRequest request) {
        int reqWidth = request.getReqWidth();
        int reqHeight = request.getReqHeight();
        if (reqWidth == 0 || reqHeight == 0) {
            return 1;
        }
        final int height = options.outHeight;
        final int width = options.outWidth;
        Log.d(TAG, "origin, w= " + width + " h=" + height);
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

}
