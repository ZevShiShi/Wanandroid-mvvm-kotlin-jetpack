package com.zevzhu.wanandroid.mvvm.ui.fragment

import android.os.Bundle
import com.zevzhu.wanandroid.R
import com.zevzhu.wanandroid.databinding.RegisterFragmentBinding
import com.zevzhu.wanandroid.mvvm.base.BaseFragment
import com.zevzhu.wanandroid.mvvm.viewmodel.view.RegisterViewModel

class RegisterFragment : BaseFragment<RegisterViewModel, RegisterFragmentBinding>() {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    override fun layoutId() = R.layout.register_fragment

    override fun initView(savedInstanceState: Bundle?) {
    }

}