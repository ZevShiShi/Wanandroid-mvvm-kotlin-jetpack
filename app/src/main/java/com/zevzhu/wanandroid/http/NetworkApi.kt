package com.zevzhu.wanandroid.http

import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ObjectUtils
import com.blankj.utilcode.util.SPUtils
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.GsonBuilder
import com.zevzhu.wanandroid.common.AppConstant
import com.zevzhu.wanandroid.http.api.ApiService
import me.hgj.jetpackmvvm.base.appContext
import me.hgj.jetpackmvvm.network.BaseNetworkApi
import me.hgj.jetpackmvvm.network.interceptor.CacheInterceptor
import me.hgj.jetpackmvvm.network.interceptor.logging.LogInterceptor
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


val apiService: ApiService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    NetworkApi.INSTANCE.getApi(ApiService::class.java, AppConstant.BASE_URL)
}


class NetworkApi : BaseNetworkApi() {

    companion object {
        val INSTANCE: NetworkApi by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetworkApi()
        }
    }

    val cookieJar: PersistentCookieJar by lazy {
        PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(appContext))
    }

    /**
     * 添加Cookie拦截器
     */
    private val cookieInterceptor: Interceptor = Interceptor {
        val request = it.request()
        if (request.url().toString().contains("login")) {
            val originResponse = it.proceed(request)
            val severCookie = originResponse.headers("Set-Cookie")
            LogUtils.d("cookieInterceptor==========severCookie==$severCookie")
            val stringBuild = StringBuilder()
            severCookie.forEach { cookie -> stringBuild.append("$cookie#") }
            SPUtils.getInstance().put("cookie", stringBuild.toString())


            val cookie = SPUtils.getInstance().getString("cookie")
            LogUtils.d("cookieInterceptor==========cookie==$cookie")
            if (ObjectUtils.isEmpty(cookie)) {
                return@Interceptor originResponse
            }
            val cookies = cookie.split("#")
            val builder = request.newBuilder()
            cookies.forEach { cookie -> builder.addHeader("Cookie", cookie) }
            return@Interceptor it.proceed(builder.build())
        }
        return@Interceptor it.proceed(request)
    }

    /**
     * 实现重写父类的setHttpClientBuilder方法，
     * 在这里可以添加拦截器，可以对 OkHttpClient.Builder 做任意操作
     */
    override fun setHttpClientBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        builder.apply {
            //设置缓存配置 缓存最大10M
            cache(Cache(File(appContext.cacheDir, "cxk_cache"), 10 * 1024 * 1024))
            cookieJar(cookieJar)
            addInterceptor(cookieInterceptor)
            //示例：添加公共heads 注意要设置在日志拦截器之前，不然Log中会不显示head信息
//            addInterceptor(MyHeadInterceptor())
            //添加缓存拦截器 可传入缓存天数，不传默认7天
            addInterceptor(CacheInterceptor())
            // 日志拦截器
            addInterceptor(LogInterceptor())
            //超时时间 连接、读、写
            connectTimeout(20, TimeUnit.SECONDS)
            readTimeout(20, TimeUnit.SECONDS)
            writeTimeout(20, TimeUnit.SECONDS)
        }
        return builder
    }

    /**
     * 实现重写父类的setRetrofitBuilder方法，
     * 在这里可以对Retrofit.Builder做任意操作，比如添加GSON解析器，protobuf等
     */
    override fun setRetrofitBuilder(builder: Retrofit.Builder): Retrofit.Builder {
        return builder.apply {
            addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        }
    }
}