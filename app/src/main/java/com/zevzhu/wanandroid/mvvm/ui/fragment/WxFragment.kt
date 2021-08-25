package com.zevzhu.wanandroid.mvvm.ui.fragment

import android.os.Bundle
import com.zevzhu.wanandroid.R
import com.zevzhu.wanandroid.databinding.WxFragmentBinding
import com.zevzhu.wanandroid.mvvm.base.BaseFragment
import com.zevzhu.wanandroid.mvvm.viewmodel.view.WxViewModel

class WxFragment : BaseFragment<WxViewModel, WxFragmentBinding>() {

    companion object {
        fun newInstance() = WxFragment()
    }

    override fun layoutId() = R.layout.wx_fragment

    override fun initView(savedInstanceState: Bundle?) {
    }


}