package com.zevzhu.wanandroid.mvvm.ui.fragment

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.LogUtils
import com.zevzhu.wanandroid.R
import com.zevzhu.wanandroid.data.ChapterEntity
import com.zevzhu.wanandroid.databinding.ProjectChildFragmentBinding
import com.zevzhu.wanandroid.ext.initPage
import com.zevzhu.wanandroid.ext.loadPageData
import com.zevzhu.wanandroid.mvvm.base.BaseFragment
import com.zevzhu.wanandroid.mvvm.ui.adapter.ChapterAdapter
import com.zevzhu.wanandroid.mvvm.viewmodel.request.ProjectReqViewModel
import com.zevzhu.wanandroid.mvvm.viewmodel.view.ProjectViewModel
import kotlinx.android.synthetic.main.layout_recycleview.*
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction

class ProjectChildFragment : BaseFragment<ProjectViewModel, ProjectChildFragmentBinding>() {


    private var cid = 0
    private var isNew = false
    private val projectVM: ProjectReqViewModel by viewModels()
    private val mAdapterEx = ChapterAdapter(false)
    private var overLoad = false

    companion object {
        fun newInstance(cid: Int, isNew: Boolean): ProjectChildFragment {
            return ProjectChildFragment().apply {
                val bundle = Bundle()
                bundle.putInt("cid", cid)
                bundle.putBoolean("isNew", isNew)
                arguments = bundle
            }
        }
    }

    override fun layoutId() = R.layout.project_child_fragment

    override fun initView(savedInstanceState: Bundle?) {
        addLoadingObserve(projectVM)

        mAdapterEx.initPage(refreshLayout, recycleView, { adapter, view, position ->
            val entity = (adapter.data)[position] as ChapterEntity
            nav().navigateAction(R.id.action_global_webPageFragment, Bundle().apply {
                putInt("id", entity.id)
                putString("url", entity.link)
                putString("title", entity.title)
            })
        }, {
            if (overLoad) {
                projectVM.getProChildList(cid, false, isNew)
            } else {
                mAdapterEx.loadMoreModule.loadMoreEnd()
            }
        }, {
            projectVM.getProChildList(cid, true, isNew)
        })
    }

    override fun createObserver() {
        projectVM.proChildResult.observe(viewLifecycleOwner, Observer {
            overLoad = loadPageData(mAdapterEx, getStatusManager(), refreshLayout, it)
        })
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        setupStatusView(recycleView, true)
        cid = requireArguments().getInt("cid")
        isNew = requireArguments().getBoolean("isNew")
        LogUtils.d("cid========${cid}========isNew=====${isNew}")
        projectVM.getProChildList(cid, true, isNew)
    }

}