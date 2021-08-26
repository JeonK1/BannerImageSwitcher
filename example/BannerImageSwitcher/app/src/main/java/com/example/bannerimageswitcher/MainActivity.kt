package com.example.bannerimageswitcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // image list with R.drawable directories
        val image_list = arrayListOf(R.drawable.first_image, R.drawable.second_image, R.drawable.third_image)

        // image list with URI String (used with Glide)
        val image_list2 = arrayListOf("https://dimg.donga.com/wps/NEWS/IMAGE/2019/10/13/97852357.2.jpg",
                                    "https://i.ytimg.com/vi/jyBbnSZFLh8/hqdefault.jpg",
                                    "https://lh3.googleusercontent.com/B6ZADk6grixEg3e8k-NpMwRI9-isdEwDnYbzcJhCbWQVhml20beao8NMe215Cs0Ils4zo_7rNP3aNUEBtR5BmHcRJA=w640-h400-e365-rj-sc0x00ffffff")

        banner_image_view.set_image_list(image_list)
    }
}