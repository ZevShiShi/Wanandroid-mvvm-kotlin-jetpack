package com.zevzhu.wanandroid.http.api

import com.zevzhu.wanandroid.data.BannerEntity
import com.zevzhu.wanandroid.data.ChapterEntity
import com.zevzhu.wanandroid.data.ProTabEntity
import com.zevzhu.wanandroid.http.ApiPageResponse
import com.zevzhu.wanandroid.http.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    /**
     * 首页列表数据
     */
    @GET("article/list/{page}/json")
    suspend fun getHomeList(@Path("page") page: Int): ApiResponse<ApiPageResponse<ChapterEntity>>

    /**
     * banner数据
     */
    @GET("banner/json")
    suspend fun getBanner(): ApiResponse<MutableList<BannerEntity>>

    /**
     * 首页置顶数据
     */
    @GET("article/top/json")
    suspend fun getTopList(): ApiResponse<MutableList<ChapterEntity>>

    /**
     * 项目Tab数据
     */
    @GET("project/tree/json")
    suspend fun getProTab(): ApiResponse<MutableList<ProTabEntity>>

    /**
     * 项目Tab下的列表数据
     */
    @GET("project/list/{page}/json")
    suspend fun getProList(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): ApiResponse<ApiPageResponse<ChapterEntity>>


    /**
     * 项目Tab下的最新项目列表数据
     */
    @GET("article/listproject/{page}/json")
    suspend fun getProNewList(@Path("page") page: Int): ApiResponse<ApiPageResponse<ChapterEntity>>


    /**
     * 广场列表数据
     */
    @GET("user_article/list/{page}/json")
    suspend fun getPlazaList(@Path("page") page: Int): ApiResponse<ApiPageResponse<ChapterEntity>>
}