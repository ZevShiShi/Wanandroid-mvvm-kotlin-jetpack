package com.zevzhu.wanandroid.ext

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.zevzhu.wanandroid.R
import com.zevzhu.wanandroid.app.appContext
import com.zevzhu.wanandroid.data.ListDataUiState
import com.zevzhu.wanandroid.mvvm.ui.view.ScaleTransitionPagerTitleView
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager
import me.hgj.jetpackmvvm.ext.util.toHtml
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator

/**
 * 初始化有返回键的toolbar
 */
fun Toolbar.initTitle(titleStr: String = ""): Toolbar {
//    setBackgroundColor(ContextCompat.getColor(appContext, R.color.color_1296db))
    title = titleStr
    return this
}

fun Toolbar.initClose(
    titleStr: String = "",
    backImg: Int = R.mipmap.ic_toolbar_back,
    onBack: (toolbar: Toolbar) -> Unit
): Toolbar {
    title = titleStr.toHtml()
    setNavigationIcon(backImg)
    setNavigationOnClickListener { onBack.invoke(this) }
    return this
}

//设置适配器的列表动画
fun BaseQuickAdapter<*, *>.setAdapterAnimation(mode: Int) {
    //等于0，关闭列表动画 否则开启,  //0 关闭动画 1.渐显 2.缩放 3.从下到上 4.从左到右 5.从右到左
    if (mode == 0) {
        this.animationEnable = false
    } else {
        this.animationEnable = true
        this.setAnimationWithDefault(BaseQuickAdapter.AnimationType.values()[mode - 1])
    }
}

fun BaseQuickAdapter<*, *>.initPage(
    swipeRefreshLayout: SmartRefreshLayout,
    recyclerView: RecyclerView,
    itemClick: (adapter: BaseQuickAdapter<*, *>, view: View, position: Int) -> Unit = { adapter, v, position -> },
    loadMore: () -> Unit = {},
    refresh: () -> Unit = {}
) {
    swipeRefreshLayout.setEnableLoadMore(false)
    loadMoreModule.isEnableLoadMore = true
    swipeRefreshLayout.setOnRefreshListener { refresh.invoke() }
    loadMoreModule.setOnLoadMoreListener {
        loadMore.invoke()
    }
    setOnItemClickListener { adapter, view, position ->
        itemClick.invoke(adapter, view, position)
    }
    recyclerView.adapter = this
}

fun BaseQuickAdapter<*, *>.initPageNoLoadMore(
    swipeRefreshLayout: SmartRefreshLayout,
    recyclerView: RecyclerView,
    itemClick: (adapter: BaseQuickAdapter<*, *>, view: View, position: Int) -> Unit = { adapter, v, position -> },
    refresh: () -> Unit = {}
) {
    swipeRefreshLayout.setEnableLoadMore(false)
    swipeRefreshLayout.setOnRefreshListener { refresh.invoke() }
    setOnItemClickListener { adapter, view, position ->
        itemClick.invoke(adapter, view, position)
    }
    recyclerView.adapter = this
}

/**
 * 加载分页数据-封装公共部分
 * 返回是否还有下一页
 */
fun <T> loadPageData(
    baseQuickAdapter: BaseQuickAdapter<T, *>,
    statusLayoutManager: StatusLayoutManager,
    swipeRefreshLayout: SmartRefreshLayout,
    data: ListDataUiState<T>
): Boolean {
    swipeRefreshLayout.finishRefresh()
    if (data.isSuccess) {
        LogUtils.d("loadPageData==========${data.listData}")
        // 数据加载成功
        when {
            // 第一页没数据
            data.isFirstEmpty -> statusLayoutManager.showEmptyLayout()
            // 第一页数据
            data.isRefresh -> {
                baseQuickAdapter.setNewInstance(data.listData)
                statusLayoutManager.showSuccessLayout()
            }
            // 不是第一页数据
            else -> {
                baseQuickAdapter.addData(data.listData)
                baseQuickAdapter.loadMoreModule.loadMoreComplete()
            }
        }
        return data.hasMore
    } else {
        LogUtils.d("loadPageData==========${data.errMessage}")
        // 数据加载失败
        if (data.isRefresh) {
            statusLayoutManager.showErrorLayout()
        } else {
            baseQuickAdapter.loadMoreModule.loadMoreFail()
        }
    }
    return true
}


fun ViewPager2.init(
    fragment: Fragment,
    fragments: MutableList<Fragment>,
    isScroll: Boolean = true
): ViewPager2 {
    this.isUserInputEnabled = isScroll
    // 设置适配器
    this.adapter = object : FragmentStateAdapter(fragment) {
        override fun createFragment(position: Int) = fragments[position]
        override fun getItemCount(): Int = fragments.size
    }
    return this
}


fun MagicIndicator.bindViewPager2(
    viewPager: ViewPager2,
    mStrings: MutableList<String>,
    action: (index: Int) -> Unit = {}
) {
    val commonNavigator = CommonNavigator(appContext)
    commonNavigator.adapter = object : CommonNavigatorAdapter() {
        override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
            return ScaleTransitionPagerTitleView(context!!).apply {
                normalColor = ContextCompat.getColor(context, R.color.white)
                selectedColor = ContextCompat.getColor(context, R.color.white)
                textSize = 17f
                text = mStrings[index]
                setOnClickListener {
                    viewPager.currentItem = index
                    action.invoke(index)
                }
            }
        }

        override fun getCount() = mStrings.size

        override fun getIndicator(context: Context?): IPagerIndicator {
            return LinePagerIndicator(context).apply {
                mode = LinePagerIndicator.MODE_EXACTLY
                lineHeight = ConvertUtils.dp2px(3f).toFloat()
                lineWidth = ConvertUtils.dp2px(30f).toFloat()
                roundRadius = ConvertUtils.dp2px(6f).toFloat()
                startInterpolator = AccelerateInterpolator()
                endInterpolator = DecelerateInterpolator(2.0f)
                //线条的颜色
                setColors(Color.WHITE)
            }
        }
    }
    this.navigator = commonNavigator
    viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            this@bindViewPager2.onPageSelected(position)
            action.invoke(position)
        }


        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            this@bindViewPager2.onPageScrollStateChanged(state)
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            this@bindViewPager2.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }
    })
}