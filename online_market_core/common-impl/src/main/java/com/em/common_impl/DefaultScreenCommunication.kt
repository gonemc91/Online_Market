package com.em.common_impl

import android.os.Handler
import android.os.Looper
import androidx.fragment.app.FragmentActivity
import com.em.common.ScreenCommunication
import com.em.common.ScreenResultListener

@Suppress("UNCHECKED_CAST")
class DefaultScreenCommunication: ScreenCommunication, ActivityRequired {

    private var activity: FragmentActivity? = null
    private var created: Boolean = false

    private val listeners = mutableListOf<Record<*>>()
    private val pendingResult = mutableListOf<Any>()

    private val handler = Handler(Looper.getMainLooper())



    override fun <T : Any> registerListener(clazz: Class<T>, listener: ScreenResultListener<T>) {
        if(!created)return
        listeners.add(Record(clazz, listener))
        val iterator = pendingResult.iterator()
        while (iterator.hasNext()){
            val pendingResult = iterator.next()
            if(clazz == pendingResult::class.java){
                listener(pendingResult as T)
                iterator.remove()
            }
        }
    }

    override fun <T : Any> unregisterListener(listener: ScreenResultListener<T>) {
       listeners.removeAll{it.listener===listener}
    }

    override fun <T : Any> publishResult(result: T) {
        if (!created) return
        listeners.forEach { record ->
            if (record.clazz == result::class.java) {
                (record.listener as ScreenResultListener<T>).invoke(result)
                return
            }
        }
        pendingResult.add(result)
        handler.postDelayed({
            pendingResult.remove(result)
        }, TIMEOUT)
    }


    override fun onCreated(activity: FragmentActivity) {
        this.activity = activity
        this.created = true
    }

    override fun onStarted() {
    }

    override fun onStopped() {
    }

    override fun onDestroyed() {
        if (this.activity?.isFinishing == true){
            created = false
            pendingResult.clear()
        }
        this.activity = null
    }

    private class Record<T> (
        val clazz: Class<T>,
        val listener: ScreenResultListener<T>
    )

    private companion object{
        const val TIMEOUT = 2000L
    }
}