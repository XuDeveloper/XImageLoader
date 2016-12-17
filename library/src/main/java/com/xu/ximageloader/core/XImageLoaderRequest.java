package com.xu.ximageloader.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;
import android.widget.Toast;

import com.xu.ximageloader.cache.ImageCache;
import com.xu.ximageloader.cache.MemoryCache;
import com.xu.ximageloader.loader.ImageLoader;
import com.xu.ximageloader.loader.LoaderFactory;
import com.xu.ximageloader.util.ImageViewHelper;
import com.xu.ximageloader.util.Util;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Xu on 2016/10/17.
 */

public final class XImageLoaderRequest {

    private final String TAG = "XImageLoaderRequest";
    private static final int CPU_COUNT = Runtime.getRuntime()
            .availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final long KEEP_ALIVE = 10L;

    private Context context;
    private ImageCache cache;
    private ImageView imageView;
    private ImageLoader loader;
    private String imageUrl;
    private boolean isCache;
    private int loadingResId;
    private int failResId;

    private Handler mMainHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            Bitmap result = (Bitmap) msg.obj;
            if (result != null) {
                imageView.setImageBitmap(result);
            } else {
                if (hasFailResId()) {
                    imageView.setImageResource(failResId);
                }
                Toast.makeText(context, "网络加载失败，请重试！", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
            CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>());

    public XImageLoaderRequest(Context context, XImageLoaderConfig config, ImageView imageView) {
        this.context = context;
        this.imageView = imageView;
        this.loader = config.getLoader();
        this.cache = config.getCache();
        this.isCache = config.isCache();
        this.loadingResId = config.getLoadingResId();
        this.failResId = config.getFailResId();
    }

    public void load(final String imageUrl) {
        this.imageUrl = imageUrl;
        if (hasLoadingResId()) {
            imageView.setImageResource(loadingResId);
        }
        Bitmap mBitmap = null;
        if (imageUrl == null) {
            throw new RuntimeException("image url is null");
        }
        if (imageView == null) {
            throw new RuntimeException("imageview is null");
        }
        if (loader == null) {
            String schema = Util.parseSchema(imageUrl);
            loader = LoaderFactory.getInstance().getLoader(schema);
        }
        if (isCache) {
            mBitmap = loadBitmapFromCache();
        }
        if (mBitmap != null) {
            imageView.setImageBitmap(mBitmap);
        } else {
            boolean mIsConn = Util.isConn(context);
            if (mIsConn) {
                Task task = new Task(this);
                THREAD_POOL_EXECUTOR.execute(task);
            } else {
                if (hasFailResId()) {
                    imageView.setImageResource(failResId);
                }
                Toast.makeText(context, "网络无法连通，请重试！", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getReqWidth() {
        return ImageViewHelper.getImageViewWidth(imageView);
    }

    public int getReqHeight() {
        return ImageViewHelper.getImageViewHeight(imageView);
    }

    private Bitmap loadBitmapFromCache() {
        if (cache != null || isCache) {
            Bitmap bitmap = cache.get(this);
            return bitmap;
        }
        return null;
    }

    private Bitmap getBitmap() {
        Bitmap mBitmap = null;
        mBitmap = loader.load(this);
        if (cache == null) {
            cache = new MemoryCache();
        }
        cache.put(this, mBitmap);
        return mBitmap;
    }

    private boolean hasLoadingResId() {
        return loadingResId != -1;
    }

    private boolean hasFailResId() {
        return failResId != -1;
    }

    class Task implements Runnable {

        private XImageLoaderRequest mRequest;

        public Task(XImageLoaderRequest request) {
            mRequest = request;
        }

        @Override
        public void run() {
            Bitmap mBitmap = getBitmap();
            Message message = new Message();
            message.obj = mBitmap;
            mMainHandler.sendMessage(message);
        }
    }

}