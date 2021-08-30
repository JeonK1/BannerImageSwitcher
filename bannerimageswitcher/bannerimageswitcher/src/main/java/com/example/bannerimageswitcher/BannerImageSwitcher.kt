package com.example.bannerimageswitcher

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.ImageSwitcher
import android.widget.ImageView
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.lang.IllegalArgumentException
import java.util.*

class BannerImageSwitcher(context: Context, attrs: AttributeSet): RelativeLayout(context, attrs) {

    private lateinit var imageList:List<Any> // image list

    companion object {
        var CUSTOM_NAMESPACE = "" // current project namespace (for get custom xml attribute)
        var IMAGE_SWITCH_INTERVAL = 5000L // image switch interval, default : 5초
        var page = 0 // current page
        lateinit var timer:Timer

        lateinit var banner_image:ImageSwitcher
        lateinit var left_button:ImageButton
        lateinit var right_button:ImageButton
    }


    init {
        // custom namespace init
        CUSTOM_NAMESPACE = "http://schemas.android.com/apk/res/${context.packageName}"

        // init widget
        inflate(context, R.layout.custom_banner_layout, this) // xml init
        banner_image = findViewById(R.id.banner_image)
        left_button = findViewById(R.id.left_button)
        right_button = findViewById(R.id.right_button)

        // left switch button clicked
        left_button.setOnClickListener {
            switchPrevPage() // switch prev image
            timerReset() // timer reset
        }

        // right switch button clicked
        right_button.setOnClickListener {
            switchNextPage()  // switch next image
            timerReset() // timer reset
        }

        // timer init
        timer = getTimer()
        banner_image.setFactory {
            ImageView(context).apply {
                adjustViewBounds = true
            }
        }
    }

    fun setLeftButtomImageResource(resId: Int){
        // set Left Button Image Resource
        left_button.setImageResource(resId)
    }

    fun setRightButtomImageResource(resId: Int){
        // set Right Button Image Resource
        right_button.setImageResource(resId)
    }

    fun setInterval(msec:Long){
        // set IMAGE_SWITCH_INTERVAL value
        IMAGE_SWITCH_INTERVAL = msec
        timerReset() // timer reset
    }

    fun setImageList(list:List<Any>){
        // set image list
        if(list.isNotEmpty()) {
            // if image_list is not empty
            when (list[0].javaClass.simpleName) {
                "Integer", "String" -> {
                    imageList = list
                    setBannerByPage(0) // banner 를 0번째 page 로 초기화하기
                }
                else -> throw IllegalArgumentException("list type is illegal")
            }
        } else {
            // if image_list is empty
            imageList = list
        }
    }

    private fun setBannerByPage(page:Int) {
        // move to valid switcher image function by image_list element type
        if(imageList.isNotEmpty()){
            when(imageList[0].javaClass.simpleName){
                "String" -> setBannerByPageGlide(page)
                "Integer" -> setBannerByPageNative(page)
                else -> throw IllegalArgumentException("list type is illegal")
            }
        } else if(page<0 || page>=imageList.size) {
            throw IllegalArgumentException("page value is illegal")
        }
    }

    private fun setBannerByPageNative(page:Int){
        // switch banner image by page (with not use glide)
        banner_image.setImageResource(imageList[page] as Int) // imageview 에 image_list[page] 대입
    }

    private fun setBannerByPageGlide(page:Int){
        // switch banner image by page (with glide)
        Glide.with(context)
            .load(imageList[page])
            .into(object: CustomTarget<Drawable>(){
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    banner_image.setImageDrawable(resource) // imageview 에 image_list[page] 대입
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }
            })
    }

    private fun switchNextPage(){
        // banner image switch to next page
        if(++page > imageList.size-1)
            page = 0
        banner_image.inAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_in_left) // image 전환 애니메이션
        banner_image.outAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_out_right)  // image 전환 애니메이션
        setBannerByPage(page)
    }

    private fun switchPrevPage() {
        // banner image switch to prev page
        if(--page < 0)
            page = imageList.size-1
        banner_image.inAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right) // image 전환 애니메이션
        banner_image.outAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_out_left)  // image 전환 애니메이션
        setBannerByPage(page)
    }

    private fun getTimer(): Timer {
        // get instance that call banner_move_next() when every IMAGE_SWITCH_INTERVAL millisecond
        val myHandler = Handler(Looper.getMainLooper()) {
            switchNextPage()
            true
        }

        return Timer().apply {
            schedule(object: TimerTask(){
                override fun run() {
                    myHandler.sendMessage(myHandler.obtainMessage())
                }
            }, IMAGE_SWITCH_INTERVAL, IMAGE_SWITCH_INTERVAL)
        }
    }

    private fun timerReset(){
        // timer cancel, init with new instance
        timer.cancel()
        timer = getTimer()
    }
}
