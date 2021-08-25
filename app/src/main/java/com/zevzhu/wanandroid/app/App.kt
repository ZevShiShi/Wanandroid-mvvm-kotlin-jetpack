package com.zevzhu.wanandroid.app

import android.content.Context
import me.hgj.jetpackmvvm.base.BaseApp

val appContext by lazy { App.context }


class App : BaseApp() {

    companion object {
        lateinit var context: Context
    }


    override fun onCreate() {
        super.onCreate()
        context = this
    }

}