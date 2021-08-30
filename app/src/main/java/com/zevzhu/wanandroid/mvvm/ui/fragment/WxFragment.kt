package com.zevzhu.wanandroid.mvvm.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.zevzhu.wanandroid.R
import com.zevzhu.wanandroid.databinding.WxFragmentBinding
import com.zevzhu.wanandroid.ext.bindViewPager2
import com.zevzhu.wanandroid.ext.init
import com.zevzhu.wanandroid.mvvm.base.BaseFragment
import com.zevzhu.wanandroid.mvvm.viewmodel.request.WxReqViewModel
import com.zevzhu.wanandroid.mvvm.viewmodel.view.WxViewModel
import kotlinx.android.synthetic.main.layout_tab_viewpager.*
import me.hgj.jetpackmvvm.ext.parseState

class WxFragment : BaseFragment<WxViewModel, WxFragmentBinding>() {

    private val mTitles = mutableListOf<String>()
    private val mFragments = mutableListOf<Fragment>()
    private val wxVM: WxReqViewModel by viewModels()


    companion object {
        fun newInstance() = WxFragment()
    }

    override fun layoutId() = R.layout.wx_fragment

    override fun initView(savedInstanceState: Bundle?) {
        viewPager.init(this, mFragments)
        tabIndicator.bindViewPager2(viewPager, mTitles)
    }


    override fun lazyLoadData() {
        setupStatusView(llRoot)
        wxVM.getWxTab()
    }

    override fun createObserver() {
        wxVM.wxResult.observe(viewLifecycleOwner, Observer { result ->
            parseState(result, {
                getStatusManager().showSuccessLayout()
                it.forEach { entity ->
                    mTitles.add(entity.name)
                    mFragments.add(WxChildFragment.newInstance(entity.id))
                }
                tabIndicator.navigator.notifyDataSetChanged()
                viewPager.adapter?.notifyDataSetChanged()

            }, {
                getStatusManager().showErrorLayout()
            })
        })

    }

    override fun reload() {
        wxVM.getWxTab()
    }


}