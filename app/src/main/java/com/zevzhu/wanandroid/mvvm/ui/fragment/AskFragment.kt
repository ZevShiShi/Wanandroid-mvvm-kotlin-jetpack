package com.zevzhu.wanandroid.mvvm.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zevzhu.wanandroid.R
import com.zevzhu.wanandroid.databinding.AskFragmentBinding
import com.zevzhu.wanandroid.databinding.TreeFragmentBinding
import com.zevzhu.wanandroid.mvvm.base.BaseFragment
import com.zevzhu.wanandroid.mvvm.viewmodel.view.TreeViewModel

class AskFragment : BaseFragment<TreeViewModel,AskFragmentBinding>() {

    companion object {
        fun newInstance() = AskFragment()
    }

    override fun layoutId() = R.layout.ask_fragment

    override fun initView(savedInstanceState: Bundle?) {
    }


}