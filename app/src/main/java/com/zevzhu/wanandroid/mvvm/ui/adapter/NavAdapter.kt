package com.zevzhu.wanandroid.mvvm.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.zevzhu.wanandroid.R
import com.zevzhu.wanandroid.app.appContext
import com.zevzhu.wanandroid.data.NavigationEntity
import com.zevzhu.wanandroid.data.SysEntity

class NavAdapter : BaseQuickAdapter<NavigationEntity, BaseViewHolder>(R.layout.item_system_layout, null) {

    override fun convert(holder: BaseViewHolder, item: NavigationEntity) {
        holder.setText(R.id.tvTitle, item.name)
        holder.getView<RecyclerView>(R.id.rvSys).run {
            val flexManager: FlexboxLayoutManager by lazy {
                FlexboxLayoutManager(appContext).apply {
                    flexDirection = FlexDirection.ROW
                    justifyContent = JustifyContent.FLEX_START
                }
            }
            layoutManager = flexManager
            setHasFixedSize(true)
            setItemViewCacheSize(200)
            isNestedScrollingEnabled = false
            adapter = NavChildAdapter(item.articles).apply {
                setOnItemClickListener { adapter, view, position ->
                    method(adapter, view, position)
                }
            }
        }
    }

    private var method: (adapter: BaseQuickAdapter<*, *>, view: View, position: Int) -> Unit =
        { adapter, view, position -> }

    fun itemClick(method: (adapter: BaseQuickAdapter<*, *>, view: View, position: Int) -> Unit) {
        this.method = method
    }
}