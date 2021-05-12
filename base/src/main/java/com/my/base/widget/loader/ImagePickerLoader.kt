package com.my.base.widget.loader

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.csz.pick.core.manager.ImageLoader
import com.my.base.util.ContextUtils
import com.my.base.util.ImageLoaderUtil

/**
 * @author caishuzhan
 */
object ImagePickerLoader : ImageLoader {
    override fun loadImage(imageView: ImageView?, imagePath: String?) {
        ImageLoaderUtil.loadImgCenterCrop(imageView, imagePath)
    }

    override fun loadPreImage(imageView: ImageView?, imagePath: String?) {
        ImageLoaderUtil.loadImgCenterCrop(imageView, imagePath)
    }

    override fun clearMemoryCache() {
        Glide.get(ContextUtils.getContext()).clearMemory()
    }

}