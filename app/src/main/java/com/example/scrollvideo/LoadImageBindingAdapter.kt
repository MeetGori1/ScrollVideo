package com.example.scrollvideo

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions


class LoadImageBindingAdapter {
    companion object{
        @JvmStatic
        @BindingAdapter(value = ["thumbnail", "error"], requireAll = false)
        fun loadImage(view: ImageView, profileImage: String?, error: Int) {
            if (!profileImage.isNullOrEmpty()) {
                Glide.with(view.context)
                    .setDefaultRequestOptions(
                        RequestOptions()
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_background))
                    .load(profileImage)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(view)
            }
        }

        @JvmStatic
        @BindingAdapter("android:visibility")
        fun setVisibility(view: View, value: Int) {
            view.visibility = if (value==3) View.VISIBLE else View.GONE
        }
    }
}