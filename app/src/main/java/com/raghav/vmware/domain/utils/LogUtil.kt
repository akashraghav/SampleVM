package com.raghav.vmware.domain.utils

import android.util.Log
import java.lang.Exception

object LogUtil {

    private const val TAG = "Logger"

    fun logD(message: String) {
        Log.d(TAG, message)
    }

    fun logE(exception: Exception) {
        Log.e(TAG, exception.toString())
    }

    fun logE(message: String) {
        Log.e(TAG, message)
    }
}