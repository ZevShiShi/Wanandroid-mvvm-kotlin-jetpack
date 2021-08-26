package com.zevzhu.wanandroid.mvvm.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zevzhu.wanandroid.R
import com.zevzhu.wanandroid.data.SysChildEntity
import com.zevzhu.wanandroid.utils.AppUtils

class SysChildAdapter(datas:MutableList<SysChildEntity>) :
    BaseQuickAdapter<SysChildEntity, BaseViewHolder>(R.layout.item_system_text, datas) {
    override fun convert(holder: BaseViewHolder, item: SysChildEntity) {
        holder.setText(R.id.tvText, item.name)
            .setTextColor(R.id.tvText, AppUtils.getRandomColor())
    }

}