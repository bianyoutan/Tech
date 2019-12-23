package com.wd.tech.base

import android.app.Application
import android.content.Context

open class BaseApplication : Application() {

    //静态变量和静态方法，用companion object统一包裹
    companion object {
        lateinit var context: Context

        fun getAppContext(): Context {
            return context
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}