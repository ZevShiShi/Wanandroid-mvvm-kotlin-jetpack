package com.zevzhu.wanandroid.http

import com.zevzhu.wanandroid.data.ChapterEntity
import com.zevzhu.wanandroid.data.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import me.hgj.jetpackmvvm.network.AppException

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


    suspend fun regAndLogin(
        username: String,
        password: String
    ): ApiResponse<UserEntity> {
        val api = apiService.register(username, password, password)
        if (api.isSucces()) {
            return apiService.login(username, password)
        } else {
            throw AppException(api.errorCode, api.errorMsg)
        }
    }


    suspend fun collectOrUnCollect(
        id: Int,
        isCollect: Boolean
    ): ApiResponse<Any?> {
        return if (isCollect) {
            apiService.collectChapter(id)
        } else {
            apiService.unCollectChapter(id)
        }
    }
}