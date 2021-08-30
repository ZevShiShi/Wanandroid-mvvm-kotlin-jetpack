package com.zevzhu.wanandroid.mvvm.ui.fragment

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.LogUtils
import com.zevzhu.wanandroid.R
import com.zevzhu.wanandroid.data.ChapterEntity
import com.zevzhu.wanandroid.databinding.NavigationFragmentBinding
import com.zevzhu.wanandroid.ext.initPageNoLoadMore
import com.zevzhu.wanandroid.ext.parseStateEx
import com.zevzhu.wanandroid.mvvm.base.BaseFragment
import com.zevzhu.wanandroid.mvvm.ui.adapter.NavAdapter
import com.zevzhu.wanandroid.mvvm.viewmodel.request.TreeReqViewModel
import com.zevzhu.wanandroid.mvvm.viewmodel.view.TreeViewModel
import kotlinx.android.synthetic.main.layout_recycleview.*
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction

class NavigationFragment : BaseFragment<TreeViewModel, NavigationFragmentBinding>() {

    private val mAdapter = NavAdapter()
    private val treeVM: TreeReqViewModel by viewModels()

    companion object {
        fun newInstance() = NavigationFragment()
    }

    override fun layoutId() = R.layout.navigation_fragment

    override fun initView(savedInstanceState: Bundle?) {
        mAdapter.apply {
            initPageNoLoadMore(refreshLayout, recycleView, { adapter, view, position -> }, {
                treeVM.getNavList()
            })
            itemClick { adapter, view, position ->
                val entity = (adapter.data)[position] as ChapterEntity
                nav().navigateAction(R.id.action_global_webPageFragment, Bundle().apply {
                    putInt("id", entity.id)
                    putString("url", entity.link)
                    putString("title", entity.title)
                })
            }
        }

    }

    override fun lazyLoadData() {
        setupStatusView(recycleView)
        treeVM.getNavList()
    }

    override fun createObserver() {
        treeVM.navResult.observe(viewLifecycleOwner, Observer { resultStatus ->
            parseStateEx(resultStatus, getStatusManager(), refreshLayout, {
                mAdapter.setNewInstance(it)
            }, {
                LogUtils.e("navResult=======${it.errorMsg}")
            })
        })

    }

    override fun reload() {
        treeVM.getNavList()
    }


}