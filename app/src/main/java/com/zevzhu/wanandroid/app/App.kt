package com.zevzhu.wanandroid.app

import android.content.Context
import com.zevzhu.wanandroid.event.EventViewModel
import me.hgj.jetpackmvvm.base.BaseApp
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

val appContext by lazy { App.context }
val eventViewModel by lazy { App.mEventViewModel }
val applicationTag = "ApplicationModule"

val appContextModule= Kodein.Module(applicationTag) {
    bind<Context>() with singleton {
        App.context
    }
}

class App : BaseApp(), KodeinAware {

    companion object {
        lateinit var context: Context
        lateinit var mEventViewModel: EventViewModel
    }


    override fun onCreate() {
        super.onCreate()
        context = this
        mEventViewModel = getAppViewModelProvider().get(EventViewModel::class.java)
    }

    override val kodein = Kodein.lazy {
        import(androidXModule(this@App))
    }
}