package com.zevzhu.wanandroid.mvvm.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.zevzhu.wanandroid.data.ChapterEntity
import com.zevzhu.wanandroid.data.ListDataUiState
import com.zevzhu.wanandroid.data.NavigationEntity
import com.zevzhu.wanandroid.data.SysEntity
import com.zevzhu.wanandroid.ext.requestEx
import com.zevzhu.wanandroid.http.apiService
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.request
import me.hgj.jetpackmvvm.state.ResultState

class TreeReqViewModel : BaseViewModel() {

    private var currPlazaPage = 0
    private var currAskPage = 1
    private var currSysDetailPage = 0

    val plazaResult = MutableLiveData<ListDataUiState<ChapterEntity>>()
    val askResult = MutableLiveData<ListDataUiState<ChapterEntity>>()
    val sysDetailResult = MutableLiveData<ListDataUiState<ChapterEntity>>()
    val systemResult = MutableLiveData<ResultState<MutableList<SysEntity>>>()
    val navResult = MutableLiveData<ResultState<MutableList<NavigationEntity>>>()

    fun getNavList() {
        requestEx({ apiService.getNavList() }, navResult)
    }

    fun getSystemList() {
        requestEx({ apiService.getSystemList() }, systemResult)
    }


    fun getSysDetailList(isRefresh: Boolean, cid: Int) {
        if (isRefresh) currSysDetailPage = 0
        request({ apiService.getSysDetailList(currSysDetailPage, cid) }, {
            currSysDetailPage++
            val statusResult = ListDataUiState(
                isSuccess = true,
                isRefresh = isRefresh,
                isEmpty = it.isEmpty(),
                isFirstEmpty = isRefresh && it.isEmpty(),
                hasMore = it.hasMore(),
                listData = it.datas
            )
            sysDetailResult.value = statusResult
        }, {
            val statusResult = ListDataUiState(
                isSuccess = false,
                isRefresh = isRefresh,
                errMessage = it.errorMsg,
                listData = mutableListOf<ChapterEntity>()
            )
            sysDetailResult.value = statusResult
        })
    }


    fun getPlazaList(isRefresh: Boolean) {
        if (isRefresh) currPlazaPage = 0
        request({ apiService.getPlazaList(currPlazaPage) }, {
            currPlazaPage++
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
                isSuccess = false,
                isRefresh = isRefresh,
                errMessage = it.errorMsg,
                listData = mutableListOf<ChapterEntity>()
            )
            plazaResult.value = statusResult
        })
    }

    fun getAskList(isRefresh: Boolean) {
        if (isRefresh) currAskPage = 1
        request({ apiService.getAskList(currAskPage) }, {
            currAskPage++
            val statusResult = ListDataUiState(
                isSuccess = true,
                isRefresh = isRefresh,
                isEmpty = it.isEmpty(),
                isFirstEmpty = isRefresh && it.isEmpty(),
                hasMore = it.hasMore(),
                listData = it.datas
            )
            askResult.value = statusResult
        }, {
            val statusResult = ListDataUiState(
                isSuccess = false,
                isRefresh = isRefresh,
                errMessage = it.errorMsg,
                listData = mutableListOf<ChapterEntity>()
            )
            askResult.value = statusResult
        })
    }

}