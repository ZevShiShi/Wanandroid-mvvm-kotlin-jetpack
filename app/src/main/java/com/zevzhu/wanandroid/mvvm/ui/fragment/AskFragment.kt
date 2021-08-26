package com.zevzhu.wanandroid.mvvm.ui.fragment

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.zevzhu.wanandroid.R
import com.zevzhu.wanandroid.data.ChapterEntity
import com.zevzhu.wanandroid.databinding.AskFragmentBinding
import com.zevzhu.wanandroid.ext.initPage
import com.zevzhu.wanandroid.ext.loadPageData
import com.zevzhu.wanandroid.mvvm.base.BaseFragment
import com.zevzhu.wanandroid.mvvm.ui.adapter.ChapterAdapter
import com.zevzhu.wanandroid.mvvm.viewmodel.request.TreeReqViewModel
import com.zevzhu.wanandroid.mvvm.viewmodel.view.TreeViewModel
import kotlinx.android.synthetic.main.layout_recycleview.*
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction

class AskFragment : BaseFragment<TreeViewModel, AskFragmentBinding>() {

    private val mAdapter = ChapterAdapter()
    private val treeReqVM: TreeReqViewModel by viewModels()
    private var overLoad = false


    companion object {
        fun newInstance() = AskFragment()
    }

    override fun layoutId() = R.layout.ask_fragment

    override fun initView(savedInstanceState: Bundle?) {
        mAdapter.initPage(refreshLayout, recycleView, { adapter, view, position ->
            val entity = (adapter.data)[position] as ChapterEntity
            nav().navigateAction(R.id.action_global_webPageFragment, Bundle().apply {
                putInt("id", entity.id)
                putString("url", entity.link)
                putString("title", entity.title)
            })
        }, {
            if (overLoad) {
                treeReqVM.getAskList(false)
            } else {
                mAdapter.loadMoreModule.loadMoreEnd()
            }
        }, {
            treeReqVM.getAskList(true)
        })
    }

    override fun lazyLoadData() {
        setupStatusView(recycleView)
        treeReqVM.getAskList(true)
    }

    override fun createObserver() {
        treeReqVM.askResult.observe(viewLifecycleOwner, Observer {
            overLoad = loadPageData(mAdapter, getStatusManager(), refreshLayout, it)
        })
    }

    override fun reload() {
        treeReqVM.getAskList(true)
    }

}