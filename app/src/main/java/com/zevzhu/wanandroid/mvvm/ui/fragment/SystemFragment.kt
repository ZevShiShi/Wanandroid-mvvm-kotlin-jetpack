package com.zevzhu.wanandroid.mvvm.ui.fragment

import android.os.Bundle
import com.zevzhu.wanandroid.R
import com.zevzhu.wanandroid.databinding.SystemFragmentBinding
import com.zevzhu.wanandroid.mvvm.base.BaseFragment
import com.zevzhu.wanandroid.mvvm.viewmodel.view.TreeViewModel

class SystemFragment : BaseFragment<TreeViewModel, SystemFragmentBinding>() {

    companion object {
        fun newInstance() = SystemFragment()
    }

    override fun layoutId() = R.layout.system_fragment

    override fun initView(savedInstanceState: Bundle?) {
    }
}