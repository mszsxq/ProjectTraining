package com.example.catchtime.backgroundService.utils;

import android.content.Context
import android.content.SharedPreferences



/**
 * Created by Neo on 2018-12-03.
 * http://m3n.ir/
 */
class AppSettings(context: Context) {


    private var sharedPreferences: SharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun setString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String): String? {
        return sharedPreferences.getString(key, "")
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun getSthNew(key: String): Boolean {
        return sharedPreferences.getBoolean(key, true)
    }

    fun setBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun setLong(key: String, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    fun getLong(key: String): Long {
        return sharedPreferences.getLong(key, 0)
    }

    fun setInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun setFloat(key: String, value: Float) {
        sharedPreferences.edit().putFloat(key, value).apply()
    }

    fun getInt(key: String): Int {
        return sharedPreferences.getInt(key, 0)
    }

    fun getFloat(key: String): Float {
        return sharedPreferences.getFloat(key, 0f)
    }


    fun resetAll(){
        sharedPreferences.edit().clear().apply()
    }

}