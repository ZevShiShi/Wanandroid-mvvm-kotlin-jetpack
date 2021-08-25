package com.zevzhu.wanandroid.mvvm.ui.fragment

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import com.zevzhu.wanandroid.R
import com.zevzhu.wanandroid.databinding.MainFragmentBinding
import com.zevzhu.wanandroid.mvvm.base.BaseFragment
import com.zevzhu.wanandroid.mvvm.ui.adapter.MainFragmentAdapter
import com.zevzhu.wanandroid.mvvm.viewmodel.view.MainViewModel
import kotlinx.android.synthetic.main.main_fragment.*

/**
 * 设置[androidx.viewpager2.widget.ViewPager2.setOffscreenPageLimit]设置需要懒加载的fragment个数
 * 配合在[BaseFragment.onResume]中使用布尔值做标记，抽象[BaseFragment.lazyLoadData]方法
 */
class MainFragment : BaseFragment<MainViewModel, MainFragmentBinding>() {

    private var mAdapter: MainFragmentAdapter? = null

    override fun layoutId() = R.layout.main_fragment

    override fun initView(savedInstanceState: Bundle?) {
        mAdapter = MainFragmentAdapter(this)
        vpMain.isUserInputEnabled = false; //true:滑动，false：禁止滑动
        vpMain.adapter = mAdapter
        for (i in 0 until mAdapter!!.itemCount) {
            tabLayout.addTab(tabLayout.newTab())
            val tab = tabLayout.getTabAt(i)
            tab?.customView = mAdapter?.getTabView(i)
        }
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }


            override fun onTabSelected(tab: TabLayout.Tab?) {
                vpMain.currentItem = tab!!.position
            }
        })
        vpMain.offscreenPageLimit = mAdapter!!.itemCount
    }
}