package com.zevzhu.wanandroid.utils

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider

val MODULE_TAG_ACTIVITY_USER = "module_tag_activity_user"

// provider 只提供创建，不保证创建对象是唯一的
val userEntityModule = Kodein.Module(MODULE_TAG_ACTIVITY_USER) {
    bind<UserEntity>() with provider { UserEntity() }
}

class UserEntity {
    fun getName(): String = "UserEntity"
}