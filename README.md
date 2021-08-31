# BannerImageSwitcher
This is image banner library for easy to set image list and get functions that switch image to next, prev page and automatically switch next page. <br>
- minSdkVersion : BannerImageSwitcher requires a minimum SDK API level of 21. <br>
- Compile Android SDK : BannerImageSwitcher requires a compile SDK API level of 26 or later. <br><br>

Notice: This Library is not zero dependency, it has Glide dependency for can set image list by url string list.<br>

<br>

## Quick Start

### Setup

1\. Add it in your build.gradle(Project) at the end of repositories <br>
~~~
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
~~~

<br>

2\. Add the dependency in your build.gradle(app) <br>
~~~
	dependencies {
		implementation 'com.github.User:Repo:Tag'
	}
~~~

<br><br>

### How to use 
1\. you can define BannerImageSwitcher Widget in your layout xml files.
~~~
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <com.example.bannerimageswitcher.BannerImageSwitcher
        android:id="@+id/banner_image_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentTop="true"/>

</RelativeLayout>
~~~

<br>

2-1. you can set imageList by R.drawable.~~~ with .setImageList(resIds: List<Int>) function in your activity or fragments
~~~
        // image list with R.drawable directories
        val imageList = arrayListOf(
            R.drawable.first_image,
            R.drawable.second_image,
            R.drawable.third_image
        )

        banner_image_view.setImageList(imageList)
~~~

<br>

2-2. you can also set imageList by URI strings with .setImageList(urls: List<String>) function in your activity or fragments
~~~
        // image list with R.drawable directories
        val imageList = arrayListOf(
            "https://dimg.donga.com/wps/NEWS/IMAGE/2019/10/13/97852357.2.jpg",
            "https://i.ytimg.com/vi/jyBbnSZFLh8/hqdefault.jpg"
        )
        
        banner_image_view.setImageList(imageList)
~~~

<br>

3\. you can set image switch interval with .setInterval(msec: Long) function in your activity or fragments
~~~
        val banner_image_view = findViewById<BannerImageSwitcher>(R.id.banner_image_view);
        banner_image_view.setInterval(1000L)
~~~

<br>

4\. you can set left/right button image with .setLeftButtomImageResource(resId: Int), .setRightButtomImageResource(resId: Int) function in your activity or fragments
~~~
        val banner_image_view = findViewById<BannerImageSwitcher>(R.id.banner_image_view);
        banner_image_view.setLeftButtomImageResource(R.drawable.ic_arrow_left)
        banner_image_view.setRightButtomImageResource(R.drawable.ic_arrow_right)
~~~

<br><br>
  
## Example
### switch to next image automatically every 10sec
<img src="/example/readme_images/banner1.gif" width=600 />
<br><br>

### switch to next image and prev image with button click
<img src="/example/readme_images/banner2.gif" width=600 />
<br><br>

### recount switch timer(set every 10sec) if click prev or next button
<img src="/example/readme_images/banner3.gif" width=600 />
<br><br>


<br><br>
## Development Note (Language: Korean)
- <a href="https://blog.naver.com/ponson1017/222475344604"> Custom View </a><br>
- <a href="https://blog.naver.com/ponson1017/222483637857"> Updating Banner Switcher </a><br>
- <a href="https://blog.naver.com/ponson1017/222488019002"> create library </a><br>