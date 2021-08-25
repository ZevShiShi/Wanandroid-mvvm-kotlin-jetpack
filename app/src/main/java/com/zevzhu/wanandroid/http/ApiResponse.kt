package com.zevzhu.wanandroid.http

import me.hgj.jetpackmvvm.network.BaseResponse

class ApiResponse<T>(val errorCode: Int, val errorMsg: String, val data: T) : BaseResponse<T>() {

    override fun getResponseCode() = errorCode

    override fun getResponseData() = data

    override fun getResponseMsg() = errorMsg

    override fun isSucces() = errorCode == 0

    override fun toString(): String {
        return "ApiResponse(errorCode=$errorCode, errorMsg='$errorMsg', data=$data)"
    }


}