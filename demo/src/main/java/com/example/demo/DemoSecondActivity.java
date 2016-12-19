package com.example.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.xu.ximageloader.cache.DoubleCache;
import com.xu.ximageloader.core.XImageLoader;
import com.xu.ximageloader.config.XImageLoaderConfig;

import java.util.ArrayList;

/**
 * Created by Xu on 2016/12/17.
 */

public class DemoSecondActivity extends AppCompatActivity implements AbsListView.OnScrollListener {

    private ArrayList<String> urls;
    private boolean mIsGridViewIdle;
    private GridView mGridView;
    private BaseAdapter mImageAdapter;
    private XImageLoaderConfig config;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initDatas();
        initViews();

        // config settings
        config = new XImageLoaderConfig();
        config.setCache(new DoubleCache(DemoSecondActivity.this));
        config.setLoadingResId(R.drawable.image_loading);
        config.setFailResId(R.drawable.image_fail);
    }

    private void initDatas() {
        mIsGridViewIdle = true;
        urls = new ArrayList<>();
        String[] imageUrls = {
                "http://image.bitauto.com/dealer/news/100041590/e6d8c821-176f-475c-8f32-23b65a66aa88.jpg",
                "http://img.jrjimg.cn/2014/10/20141028000128871.jpg",
                "http://n.sinaimg.cn/transform/20150116/x0aw-cczmvun5069864.jpg",
                "http://cms-bucket.nosdn.127.net/catchpic/9/93/935faaac49645138c6788301701f8ad1.jpg",
                "http://himg2.huanqiu.com/attachment2010/2013/1212/20131212021006631.jpg",
                "http://car.southcn.com/7/images/attachement/jpg/site4/20161104/c03fd5725abe19869a6c2d.jpg",
                "http://i2.dd-img.com/upload/2016/1107/1478495859583.jpg",
                "http://i.ce.cn/auto/auto/gundong/201611/08/W020161108584391202911.jpg",
                "http://img1.imgtn.bdimg.com/it/u=225246995,3631782845&fm=214&gp=0.jpg",
                "http://dingyue.nosdn.127.net/3OBv1SEb8G7cfex8z0waJhutyxOAzpKTgo4VVtJ5Wkitj1479286761337.jpg",
                "http://img.mp.itc.cn/upload/20161213/57de565f1f034d2ba1da6e6a5980086f_th.jpg",
                "http://news.xinhuanet.com/sports/titlepic/128082298_1438396856343_title1n.jpg",
                "http://photocdn.sohu.com/20120613/Img345493678.jpg",
                "http://www.xnwbw.com/res/1/1/2016-02/21/A16/res01_attpic_brief.jpg",
                "http://img4.imgtn.bdimg.com/it/u=4063972650,2938693754&fm=11&gp=0.jpg"};
        for (String url : imageUrls) {
            urls.add(url);
        }
    }

    private void initViews() {
        mGridView = (GridView) findViewById(R.id.gridView);
        mImageAdapter = new ImageAdapter(this, urls);
        mGridView.setAdapter(mImageAdapter);
        mGridView.setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            mIsGridViewIdle = true;
            mImageAdapter.notifyDataSetChanged();
        } else {
            mIsGridViewIdle = false;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    private class ImageAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private ArrayList<String> mUrls;

        private ImageAdapter(Context context, ArrayList<String> urls) {
            mInflater = LayoutInflater.from(context);
            this.mUrls = urls;
        }

        @Override
        public int getCount() {
            return mUrls.size();
        }

        @Override
        public String getItem(int position) {
            return mUrls.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.image_list_item,
                        parent, false);
                holder = new ViewHolder();
                holder.imageView = (ImageView) convertView
                        .findViewById(R.id.image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ImageView imageView = holder.imageView;
            String url = getItem(position);
            if (mIsGridViewIdle) {
                XImageLoader.build(DemoSecondActivity.this).imageview(config, imageView).load(url);
            }
            return convertView;
        }

    }

    class ViewHolder {
        ImageView imageView;
    }
}
