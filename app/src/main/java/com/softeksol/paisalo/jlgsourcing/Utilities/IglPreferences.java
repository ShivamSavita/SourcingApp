package com.softeksol.paisalo.jlgsourcing.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.softeksol.paisalo.jlgsourcing.SEILIGL;
import com.softeksol.paisalo.jlgsourcing.entities.SmCode_DateModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Created by sachindra on 2/7/2017.
 */
public class IglPreferences {

    public static void loadLanguage(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Configuration config = context.getResources().getConfiguration();
        String lang = preferences.getString(SEILIGL.LANGUAGE, "en");
        Locale locale = new Locale(lang);
        config.setLocale(locale);

        Locale.setDefault(locale);
        //config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }

    public static void setLanguage(Context context, String lang) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(SEILIGL.LANGUAGE, lang).apply();
    }

    public static <T> void setSharedPref(Context context, String key, T value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String typeName = value.getClass().getSimpleName();
        if (typeName.equals("String"))
            preferences.edit().putString(key, (String) value).apply();
        if (typeName.equals("Integer"))
            preferences.edit().putInt(key, (Integer) value).apply();
        if (typeName.equals("Long"))
            preferences.edit().putLong(key, (Long) value).apply();
        if (typeName.equals("Boolean"))
            preferences.edit().putBoolean(key, (Boolean) value).apply();
        if (typeName.equals("Float"))
            preferences.edit().putFloat(key, (Float) value).apply();
        if (typeName.equals("Set"))
            preferences.edit().putStringSet(key, (Set<String>) value).apply();
    }

    public static String getPrefString(Context context, String key, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, value);
    }

    public static int getPrefInteger(Context context, String key, int value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(key, value);
    }

    public static long getPrefLong(Context context, String key, long value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getLong(key, value);
    }

    public static boolean getPrefLong(Context context, String key, boolean value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(key, value);
    }

    public static float getPrefFloat(Context context, String key, float value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getFloat(key, value);
    }

    public static Set<String> getPrefStringSet(Context context, String key, Set<String> value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getStringSet(key, value);
    }

    public static void removePref(Context context, String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().remove(key).apply();
    }

    public static JSONObject getAccesstoken(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        JSONObject jo;
        try {
            jo = new JSONObject(preferences.getString(SEILIGL.LOGIN_TOKEN, ""));
        } catch (JSONException je) {
            jo = null;
        }

        return jo;
    }  public static JSONObject getAccesstokenESign(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        JSONObject jo;
        try {
            jo = new JSONObject(preferences.getString("tokenEsign", ""));
        } catch (JSONException je) {
            jo = null;
        }

        return jo;
    }


    public static void saveList(Context context, List<SmCode_DateModel> list) {
        SharedPreferences prefs = context.getSharedPreferences("payment_case", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(list);
        editor.putString("payment_list", json);
        editor.apply();
    }

    public static List<SmCode_DateModel> getList(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("payment_case", Context.MODE_PRIVATE);
        Gson gson = new GsonBuilder().create();
        String json = prefs.getString("payment_list", null);
        Type type = new TypeToken<List<SmCode_DateModel>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public static void removeItem(Context context, String propertyValueToRemove) {
        List<SmCode_DateModel> list = getList(context);
        for (SmCode_DateModel obj : list) {
            if (obj.getTranDate().equals(propertyValueToRemove)) {
                list.remove(obj);
                break;
            }
        }
        saveList(context, list);
    }
}
