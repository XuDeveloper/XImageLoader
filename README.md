# XImageLoader

[中文版](https://github.com/XuDeveloper/XImageLoader/blob/master/docs/README-ZH.md)

It's a custom image-loading repository for Android.

You can use XImageLoader to load images from Internet or local files.By default it uses a HTTPUrlConnection to download images but you can also use OkhttpImageLoader instead or customize your own imageloader because it provides a interface.

Notice:This is a repository for people who want to learn more knowledge about the image loading and caching.It is not recommended for use in actual projects!

If you want to improve it, please fork it and pull requests to me!

If you like it, please star it or follow me!Thank you!

### Integration

#### Android Studio

```xml

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

> Maybe you can copy my code to your project!

### Usage

Default usage:

``` java

	// Asynchronous call
    XImageLoader.build(context).imageview(ImageView).load(imageUrl);
	// load local file，you need to use the format like "file:///address"
	XImageLoader.build(context).imageview(ImageView).load("file:///address");

```

or

```java

	// Synchronous call(should use it in a new thread)
	Bitmap bitmap = XImageLoader.build(context).imageview(ImageView).getBitmap(imageUrl);

```

You can choose whether to use cache or not, or customize your own config(using XImageLoaderConfig):

```java

	XImageLoader.build(context).imageview(isMemoryCache, isDiskCache, ImageView).load(imageUrl);

	XImageLoader.build(context).imageview(isDoubleCache, ImageView).load(imageUrl);
	
	// config settings
    XImageLoaderConfig config = new XImageLoaderConfig();
    config.setCache(new DoubleCache(context));
    config.setLoader(new OkhttpImageLoader());
    config.setLoadingResId(R.drawable.image_loading);
    config.setFailResId(R.drawable.image_fail);
	XImageLoader.build(context).imageview(config, ImageView).load(imageUrl);

```

You need to set the permissions in AndroidManifest.xml:

```xml

	<uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

```

If you use a Android 6.0 device or more, you need to set permissions dynamically:

```java

	XImageLoader.verifyStoragePermissions(activity);

```


##**License**

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