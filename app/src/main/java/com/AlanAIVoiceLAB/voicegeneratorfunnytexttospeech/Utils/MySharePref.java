package com.AlanAIVoiceLAB.voicegeneratorfunnytexttospeech.Utils;

import android.content.Context;

/**
 * Created by haolek on 14/11/2017.
 */

public class MySharePref {
    private static final String KEY_LANGUAGE_TYPE = "language_type";
    private static final String KEY_SEX_TYPE = "sex_type";
    private static final String KEY_VOLUME = "volume";
    private static final String KEY_SPEECH = "speech";

    private static MySharePref mInstance;

    private MySharePref() {
    }

    public static MySharePref getInstance() {
        if (mInstance == null) {
            mInstance = new MySharePref();
        }
        return mInstance;
    }

    public void setLanguageType(Context context, String languageType) {
        SharePref.saveString(context, KEY_LANGUAGE_TYPE, languageType);
    }

    public String getLanguageType(Context context) {
        return SharePref.getString(context, KEY_LANGUAGE_TYPE);
    }

    public void setSexType(Context context, String sexType) {
        SharePref.saveString(context, KEY_SEX_TYPE, sexType);
    }

    public String getSexType(Context context) {
        return SharePref.getString(context, KEY_SEX_TYPE);
    }

    public void setVolume(Context context, int value) {
        SharePref.saveInt(context, KEY_VOLUME, value);
    }

    public int getVolume(Context context) {
        return SharePref.getInt(context, KEY_VOLUME);
    }

    public void saveSpeech(Context context, int value) {
        SharePref.saveInt(context, KEY_SPEECH, value);
    }

    public int getSpeech(Context context) {
        return SharePref.getInt(context, KEY_SPEECH);
    }
}
