package com.zevzhu.wanandroid.mvvm.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.zevzhu.wanandroid.data.ChapterEntity
import com.zevzhu.wanandroid.data.ListDataUiState
import com.zevzhu.wanandroid.data.ProTabEntity
import com.zevzhu.wanandroid.ext.requestEx
import com.zevzhu.wanandroid.http.HttpManager
import com.zevzhu.wanandroid.http.apiService
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.request
import me.hgj.jetpackmvvm.state.ResultState

class ProjectReqViewModel : BaseViewModel() {

    val projectResult = MutableLiveData<ResultState<MutableList<ProTabEntity>>>()
    val proChildResult = MutableLiveData<ListDataUiState<ChapterEntity>>()

    private var currProPage = 1

    fun getProTab() {
        requestEx({ apiService.getProTab() }, projectResult)
    }


    fun getProChildList(cid: Int, isRefresh: Boolean, isNew: Boolean) {
        if (isRefresh) {
            currProPage = if (isNew) 0 else 1
        }
        request({ HttpManager.getProjectList(currProPage, cid, isNew) }, {
            currProPage++
            val statusResult = ListDataUiState(
                isSuccess = true,
                isRefresh = isRefresh,
                isEmpty = it.isEmpty(),
                hasMore = it.hasMore(),
                isFirstEmpty = isRefresh && it.isEmpty(),
                listData = it.datas
            )
            proChildResult.value = statusResult
        }, {
            val statusResult = ListDataUiState(
                isSuccess = false,
                isRefresh = isRefresh,
                errMessage = it.errorMsg,
                listData = mutableListOf<ChapterEntity>()
            )
            proChildResult.value = statusResult
        })
    }


}