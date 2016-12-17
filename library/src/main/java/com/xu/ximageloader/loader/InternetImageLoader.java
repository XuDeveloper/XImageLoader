package com.xu.ximageloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.xu.ximageloader.core.ImageDecoder;
import com.xu.ximageloader.core.XImageLoaderRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Xu on 2016/10/23.
 */

public class InternetImageLoader implements ImageLoader {

    private Bitmap bitmap;

    public InternetImageLoader() {
    }

    @Override
    public Bitmap load(final XImageLoaderRequest request) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(request.getImageUrl());
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000);
            conn.setDoInput(true);
            final InputStream is = conn.getInputStream();
            ImageDecoder decoder = new ImageDecoder() {
                @Override
                public Bitmap decodeBitmapWithOptions(BitmapFactory.Options options) {
                    return BitmapFactory.decodeStream(is, null, options);
                }
            };
            return decoder.decode(request);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return bitmap;
    }

}
