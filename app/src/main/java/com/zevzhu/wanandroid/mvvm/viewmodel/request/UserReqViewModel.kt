package com.zevzhu.wanandroid.mvvm.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.zevzhu.wanandroid.data.UserEntity
import com.zevzhu.wanandroid.data.UserinfoEntity
import com.zevzhu.wanandroid.http.HttpManager
import com.zevzhu.wanandroid.http.apiService
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.request
import me.hgj.jetpackmvvm.state.ResultState

class UserReqViewModel : BaseViewModel() {

    val loginResult = MutableLiveData<ResultState<UserEntity>>()
    val loginOutResult = MutableLiveData<ResultState<Any>>()
    val userResult = MutableLiveData<ResultState<UserinfoEntity>>()
    val regResult = MutableLiveData<ResultState<UserEntity>>()

    fun login(username: String, password: String) {
        request({ apiService.login(username, password) }, loginResult)
    }

    fun loginOut() {
        request({ apiService.loginOut() }, loginOutResult)
    }

    fun getUserInfo() {
        request({ apiService.getUserInfo() }, userResult)
    }

    fun register(username: String, password: String) {
        request({ HttpManager.regAndLogin(username,password) }, regResult)
    }
}