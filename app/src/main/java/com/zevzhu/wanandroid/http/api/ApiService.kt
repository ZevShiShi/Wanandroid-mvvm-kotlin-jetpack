package com.zevzhu.wanandroid.http.api

import com.zevzhu.wanandroid.data.*
import com.zevzhu.wanandroid.http.ApiPageResponse
import com.zevzhu.wanandroid.http.ApiResponse
import retrofit2.http.*

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


    /**
     * 每日一问
     */
    @GET("wenda/list/{page}/json")
    suspend fun getAskList(@Path("page") page: Int): ApiResponse<ApiPageResponse<ChapterEntity>>


    /**
     * 体系下的文章列表
     */
    @GET("article/list/{page}/json")
    suspend fun getSysDetailList(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): ApiResponse<ApiPageResponse<ChapterEntity>>

    /**
     * 体系列表
     */
    @GET("tree/json")
    suspend fun getSystemList(): ApiResponse<MutableList<SysEntity>>

    /**
     * 导航数据
     */
    @GET("navi/json")
    suspend fun getNavList(): ApiResponse<MutableList<NavigationEntity>>

    /**
     * 微信公众号tab
     */
    @GET("wxarticle/chapters/json")
    suspend fun getWxTab(): ApiResponse<MutableList<WxTabEntity>>

    /**
     * 微信公众号列表
     */
    @GET("wxarticle/list/{id}/{page}/json")
    suspend fun getWxList(
        @Path("id") id: Int,
        @Path("page") page: Int
    ): ApiResponse<ApiPageResponse<ChapterEntity>>


    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): ApiResponse<UserEntity>


    /**
     * 退出登录
     */
    @GET("user/logout/json")
    suspend fun loginOut(): ApiResponse<Any>

}