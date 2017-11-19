package com.AlanAIVoiceLAB.voicegeneratorfunnytexttospeech.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by haolek on 14/11/2017.
 */

public class SharePref {
    private static final String SHARE_PREFERENCE_DATA = "share_preference_data";

    /*
     * Save string
     */
    public static void saveString(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                SHARE_PREFERENCE_DATA, Context.MODE_PRIVATE
        );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /*
     * Get string
     */
    public static String getString(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                SHARE_PREFERENCE_DATA, Context.MODE_PRIVATE
        );
        return sharedPreferences.getString(key, "");
    }

    /*
     * Save boolean
     */
    public static void saveBoolean(Context context, String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                SHARE_PREFERENCE_DATA, Context.MODE_PRIVATE
        );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /*
     * Get boolean
     */
    public static Boolean getBoolean(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                SHARE_PREFERENCE_DATA, Context.MODE_PRIVATE
        );
        return sharedPreferences.getBoolean(key, false);
    }

    /*
     * Save int
     */
    public static void saveInt(Context context, String key, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                SHARE_PREFERENCE_DATA, Context.MODE_PRIVATE
        );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /*
     * Get int
     */
    public static int getInt(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                SHARE_PREFERENCE_DATA, Context.MODE_PRIVATE
        );
        return sharedPreferences.getInt(key, 0);
    }
}
