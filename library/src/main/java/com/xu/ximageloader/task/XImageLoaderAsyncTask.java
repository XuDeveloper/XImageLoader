package com.xu.ximageloader.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.xu.ximageloader.cache.DoubleCache;
import com.xu.ximageloader.config.XImageLoaderConfig;
import com.xu.ximageloader.core.XImageLoaderRequest;
import com.xu.ximageloader.loader.LoaderFactory;
import com.xu.ximageloader.util.Util;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Xu on 2016/12/18.
 */

public final class XImageLoaderAsyncTask {

    private static final String TAG = "XImageLoaderAsyncTask";
    private static final int CPU_COUNT = Runtime.getRuntime()
            .availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final long KEEP_ALIVE = 10L;

    private Context context;
    private XImageLoaderRequest request;
    private XImageLoaderConfig config;

    private Handler mMainHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            Bitmap result = (Bitmap) msg.obj;
            if (result != null) {
                request.getImageView().setImageBitmap(result);
            } else {
                if (hasFailResId()) {
                    request.getImageView().setImageResource(request.getFailResId());
                }
            }
        }
    };

    public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
            CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>());

    public XImageLoaderAsyncTask(Context context, XImageLoaderRequest request, XImageLoaderConfig config) {
        this.context = context;
        this.request = request;
        this.config = config;
    }

    public void load() {
        if (hasLoadingResId()) {
            request.getImageView().setImageResource(request.getLoadingResId());
        }
        if (config.getLoader() == null) {
            config.setLoader(LoaderFactory.getInstance().getLoader(request.getImageUrl()));
        }
        boolean mIsConn = Util.isConn(context);
        if (mIsConn) {
            Task task = new Task();
            THREAD_POOL_EXECUTOR.execute(task);
        } else {
            if (hasFailResId()) {
                request.getImageView().setImageResource(request.getFailResId());
            }
            Log.e(TAG, "Connection is not available, please retry!");
            Toast.makeText(context, "网络连接失败，请重试！", Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap getBitmap() {
        Bitmap mBitmap = null;
        mBitmap = config.getLoader().load(request);
        if (config.getCache() == null) {
            config.setCache(new DoubleCache(context));
        }
        config.getCache().put(request, mBitmap);
        return mBitmap;
    }

    private boolean hasLoadingResId() {
        return request.getLoadingResId() != -1;
    }

    private boolean hasFailResId() {
        return request.getFailResId() != -1;
    }

    class Task implements Runnable {

        @Override
        public void run() {
            Bitmap mBitmap = getBitmap();
            Message message = Message.obtain();
            message.obj = mBitmap;
            mMainHandler.sendMessage(message);
        }
    }

}
