package com.zevzhu.wanandroid.mvvm.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zevzhu.wanandroid.R
import com.zevzhu.wanandroid.app.appContext
import com.zevzhu.wanandroid.mvvm.ui.fragment.*


class MainFragmentAdapter(
    val fragment: Fragment
) : FragmentStateAdapter(fragment) {


    private val mTitles = arrayListOf("首页", "项目", "广场", "公众号", "我的")
    private val mIcons = arrayListOf(
        R.drawable.home_selector,
        R.drawable.project_selector,
        R.drawable.system_selector,
        R.drawable.wx_selector,
        R.drawable.my_selector
    )

    /**
     * 自定义方法，提供自定义Tab
     *
     * @param position 位置
     * @return 返回Tab的View
     */
    fun getTabView(position: Int): View {
        val v = LayoutInflater.from(appContext).inflate(R.layout.home_tab_layout, null)
        val tvTabText = v.findViewById<TextView>(R.id.tvTabText)
        val ivTabImg = v.findViewById<ImageView>(R.id.ivTabImg)
        tvTabText.text = mTitles[position]
        ivTabImg.setImageResource(mIcons[position])
        return v
    }

    override fun getItemCount() = 5


    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment.newInstance()
            1 -> ProjectFragment.newInstance()
            2 -> TreeFragment.newInstance()
            3 -> WxFragment.newInstance()
            4 -> MyFragment.newInstance()
            else -> HomeFragment.newInstance()
        }
    }
}