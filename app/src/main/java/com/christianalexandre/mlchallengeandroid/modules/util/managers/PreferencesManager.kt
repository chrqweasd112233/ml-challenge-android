package com.christianalexandre.mlchallengeandroid.modules.util.managers

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    enum class PreferencesKeys {
        ML_APP_PREFERENCES,
        SEARCH_HISTORY
    }

    var searchHistory: List<String>
        get() {
            val jsonString = sharedPreferences.getString(PreferencesKeys.SEARCH_HISTORY.name, null)
            return jsonString?.let {
                val type = object : TypeToken<List<String>>() {}.type
                Gson().fromJson(it, type)
            } ?: emptyList()
        }
        set(value) {
            val newValue = value.first()
            if (newValue.isBlank()) return
            val newHistory = searchHistory.toMutableList()
            newHistory.add(0, newValue)
            val json = Gson().toJson(newHistory.distinct().take(10))
            sharedPreferences.edit().putString(PreferencesKeys.SEARCH_HISTORY.name, json).apply()
        }
}