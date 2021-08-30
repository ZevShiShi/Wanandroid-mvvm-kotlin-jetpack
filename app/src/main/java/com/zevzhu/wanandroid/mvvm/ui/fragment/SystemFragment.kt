package com.zevzhu.wanandroid.mvvm.ui.fragment

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.LogUtils
import com.zevzhu.wanandroid.R
import com.zevzhu.wanandroid.data.SysChildEntity
import com.zevzhu.wanandroid.databinding.SystemFragmentBinding
import com.zevzhu.wanandroid.ext.initPageNoLoadMore
import com.zevzhu.wanandroid.ext.nav
import com.zevzhu.wanandroid.ext.navigateAction
import com.zevzhu.wanandroid.ext.parseStateEx
import com.zevzhu.wanandroid.mvvm.base.BaseFragment
import com.zevzhu.wanandroid.mvvm.ui.adapter.SysAdapter
import com.zevzhu.wanandroid.mvvm.viewmodel.request.TreeReqViewModel
import com.zevzhu.wanandroid.mvvm.viewmodel.view.TreeViewModel
import kotlinx.android.synthetic.main.layout_recycleview.*

class SystemFragment : BaseFragment<TreeViewModel, SystemFragmentBinding>() {

    private val mAdapter = SysAdapter()
    private val treeReqVM: TreeReqViewModel by viewModels()

    companion object {
        fun newInstance() = SystemFragment()
    }

    override fun layoutId() = R.layout.system_fragment

    override fun initView(savedInstanceState: Bundle?) {
        mAdapter.apply {
            initPageNoLoadMore(refreshLayout, recycleView, { adapter, view, position ->
            }, {
                treeReqVM.getSystemList()
            })
            itemClick { adapter, view, position ->
                val data = adapter.data as ArrayList<SysChildEntity>
                data.forEachIndexed { index, entity ->
                    entity.select = false
                    if (index == position) {
                        entity.select = true
                    }
                }
                nav().navigateAction(R.id.action_system_to_sysDetail, Bundle().apply {
                    putParcelableArrayList("sysArray", data)
                })
            }
        }
    }


    override fun lazyLoadData() {
        setupStatusView(recycleView)
        treeReqVM.getSystemList()
    }

    override fun reload() {
        treeReqVM.getSystemList()
    }

    override fun createObserver() {
        treeReqVM.systemResult.observe(viewLifecycleOwner, Observer { resultStatus ->
            parseStateEx(resultStatus, getStatusManager(), refreshLayout, {
                mAdapter.setNewInstance(it)
            }, {
                LogUtils.e("systemResult=========${it}")
            })
        })
    }
}