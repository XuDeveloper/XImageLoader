package com.xu.ximageloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.xu.ximageloader.core.XImageLoaderRequest;
import com.xu.ximageloader.core.ImageDecoder;

import java.io.File;

/**
 * Created by Xu on 2016/11/6.
 */

public class LocalImageLoader implements ImageLoader {

    @Override
    public Bitmap load(XImageLoaderRequest request) {
        final String imagePath = Uri.parse(request.getImageUrl()).getPath();
        final File imgFile = new File(imagePath);
        if (!imgFile.exists()) {
            return null;
        }
//        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
//        ImageDecoder imageCompress = request.getImageDecoder();
//        imageCompress.compress(bitmap, request.getImageView());
        ImageDecoder decoder = new ImageDecoder() {
            @Override
            public Bitmap decodeBitmapWithOptions(BitmapFactory.Options options) {
                return BitmapFactory.decodeFile(imagePath, options);
            }
        };
        return decoder.decode(request);
    }
}
