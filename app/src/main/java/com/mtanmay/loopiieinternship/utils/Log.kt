package com.mtanmay.loopiieinternship.utils

import android.util.Log
import com.mtanmay.loopiieinternship.BuildConfig

class Log {

    companion object {
        fun d(tag: String, msg: String) {
            if (BuildConfig.DEBUG)
                Log.d(tag, msg)
        }
    }
}