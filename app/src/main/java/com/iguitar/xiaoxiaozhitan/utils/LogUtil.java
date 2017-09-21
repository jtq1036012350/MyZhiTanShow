package com.iguitar.xiaoxiaozhitan.utils;

import android.util.Log;

/**
 * Log工具类
 * Created by Jiang on 2016/10/19.
 */
public class LogUtil {	
	private static boolean isDebug = true;//在app代码完成后置为false
	
	public static void d(String tag, String msg){
		if(isDebug){
			Log.d(tag, msg);
		}
	}
	public static void e(String tag, String msg){
		if(isDebug){
			Log.e(tag, msg);
		}
	}
	
	public static void d(Object object, String msg){
		if(isDebug){
			Log.d(object.getClass().getSimpleName(), msg);
		}
	}
	public static void e(Object object, String msg){
		if(isDebug){
			Log.e(object.getClass().getSimpleName(), msg);
		}
	}
}
