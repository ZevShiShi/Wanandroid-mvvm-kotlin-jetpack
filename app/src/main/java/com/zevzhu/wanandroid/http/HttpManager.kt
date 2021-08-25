package com.zevzhu.wanandroid.http

import com.zevzhu.wanandroid.data.ChapterEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

object HttpManager {

    suspend fun getHomeList(currPage: Int): ApiResponse<ApiPageResponse<ChapterEntity>> {
        return withContext(Dispatchers.IO) {
            val listData = async { apiService.getHomeList(currPage) }
            if (currPage == 0) {
                val topData = async { apiService.getTopList() }
                listData.await().data.datas.addAll(0, topData.await().data)
                listData.await()
            } else {
                listData.await()
            }
        }
    }


    suspend fun getProjectList(
        currPage: Int,
        cid: Int,
        isNew: Boolean
    ): ApiResponse<ApiPageResponse<ChapterEntity>> {
        return if (isNew) apiService.getProNewList(currPage)
        else apiService.getProList(currPage, cid)
    }
}