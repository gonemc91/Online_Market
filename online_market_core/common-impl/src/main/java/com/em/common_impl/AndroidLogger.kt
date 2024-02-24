package com.em.common_impl

import android.util.Log
import com.em.common.Logger


/**
 * Default implementation of [Logger] which send all logs to the LogCat.
 */

class AndroidLogger: Logger {

    override fun log(message: String) {
        Log.d("AndroidLogger", message)
    }

    override fun err(exception: Throwable, message: String?) {
        Log.e("AndroidLogger", message, exception)
    }
}