package com.lll.myimtest.utils;

import android.Manifest;
import android.util.Log;

/**
 * Created by admin on 2017/1/13.
 *
 * @ClassName: LogUtils
 * @Description:
 * @Date 2017/1/13
 */
public class LogUtils {
    private static boolean isOpenLog = true;

    /**
     * 打印一个错误信息
     * @param TAG
     * @param message
     */
    public static void e(String TAG,String message){
        if(isOpenLog){
            Log.e(TAG,message);
        }
    }

    public static void d(String TAG,String message){
        if(isOpenLog){
            Log.d(TAG,message);
        }
    }

    public static void v(String TAG,String message){
        if(isOpenLog){
            Log.v(TAG,message);
        }
    }
}
