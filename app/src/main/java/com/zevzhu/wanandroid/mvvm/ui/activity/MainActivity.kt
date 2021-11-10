package com.zevzhu.wanandroid.mvvm.ui.activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import com.zevzhu.wanandroid.R
import com.zevzhu.wanandroid.app.appContextModule
import com.zevzhu.wanandroid.databinding.ActivityMainBinding
import com.zevzhu.wanandroid.mvvm.base.BaseActivity
import com.zevzhu.wanandroid.mvvm.viewmodel.view.MainViewModel
import com.zevzhu.wanandroid.utils.*
import org.kodein.di.Copy
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(),KodeinAware {

    // closesKodein 函数返回了相邻上层的一个Kodien容器，
    // 对于Activity来说它返回的是Application层级的kodein容器
    private val parentKodein by closestKodein()

    override val kodein: Kodein by retainedKodein {
        // 通过extend（）函数，我们将Application层级的Kodien容器也放在了Activity的kodien容器中，
        // 这样activity就能从上层的Kodein容器取出对应依赖
        extend(parentKodein,copy = Copy.All)
        // 定义kodeinTestModule和userEntityModule以存放MainActivity所需依赖的绑定函数
        import(kodeinTestModule)
        import(userEntityModule)
        import(appContextModule)
        bind<Activity>() with instance(this@MainActivity)
    }

    private val kodeinTest:KodeinTest by instance()
    private val kodeinTest1:KodeinTest by instance()
    private val userEntity:UserEntity by instance()
    private val userEntity1:UserEntity by instance()
    private val mContext:Context by instance()


    override fun layoutId() = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        LogUtils.d("kodeinTest=====${kodeinTest}/${kodeinTest1}")
        LogUtils.d("userEntity=====${userEntity}/${userEntity1}")
        LogUtils.d("appContextModule=====${mContext}")
    }







}