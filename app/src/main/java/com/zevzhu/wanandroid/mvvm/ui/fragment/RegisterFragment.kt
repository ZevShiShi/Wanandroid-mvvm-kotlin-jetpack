package com.zevzhu.wanandroid.mvvm.ui.fragment

import android.os.Bundle
import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.zevzhu.wanandroid.R
import com.zevzhu.wanandroid.app.eventViewModel
import com.zevzhu.wanandroid.databinding.RegisterFragmentBinding
import com.zevzhu.wanandroid.ext.hideKeyboard
import com.zevzhu.wanandroid.mvvm.base.BaseFragment
import com.zevzhu.wanandroid.mvvm.viewmodel.request.UserReqViewModel
import com.zevzhu.wanandroid.mvvm.viewmodel.view.LoginRegViewModel
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction
import me.hgj.jetpackmvvm.ext.parseState

class RegisterFragment : BaseFragment<LoginRegViewModel, RegisterFragmentBinding>() {


    private val userReqVM: UserReqViewModel by viewModels()

    companion object {
        fun newInstance() = RegisterFragment()
    }

    override fun layoutId() = R.layout.register_fragment

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.viewmodel = mViewModel
        mDatabind.click = ProxyClick()


    }


    override fun createObserver() {
        userReqVM.regResult.observe(viewLifecycleOwner, Observer { result ->
            parseState(result, {
                eventViewModel.userInfo.value = it
                ToastUtils.showShort("注册登录成功")
                nav().navigateAction(R.id.action_registerFragment_to_main)
            }, {
                ToastUtils.showShort(it.errorMsg)
            })
        })

    }

    inner class ProxyClick {


        fun clickReg() {
            if (mViewModel.username.get().isEmpty() ||
                mViewModel.password.get().isEmpty() ||
                mViewModel.repassword.get().isEmpty()
            ) {
                ToastUtils.showShort("请填写账号密码")
                return
            }
            if (mViewModel.password.get() != mViewModel.repassword.get()) {
                ToastUtils.showShort("两次输入的密码不一致")
                return
            }
            userReqVM.register(
                mViewModel.username.get(),
                mViewModel.password.get()
            )
            hideKeyboard()
        }


        fun clear() {
            mViewModel.username.set("")
        }

        var onCheckedChangeListener =
            CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                mViewModel.isShowPwd.set(isChecked)
            }

        var onCheckedChangeListener2 =
            CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                mViewModel.isShowRePwd.set(isChecked)
            }
    }

}