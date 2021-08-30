package com.zevzhu.wanandroid.mvvm.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.zevzhu.wanandroid.data.ChapterEntity
import com.zevzhu.wanandroid.data.ListDataUiState
import com.zevzhu.wanandroid.data.WxTabEntity
import com.zevzhu.wanandroid.ext.requestEx
import com.zevzhu.wanandroid.http.apiService
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.request
import me.hgj.jetpackmvvm.state.ResultState

class WxReqViewModel : BaseViewModel() {


    val wxResult = MutableLiveData<ResultState<MutableList<WxTabEntity>>>()
    val wxListResult = MutableLiveData<ListDataUiState<ChapterEntity>>()
    private var curPage = 1

    fun getWxTab() {
        requestEx({ apiService.getWxTab() }, wxResult)
    }


    fun getWxList(id: Int, isRefresh: Boolean) {
        if (isRefresh) {
            curPage = 1
        }
        request({ apiService.getWxList(id, curPage) }, {
            curPage++
            val status = ListDataUiState(
                isSuccess = true,
                isEmpty = it.isEmpty(),
                isRefresh = isRefresh,
                hasMore = it.hasMore(),
                isFirstEmpty = isRefresh && it.isEmpty(),
                listData = it.datas
            )
            wxListResult.value = status
        }, {
            val status = ListDataUiState(
                isSuccess = false,
                isRefresh = isRefresh,
                errMessage = it.errorMsg,
                listData = mutableListOf<ChapterEntity>()
            )
            wxListResult.value = status
        })
    }

}