package com.zevzhu.wanandroid.data

data class NavigationEntity(
    val cid: Int,
    val name: String,
    val articles: MutableList<ChapterEntity>
)