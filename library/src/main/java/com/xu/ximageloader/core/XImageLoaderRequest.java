package com.xu.ximageloader.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.xu.ximageloader.config.XImageLoaderConfig;
import com.xu.ximageloader.task.XImageLoaderAsyncTask;
import com.xu.ximageloader.task.XImageLoaderSynTask;
import com.xu.ximageloader.util.ImageViewHelper;

/**
 * Created by Xu on 2016/10/17.
 */

public final class XImageLoaderRequest {

    private final String TAG = "XImageLoaderRequest";

    private Context context;
    private ImageView imageView;
    private String imageUrl;
    private XImageLoaderConfig config;

    public int getLoadingResId() {
        return loadingResId;
    }

    public int getFailResId() {
        return failResId;
    }

    private int loadingResId;
    private int failResId;

    public XImageLoaderRequest(Context context, XImageLoaderConfig config, ImageView imageView) {
        this.context = context;
        this.imageView = imageView;
        this.config = config;
        this.loadingResId = config.getLoadingResId();
        this.failResId = config.getFailResId();
    }

    private boolean check(String imageUrl) {
        this.imageUrl = imageUrl;
        Bitmap mBitmap = null;
        if (hasLoadingResId()) {
            imageView.setImageResource(loadingResId);
        }
        if (imageUrl == null) {
            throw new RuntimeException("image url is null");
        }
        if (imageView == null) {
            throw new RuntimeException("imageview is null");
        }
        if (config.isCache()) {
            mBitmap = loadBitmapFromCache();
            if (mBitmap != null) {
                imageView.setImageBitmap(mBitmap);
                return true;
            }
        }
        return false;
    }

    public Bitmap getBitmap(String imageUrl) {
        if (!check(imageUrl)) {
            XImageLoaderSynTask task = new XImageLoaderSynTask(context, this, config);
            if (task.load() != null) {
                return task.load();
            } else {
                if (hasFailResId()) {
                    return BitmapFactory.decodeResource(context.getResources(), config.getFailResId());
                }
            }
        }
        return null;

//        if (loader == null) {
//            String schema = Util.parseSchema(imageUrl);
//            loader = LoaderFactory.getInstance().getLoader(schema);
//        }
//        if (isCache) {
//            mBitmap = loadBitmapFromCache();
//        }
//        if (mBitmap != null) {
//            imageView.setImageBitmap(mBitmap);
//            return;
//        } else {
//            boolean mIsConn = Util.isConn(context);
//            if (mIsConn) {
//                Task task = new Task(this);
//                THREAD_POOL_EXECUTOR.execute(task);
//            } else {
//                if (hasFailResId()) {
//                    imageView.setImageResource(failResId);
//                }
//                Log.e(TAG, "Connection is not available, please retry!");
//                Toast.makeText(context, "网络连接失败，请重试！", Toast.LENGTH_SHORT).show();
//                return;
//            }
//        }

    }

    public void load(String imageUrl) {
        if(!check(imageUrl)) {
            XImageLoaderAsyncTask task = new XImageLoaderAsyncTask(context, this, config);
            task.load();
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
        if (config.getCache() != null || config.isCache()) {
            Bitmap bitmap = config.getCache().get(this);
            return bitmap;
        }
        return null;
    }

    private boolean hasLoadingResId() {
        return loadingResId != -1;
    }

    private boolean hasFailResId() {
        return failResId != -1;
    }

}