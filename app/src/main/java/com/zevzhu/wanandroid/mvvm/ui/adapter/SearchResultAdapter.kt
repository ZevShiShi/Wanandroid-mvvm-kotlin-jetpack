package com.zevzhu.wanandroid.mvvm.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ObjectUtils
import com.zevzhu.wanandroid.R
import com.zevzhu.wanandroid.app.appContext
import com.zevzhu.wanandroid.ext.getColor
import com.zevzhu.wanandroid.ext.onClick
import com.zevzhu.wanandroid.ext.setHeightLightText

class SearchResultAdapter(var datas: MutableList<String> = mutableListOf()) : BaseAdapter(), Filterable {

    private var clearWord: (position: Int) -> Unit = {}
    private val mTotals: MutableList<String> = mutableListOf()
    private var mSearchWord = ""


    fun setClearWordListener(clearWord: (position: Int) -> Unit) {
        this.clearWord = clearWord
    }

    init {
        mTotals.addAll(datas)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                LogUtils.d("getFilter performFiltering======${constraint}")
                return FilterResults().apply {
                    if (constraint == null || constraint.toString().isEmpty()) {
                        values = mTotals
                        count = mTotals.size
                    } else {
                        val filterDatas: MutableList<String> = mutableListOf()
                        mTotals.forEach {
                            if (it.contains(constraint.toString())) {
                                filterDatas.add(it)
                            }
                        }
                        count = filterDatas.size
                        values = filterDatas
                    }
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                mSearchWord = constraint?.toString() ?: ""
                datas.clear()
                datas.addAll(results?.values as MutableList<String>)
                LogUtils.d("getFilter publishResults===$constraint=======$datas")
                notifyDataSetChanged()
            }
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var holder: ViewHolder?
        var convertViewProxy = convertView
        if (convertViewProxy == null) {
            holder = ViewHolder()
            convertViewProxy = LayoutInflater.from(appContext)
                .inflate(R.layout.item_search_layout, parent, false)
            holder.tvSearch = convertViewProxy.findViewById(R.id.tvSearch)
            holder.ivClear = convertViewProxy.findViewById(R.id.ivClearWord)
            convertViewProxy?.tag = holder
        } else {
            holder = convertViewProxy.tag as ViewHolder?
        }

        holder?.tvSearch?.text = datas[position]
        holder?.tvSearch?.setTextColor(getColor(R.color.color_2c2c2c))
        if (ObjectUtils.isNotEmpty(mSearchWord)) {
            LogUtils.d("getFilter getView===$mSearchWord=======${holder?.tvSearch?.text}")
            holder?.tvSearch?.setHeightLightText(
                holder?.tvSearch?.text.toString(),
                mSearchWord,
                R.color.color_059f11
            )
        }
        holder?.ivClear?.onClick {
            clearWord.invoke(position)
        }
        return convertViewProxy!!
    }

    inner class ViewHolder {
        var tvSearch: TextView? = null
        var ivClear: ImageView? = null
    }

    override fun getItem(position: Int) = datas[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = datas.size
}