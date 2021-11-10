package com.zevzhu.wanandroid.utils

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

val MODULE_TAG_ACTIVITY_MANAGER = "module_tag_activity_manager"

// singleton 提供单例，保证创建对象是唯一的
val kodeinTestModule = Kodein.Module(MODULE_TAG_ACTIVITY_MANAGER) {
    bind<KodeinTest>() with singleton { KodeinTest() }
}

class KodeinTest {
    fun getName(): String = "KodeinTest"
}