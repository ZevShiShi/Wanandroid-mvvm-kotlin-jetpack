package com.zevzhu.wanandroid.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SysChildEntity(
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int,
    var select: Boolean = false
) : Parcelable