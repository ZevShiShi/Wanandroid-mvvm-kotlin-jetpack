package com.zevzhu.wanandroid.mvvm.ui.fragment

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.zevzhu.wanandroid.R
import com.zevzhu.wanandroid.app.eventViewModel
import com.zevzhu.wanandroid.databinding.MyFragmentBinding
import com.zevzhu.wanandroid.ext.isLogin
import com.zevzhu.wanandroid.ext.nav
import com.zevzhu.wanandroid.ext.navigateAction
import com.zevzhu.wanandroid.ext.onClick
import com.zevzhu.wanandroid.mvvm.base.BaseFragment
import com.zevzhu.wanandroid.mvvm.viewmodel.request.UserReqViewModel
import com.zevzhu.wanandroid.mvvm.viewmodel.view.MyViewModel
import kotlinx.android.synthetic.main.my_fragment.*
import me.hgj.jetpackmvvm.ext.parseState
import me.hgj.jetpackmvvm.ext.util.notNull

class MyFragment : BaseFragment<MyViewModel, MyFragmentBinding>() {

    private val userReqVM: UserReqViewModel by viewModels()

    companion object {
        fun newInstance() = MyFragment()
    }

    override fun layoutId() = R.layout.my_fragment

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.viewModel = mViewModel
        stvUser.onClick {
            if (!isLogin()) {
                nav().navigateAction(R.id.action_myFragment_to_loginFragment)
            }
        }
        stvScore.onClick {
            userReqVM.loginOut()
            SPUtils.getInstance().put("cookie", "")
        }
        stvCollect.onClick {
        }
        stvChapter.onClick {

        }

        refreshLayout.setOnRefreshListener {
            if (isLogin()) {
                userReqVM.getUserInfo()
            } else {
                refreshLayout.finishRefresh()
            }
        }
    }

    override fun lazyLoadData() {
        refreshLayout.autoRefresh()
    }

    override fun createObserver() {
        // 这里已经获取到值，返回当前界面立马获取到值
        eventViewModel.userInfo.observeInFragment(this, Observer {
            LogUtils.d("userInfo==createObserver==${it}")
            it.notNull({
//                userReqVM.getUserInfo()
                refreshLayout.autoRefresh()
            })
        })

        userReqVM.userResult.observe(viewLifecycleOwner, Observer { result ->
            parseState(result, {
                refreshLayout.finishRefresh()
                stvUser.setLeftTopString(it.username)
                stvUser.setLeftBottomString("id：${it.userId}   排名：${it.rank}")
            }, {
                refreshLayout.finishRefresh()
                ToastUtils.showShort(it.errorMsg)
            })
        })


    }

}