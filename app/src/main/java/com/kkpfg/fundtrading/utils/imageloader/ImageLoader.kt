package com.kkpfg.fundtrading.utils.imageloader

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kkpfg.fundtrading.utils.config.AppConfig

class ImageLoader {
    companion object{
        fun loadImageAsCircle(context: Context,imageView: ImageView, url: String){
            Glide.with(context)
                .load("${AppConfig.baseUrl}/${url}")
                .apply(RequestOptions.circleCropTransform())
                .into( imageView)
        }

        fun loadImage(context: Context,imageView: ImageView, url: String){
            Glide.with(context)
                .load("${AppConfig.baseUrl}/${url}")
                .into( imageView)
        }
    }
}