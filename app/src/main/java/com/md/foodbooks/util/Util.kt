package com.md.foodbooks.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.md.foodbooks.R


fun ImageView.imageDownload(url : String?, placeholder: CircularProgressDrawable){
    val options = RequestOptions().placeholder(placeholder).error(R.mipmap.ic_launcher_round)
    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
}

fun makePlaceHolderImage(context: Context) : CircularProgressDrawable {
    return CircularProgressDrawable(context).apply{
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}