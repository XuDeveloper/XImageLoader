package com.example.demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.xu.ximageloader.core.XImageLoader;

public class DemoFirstActivity extends AppCompatActivity {

    private ImageView mImageView1;
    private ImageView mImageView2;
    private ImageView mImageView3;
    private Button mButton;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            XImageLoader.verifyStoragePermissions(this);
        }

        mImageView1 = (ImageView) findViewById(R.id.image1);
        mImageView2 = (ImageView) findViewById(R.id.image2);
        mImageView3 = (ImageView) findViewById(R.id.image3);
        mButton = (Button) findViewById(R.id.button);

        // 1.Default
        XImageLoader.build(this).imageview(mImageView1).load("http://artimg.chefafa.com/upload/Image/20161027/1477530825513283.jpg");

        // 2.Use memorycache, without diskcache
        new Thread(new Runnable() {
            @Override
            public void run() {
                mBitmap = XImageLoader.build(DemoFirstActivity.this).imageview(true, false, mImageView2).getBitmap("http://img4.imgtn.bdimg.com/it/u=736732793,2989478129&fm=11&gp=0.jpg");
            }
        }).start();
        mImageView2.setImageBitmap(mBitmap);

        // 3.Use doublecache
        XImageLoader.build(this).imageview(true, mImageView3).load("http://artimg.chefafa.com/upload/Image/20161027/1477530837865270.jpg");

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DemoFirstActivity.this, DemoSecondActivity.class);
                startActivity(intent);
            }
        });
    }

}
