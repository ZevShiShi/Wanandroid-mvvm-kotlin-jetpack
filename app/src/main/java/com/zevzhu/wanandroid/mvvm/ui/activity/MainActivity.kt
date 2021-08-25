package com.zevzhu.wanandroid.mvvm.ui.activity

import android.os.Bundle
import com.zevzhu.wanandroid.R
import com.zevzhu.wanandroid.databinding.ActivityMainBinding
import com.zevzhu.wanandroid.mvvm.base.BaseActivity
import com.zevzhu.wanandroid.mvvm.viewmodel.view.MainViewModel

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override fun layoutId() = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
    }
}