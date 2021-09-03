package com.zevzhu.wanandroid.data

import com.blankj.utilcode.util.ObjectUtils
import com.chad.library.adapter.base.entity.MultiItemEntity

data class ChapterEntity(
    val apkLink: String,
    val audit: Int,
    val author: String,
    val canEdit: Boolean,
    val chapterId: Int,
    val chapterName: String,
    var collect: Boolean,
    val courseId: Int,
    val desc: String,
    val descMd: String,
    val envelopePic: String,
    val fresh: Boolean,
    val id: Int,
    val originId: Int,
    val link: String,
    val niceDate: String,
    val niceShareDate: String,
    val origin: String,
    val prefix: String,
    val projectLink: String,
    val selfVisible: Int,
    val shareDate: Long,
    val shareUser: String,
    val superChapterId: Int,
    val superChapterName: String,
    val tags: ArrayList<ChapterTags>,
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int,
    var showTop: Boolean = false,
    override var itemType: Int = 0
) : MultiItemEntity {

    companion object {
        const val NORMAL = 0
        const val PROJECT = 1
    }

    fun isTop(): Boolean {
        return type == 1
    }

    fun getUser(): String {
        if (ObjectUtils.isEmpty(shareUser)) {
            return when (ObjectUtils.isEmpty(author)) {
                true -> "匿名用户"
                false -> author
            }
        }
        return shareUser
    }
}