package com.zevzhu.wanandroid.mvvm.viewmodel.adapter

import android.text.InputType
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import androidx.databinding.BindingAdapter

object CommonAdapter {


    @BindingAdapter(value = ["showPwd"])
    @JvmStatic
    fun showPwd(view: EditText, eye: Boolean) {
        when (eye) {
            true -> view.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            false -> view.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
    }

    @BindingAdapter(value = ["checkChange"])
    @JvmStatic
    fun checkChange(checkBox: CheckBox, listener: CompoundButton.OnCheckedChangeListener) {
        checkBox.setOnCheckedChangeListener(listener)
    }
}