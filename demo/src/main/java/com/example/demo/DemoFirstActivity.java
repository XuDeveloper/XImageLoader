package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.xu.ximageloader.core.XImageLoader;

public class DemoFirstActivity extends AppCompatActivity {

    // TODO: 2016/12/18  1.效率问题！ 2.同步加载和异步加载接口

    private ImageView mImageView1;
    private ImageView mImageView2;
    private ImageView mImageView3;
    private Button mButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        XImageLoader.verifyStoragePermissions(this);

        mImageView1 = (ImageView) findViewById(R.id.image1);
        mImageView2 = (ImageView) findViewById(R.id.image2);
        mImageView3 = (ImageView) findViewById(R.id.image3);
        mButton = (Button) findViewById(R.id.button);

        // Default
        XImageLoader.build(this).imageview(mImageView1).asyncLoad("http://artimg.chefafa.com/upload/Image/20161027/1477530825513283.jpg");
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Use memorycache, without diskcache
                XImageLoader.build(DemoFirstActivity.this).imageview(true, false, mImageView2).load("http://tupian.enterdesk.com/2013/lxy/09/28/2/8.jpg");
            }
        }).start();
        // Use doublecache
        XImageLoader.build(this).imageview(true, mImageView3).asyncLoad("http://artimg.chefafa.com/upload/Image/20161027/1477530837865270.jpg");

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DemoFirstActivity.this, DemoSecondActivity.class);
                startActivity(intent);
            }
        });
    }

}
