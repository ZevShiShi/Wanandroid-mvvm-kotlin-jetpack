package com.zevzhu.wanandroid.mvvm.ui.fragment

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.zevzhu.wanandroid.R
import com.zevzhu.wanandroid.data.ChapterEntity
import com.zevzhu.wanandroid.databinding.WxChildFragmentBinding
import com.zevzhu.wanandroid.ext.initPage
import com.zevzhu.wanandroid.ext.loadPageData
import com.zevzhu.wanandroid.mvvm.base.BaseFragment
import com.zevzhu.wanandroid.mvvm.ui.adapter.ChapterAdapter
import com.zevzhu.wanandroid.mvvm.viewmodel.request.WxReqViewModel
import com.zevzhu.wanandroid.mvvm.viewmodel.view.WxViewModel
import kotlinx.android.synthetic.main.layout_recycleview.*
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction

class WxChildFragment : BaseFragment<WxViewModel, WxChildFragmentBinding>() {

    private val mAdapter = ChapterAdapter(false)
    private val wxVM: WxReqViewModel by viewModels()
    private var mId = 0
    private var overLoad = false

    companion object {
        fun newInstance(id: Int): WxChildFragment {
            return WxChildFragment().apply {
                arguments = Bundle().apply {
                    putInt("id", id)
                }
            }
        }
    }

    override fun layoutId() = R.layout.wx_child_fragment

    override fun initView(savedInstanceState: Bundle?) {
        mId = requireArguments().getInt("id")
        mAdapter.apply {
            initPage(refreshLayout, recycleView,
                { adapter, view, position ->
                    val entity = (adapter.data)[position] as ChapterEntity
                    nav().navigateAction(R.id.action_global_webPageFragment, Bundle().apply {
                        putInt("id", entity.id)
                        putString("url", entity.link)
                        putString("title", entity.title)
                    })
                },
                {
                    when (overLoad) {
                        true -> wxVM.getWxList(mId, false)
                        false -> loadMoreModule.loadMoreEnd()
                    }
                },
                {
                    wxVM.getWxList(mId, true)
                })
        }


    }

    override fun lazyLoadData() {
        setupStatusView(recycleView)
        wxVM.getWxList(mId, true)
    }


    override fun createObserver() {
        wxVM.wxListResult.observe(viewLifecycleOwner, Observer {
            overLoad = loadPageData(mAdapter, getStatusManager(), refreshLayout, it)
        })

    }

    override fun reload() {
        wxVM.getWxList(mId, true)
    }


}