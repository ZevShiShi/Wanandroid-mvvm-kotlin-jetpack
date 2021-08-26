package com.zevzhu.wanandroid.utils

import android.graphics.Color
import java.util.*

object AppUtils {


    private val random = Random()

    fun getRandomColor(): Int {
        random.run {
            val red = nextInt(190)
            val green = nextInt(190)
            val blue = nextInt(190)
            return Color.rgb(red, green, blue)
        }
    }
}