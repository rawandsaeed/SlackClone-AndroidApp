package com.example.slackclone.controllers

import android.app.Application
import com.example.slackclone.utilities.SharedPrefs

/**
Created by rawandsaeed on 7/1/20
 */
class App: Application() {

    companion object {
        lateinit var prefs: SharedPrefs
    }

    override fun onCreate() {
        prefs = SharedPrefs(applicationContext)
        super.onCreate()
    }
}