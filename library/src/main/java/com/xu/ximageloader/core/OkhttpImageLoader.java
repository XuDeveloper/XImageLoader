package com.xu.ximageloader.core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.xu.ximageloader.loader.ImageLoader;
import com.xu.ximageloader.util.FileUtils;
import com.xu.ximageloader.util.Util;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Xu on 2016/12/19.
 */

public class OkhttpImageLoader implements ImageLoader {

    private static final String TAG = "OkhttpImageLoader";

    private OkHttpClient mOkHttpClient;

    public OkhttpImageLoader() {
        mOkHttpClient = new OkHttpClient();
    }

    @Override
    public Bitmap load(XImageLoaderRequest request) {
        InputStream is = null;
        try {
            String url = request.getImageUrl();
            Request okhttpRequest = new Request.Builder().url(url).build();
            Response response = mOkHttpClient.newCall(okhttpRequest).execute();
            is = response.body().byteStream();
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
        }
        return null;
    }
}
