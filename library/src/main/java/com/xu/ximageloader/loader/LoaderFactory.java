package com.xu.ximageloader.loader;

import com.xu.ximageloader.util.Util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Xu on 2016/12/13.
 */

public class LoaderFactory {

    public static final String HTTP = "http";
    public static final String HTTPS = "https";
    public static final String FILE = "file";

    private Map<String, ImageLoader> loaderMap = new HashMap<>();
    private static LoaderFactory INSTANCE;
    private NullImageLoader mNullLoader;

    private LoaderFactory() {
        mNullLoader = new NullImageLoader();
        register(HTTP, new InternetImageLoader());
        register(HTTPS, new InternetImageLoader());
        register(FILE, new LocalImageLoader());
    }

    public static LoaderFactory getInstance() {
        if (INSTANCE == null) {
            synchronized (LoaderFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LoaderFactory();
                }
            }
        }
        return INSTANCE;
    }

    private final synchronized void register(String schema, ImageLoader loader) {
        loaderMap.put(schema, loader);
    }

    public ImageLoader getLoader(String imageUrl) {
        String schema = Util.parseSchema(imageUrl);
        if (loaderMap.containsKey(schema)) {
            return loaderMap.get(schema);
        }
        return mNullLoader;
    }
}
