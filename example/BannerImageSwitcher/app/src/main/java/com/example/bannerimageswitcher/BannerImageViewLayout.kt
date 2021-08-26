package com.example.bannerimageswitcher

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.custom_banner_layout.view.*
import java.lang.IllegalArgumentException
import java.util.*

class BannerImageViewLayout(context: Context, attrs: AttributeSet): RelativeLayout(context, attrs) {

    lateinit var image_list:List<Any>

    companion object{
        val TAG = "BannerImageViewLayout"
        var CUSTOM_NAMESPACE = ""
        var IMAGE_SWITCH_INTERVAL = 5000L // 이미지 스위치되는 시간간격, default : 5초
        var page = 0
    }

    init {
        // xml 불러와서 화면 초기화
        inflate(context, R.layout.custom_banner_layout, this)

        // custom namespace 초기화
        CUSTOM_NAMESPACE = "http://schemas.android.com/apk/res/${context.packageName}"

        // interval 값 초기화
        attrs.getAttributeValue(CUSTOM_NAMESPACE, "switch_interval")?.let{
            IMAGE_SWITCH_INTERVAL = it.toLong()
        }

        // 타이머 초기화
        var timer = getTimer()
        banner_image.setFactory {
            ImageView(context).apply {
                adjustViewBounds = true
            }
        }

        // 왼쪽 버튼 클릭
        left_button.setOnClickListener {
            banner_move_prev() // 이전 페이지 이동

            // 타이머 취소 및 초기화
            timer.cancel()
            timer = getTimer()
        }

        // 오른쪽 버튼 클릭
        right_button.setOnClickListener {
            banner_move_next()  // 이전 페이지 이동

            // 타이머 취소 및 초기화
            timer.cancel()
            timer = getTimer()
        }
    }

    fun set_image_list(list:List<Any>){
        if(list.isNotEmpty()) {
            // if image_list is not empty
            when (list[0].javaClass.simpleName) {
                "Integer", "String" -> {
                    image_list = list
                    set_banner_by_page(0) // banner 를 0번째 page 로 초기화하기
                }
                else -> throw IllegalArgumentException("list type is illegal")
            }
        } else {
            // if image_list is empty
            image_list = list
        }
    }

    private fun set_banner_by_page(page:Int){
        // banner 를 특정 page로 넘겨주기
        Glide.with(context)
            .load(image_list[page])
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

    private fun banner_move_next(){
        // banner 를 다음으로 넘겨주기
        if(++page > image_list.size-1)
            page = 0
        banner_image.inAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_in_left) // image 전환 애니메이션
        banner_image.outAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_out_right)  // image 전환 애니메이션
        set_banner_by_page(page)
    }

    private fun banner_move_prev() {
        // banner 를 이전으로 넘겨주기
        if(--page < 0)
            page = image_list.size-1
        banner_image.inAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right) // image 전환 애니메이션
        banner_image.outAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_out_left)  // image 전환 애니메이션
        set_banner_by_page(page)
    }

    private fun getTimer(): Timer {
        // IMAGE_SWITCH_INTERVAL 마다 banner_move_next() 를 수행하는 Timer 객체 반환
        val myHandler = Handler(Looper.getMainLooper()) {
            banner_move_next()
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
}
