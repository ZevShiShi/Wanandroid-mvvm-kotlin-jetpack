package com.zevzhu.wanandroid.mvvm.ui.fragment

import android.os.Bundle
import android.webkit.WebView
import androidx.activity.OnBackPressedCallback
import com.blankj.utilcode.util.LogUtils
import com.zevzhu.wanandroid.R
import com.zevzhu.wanandroid.databinding.WebPageFragmentBinding
import com.zevzhu.wanandroid.ext.*
import com.zevzhu.wanandroid.mvvm.base.BaseFragment
import com.zevzhu.wanandroid.mvvm.ui.activity.MainActivity
import com.zevzhu.wanandroid.mvvm.viewmodel.view.WebPageViewModel
import kotlinx.android.synthetic.main.toolbar_layout.*
import kotlinx.android.synthetic.main.web_page_fragment.*


class WebPageFragment : BaseFragment<WebPageViewModel, WebPageFragmentBinding>() {

    override fun layoutId() = R.layout.web_page_fragment

    override fun initView(savedInstanceState: Bundle?) {
        val id = requireArguments().getInt("id")
        val url = requireArguments().getString("url")
        val title = requireArguments().getString("title")
        LogUtils.d("WebPageFragment====$id====$url")
        initWeb(url!!, webContainer, object : WebCallbackImpl() {
            override fun onProgress(view: WebView?, newProgress: Int) {
                super.onProgress(view, newProgress)
                if (newProgress >= 80) {
                    getStatusManager().showSuccessLayout()
                }

            }
        })
        (requireActivity() as MainActivity).setSupportActionBar(toolbar)
        toolbar.run {
            initClose(title!!) {
                goBack()
            }
        }
        // fragment返回按键监听
        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    goBack()
                }
            })
    }


    override fun lazyLoadData() {
        super.lazyLoadData()
        setupStatusView(webContainer, true)
    }

    override fun onResume() {
        resumeWeb()
        super.onResume()
    }

    override fun onPause() {
        pauseWeb()
        super.onPause()
    }

    override fun onDestroyView() {
        destroyWeb()
        super.onDestroyView()
    }
}