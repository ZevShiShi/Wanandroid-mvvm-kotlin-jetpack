package com.zevzhu.wanandroid.app

import android.content.Context
import com.zevzhu.wanandroid.event.EventViewModel
import me.hgj.jetpackmvvm.base.BaseApp

val appContext by lazy { App.context }
val eventViewModel by lazy { App.mEventViewModel }

class App : BaseApp() {

    companion object {
        lateinit var context: Context
        lateinit var mEventViewModel: EventViewModel
    }


    override fun onCreate() {
        super.onCreate()
        context = this
        mEventViewModel = getAppViewModelProvider().get(EventViewModel::class.java)
    }

}