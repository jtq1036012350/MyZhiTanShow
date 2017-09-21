package me.iwf.photopicker.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jiang on 2016/10/28.
 */

public class MyPhotoUtil {
    public static Map mPhotos;
    public static int FORRESULT = 100;

    public static void putPhotoMap(Object mSelectedphotos) {
        mPhotos = new HashMap<String, String>();
        mPhotos.put("headPhotos", mSelectedphotos);
    }

    public static Object getPhotoMap() {
        if (mPhotos == null || mPhotos.get("headPhotos") == null) {
            return null;
        } else {
            return mPhotos.get("headPhotos");
        }
    }
}
