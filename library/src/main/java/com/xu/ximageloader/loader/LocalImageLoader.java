package com.xu.ximageloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.xu.ximageloader.core.ImageDecoder;
import com.xu.ximageloader.core.XImageLoaderRequest;
import com.xu.ximageloader.util.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Xu on 2016/11/6.
 */

public class LocalImageLoader implements ImageLoader {

    private FileInputStream fileInputStream;

    @Override
    public Bitmap load(XImageLoaderRequest request) {
        try {
            String imagePath = Uri.parse(request.getImageUrl()).getPath();
            File imgFile = new File(imagePath);
            if (!imgFile.exists()) {
                return null;
            }
            fileInputStream = new FileInputStream(imagePath);
            ImageDecoder decoder = new ImageDecoder() {
                @Override
                public Bitmap decodeBitmapWithOptions(BitmapFactory.Options options) {
                    try {
                        return BitmapFactory.decodeFileDescriptor(fileInputStream.getFD(), null, options);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
            return decoder.decode(request);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            Util.closeQuietly(fileInputStream);
        }
        return null;
    }
}
