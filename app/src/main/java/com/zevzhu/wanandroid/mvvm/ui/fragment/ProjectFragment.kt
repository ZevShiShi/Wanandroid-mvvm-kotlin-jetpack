package com.zevzhu.wanandroid.mvvm.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.LogUtils
import com.zevzhu.wanandroid.R
import com.zevzhu.wanandroid.databinding.ProjectFragmentBinding
import com.zevzhu.wanandroid.ext.bindViewPager2
import com.zevzhu.wanandroid.ext.init
import com.zevzhu.wanandroid.mvvm.base.BaseFragment
import com.zevzhu.wanandroid.mvvm.viewmodel.request.ProjectReqViewModel
import com.zevzhu.wanandroid.mvvm.viewmodel.view.ProjectViewModel
import kotlinx.android.synthetic.main.layout_tab_viewpager.*
import me.hgj.jetpackmvvm.ext.parseState

class ProjectFragment : BaseFragment<ProjectViewModel, ProjectFragmentBinding>() {

    private val mFragments: MutableList<Fragment> = mutableListOf()
    private val mTabs: MutableList<String> = mutableListOf()
    private val projectVM: ProjectReqViewModel by viewModels()

    companion object {
        fun newInstance() = ProjectFragment()
    }

    override fun layoutId() = R.layout.project_fragment

    override fun initView(savedInstanceState: Bundle?) {
        viewPager.init(this, mFragments)
        tabIndicator.bindViewPager2(viewPager, mTabs)
    }


    override fun createObserver() {
        projectVM.projectResult.observe(viewLifecycleOwner, Observer { result ->
            parseState(result, {
                mTabs.clear()
                mFragments.clear()
                mTabs.add("最新项目")
                mFragments.add(ProjectChildFragment.newInstance(-1, true))
                for (data in it) {
                    mTabs.add(data.name)
                    mFragments.add(ProjectChildFragment.newInstance(data.id, false))
                }
                tabIndicator.navigator.notifyDataSetChanged()
                viewPager.adapter?.notifyDataSetChanged()
                viewPager.offscreenPageLimit = mTabs.size
                getStatusManager().showSuccessLayout()
            }, {
                LogUtils.e("createObserver=====$it")
                getStatusManager().showErrorLayout()
            })
        })
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        setupStatusView(llRoot)
        projectVM.getProTab()
    }

    override fun reload() {
        projectVM.getProTab()
    }
}