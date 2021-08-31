package com.zevzhu.wanandroid.mvvm.ui.fragment

import android.os.Bundle
import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ToastUtils
import com.zevzhu.wanandroid.R
import com.zevzhu.wanandroid.app.eventViewModel
import com.zevzhu.wanandroid.databinding.LoginFragmentBinding
import com.zevzhu.wanandroid.ext.nav
import com.zevzhu.wanandroid.ext.navigateAction
import com.zevzhu.wanandroid.mvvm.base.BaseFragment
import com.zevzhu.wanandroid.mvvm.viewmodel.request.UserReqViewModel
import com.zevzhu.wanandroid.mvvm.viewmodel.view.LoginRegViewModel
import me.hgj.jetpackmvvm.ext.parseState

class LoginFragment : BaseFragment<LoginRegViewModel, LoginFragmentBinding>() {


    private val userReqVM: UserReqViewModel by viewModels()

    companion object {
        fun newInstance() = LoginFragment()
    }

    override fun layoutId() = R.layout.login_fragment

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.viewmodel = mViewModel
        mDatabind.click = ProxyClick()
    }

    override fun createObserver() {
        userReqVM.loginResult.observe(viewLifecycleOwner, Observer { result ->
            parseState(result, {
                eventViewModel.userInfo.value = it
                nav().navigateUp()
            }, {
                ToastUtils.showShort(it.errorMsg)
            })
        })
    }

    inner class ProxyClick {

        fun clickLogin() {
            if (mViewModel.username.get().isEmpty() || mViewModel.password.get().isEmpty()) {
                ToastUtils.showShort("请填写账号密码")
                return
            }
            userReqVM.login(mViewModel.username.get(), mViewModel.password.get())
            if (KeyboardUtils.isSoftInputVisible(requireActivity())) {
                KeyboardUtils.toggleSoftInput()
            }
        }

        fun clickReg() {
            nav().navigateAction(R.id.action_loginFragment_to_registerFragment)
        }


        fun clear() {
            mViewModel.username.set("")
        }

        var onCheckedChangeListener =
            CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                mViewModel.isShowPwd.set(isChecked)
            }
    }

}