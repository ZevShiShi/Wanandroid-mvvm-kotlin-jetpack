package com.zevzhu.wanandroid.http

import com.blankj.utilcode.util.ObjectUtils

data class ApiPageResponse<T>(
    val curPage: Int = 0,
    val offset: Int = 0,
    val over: Boolean = false,
    val pageCount: Int = 0,
    val size: Int = 0,
    val total: Int = 0,
    val datas: MutableList<T> = mutableListOf()
) {

    fun isEmpty() = ObjectUtils.isEmpty(datas)

    fun hasMore() = !over

    fun isRefresh() = offset == 0

    fun isFirstEmpty() = isRefresh() && isEmpty()

    override fun toString(): String {
        return "ApiPageResponse(curPage=$curPage, offset=$offset, over=$over, pageCount=$pageCount, size=$size, total=$total, datas=$datas)"
    }
}
