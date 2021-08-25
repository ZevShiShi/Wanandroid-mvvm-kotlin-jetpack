package com.zevzhu.wanandroid.mvvm.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.zevzhu.wanandroid.R
import com.zevzhu.wanandroid.databinding.TreeFragmentBinding
import com.zevzhu.wanandroid.ext.bindViewPager2
import com.zevzhu.wanandroid.ext.init
import com.zevzhu.wanandroid.mvvm.base.BaseFragment
import com.zevzhu.wanandroid.mvvm.viewmodel.view.TreeViewModel
import kotlinx.android.synthetic.main.layout_tab_viewpager.*

class TreeFragment : BaseFragment<TreeViewModel, TreeFragmentBinding>() {


    private val mTitles = mutableListOf("广场", "每日一问", "体系", "导航")
    private val mFragments = mutableListOf<Fragment>()

    companion object {
        fun newInstance() = TreeFragment()
    }

    override fun layoutId() = R.layout.tree_fragment

    override fun initView(savedInstanceState: Bundle?) {
        mFragments.clear()
        mFragments.add(PlazaFragment.newInstance())
        mFragments.add(AskFragment.newInstance())
        mFragments.add(SystemFragment.newInstance())
        mFragments.add(NavigationFragment.newInstance())
        viewPager.init(this, mFragments)
        tabIndicator.bindViewPager2(viewPager, mTitles)
    }
}