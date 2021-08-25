package com.zevzhu.wanandroid.mvvm.ui.adapter

import com.blankj.utilcode.util.ObjectUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseDelegateMultiAdapter
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jaren.lib.view.LikeView
import com.zevzhu.wanandroid.R
import com.zevzhu.wanandroid.app.appContext
import com.zevzhu.wanandroid.data.ChapterEntity
import com.zevzhu.wanandroid.ext.setAdapterAnimation

class ChapterAdapter(data: MutableList<ChapterEntity>? = null) :
    BaseDelegateMultiAdapter<ChapterEntity, BaseViewHolder>(data), LoadMoreModule {

    private var mShowTag = true

    constructor(showTag: Boolean = true, data: MutableList<ChapterEntity>? = null) : this(data) {
        mShowTag = showTag
    }

    init {
        setAdapterAnimation(5)
        setMultiTypeDelegate(object : BaseMultiTypeDelegate<ChapterEntity>() {
            override fun getItemType(data: List<ChapterEntity>, position: Int): Int {
                return if (ObjectUtils.isEmpty(data[position].envelopePic))
                    ChapterEntity.NORMAL else ChapterEntity.PROJECT
            }

        })
        getMultiTypeDelegate()?.let {
            it.addItemType(ChapterEntity.NORMAL, R.layout.item_home_chapter)
            it.addItemType(ChapterEntity.PROJECT, R.layout.item_home_project)
        }
    }

    override fun convert(holder: BaseViewHolder, item: ChapterEntity) {
        when (holder.itemViewType) {
            ChapterEntity.NORMAL -> {
                createNormal(holder, item)
            }
            else -> {
                createNormal(holder, item)
                Glide.with(appContext).load(item.envelopePic)
                    .into(holder.getView(R.id.ivChapterPro))
                holder.setText(R.id.tvDesc, item.desc)
            }
        }
    }

    private fun createNormal(
        holder: BaseViewHolder,
        item: ChapterEntity
    ) {
        holder.setText(R.id.tvAuthor, item.getUser())
        holder.setText(R.id.tvTime, item.niceDate)
        holder.setText(R.id.tvTitle, item.title)
        holder.setText(R.id.tvSuperChapterName, item.superChapterName)
        holder.setText(R.id.tvChapterName, item.chapterName)

        if (mShowTag) {
            if (ObjectUtils.isEmpty(item.tags)) {
                holder.setGone(R.id.tvChapterTag, true)
            } else {
                holder.setGone(R.id.tvChapterTag, false)
                holder.setText(R.id.tvChapterTag, item.tags[0].name)
            }
            holder.setGone(R.id.tvNewChapter, !item.fresh)
            holder.setGone(R.id.tvChapterAttr, !item.isTop())
        } else {
            holder.setGone(R.id.tvNewChapter, true)
            holder.setGone(R.id.tvChapterAttr, true)
            holder.setGone(R.id.tvChapterTag, true)
        }
        val likeView = holder.getView<LikeView>(R.id.likeZan)
        likeView.isChecked = item.collect
        likeView.setOnClickListener {
            likeView.toggle()
        }
    }

}