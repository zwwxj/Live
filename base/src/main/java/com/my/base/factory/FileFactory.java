package com.my.base.factory;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import androidx.recyclerview.widget.RecyclerView;

import com.my.base.manager.FileStorageManager;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

/**
 * @author caishuzhan
 * <p>
 * 文件工厂，为所有下载，视频保存，图片保存提供路径出口，避免造成临时文件（夹）的碎片化
 * </p>
 */
public class FileFactory {

    /**
     * 返回网络缓存目录
     *
     * @return
     */
    @NotNull
    public static File getHttpCacheDir(Context context){
        File parent = FileStorageManager.getCacheParentDir(context);
        File videoDir = new File(parent, "http_cache");
        if (!videoDir.exists()) {
            videoDir.mkdirs();
        }
        return videoDir;
    }

    /**
     * 返回视频文件的下载保存目录
     *
     * @return
     */
    @NotNull
    public static File getVideoDownloadDir(Context context) {
        File parent = FileStorageManager.getCacheParentDir(context);
        File videoDir = new File(parent, "video_download");
        if (!videoDir.exists()) {
            videoDir.mkdirs();
        }
        return videoDir;
    }

    /**
     * 返回视频文件的添加水印后的保存目录
     *
     * @return
     */
    @NotNull
    public static File getVideoWatermarkdDir(Context context) {
        File parent = FileStorageManager.getCacheParentDir(context);
        File videoDir = new File(parent, "video_watermark");
        if (!videoDir.exists()) {
            videoDir.mkdirs();
        }
        return videoDir;
    }

    /**
     * 返回普通的下载目录
     */
    @NotNull
    public static File getDownloadDir(Context context){
        File parent = FileStorageManager.getCacheParentDir(context);
        File videoDir = new File(parent, "download");
        if (!videoDir.exists()) {
            videoDir.mkdirs();
        }
        return videoDir;
    }

    /**
     * 返回图片文件的保存目录
     *
     * @return
     */
    @NotNull
    public static File getPictureDir(Context context) {
        File parent = FileStorageManager.getCacheParentDir(context);
        File videoDir = new File(parent, Environment.DIRECTORY_PICTURES);
        if (!videoDir.exists()) {
            videoDir.mkdirs();
        }
        return videoDir;
    }

    /**
     * 返回视频文件的路径,父目录为{@link #getVideoDownloadDir(Context)}
     * @param context
     * @param url
     * @param create true:生成文件   false:不生成
     * @return
     */
    @NotNull
    public static File getVideoDownloadPath(Context context, String url, boolean create) {
        String name = Uri.parse(url).getLastPathSegment();
        if (TextUtils.isEmpty(name)){
            name = url;
        }
        File dir = getVideoDownloadDir(context);
        File file  = new File(dir,name);
        if (create && !file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 返回视频文件的路径,父目录为{@link #getVideoWatermarkdDir(Context)}
     * @param context
     * @param url
     * @param create true:生成文件   false:不生成
     * @return
     */
    @NotNull
    public static File getVideoWatermarkPath(Context context, String url, boolean create) {
        String name = Uri.parse(url).getLastPathSegment();
        if (TextUtils.isEmpty(name)){
            name = url;
        }
        File dir = getVideoWatermarkdDir(context);
        File file  = new File(dir,name);
        if (create && !file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    @NotNull
    public static File getDownloadPath(Context context, String url, boolean create){
        String name = Uri.parse(url).getLastPathSegment();
        if (TextUtils.isEmpty(name)){
            name = url;
        }
        File dir = getDownloadDir(context);
        File file  = new File(dir,name);
        if (create && !file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 返回图片文件
     *
     * @return
     */
    @NotNull
    public static File getPicturePath(Context context, String url, boolean create) {
        String name = Uri.parse(url).getLastPathSegment();
        if (TextUtils.isEmpty(name)){
            name = url;
        }
        File dir = getPictureDir(context);
        File file  = new File(dir,name);
        if (create && !file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
