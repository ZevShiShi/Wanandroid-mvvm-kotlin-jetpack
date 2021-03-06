package com.zevzhu.wanandroid.mvvm.ui.fragment

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ObjectUtils
import com.blankj.utilcode.util.ToastUtils
import com.youth.banner.Banner
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnPageChangeListener
import com.youth.banner.transformer.ZoomOutPageTransformer
import com.zevzhu.wanandroid.R
import com.zevzhu.wanandroid.data.BannerEntity
import com.zevzhu.wanandroid.data.ChapterEntity
import com.zevzhu.wanandroid.databinding.HomeFragmentBinding
import com.zevzhu.wanandroid.ext.initPage
import com.zevzhu.wanandroid.ext.initTitle
import com.zevzhu.wanandroid.ext.loadPageData
import com.zevzhu.wanandroid.mvvm.base.BaseFragment
import com.zevzhu.wanandroid.mvvm.ui.adapter.BannerAdapter
import com.zevzhu.wanandroid.mvvm.ui.adapter.ChapterAdapter
import com.zevzhu.wanandroid.mvvm.viewmodel.request.CollectReqViewModel
import com.zevzhu.wanandroid.mvvm.viewmodel.request.HomeReqViewModel
import com.zevzhu.wanandroid.mvvm.viewmodel.view.HomeViewModel
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction
import me.hgj.jetpackmvvm.ext.parseState

class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>() {

    private var mBannerAdapter = BannerAdapter(null)
    private val homeReqViewModel: HomeReqViewModel by viewModels()
    private val collectReqVM: CollectReqViewModel by viewModels()

    private lateinit var homeBanner: Banner<BannerEntity, BannerAdapter>
    private var mChapterAdapter = ChapterAdapter(this)
    private var overLoad = false

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun layoutId() = R.layout.home_fragment

    override fun initView(savedInstanceState: Bundle?) {
        addLoadingObserve(homeReqViewModel)
        toolbar.run {
            initTitle("??????")
            inflateMenu(R.menu.home_menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.home_search -> {
                        ToastUtils.showShort("Search")
                        nav().navigateAction(R.id.action_mainFragment_to_searchFragment)
                    }
                }
                true
            }
        }
        createBanner()
        createHomeList()
    }

    private fun createBanner() {
        LogUtils.d("????????????=====================")
        homeBanner =
            layoutInflater.inflate(R.layout.layout_banner, null).findViewById(R.id.homeBanner)
        homeBanner.setPageTransformer(ZoomOutPageTransformer()) // ????????????
        homeBanner.addBannerLifecycleObserver(this)
            .setAdapter(mBannerAdapter)
            .setIndicator(CircleIndicator(activity))
            .addOnPageChangeListener(object : OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {

                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                }
            }).setOnBannerListener { data, position ->
                // banner ????????????
                val entity = data as BannerEntity
                nav().navigateAction(R.id.action_global_webPageFragment, Bundle().apply {
                    putInt("id", entity.id)
                    putString("url", entity.url)
                    putString("title", entity.title)
                })
            }
    }

    override fun createObserver() {
        super.createObserver()
        homeReqViewModel.bannerResult.observe(viewLifecycleOwner, Observer { result ->
            parseState(result, {
                LogUtils.d("bannerResult===${it}")
                if (ObjectUtils.isEmpty(it)) return@parseState
                homeBanner.setDatas(it)
            }, {
                LogUtils.e("bannerResult===${it.throwable}")
            })
        })
        homeReqViewModel.homeResult.observe(viewLifecycleOwner, Observer {
            overLoad = loadPageData(mChapterAdapter, getStatusManager(), refreshLayout, it)
            LogUtils.d("hasMore==overLoad===$overLoad")
        })

        collectReqVM.collectResult.observe(viewLifecycleOwner, Observer { result ->
            parseState(result, {}, {
                LogUtils.e("collectResult====${it.errorMsg}")
            })
        })
    }

    override fun reload() {
        homeReqViewModel.getBanner()
        homeReqViewModel.getHomeList(true)
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        // ?????????????????????????????????
        setupStatusView(rvChapter, true)
        homeReqViewModel.getBanner()
        homeReqViewModel.getHomeList(true)
    }


    private fun createHomeList() {
        mChapterAdapter.initPage(
            refreshLayout,
            rvChapter,
            { adapter, view, position ->
                val entity = (adapter.data)[position] as ChapterEntity
                nav().navigateAction(R.id.action_global_webPageFragment, Bundle().apply {
                    putInt("id", entity.id)
                    putString("url", entity.link)
                    putString("title", entity.title)
                })
            },
            {
                if (overLoad) {
                    homeReqViewModel.getHomeList(false)
                } else {
                    mChapterAdapter.loadMoreModule.loadMoreEnd()
                }
            },
            {
                homeReqViewModel.getBanner()
                homeReqViewModel.getHomeList(true)
            })
        (homeBanner.parent as ViewGroup).removeView(homeBanner)
        mChapterAdapter.setHeaderView(homeBanner)
        mChapterAdapter.setCollect { likeView, item, pos ->
            collectReqVM.collectOrCollectByChapter(item.id, likeView.isChecked)
        }
    }

}