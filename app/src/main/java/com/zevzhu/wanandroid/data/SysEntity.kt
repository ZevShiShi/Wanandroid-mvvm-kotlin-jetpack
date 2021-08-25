package com.zevzhu.wanandroid.data

import com.zevzhu.wanandroid.data.SysChildEntity

data class SysEntity(
    val children: ArrayList<SysChildEntity>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int,
    var select:Boolean = false

)