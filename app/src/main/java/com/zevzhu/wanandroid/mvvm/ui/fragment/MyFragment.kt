package com.zevzhu.wanandroid.mvvm.ui.fragment

import android.os.Bundle
import com.zevzhu.wanandroid.R
import com.zevzhu.wanandroid.databinding.MyFragmentBinding
import com.zevzhu.wanandroid.mvvm.base.BaseFragment
import com.zevzhu.wanandroid.mvvm.viewmodel.view.MyViewModel

class MyFragment : BaseFragment<MyViewModel, MyFragmentBinding>() {

    companion object {
        fun newInstance() = MyFragment()
    }

    override fun layoutId() = R.layout.my_fragment

    override fun initView(savedInstanceState: Bundle?) {
    }


}