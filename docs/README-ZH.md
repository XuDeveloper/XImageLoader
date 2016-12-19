# XImageLoader

[English Version](https://github.com/XuDeveloper/XImageLoader/blob/master/README.md)

这是一个Android的自定义图片加载库。

你可以使用XImageLoader去加载网络上的或者本地的图片。它默认使用HttpUrlConnection去下载网络图片，你也可以使用库中已实现的OkhttpImageLoader或者自定义你的ImageLoader因为这个库提供了接口。

注意：这是一个用于学习图片加载与缓存的库，不推荐使用在实际项目之中！

如果你想改进这个库，欢迎fork这个项目然后pull request！

如果你喜欢这个库，请给它一个star或者关注我！谢谢！
 
### 导入

#### Android Studio

``` xml
  allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
  }

  dependencies {
	    compile 'com.github.XuDeveloper:XImageLoader:v1.0'
  }

```
#### Eclipse

> 可以复制我的源码到你的项目中！

### 使用

默认用法：

``` java

	// 异步接口调用
    XImageLoader.build(context).imageview(ImageView).load(imageUrl);
	// 加载本地文件，你需要使用这样的格式："file:///address"
	XImageLoader.build(context).imageview(ImageView).load("file:///address");

```

或者：

```java

	// 同步接口调用(需要运行在一条新线程中)
	Bitmap bitmap = XImageLoader.build(context).imageview(ImageView).getBitmap(imageUrl);

```

你可以选择是否缓存或者自定义（使用XImageLoaderConfig）：


```java

	XImageLoader.build(context).imageview(isMemoryCache, isDiskCache, ImageView).load(imageUrl);

	XImageLoader.build(context).imageview(isDoubleCache, ImageView).load(imageUrl);
	
	// 具体配置
    XImageLoaderConfig config = new XImageLoaderConfig();
    config.setCache(new DoubleCache(context));
    config.setLoader(new OkhttpImageLoader());
    config.setLoadingResId(R.drawable.image_loading);
    config.setFailResId(R.drawable.image_fail);
	XImageLoader.build(context).imageview(config, ImageView).load(imageUrl);

```

你需要AndroidManifest.xml中设置权限:

```xml

	<uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

```

如果你使用的是Android 6.0以上的设备，你需要动态设置权限：

```java

	XImageLoader.verifyStoragePermissions(activity);

```


##**协议**

```license
Copyright [2016] XuDeveloper

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

