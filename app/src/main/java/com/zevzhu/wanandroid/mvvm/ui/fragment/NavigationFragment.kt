package com.zevzhu.wanandroid.mvvm.ui.fragment

import android.os.Bundle
import com.zevzhu.wanandroid.R
import com.zevzhu.wanandroid.databinding.NavigationFragmentBinding
import com.zevzhu.wanandroid.mvvm.base.BaseFragment
import com.zevzhu.wanandroid.mvvm.viewmodel.view.TreeViewModel

class NavigationFragment : BaseFragment<TreeViewModel, NavigationFragmentBinding>() {

    companion object {
        fun newInstance() = NavigationFragment()
    }

    override fun layoutId() = R.layout.navigation_fragment

    override fun initView(savedInstanceState: Bundle?) {
    }


}