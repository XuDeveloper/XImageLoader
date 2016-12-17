package com.example.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.xu.ximageloader.core.XImageLoader;

public class MainActivity extends AppCompatActivity {


    // TODO: 2016/12/17 基本完成
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = (ImageView) findViewById(R.id.image);
        XImageLoader.build(this).imageview(true, false, imageView).load("http://pic.kekenet.com/2016/0318/31221458265140.jpg");
//        XImageLoaderConfig config = new XImageLoaderConfig();
//        config.setLoadingResId(R.drawable.loading);
//        config.setFailResId(R.drawable.fail);
//        config.setCache(new DiskCache(this));
//        XImageLoader.build(this).imageview(config, imageView).load("http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1301/04/c0/17113078_1357279663329.jpg");

    }

}
