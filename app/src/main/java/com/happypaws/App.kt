package com.happypaws

import android.app.Application

class App : Application() {

    companion object {

        /**
         * Returns the application instance (not null).
         */
        var appInstance: App? = null
    }

    override fun onCreate() {
        super.onCreate()
        appInstance = this

    }
}