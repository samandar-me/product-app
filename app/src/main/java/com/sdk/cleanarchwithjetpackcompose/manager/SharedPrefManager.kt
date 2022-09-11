package com.sdk.cleanarchwithjetpackcompose.manager

import android.content.Context
import android.content.SharedPreferences
import com.sdk.cleanarchwithjetpackcompose.utils.Constants.MY_PREF
import com.sdk.cleanarchwithjetpackcompose.utils.Constants.PREF_TOKEN

@Suppress("UNCHECKED_CAST")
class SharedPrefManager(context: Context) {
    private val sharedPref: SharedPreferences = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        set(PREF_TOKEN, token)
    }

    fun getToken(): String {
        return get(PREF_TOKEN, String::class.java)
    }

    private fun <T> get(key: String, clazz: Class<T>): T {
        return when (clazz) {
            String::class.java -> sharedPref.getString(key, "")
            Boolean::class.java -> sharedPref.getBoolean(key, false)
            Float::class.java -> sharedPref.getFloat(key, -1f)
            Double::class.java -> sharedPref.getFloat(key, -1f)
            Int::class.java -> sharedPref.getInt(key, -1)
            Long::class -> sharedPref.getLong(key, -1L)
            else -> null
        } as T
    }

    private fun <T> set(key: String, data: T) {
        val editor = sharedPref.edit()
        when (data) {
            is String -> editor.putString(key, data)
            is Boolean -> editor.putBoolean(key, data)
            is Double -> editor.putFloat(key, data.toFloat())
            is Int -> editor.putInt(key, data)
            is Long -> editor.putLong(key, data)
            is Float -> editor.putFloat(key, data)
        }
        editor.apply()
    }
    fun clear() {
        sharedPref.edit().clear().apply()
    }
}