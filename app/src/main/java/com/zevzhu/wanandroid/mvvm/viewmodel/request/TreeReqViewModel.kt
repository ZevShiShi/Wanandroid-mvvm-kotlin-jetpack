package com.zevzhu.wanandroid.mvvm.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.zevzhu.wanandroid.data.ChapterEntity
import com.zevzhu.wanandroid.data.ListDataUiState
import com.zevzhu.wanandroid.http.apiService
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.request

class TreeReqViewModel : BaseViewModel() {

    private var currPlazaPage = 0
    val plazaResult = MutableLiveData<ListDataUiState<ChapterEntity>>()

    fun getPlazaList(isRefresh: Boolean) {
        if (isRefresh) currPlazaPage = 0
        request({ apiService.getPlazaList(currPlazaPage) }, {
            val statusResult = ListDataUiState(
                isSuccess = true,
                isRefresh = isRefresh,
                isEmpty = it.isEmpty(),
                isFirstEmpty = isRefresh && it.isEmpty(),
                hasMore = it.hasMore(),
                listData = it.datas
            )
            plazaResult.value = statusResult
        }, {
            val statusResult = ListDataUiState(
                isSuccess = true,
                isRefresh = isRefresh,
                errMessage = it.errorMsg,
                listData = mutableListOf<ChapterEntity>()
            )
            plazaResult.value = statusResult
        })

    }

}