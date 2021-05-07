package com.my.base.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.transition.Transition;
import com.my.base.widget.transform.CenterBlurTransformation;
import com.my.base.widget.transform.GlideCircleTransform;
import com.my.base.widget.transform.GlideRoundTransform;
import com.zs.base_library.utils.CloseUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * @author caishuzhan
 * loader
 */
public class ImageLoaderUtil {

    public static void init(Context context) {
        GlideBuilder glideBuilder = new GlideBuilder();
        glideBuilder.setDefaultRequestOptions(new RequestOptions().override(400).fitCenter());
        Glide.init(context, glideBuilder);
    }

    public static void loadImg(ImageView v, String url) {
        if (ContextChecker.check(v.getContext())) {
            Glide.with(v.getContext()).load(url).apply(new RequestOptions().fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL)).into(v);
        }
    }

    public static void loadImgNoCache(ImageView v, String url) {
        if (ContextChecker.check(v.getContext())) {
            Glide.with(v.getContext()).load(url).apply(new RequestOptions().fitCenter().diskCacheStrategy(DiskCacheStrategy.NONE)).into(v);
        }
    }

    public static void loadImgCenterCrop(ImageView v, String url) {
        if (ContextChecker.check(v.getContext())) {
            Glide.with(v.getContext()).load(url).apply(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)).into(v);
        }
    }

    public static void loadImg(ImageView v, String url, int placeholder) {
        if (ContextChecker.check(v.getContext())) {
            Glide.with(v.getContext()).load(url).apply(new RequestOptions().placeholder(placeholder).error(placeholder).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL)).into(v);
        }
    }

    public static void loadConnerImg(ImageView v, String url, int roundCorner) {
        if (ContextChecker.check(v.getContext())) {
            Glide.with(v.getContext()).load(url).apply(new RequestOptions().circleCrop().transform(new RoundedCorners(roundCorner)).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL)).into(v);
        }
    }

    public static void loadRoundImg(ImageView v, String url, int placeholder, int roundCorner) {

        RequestOptions options = new RequestOptions().transform(new CenterCrop(), new RoundedCorners(roundCorner)).placeholder(placeholder);
        Glide.with(v.getContext()).applyDefaultRequestOptions(options).load(url).into(v);
    }

    /**
     * 高斯模糊加渐入渐出
     */
    public static void loadBlurTrans(ImageView v, Uri uri, int radius) {
        if (ContextChecker.check(v.getContext())) {
            Glide.with(v)
                    .load(uri)
                    .thumbnail(0.1f).skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .apply(RequestOptions.bitmapTransform(new CenterBlurTransformation(radius, 8, v.getContext())))
                    .transition(withCrossFade(400))
                    .into(v);
        }
    }

    /**
     * 自定义四周的圆角
     *
     * @param v
     * @param url
     */
    public static void loadGranularRoundImg(ImageView v, String url) {
        if (ContextChecker.check(v.getContext())) {
            loadGranularRoundImg(v, 0, url);
        }
    }

    public static void loadGranularRoundImg(ImageView v, int error, String url) {
        if (ContextChecker.check(v.getContext())) {
            loadGranularRoundImg(v, error, url, 30f, 30f, 0f, 0f);
        }
    }

    public static void loadGranularRoundImg(ImageView v, int error, String url, float topLeft, float topRight, float bottomRight, float bottomLeft) {
        if (!ContextChecker.check(v.getContext())) {
            return;
        }
        Glide.with(v.getContext()).load(url).transform(new BitmapTransformation() {

            @Override
            public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

            }

            @Override
            protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
                Bitmap bitmap = TransformationUtils.centerCrop(pool, toTransform, outWidth, outHeight);

                return TransformationUtils.roundedCorners(pool, bitmap, topLeft, topRight, bottomRight, bottomLeft);
            }
        }).diskCacheStrategy(DiskCacheStrategy.ALL).into(v);
    }


    /**
     * Glide同时使用RoundedCorner和CenterCrop，在图片宽高与ImageView不一致对情况下
     *
     * @param v
     * @param url
     * @param roundingRadius
     */
    public static void loadRoundedCornerCenterCrop(ImageView v, String url, int roundingRadius) {
        if (ContextChecker.check(v.getContext())) {
            loadRoundedCornerCenterCrop(v, url, 0, roundingRadius);
        }
    }

    public static void loadRoundedCornerCenterCrop(ImageView v, String url, int error, int roundingRadius) {
        if (!ContextChecker.check(v.getContext())) {
            return;
        }
        RequestBuilder<Drawable> drawableRequestBuilder = Glide.with(v.getContext()).load(url).transform(new BitmapTransformation() {

            @Override
            public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

            }

            @Override
            protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
                Bitmap bitmap = TransformationUtils.centerCrop(pool, toTransform, outWidth, outHeight);
                return TransformationUtils.roundedCorners(pool, bitmap, roundingRadius);
            }
        }).diskCacheStrategy(DiskCacheStrategy.ALL);
        if (error > 0) {
            drawableRequestBuilder.error(error);
        }
        drawableRequestBuilder.into(v);
    }

    public static void loadRoundedCornerCenterCrop(ImageView v, String url, int holder, int error, int roundingRadius) {
        if (!ContextChecker.check(v.getContext())) {
            return;
        }
        RequestBuilder<Drawable> drawableRequestBuilder = Glide.with(v.getContext()).load(url).transform(new BitmapTransformation() {

            @Override
            public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

            }

            @Override
            protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
                Bitmap bitmap = TransformationUtils.centerCrop(pool, toTransform, outWidth, outHeight);
                return TransformationUtils.roundedCorners(pool, bitmap, roundingRadius);
            }
        }).diskCacheStrategy(DiskCacheStrategy.ALL);
        if (error > 0) {
            drawableRequestBuilder.error(error);
        }
        if (holder > 0) {
            drawableRequestBuilder.placeholder(holder);
        }
        drawableRequestBuilder.into(v);
    }

    public static void loadRoundImgWithBorder(ImageView v, String url, int borderWidth, int borderColor) {
        if (!ContextChecker.check(v.getContext())) {
            return;
        }
        GlideCircleTransform glideCircleTransform = new GlideCircleTransform(borderWidth, borderColor);
        Glide.with(v.getContext()).load(url).
                transform(new CenterCrop(), glideCircleTransform).diskCacheStrategy(DiskCacheStrategy.ALL).
                into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        v.setImageDrawable(resource);
                    }
                });
    }

    public static void loadRoundImg(ImageView v, String url, int size) {
        if (!ContextChecker.check(v.getContext())) {
            return;
        }
        GlideRoundTransform glideRoundTransform = new GlideRoundTransform(size);
        Glide.with(v.getContext()).load(url).
                transform(new CenterCrop(), glideRoundTransform).diskCacheStrategy(DiskCacheStrategy.ALL).
                into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        v.setImageDrawable(resource);
                    }
                });
    }

    public static void loadImg(ImageView v, int resourceId) {
        if (ContextChecker.check(v.getContext())) {
            Glide.with(v.getContext()).load(resourceId).apply(new RequestOptions().fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL)).into(v);
        }
    }

    public static void loadGifImg(ImageView v, String url) {
        if (ContextChecker.check(v.getContext())) {
            Glide.with(v.getContext()).load(url).apply(new RequestOptions().fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL)).into(v);
        }
    }

    public static void loadCircleImg(ImageView v, File file) {
        if (ContextChecker.check(v.getContext())) {
            Glide.with(v.getContext()).load(file).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(v);
        }
    }

    public static void loadImgBg(ImageView v, String url) {
        if (ContextChecker.check(v.getContext())) {
            loadImgBg(v, url, true);
        }
    }

    public static void loadImgBg(ImageView v, String url, boolean isRotation) {
        if (!ContextChecker.check(v.getContext())) {
            return;
        }
        Glide.with(v.getContext()).asBitmap().load(url).transform(new BitmapTransformation() {

            @Override
            protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
                if (outWidth < outHeight && isRotation) {
                    return rotaingImageView(270, toTransform);
                }
                return toTransform;
            }

            @Override
            public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

            }
        }).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                Drawable drawable = new BitmapDrawable(resource);
                v.setBackground(drawable);
            }

        });

    }

    /**
     * 旋转图片
     *
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        System.out.println("angle2=" + angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */

    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    public static void loadCircleImg(ImageView v, Bitmap bitmap) {
        if (ContextChecker.check(v.getContext())) {
            Glide.with(v.getContext()).load(bitmap).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(v);
        }
    }

    public static void loadCircleImg(ImageView v, String url) {
        if (ContextChecker.check(v.getContext())) {
            Glide.with(v.getContext()).load(url).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(v);
        }
    }

    /**
     * 新增错误图片
     *
     * @param v
     * @param url
     */
    public static void loadCircleImg2(ImageView v, String url) {
        if (ContextChecker.check(v.getContext())) {
            Glide.with(v.getContext()).load(url).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(v);
        }
    }

    public static void loadCircleImg(ImageView v, String url, int errorResId) {
        if (ContextChecker.check(v.getContext())) {
            Glide.with(v.getContext()).load(url).apply(RequestOptions.bitmapTransform(new CircleCrop())).error(errorResId).into(v);
        }
    }

    public static void loadCircleImg(ImageView v, String url, int errorResId, int width, int height) {
        if (ContextChecker.check(v.getContext())) {
            Glide.with(v.getContext()).load(url).apply(RequestOptions.bitmapTransform(new CircleCrop())).error(errorResId).override(width, height).into(v);
        }
    }

    public static void loadCircleImg(ImageView v, Drawable drawable) {
        if (ContextChecker.check(v.getContext())) {
            Glide.with(v.getContext()).load(drawable).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(v);
        }
    }

    public static void loadImgFillCenter(ImageView v, String localPath) {
        if (ContextChecker.check(v.getContext())) {
            Glide.with(v.getContext()).load(localPath).apply(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)).into(v);
        }
    }

    public static void loadAdapterImg(ImageView v, String url, final View itemView) {
        if (ContextChecker.check(v.getContext())) {
            Glide.with(v.getContext()).load(url).apply(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)).into(v).getSize(new SizeReadyCallback() {
                @Override
                public void onSizeReady(int width, int height) {
                    if (!itemView.isShown()) {
                        itemView.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    /**
     * 保存文件
     *
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public static String saveFile(Bitmap bm, String fileName) {
        File dirFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(dirFile.getAbsolutePath() + File.separator + fileName);
        if (!myCaptureFile.exists()) {
            try {
                myCaptureFile.createNewFile();
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
                bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                CloseUtils.Companion.closeIO(bos);
                return myCaptureFile.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获得指定文件的byte数组
     */
    public static byte[] getBytes(File file) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public static Bitmap newBitmap(Bitmap bit1, Bitmap bit2) {
        int width = bit1.getWidth();
        int height = bit1.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height + 60, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(bit1, 0, 0, null);
        canvas.drawBitmap(bit2, 0, bit1.getHeight(), null);
        return bitmap;
    }

    public static Bitmap getBitmap(int width, String content) {
        Bitmap bitmap = Bitmap.createBitmap(width, 60, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(Color.GRAY);
        textPaint.setTextSize(16.0F);
        StaticLayout layout = new StaticLayout(content, textPaint, width - 30, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);

        canvas.save();
        canvas.translate(10, 10);
        layout.draw(canvas);
        canvas.restore();

        return bitmap;
    }

    /**
     * 清除磁盘缓存.必须在子线程里做
     */
    public static void clearDiskCache(Context context) {
        System.gc();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Glide.get(context).clearDiskCache();
                return null;
            }
        };
    }

}
