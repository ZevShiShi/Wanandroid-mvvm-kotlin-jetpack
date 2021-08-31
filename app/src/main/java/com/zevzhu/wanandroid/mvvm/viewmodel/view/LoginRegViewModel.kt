package com.zevzhu.wanandroid.mvvm.viewmodel.view

import android.view.View
import androidx.databinding.ObservableInt
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.callback.databind.BooleanObservableField
import me.hgj.jetpackmvvm.callback.databind.StringObservableField

class LoginRegViewModel : BaseViewModel() {
    val username = StringObservableField()
    val password = StringObservableField()
    val repassword = StringObservableField()
    val isShowPwd = BooleanObservableField()
    val isShowRePwd = BooleanObservableField()


    val clearVisible = object : ObservableInt(username) {
        override fun get(): Int {
            return if (username.get().isEmpty()) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
    }

    val passwordVisible = object : ObservableInt(password) {
        override fun get(): Int {
            return if (password.get().isEmpty()) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
    }

    val repasswordVisible = object : ObservableInt(repassword) {
        override fun get(): Int {
            return if (repassword.get().isEmpty()) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
    }
}