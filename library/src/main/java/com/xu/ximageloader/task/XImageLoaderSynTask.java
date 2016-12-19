package com.xu.ximageloader.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.xu.ximageloader.cache.MemoryCache;
import com.xu.ximageloader.config.XImageLoaderConfig;
import com.xu.ximageloader.core.XImageLoaderRequest;
import com.xu.ximageloader.loader.LoaderFactory;
import com.xu.ximageloader.util.Util;

/**
 * Created by Xu on 2016/12/18.
 */

public final class XImageLoaderSynTask{

    private static final String TAG = "XImageLoaderSynTask";

    private Context context;
    private XImageLoaderRequest request;
    private XImageLoaderConfig config;

    public XImageLoaderSynTask(Context context, XImageLoaderRequest request, XImageLoaderConfig config) {
        this.context = context;
        this.request = request;
        this.config = config;
    }

    public Bitmap load() {
        if (config.getLoader() == null) {
            config.setLoader(LoaderFactory.getInstance().getLoader(request.getImageUrl()));
        }
        boolean mIsConn = Util.isConn(context);
        if (mIsConn) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                throw new RuntimeException("can not visit network from UI Thread.");
            }
            return getBitmap();
        } else {
            Log.e(TAG, "Connection is not available, please retry!");
            Toast.makeText(context, "网络连接失败，请重试！", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    private Bitmap getBitmap() {
        Bitmap mBitmap = null;
        mBitmap = config.getLoader().load(request);
        if (config.getCache() == null) {
            config.setCache(new MemoryCache());
        }
        config.getCache().put(request, mBitmap);
        return mBitmap;
    }

}
