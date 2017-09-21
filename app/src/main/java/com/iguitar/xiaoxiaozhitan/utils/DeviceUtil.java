package com.iguitar.xiaoxiaozhitan.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.DisplayMetrics;
import android.view.View;

import java.io.IOException;


public class DeviceUtil {

    /**
     * @biref 获取手机型号
     * @return String
     */
    public static String getModel() {
        return android.os.Build.MODEL;
    }

    /**
     * @biref 获取手机MAC地址
     * @param context
     *            上下文
     * @return String
     */
    public static String getMac(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        WifiInfo info = wifi.getConnectionInfo();

        return info.getMacAddress();
    }

    /**
     * @brief 获取手机设别android SDK版本号
     * @return int
     */
    public static int getSDKVersion() {
        int version = 0;
        try {
            version = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * @brief 硬件设备类型
     * @return String
     */
    public static String getDeviceType() {
        return "ANDROID";
    }


    /**
     * @brief 获取手机屏幕尺寸
     * @param context
     *            上下文
     * @return String(480*800)
     */
    public static String getDisplayMetrics(Context context) {
        // String str = "";
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;// 屏幕宽（像素，如：480px）
        int screenHeight = dm.heightPixels;// 屏幕高（像素，如：800px）
        return String.valueOf(screenWidth) + "*" + String.valueOf(screenHeight);
    }

    /**
     * @brief 获取手机屏幕尺寸 高度
     * @param context
     *            上下文
     * @return int
     */
    public static int getMetricsHeight(Context context) {
        // String str = "";
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        int screenHeight = dm.heightPixels;// 屏幕高（像素，如：800px）
        return screenHeight;
    }

    /**
     * @brief 获取手机屏幕尺寸 宽度
     * @param context
     *            上下文
     * @return int
     */
    public static int getMetricsWidth(Context context) {
        // String str = "";
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;// 屏幕高（像素，如：800px）
        return screenWidth;
    }

    /**
     * @brief 判断手机当前网络开关状态 wifi gprs
     * @param context
     *            上下文
     * @return boolean
     */
    public static boolean IsNetWork(Context context) {
        // 判断手机当前网络开关状态 wifi gprs
        boolean isnetwork = true;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            boolean isWifiConnected = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED ? true : false;
            boolean isGprsConnected = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ? true : false;
            if (isWifiConnected || isGprsConnected) {
                isnetwork = true;
            } else {
                isnetwork = false;
            }
        } catch (Exception e) {
            isnetwork = true;
        }
        return isnetwork;
    }

    /**
     * @brief 获取指定Activity的截屏
     * @param activity
     *            activity对象
     * @return Bitmap
     */
    public static Bitmap getScreenShot(Activity activity) {

        // View是你需要截图的View
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();
        // 获取状态栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        // 获取屏幕长和高
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();
        // 去掉标题栏
        // Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
        Bitmap b = Bitmap.createBitmap(b1, 0, 0, width, height);
        view.destroyDrawingCache();
        return b;
    }

    /**
     * @brief 判断系统是否root
     * @return boolean
     */
    public static boolean isRoot() {
        boolean isroot = false;
        try {
            if (Runtime.getRuntime().exec("su").getOutputStream() == null) {
                // 没有root
                isroot = false;
            } else {
                isroot = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isroot;
    }

    /**
     * @brief 判断sd卡是否存在
     * @return boolean
     */
    public static boolean hasSDCard() {
        String SDState = android.os.Environment.getExternalStorageState();
        if (SDState.equals(android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @brief 判断手机GPS是否开启
     * @param context
     *            上下文
     * @return boolean
     */
    public static boolean isOpenGPS(Context context) {
        LocationManager alm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (alm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return true;
        } else {
            return false;
            // Intent myIntent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
            // mContext.startActivity(myIntent);
        }
    }

    /**
     * @brief 获取应用程序版本号
     * @param context
     *            上下文
     * @return String
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * @brief 获取应用程序版本名
     * @param context
     *            上下文
     * @return String
     */
    public static String getVersionName(Context context) {
        String versionName = "";
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }


    /**
     * @brief 获取手机屏幕密度
     * @param activity
     *            activity对象
     * @return int
     */
    public static int getMetricDpi(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int densityDpi = metric.densityDpi; // 屏幕密度DPI（120 / 160 / 240）
        return densityDpi;
    }

    public static String getClientType() {
        return "Android";
    }

}
