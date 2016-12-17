package com.xu.ximageloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.xu.ximageloader.core.XImageLoaderRequest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Xu on 2016/10/23.
 */

public class InternetImageLoader implements ImageLoader {

    private Executor mExecutor;
    private Bitmap bitmap;

    public InternetImageLoader() {
        mExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Override
    public Bitmap load(final XImageLoaderRequest request) {
//        mExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    URL url = new URL(request.getImageUrl());
//                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                    conn.setConnectTimeout(3000);
//                    conn.setDoInput(true);
//                    conn.setUseCaches(false);
//                    bitmap = BitmapFactory.decodeStream(conn.getInputStream());
//                    conn.disconnect();
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
        try {
            URL url = new URL(request.getImageUrl());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    URL url = new URL(request.getImageUrl());
//                    final HttpURLConnection conne= (HttpURLConnection) url.openConnection();
//                    bitmap = BitmapFactory.decodeStream(conn.getInputStream());
//                    Log.i("InternetImageLoader", "bitmap:" + (bitmap == null));
//                    conn.disconnect();
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
        return bitmap;
    }

}
