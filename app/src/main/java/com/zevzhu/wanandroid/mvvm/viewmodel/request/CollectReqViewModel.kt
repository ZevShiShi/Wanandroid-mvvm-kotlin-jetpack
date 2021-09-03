package com.zevzhu.wanandroid.mvvm.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.zevzhu.wanandroid.data.ChapterEntity
import com.zevzhu.wanandroid.data.ListDataUiState
import com.zevzhu.wanandroid.http.HttpManager
import com.zevzhu.wanandroid.http.apiService
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.request
import me.hgj.jetpackmvvm.state.ResultState

class CollectReqViewModel : BaseViewModel() {

    private var collectPage = 0

    val collectPageResult = MutableLiveData<ListDataUiState<ChapterEntity>>()
    val collectResult = MutableLiveData<ResultState<Any?>>()
    val unCollectResult = MutableLiveData<ResultState<Any?>>()

    /**
     * isCollect：是否收藏
     * unCollectForChapter：从文章列表-取消收藏，还是从我的收藏-取消收藏
     */
    fun collectOrCollectByChapter(id: Int, isCollect: Boolean) {
        request({ HttpManager.collectOrUnCollect(id, isCollect) }, collectResult)
    }

    fun unCollectByMyList(id: Int, originId: Int = -1) {
        request({ apiService.unCollectByMyList(id, originId) }, unCollectResult)
    }

    fun getCollectList(isRefresh: Boolean) {
        if (isRefresh) collectPage = 0
        request({ apiService.getCollectList(collectPage) }, {
            collectPage++
            val statusResult = ListDataUiState(
                isSuccess = true,
                isRefresh = isRefresh,
                isEmpty = it.isEmpty(),
                isFirstEmpty = isRefresh && it.isEmpty(),
                hasMore = it.hasMore(),
                listData = it.datas
            )
            collectPageResult.value = statusResult
        }, {
            val statusResult = ListDataUiState(
                isSuccess = false,
                isRefresh = isRefresh,
                errMessage = it.errorMsg,
                listData = mutableListOf<ChapterEntity>()
            )
            collectPageResult.value = statusResult
        })
    }

}