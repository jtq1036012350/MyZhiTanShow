package com.iguitar.xiaoxiaozhitan.utils;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidadvance.topsnackbar.TSnackbar;
import com.iguitar.xiaoxiaozhitan.model.UserInfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 常用工具类
 */
public class CommonUtil {

    public static int getTotalHeightofListView(ListView listView) {
        ListAdapter mAdapter = listView.getAdapter();
        if (mAdapter == null) {
            return 0;
        }
        int totalHeight = 0;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View mView = mAdapter.getView(i, null, listView);
            mView.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            //mView.measure(0, 0);
            totalHeight += mView.getMeasuredHeight();
            LogUtil.d("数据" + i, String.valueOf(totalHeight));
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (mAdapter.getCount() - 1));
        LogUtil.d("数据", "listview总高度=" + params.height);
        listView.setLayoutParams(params);
        listView.requestLayout();
        return totalHeight;
    }

    /**
     * 获取屏幕高度
     *
     * @param activity
     * @return
     */
    public static int getDisplayHeight(Activity activity) {
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int screenHeight = display.getHeight();
        return screenHeight;
    }

    /**
     * 获取屏幕宽度
     *
     * @param activity
     * @return
     */
    public static int getDisplayWidth(Activity activity) {
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int screenWidth = display.getWidth();
        return screenWidth;
    }

    /**
     * 保存首次登陆
     */
    public static void saveFirstLogin(Context context, boolean IsFirst) {
        SharedPreferences preferences = context.getSharedPreferences("FirstLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putBoolean("Isfirst", IsFirst);
        edit.commit();
    }

    /**
     * 是否是首次登陆
     */
    public static Boolean IsFirstLogin(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("FirstLogin", Context.MODE_PRIVATE);
        return preferences.getBoolean("Isfirst", false);
    }

    /**
     * 保存首次登陆
     */
    public static void saveFirstGuide(Context context, boolean IsFirstGuide) {
        SharedPreferences preferences = context.getSharedPreferences("FirstLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putBoolean("IsFirstGuide", IsFirstGuide);
        edit.commit();
    }

    /**
     * 是否是首次登陆
     */
    public static Boolean IsFirstGuide(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("FirstLogin", Context.MODE_PRIVATE);
        return preferences.getBoolean("IsFirstGuide", false);
    }

    /**
     * 保存配置IP信息
     */
    public static void saveIp(Context context, String Ipname) {
        SharedPreferences preferences = context.getSharedPreferences("IP", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("ip", Ipname);
        edit.commit();
    }

    /**
     * 获取IP配置信息
     */
    public static String getIP(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("IP", Context.MODE_PRIVATE);
        return preferences.getString("ip", "");
    }

    /**
     * 获取当前app版本code
     *
     * @param context
     * @return
     */
    public static int getCode(Context context) {
        int currentVersionCode = 0;
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            currentVersionCode = info.versionCode; // 版本号
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return currentVersionCode;
    }

    /**
     * 保存用户信息
     *
     * @param context
     * @param info    用户登陆信息
     */
    public static void saveLoginInfo(Context context, UserInfo info) {
        SharedPreferences.Editor editor = context.getSharedPreferences("LoginCookie", Context.MODE_PRIVATE).edit();
        editor.putString("username", info.getName());
        editor.putString("password", info.getPassword());
        editor.putString("password", info.getPassword());
        editor.putBoolean("isRember", info.isRember());
        editor.commit();
    }

    /**
     * 获取保存在本地的用户信息
     *
     * @param context
     * @return
     */
    public static UserInfo getLoginInfo(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("LoginCookie", Context.MODE_PRIVATE);
        UserInfo info = new UserInfo();
        info.setName(preferences.getString("username", null));
        info.setPassword(preferences.getString("password", null));
        info.setToken(preferences.getString("token", null));
        info.setRember(preferences.getBoolean("isRember", false));
        return info;
    }

    /**
     * 复制内容到剪贴板
     */
    public static void CopyToClipBoard(Activity activity, String text) {
        // 从API11开始android推荐使用android.content.ClipboardManager
        // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
        ClipboardManager cm = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(text);
    }

    /**
     * 判断网络类型
     *
     * @param context
     * @return //返回值 -1：没有网络  1：WIFI网络2：wap网络3：net网络
     */
    public static int getNetype(Context context) {
        int netType = -1;
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {
                netType = 3;
            } else {
                netType = 2;
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = 1;
        }
        return netType;
    }


    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    public static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }


    public static boolean checkPackage(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }

    }

    /**
     * 保存头像url
     *
     * @param context
     * @param url
     * @return
     */
    public static void saveHeadIcon(Context context, String url) {
        SharedPreferences.Editor editor = context.getSharedPreferences(ConstantUtil.HEADURL, Context.MODE_PRIVATE).edit();
        editor.putString("headUrl", url);
        editor.commit();
    }

    /**
     * 获取保存头像url
     *
     * @param context
     * @return
     */
    public static String getSaveHeadIcon(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(ConstantUtil.HEADURL, Context.MODE_PRIVATE);
        String url = preferences.getString("headUrl", null);
        return url;
    }

    /****************
     *
     * 发起添加群流程
     * 调用 joinQQGroup(OOrTLMg5sXHGVFVLALNlSDQg8DIaVGG2) 即可发起手Q客户端申请加群 安卓开发共享群(123963699)
     *
     * @param key 由官网生成的key
     * @return 返回true表示呼起手Q成功，返回fals表示呼起失败
     ******************/
    public static boolean joinQQGroup(Activity mActivity, String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            mActivity.startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            return false;
        }
    }


    /**
     * 新版本弹框
     *
     * @param activity Activity对象
     * @param text     内容
     */
    public static void showTopToast(Activity activity, String text) {
//        TSnackbar snackbar = TSnackbar.make(activity.findViewById(android.R.id.content), text, TSnackbar.LENGTH_LONG);
//        snackbar.setActionTextColor(Color.WHITE);
//        snackbar.setDuration(2000);
//        View snackbarView = snackbar.getView();
//        snackbarView.setBackgroundColor(Color.parseColor("#000000"));
//        TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
//        textView.setTextColor(Color.WHITE);
//        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
//        textView.setTextSize(18);
//        snackbar.show();
        TSnackbar.make(activity.findViewById(android.R.id.content), text, TSnackbar.LENGTH_LONG).show();
    }


    /**
     * 获取apk的版本号 currentVersion
     *
     * @param context
     * @return
     */
    public static String getAPPVersion(Context context) {
        int currentVersionCode = 0;
        String appVersionName = "";
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            appVersionName = info.versionName; // 版本名
            currentVersionCode = info.versionCode; // 版本号
            LogUtil.d("version", currentVersionCode + " " + appVersionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appVersionName;
    }


    /**
     * 检测sdcard是否可用
     *
     * @return true为可用，否则为不可用
     */
    public static boolean sdCardIsAvailable() {
        String status = Environment.getExternalStorageState();
        if (!status.equals(Environment.MEDIA_MOUNTED))
            return false;
        return true;
    }

    /**
     * 根据手机分辨率从dp转成px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f) - 15;
    }

    private static Toast toast = null;
    private static Timer timer = new Timer();

    public static void showToast(Context context, String text) {
        if (toast == null) {
            context = context.getApplicationContext();
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);

            LinearLayout layout = (LinearLayout) toast.getView();
            TextView textView = (TextView) layout.getChildAt(0);
            textView.setTextSize(20);
            toast.show();

            final Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    toast.show();
                }
            }, 0, 1000);

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    toast.cancel();
                    timer.cancel();
                    toast = null;
                }
            }, 4000);

        } else {
            toast.setText(text);
        }


    }


    public static void showToast(Context context, int text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    /**
     * 获取当前app版本code
     *
     * @param context
     * @return
     */
    public static int GetCode(Context context) {
        int currentVersionCode = 0;
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            currentVersionCode = info.versionCode; // 版本号
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return currentVersionCode;
    }

    public static String getDeviceId(Context context) {
        String deviceId;
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        deviceId = tm.getDeviceId();
        if (deviceId == null) {
            return "";
        }
        return deviceId;
    }
}
