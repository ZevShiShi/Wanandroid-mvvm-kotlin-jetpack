package com.zevzhu.wanandroid.mvvm.ui.fragment

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.zevzhu.wanandroid.R
import com.zevzhu.wanandroid.app.eventViewModel
import com.zevzhu.wanandroid.databinding.MyFragmentBinding
import com.zevzhu.wanandroid.ext.isLogin
import com.zevzhu.wanandroid.ext.nav
import com.zevzhu.wanandroid.ext.navigateAction
import com.zevzhu.wanandroid.ext.onClick
import com.zevzhu.wanandroid.mvvm.base.BaseFragment
import com.zevzhu.wanandroid.mvvm.viewmodel.request.UserReqViewModel
import com.zevzhu.wanandroid.mvvm.viewmodel.view.MyViewModel
import kotlinx.android.synthetic.main.my_fragment.*

class MyFragment : BaseFragment<MyViewModel, MyFragmentBinding>() {

    private val userReqVM: UserReqViewModel by viewModels()

    companion object {
        fun newInstance() = MyFragment()
    }

    override fun layoutId() = R.layout.my_fragment

    override fun initView(savedInstanceState: Bundle?) {
        stvUser.onClick {
            if (!isLogin())
                nav().navigateAction(R.id.action_myFragment_to_loginFragment)
        }
        stvScore.onClick {
            userReqVM.loginOut()
            SPUtils.getInstance().put("cookie", "")
        }
        stvCollect.onClick {
        }
        stvChapter.onClick {

        }
    }

    override fun lazyLoadData() {
        eventViewModel.userInfo.value?.run {
            LogUtils.d("userInfo====${toString()}")
        }
    }

    override fun createObserver() {
        eventViewModel.userInfo.observeInFragment(this, Observer {
            LogUtils.d("userInfo==createObserver==${it}")
        })
    }

}