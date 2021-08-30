package com.zevzhu.wanandroid.mvvm.ui.fragment

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.zevzhu.wanandroid.R
import com.zevzhu.wanandroid.data.ChapterEntity
import com.zevzhu.wanandroid.databinding.SysChildFragmentBinding
import com.zevzhu.wanandroid.ext.initPage
import com.zevzhu.wanandroid.ext.loadPageData
import com.zevzhu.wanandroid.mvvm.base.BaseFragment
import com.zevzhu.wanandroid.mvvm.ui.adapter.ChapterAdapter
import com.zevzhu.wanandroid.mvvm.viewmodel.request.TreeReqViewModel
import com.zevzhu.wanandroid.mvvm.viewmodel.view.TreeViewModel
import kotlinx.android.synthetic.main.layout_recycleview.*
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction

class SysChildFragment : BaseFragment<TreeViewModel, SysChildFragmentBinding>() {

    private val mAdapter = ChapterAdapter(false)
    private val treeVM: TreeReqViewModel by viewModels()
    private var cid = 0
    private var overLoad = false

    companion object {
        fun newInstance(cid: Int): SysChildFragment {
            return SysChildFragment().apply {
                Bundle().run {
                    putInt("cid", cid)
                    arguments = this
                }
            }
        }
    }

    override fun layoutId() = R.layout.sys_child_fragment

    override fun initView(savedInstanceState: Bundle?) {
        cid = requireArguments().getInt("cid")
        mAdapter.initPage(refreshLayout, recycleView, { adapter, view, position ->
            val entity = (adapter.data)[position] as ChapterEntity
            nav().navigateAction(R.id.action_global_webPageFragment, Bundle().apply {
                putInt("id", entity.id)
                putString("url", entity.link)
                putString("title", entity.title)
            })
        }, {
            when (overLoad) {
                true -> treeVM.getSysDetailList(false, cid)
                false -> mAdapter.loadMoreModule.loadMoreEnd()
            }
        }, {
            treeVM.getSysDetailList(true, cid)
        })
    }

    override fun reload() {
        treeVM.getSysDetailList(true, cid)
    }

    override fun lazyLoadData() {
        setupStatusView(recycleView)
        treeVM.getSysDetailList(true, cid)
    }

    override fun createObserver() {
        treeVM.sysDetailResult.observe(viewLifecycleOwner, Observer {
            overLoad = loadPageData(mAdapter, getStatusManager(), refreshLayout, it)
        })
    }
}