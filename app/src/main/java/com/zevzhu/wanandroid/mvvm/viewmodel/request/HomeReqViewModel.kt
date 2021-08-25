package com.zevzhu.wanandroid.mvvm.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.zevzhu.wanandroid.data.BannerEntity
import com.zevzhu.wanandroid.data.ChapterEntity
import com.zevzhu.wanandroid.data.ListDataUiState
import com.zevzhu.wanandroid.http.HttpManager
import com.zevzhu.wanandroid.http.apiService
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.request
import me.hgj.jetpackmvvm.state.ResultState

class HomeReqViewModel : BaseViewModel() {


    val bannerResult: MutableLiveData<ResultState<MutableList<BannerEntity>>> = MutableLiveData()

    val homeResult: MutableLiveData<ListDataUiState<ChapterEntity>> = MutableLiveData()

    var currPage = 0

    fun getBanner() {
        request({ apiService.getBanner() }, bannerResult)
    }

    fun getHomeList(isRefresh: Boolean) {
        if (isRefresh) currPage = 0
        request({ HttpManager.getHomeList(currPage) }, {
            currPage++
            val stateUi = ListDataUiState(
                isSuccess = true,
                isRefresh = isRefresh,
                isEmpty = it.isEmpty(),
                hasMore = it.hasMore(),
                isFirstEmpty = isRefresh && it.isEmpty(),
                listData = it.datas
            )
            homeResult.value = stateUi
        }, {
            val stateUi = ListDataUiState(
                isSuccess = false,
                errMessage = it.errorMsg,
                isRefresh = isRefresh,
                listData = mutableListOf<ChapterEntity>()
            )
            homeResult.value = stateUi
        }, true)
    }
}