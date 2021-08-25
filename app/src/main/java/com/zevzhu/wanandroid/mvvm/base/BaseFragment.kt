package com.zevzhu.wanandroid.mvvm.base

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.blankj.utilcode.util.LogUtils
import com.zevzhu.wanandroid.R
import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager
import me.hgj.jetpackmvvm.base.fragment.BaseVmDbFragment
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * 作者　: hegaojian
 * 时间　: 2019/12/21
 * 描述　: 你项目中的Fragment基类，在这里实现显示弹窗，吐司，还有自己的需求操作 ，如果不想用Databind，请继承
 * BaseVmFragment例如
 * abstract class BaseFragment<VM : BaseViewModel> : BaseVmFragment<VM>() {
 */
abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmDbFragment<VM, DB>() {


    private lateinit var mStatusManager: StatusLayoutManager
    private var statusView: View? = null

    /**
     * 当前Fragment绑定的视图布局
     */
    abstract override fun layoutId(): Int


    abstract override fun initView(savedInstanceState: Bundle?)


    protected open fun setupStatusView(v: View, showLoading: Boolean = true) {
        this.statusView = v
        createStatusLayout()
        if (showLoading)
            getStatusManager().showLoadingLayout()
    }

    protected open fun reload() {}

    protected open fun getStatusManager(): StatusLayoutManager {
        return mStatusManager
    }

    private fun createStatusLayout() {
        if (statusView == null) return
        mStatusManager = StatusLayoutManager.Builder(statusView!!)
            .setErrorLayout(R.layout.layout_error)
            .setLoadingLayout(R.layout.layout_loading)
            .setEmptyLayout(R.layout.layout_empty)
            .setErrorClickViewID(R.id.ivRetry)
            .setOnStatusChildClickListener(object : OnStatusChildClickListener {
                override fun onErrorChildClick(view: View?) {
                    reload()
                }

                override fun onEmptyChildClick(view: View?) {}

                override fun onCustomerChildClick(view: View?) {}

            })
            .build()
    }


    fun showContent() {

    }

    /**
     * 懒加载 只有当前fragment视图显示时才会触发该方法
     */
    override fun lazyLoadData() {}


    /**
     * 创建LiveData观察者 Fragment执行onViewCreated后触发
     */
    override fun createObserver() {
    }

    /**
     * Fragment执行onViewCreated后触发
     */
    override fun initData() {
    }

    /**
     * 打开等待框
     */
    override fun showLoading(message: String) {
        LogUtils.d("showLoading===========")
    }

    /**
     * 关闭等待框
     */
    override fun dismissLoading() {
        LogUtils.d("dismissLoading===========")
    }


    /**
     * 延迟加载 防止 切换动画还没执行完毕时数据就已经加载好了，这时页面会有渲染卡顿  bug
     * 这里传入你想要延迟的时间，延迟时间可以设置比转场动画时间长一点 单位： 毫秒
     * 不传默认 300毫秒
     * @return Long
     */
    override fun lazyLoadTime(): Long {
        return 300
    }
}