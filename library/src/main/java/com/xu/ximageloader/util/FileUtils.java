package com.xu.ximageloader.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Xu on 2016/11/15.
 */

public class FileUtils {

    private static final int IO_BUFFER_SIZE = 8 * 1024;
    private static final int BUFFER_SIZE = 4 * 1024;

    public static File getDiskCacheDir(Context context, String uniqueName) {
        boolean externalStorageAvailable = Environment
                .getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        final String cachePath;
        if (externalStorageAvailable) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static long getUsableSpace(File path) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return path.getUsableSpace();
        }
        final StatFs stats = new StatFs(path.getPath());
        return stats.getBlockSizeLong() * stats.getAvailableBlocksLong();
    }

    public static boolean writeBitmapToDisk(Bitmap bitmap, OutputStream outputStream) {
        BufferedOutputStream bos = new BufferedOutputStream(outputStream, 15 * 1024);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, bos);
        boolean result = true;
        try {
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        } finally {
            Util.closeQuietly(bos);
        }
        return result;
    }

    public static boolean downloadUrlToStream(InputStream inputStream,
                                              OutputStream outputStream) {
        BufferedOutputStream out = null;
        BufferedInputStream in = null;

        try {
            in = new BufferedInputStream(inputStream, IO_BUFFER_SIZE);
            out = new BufferedOutputStream(outputStream, IO_BUFFER_SIZE);

            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (IOException e) {
            Log.e("ximageloader", "downloadBitmap failed." + e);
        } finally {
            Util.closeQuietly(out);
            Util.closeQuietly(in);
        }
        return false;
    }

    public static byte[] inputstream2ByteArr(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[BUFFER_SIZE];
        int len = 0;
        while ((len = inputStream.read(buff)) != -1) {
            byteArrayOutputStream.write(buff, 0, len);
        }
        Util.closeQuietly(inputStream);
        Util.closeQuietly(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
