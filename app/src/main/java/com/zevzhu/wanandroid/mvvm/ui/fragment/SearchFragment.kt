package com.zevzhu.wanandroid.mvvm.ui.fragment

import android.os.Bundle
import com.blankj.utilcode.util.ToastUtils
import com.zevzhu.wanandroid.R
import com.zevzhu.wanandroid.databinding.SearchFragmentBinding
import com.zevzhu.wanandroid.mvvm.base.BaseFragment
import com.zevzhu.wanandroid.mvvm.ui.adapter.SearchResultAdapter
import com.zevzhu.wanandroid.mvvm.viewmodel.view.SearchViewModel
import kotlinx.android.synthetic.main.search_fragment.*

class SearchFragment : BaseFragment<SearchViewModel, SearchFragmentBinding>() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private val datas = mutableListOf("小猪猪", "小狗狗", "小鸡鸡", "小猫猫", "小咪咪")

//    private val mPop: BackgroundBlurPopupWindow by lazy {
//        initPop(
//            requireActivity(), cvSearch, R.layout.pop_search_layout,
//            isDropDown = true,
//            isShow = false,
//            width = ScreenUtils.getScreenWidth(),
//            height = ConvertUtils.dp2px(300f),
//            gravity = Gravity.BOTTOM
//        )
//    }

    override fun layoutId() = R.layout.search_fragment

    override fun initView(savedInstanceState: Bundle?) {
//        val mAdapter =
//            ArrayAdapter<String>(appContext, android.R.layout.simple_dropdown_item_1line, datas)
        etSearch.setAdapter(SearchResultAdapter(datas).apply {
            setClearWordListener {
                ToastUtils.showShort("点击${it + 1}")
            }
        })
    }
}