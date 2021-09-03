package com.zevzhu.wanandroid.ext

import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.SPUtils

fun isLogin(): Boolean {
    return SPUtils.getInstance().getString("cookie").isNotEmpty()
}

fun Fragment.hideKeyboard() {
    if (KeyboardUtils.isSoftInputVisible(requireActivity())) {
        KeyboardUtils.toggleSoftInput()
    }
}