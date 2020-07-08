package com.example.slackclone.utilities

import android.content.Context
import android.content.SharedPreferences
import com.android.volley.toolbox.Volley

/**
Created by rawandsaeed on 7/1/20
 */
class SharedPrefs (context: Context) {

    private val PREFS_FILENAME = "prefs"
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    private val IS_LOGGED_IN = "isLoggedIn"
    private val AUTH_TOKEN = "authToken"
    private val USER_EMAIL = "userEmail"

    var isLoggedIn: Boolean
        get() = prefs.getBoolean(IS_LOGGED_IN, false)
        set(value) = prefs.edit().putBoolean(IS_LOGGED_IN, value).apply()

    var authToken: String?
        get() = prefs.getString(AUTH_TOKEN, "")
        set(value) = prefs.edit().putString(AUTH_TOKEN, value).apply()

    var userEmail: String?
        get() = prefs.getString(USER_EMAIL, "")
        set(value) = prefs.edit().putString(USER_EMAIL, value).apply()

    val requestQueue = Volley.newRequestQueue(context)
}