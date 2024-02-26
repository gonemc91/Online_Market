package com.em.common_impl

import android.util.Log
import com.em.common.AppLogger


/**
 * Default implementation of [AppLogger] which send all logs to the LogCat.
 */

class AndroidLogger: AppLogger {

    override fun log(message: String) {
        Log.d("AndroidLogger", message)
    }

    override fun err(exception: Throwable, message: String?) {
        Log.e("AndroidLogger", message, exception)
    }
}