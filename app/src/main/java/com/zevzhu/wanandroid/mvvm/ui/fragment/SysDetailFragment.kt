package com.zevzhu.wanandroid.mvvm.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.zevzhu.wanandroid.R
import com.zevzhu.wanandroid.data.SysChildEntity
import com.zevzhu.wanandroid.databinding.SysDetailFragmentBinding
import com.zevzhu.wanandroid.ext.bindViewPager2
import com.zevzhu.wanandroid.ext.init
import com.zevzhu.wanandroid.mvvm.base.BaseFragment
import com.zevzhu.wanandroid.mvvm.viewmodel.view.TreeViewModel
import kotlinx.android.synthetic.main.layout_tab_viewpager.*

class SysDetailFragment : BaseFragment<TreeViewModel, SysDetailFragmentBinding>() {

    private val mFragments: MutableList<Fragment> = mutableListOf()
    private val mTabs: MutableList<String> = mutableListOf()
    private val mDatas: ArrayList<SysChildEntity> = arrayListOf()
    private var mSelectedIndex = 0


    override fun layoutId() = R.layout.sys_detail_fragment

    override fun initView(savedInstanceState: Bundle?) {
        mDatas.clear()
        mTabs.clear()
        mFragments.clear()
        mDatas.addAll(requireArguments().getParcelableArrayList("sysArray")!!)
        mDatas.forEachIndexed { index, it ->
            mTabs.add(it.name)
            mFragments.add(SysChildFragment.newInstance(it.id))
            if (it.select) {
                mSelectedIndex = index
            }
        }
        viewPager.init(this, mFragments)
        tabIndicator.bindViewPager2(viewPager, mTabs)
    }

    override fun lazyLoadData() {
        viewPager.currentItem = mSelectedIndex
    }
}