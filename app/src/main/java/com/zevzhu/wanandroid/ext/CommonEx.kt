package com.zevzhu.wanandroid.ext

import com.blankj.utilcode.util.SPUtils

fun isLogin(): Boolean {
    return SPUtils.getInstance().getString("cookie").isNotEmpty()
}