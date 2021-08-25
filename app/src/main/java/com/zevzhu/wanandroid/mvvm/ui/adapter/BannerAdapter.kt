package com.zevzhu.wanandroid.mvvm.ui.adapter

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.zevzhu.wanandroid.app.appContext
import com.zevzhu.wanandroid.data.BannerEntity

class BannerAdapter(mDatas: MutableList<BannerEntity>?) :
    BannerImageAdapter<BannerEntity>(mDatas) {

    override fun onBindView(
        holder: BannerImageHolder?,
        data: BannerEntity?,
        position: Int,
        size: Int
    ) {
        val options = RequestOptions.bitmapTransform(RoundedCorners(10))
        Glide.with(appContext)
            .load(data?.imagePath)
            .apply(options)
            .into(holder!!.imageView)
    }
}