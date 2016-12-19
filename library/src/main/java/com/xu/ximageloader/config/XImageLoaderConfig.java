package com.xu.ximageloader.config;

import com.xu.ximageloader.cache.ImageCache;
import com.xu.ximageloader.loader.ImageLoader;

/**
 * Created by Xu on 2016/11/6.
 */

public final class XImageLoaderConfig {

    private ImageLoader loader;
    private ImageCache cache;
    private boolean isCache;
    private DisplayConfig mDisplayConfig;

    public XImageLoaderConfig() {
        mDisplayConfig = new DisplayConfig();
    }

    public ImageLoader getLoader() {
        return loader;
    }

    public void setLoader(ImageLoader loader) {
        this.loader = loader;
    }

    public ImageCache getCache() {
        return cache;
    }

    public void setCache(ImageCache cache) {
        this.cache = cache;
        if (this.cache != null) {
            isCache = true;
        }
    }

    public boolean isCache() {
        return isCache;
    }

    public void setIsCache(boolean isCache) {
        this.isCache = isCache;
    }

    public DisplayConfig getDisplayConfig() {
        return mDisplayConfig;
    }

    public void setLoadingResId(int loadingResid) {
        mDisplayConfig.setLoadingResId(loadingResid);
    }

    public void setFailResId(int failResId) {
        mDisplayConfig.setFailResId(failResId);
    }

    public int getLoadingResId() {
        return mDisplayConfig.getLoadingResId();
    }

    public int getFailResId() {
        return mDisplayConfig.getFailResId();
    }

    private class DisplayConfig {
        private int loadingResId = -1;
        private int failResId = -1;

        public int getLoadingResId() {
            return loadingResId;
        }

        public void setLoadingResId(int loadingResId) {
            this.loadingResId = loadingResId;
        }

        public int getFailResId() {
            return failResId;
        }
        public void setFailResId(int failResId) {
            this.failResId = failResId;
        }
    }
}