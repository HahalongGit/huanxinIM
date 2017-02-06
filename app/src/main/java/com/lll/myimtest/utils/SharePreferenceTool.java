package com.lll.myimtest.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.lll.myimtest.Application;

/**
 * Created by admin on 2017/1/13.
 *
 * @ClassName: SharePreferenceTool
 * @Description:
 * @Date 2017/1/13
 */
public class SharePreferenceTool {
    private static SharedPreferences sharedPreferences =
            Application.getInstance().getApplicationContext()
                    .getSharedPreferences("myIMtext", Context.MODE_PRIVATE);

    private static SharedPreferences.Editor editor = sharedPreferences.edit();

    /**
     * 保存一个字符串
     * @param key
     * @param value
     */
    public static void saveString(String key,String value){
        editor.putString(key,value);
        editor.commit();
    }

    /**
     * 获取一个字符串
     * @param key
     * @return
     */
    public static String getString(String key){
        return sharedPreferences.getString(key,null);
    }

}
