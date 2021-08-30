package com.zevzhu.wanandroid.mvvm.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zevzhu.wanandroid.R
import com.zevzhu.wanandroid.data.ChapterEntity
import com.zevzhu.wanandroid.utils.AppUtils
import me.hgj.jetpackmvvm.ext.util.toHtml

class NavChildAdapter(datas: MutableList<ChapterEntity>) :
    BaseQuickAdapter<ChapterEntity, BaseViewHolder>(R.layout.item_system_text, datas) {
    override fun convert(holder: BaseViewHolder, item: ChapterEntity) {
        holder.setText(R.id.tvText, item.title.toHtml())
            .setTextColor(R.id.tvText, AppUtils.getRandomColor())
    }

}