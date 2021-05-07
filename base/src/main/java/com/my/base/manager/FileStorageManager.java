package com.my.base.manager;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * @author caishuzhan
 */
public class FileStorageManager {

    public static File getCacheParentDir(Context context){
        File parent;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            parent = context.getExternalCacheDir();
        } else {
            parent = context.getCacheDir();
        }
        return parent;
    }
}
