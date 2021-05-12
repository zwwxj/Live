package com.zs.base_library.common

import android.app.Activity
import android.content.res.Resources
import android.graphics.Bitmap
import android.widget.Toast
import com.csz.permission.core.PermissionHelper
import com.csz.permission.core.ResultCallback
import com.csz.pick.core.ImagePicker
import com.my.base.factory.FileFactory
import com.my.base.util.ContextUtils
import com.my.base.widget.loader.ImagePickerLoader
import com.nanchen.compresshelper.CompressHelper
import java.io.File
import java.text.DecimalFormat
import java.util.*


/**
 * @author caishuzhan
 */

const val REQUEST_SELECT_IMAGES_CODE = 0x10

/**
 * 像素转换
 */
val Number.dp2px get() = (toFloat() * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
val Number.px2dp get() = (toFloat() / Resources.getSystem().displayMetrics.density + 0.5f).toInt()
val Number.px2sp get() = (toFloat() / Resources.getSystem().displayMetrics.scaledDensity + 0.5f).toInt()
val Number.sp2px get() = (toFloat() * Resources.getSystem().displayMetrics.scaledDensity + 0.5f).toInt()


/**
 * 将毫秒转换为分秒-00:00格式
 */
fun stringForTime(timeMs: Int): String {
    val list = mutableListOf<Int>(10, 20)
    list.isNullOrEmpty()

    val totalSeconds = timeMs / 1000
    val seconds = totalSeconds % 60
    val minutes = (totalSeconds / 60) % 60

    return Formatter().format("%02d:%02d", minutes, seconds).toString();
}

/**
 * 根据字节数转文件大小
 */
fun Long.getReadableFileSize(): String {
    if (this <= 0) {
        return "0"
    }
    val units = arrayOf("B", "KB", "MB", "GB", "TB")
    val digitGroups = (Math.log10(this.toDouble()) / Math.log10(1024.0)).toInt()
    return DecimalFormat("#,##0.#").format(
        this / Math.pow(
            1024.0,
            digitGroups.toDouble()
        )
    ) + " " + units[digitGroups]
}

/**
 * 图片压缩，耗时任务
 */
fun File.compressToFile(
    src: File,
    maxWidth: Float = 720f,
    maxHeight: Float = 720f,
    quality: Int = 100,
    format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
    fileName: String = System.currentTimeMillis().toString()
): File =
    CompressHelper.Builder(ContextUtils.getContext())
        .setMaxWidth(maxWidth)
        .setMaxHeight(maxHeight)
        .setQuality(quality)
        .setCompressFormat(format)
        .setFileName(fileName)
        .setDestinationDirectoryPath(FileFactory.getPictureDir(ContextUtils.getContext()).absolutePath)
        .build()
        .compressToFile(src)

/**
 * 图片压缩，耗时任务
 */
fun File.compressToBitmap(
    src: File,
    maxWidth: Float = 720f,
    maxHeight: Float = 720f,
    quality: Int = 100,
    format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
    fileName: String = System.currentTimeMillis().toString()
): Bitmap =
    CompressHelper.Builder(ContextUtils.getContext())
        .setMaxWidth(maxWidth)
        .setMaxHeight(maxHeight)
        .setQuality(quality)
        .setCompressFormat(format)
        .setFileName(fileName)
        .setDestinationDirectoryPath(FileFactory.getPictureDir(ContextUtils.getContext()).absolutePath)
        .build()
        .compressToBitmap(src)

/**
 * 开启图片选择
 */
fun Activity.startImagePicker(
    act: Activity, title: String = "", showCamera: Boolean = true,
    showImage: Boolean = true, showVideo: Boolean = true,
    filterGif: Boolean = false, maxCount: Int = 9, singleType: Boolean = true
) {
    ImagePicker.getInstance()
        .setTitle(title) //设置标题
        .showCamera(showCamera) //设置是否显示拍照按钮
        .showImage(showImage) //设置是否展示图片
        .showVideo(showVideo) //设置是否展示视频
        .filterGif(filterGif) //设置是否过滤gif图片
        .setMaxCount(maxCount) //设置最大选择图片数目(默认为1，单选)
        .setSingleType(singleType) //设置图片视频不能同时选择
        //.setImagePaths(mImagePaths) //设置历史选择记录
        .setImageLoader(ImagePickerLoader) //设置自定义图片加载器
        .start(act, REQUEST_SELECT_IMAGES_CODE)
}

/**
 * 权限请求
 */
fun Activity.requestPermissionsInner(callback: ResultCallback, vararg permissions: String) {
    PermissionHelper.simpleRequest(
        this, object : ResultCallback() {
            override fun onFailure(ungrantPermissions: Array<String?>?) {
                callback.onFailure(ungrantPermissions)
            }

            override fun onSuccess() {
                callback.onSuccess()
            }
        }, *permissions
    )
}









