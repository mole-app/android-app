package com.mole.android.mole.di.repository

import android.app.Activity
import android.content.Context

class PreferenceRepository(activity: Activity?) : Repository {
    private val preferences = activity?.getPreferences(Context.MODE_PRIVATE)

    override fun setString(key: String, value: String) {
        preferences?.edit()?.apply {
            putString(key, value)
            apply()
        }
    }

    override fun setInt(key: String, value: Int) {
        preferences?.edit()?.apply {
            putInt(key, value)
            apply()
        }
    }

    override fun setLong(key: String, value: Long) {
        preferences?.edit()?.apply {
            putLong(key, value)
            apply()
        }
    }

    override fun setFloat(key: String, value: Float) {
        preferences?.edit()?.apply {
            putFloat(key, value)
            apply()
        }
    }

    override fun setBoolean(key: String, value: Boolean) {
        preferences?.edit()?.apply {
            putBoolean(key, value)
            apply()
        }
    }

    override fun getString(key: String, defaultValue: String): String {
        return preferences?.getString(key, defaultValue) ?: defaultValue
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return preferences?.getInt(key, defaultValue) ?: defaultValue
    }

    override fun getLong(key: String, defaultValue: Long): Long {
        return preferences?.getLong(key, defaultValue) ?: defaultValue
    }

    override fun getFloat(key: String, defaultValue: Float): Float {
        return preferences?.getFloat(key, defaultValue) ?: defaultValue
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return preferences?.getBoolean(key, defaultValue) ?: defaultValue
    }

}