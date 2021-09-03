package com.zevzhu.wanandroid.mvvm.ui.fragment

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.LogUtils
import com.zevzhu.wanandroid.R
import com.zevzhu.wanandroid.data.ChapterEntity
import com.zevzhu.wanandroid.databinding.CollectFragmentBinding
import com.zevzhu.wanandroid.ext.initPage
import com.zevzhu.wanandroid.ext.initTitle
import com.zevzhu.wanandroid.ext.loadPageData
import com.zevzhu.wanandroid.mvvm.base.BaseFragment
import com.zevzhu.wanandroid.mvvm.ui.adapter.ChapterAdapter
import com.zevzhu.wanandroid.mvvm.viewmodel.request.CollectReqViewModel
import com.zevzhu.wanandroid.mvvm.viewmodel.view.UserViewModel
import kotlinx.android.synthetic.main.layout_recycleview.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction
import me.hgj.jetpackmvvm.ext.parseState

class CollectFragment : BaseFragment<UserViewModel, CollectFragmentBinding>() {

    private val collectReqVM: CollectReqViewModel by viewModels()
    private var mAdapter = ChapterAdapter(this, true)
    private var overLoad = false
    private var pos = 0

    override fun layoutId() = R.layout.collect_fragment

    override fun initView(savedInstanceState: Bundle?) {
        toolbar.initTitle("我的收藏")
        mAdapter.initPage(refreshLayout, recycleView, { adapter, view, position ->
            val entity = (adapter.data)[position] as ChapterEntity
            nav().navigateAction(R.id.action_global_webPageFragment, Bundle().apply {
                putInt("id", entity.id)
                putString("url", entity.link)
                putString("title", entity.title)
            })
        }, {
            when (overLoad) {
                true -> collectReqVM.getCollectList(false)
                false -> mAdapter.loadMoreModule.loadMoreEnd()
            }
        }, {
            collectReqVM.getCollectList(true)
        })
        mAdapter.setCollect { likeView, item, pos ->
            this.pos = pos
            collectReqVM.unCollectByMyList(item.id, item.originId)
        }
    }


    override fun createObserver() {
        collectReqVM.collectPageResult.observe(viewLifecycleOwner, Observer {
            overLoad = loadPageData(mAdapter, getStatusManager(), refreshLayout, it)
        })
        collectReqVM.unCollectResult.observe(viewLifecycleOwner, Observer { result ->
            parseState(result, {
                LogUtils.d("unCollectResult=========$pos")
                mAdapter.removeAt(pos)
            }, {
                LogUtils.e("unCollectResult=====${it.errorMsg}")
            })
        })
    }

    override fun lazyLoadData() {
        setupStatusView(recycleView)
        collectReqVM.getCollectList(true)
    }

    override fun reload() {
        collectReqVM.getCollectList(true)
    }

}