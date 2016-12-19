package com.xu.ximageloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.xu.ximageloader.core.ImageDecoder;
import com.xu.ximageloader.core.XImageLoaderRequest;
import com.xu.ximageloader.util.FileUtils;
import com.xu.ximageloader.util.Util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Xu on 2016/10/23.
 */

public class InternetImageLoader implements ImageLoader {

    private static final String TAG = "InternetImageLoader";

    public InternetImageLoader() {
    }

    @Override
    public Bitmap load(final XImageLoaderRequest request) {
        HttpURLConnection conn = null;
        InputStream is = null;
        try {
            URL url = new URL(request.getImageUrl());
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000);
            conn.setDoInput(true);
            is = conn.getInputStream();
            final byte[] data = FileUtils.inputstream2ByteArr(is);
            ImageDecoder decoder = new ImageDecoder() {
                @Override
                public Bitmap decodeBitmapWithOptions(BitmapFactory.Options options) {
                    return BitmapFactory.decodeByteArray(data, 0, data.length, options);
                }
            };
            return decoder.decode(request);
        } catch (IOException e) {
            Log.e(TAG, "loading failed, url:" + request.getImageUrl() + "reason:" + e.getMessage());
        } finally {
            Util.closeQuietly(is);
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }

}
