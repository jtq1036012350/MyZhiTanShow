package com.iguitar.xiaoxiaozhitan;

import android.app.Application;
import android.content.Context;

import com.iguitar.xiaoxiaozhitan.utils.CommonUtil;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.socialize.PlatformConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import me.iwf.photopicker.utils.MyPhotoUtil;


/**
 * Created by Jiang.
 */

public class MyApplication extends Application {
    private static Context context;
    private MyPhotoUtil myPhotoUtil;
    public static Map mCheckMap;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        String headUrl = CommonUtil.getSaveHeadIcon(getApplicationContext());
        myPhotoUtil = new MyPhotoUtil();
        ArrayList<String> headurls = new ArrayList<>();
        headurls.add(headUrl);
        PlatformConfig.setQQZone("1106427116", "eDB7hrkmPVSgfyyK");
        myPhotoUtil.putPhotoMap(headurls);

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = CommonUtil.getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        // 初始化Bugly
        CrashReport.initCrashReport(context, "b5365217de", false, strategy);
        // 如果通过“AndroidManifest.xml”来配置APP信息，初始化方法如下
        // CrashReport.initCrashReport(context, strategy);

    }


    public static void putIconMap(String profile_image_url) {
        mCheckMap = new HashMap<String, String>();
        mCheckMap.put("profile_image_url", profile_image_url);
    }

    public static String getIconMap() {
        if (mCheckMap != null) {
            return (String) mCheckMap.get("profile_image_url");
        }
        return "";
    }

    public static void putMap(Object key,Object value) {
        mCheckMap = new HashMap<>();
        mCheckMap.put(key, value);
    }

    public static Object getMap(Object key) {
        if (mCheckMap != null) {
            return  mCheckMap.get(key);
        }
        return null;
    }
}
