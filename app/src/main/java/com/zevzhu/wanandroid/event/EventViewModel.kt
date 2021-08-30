package com.zevzhu.wanandroid.event

import com.kunminx.architecture.ui.callback.UnPeekLiveData
import com.zevzhu.wanandroid.data.UserEntity
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

class EventViewModel : BaseViewModel() {

    val userInfo = UnPeekLiveData.Builder<UserEntity>().setAllowNullValue(true).create()

}