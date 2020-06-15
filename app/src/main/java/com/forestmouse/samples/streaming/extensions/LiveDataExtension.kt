package com.forestmouse.samples.streaming.extensions

import android.os.Looper
import android.view.View
import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.setOrPost(t: T) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        value = t
    } else {
        postValue(t)
    }
}

fun View?.setVisibleOrGone(show: Boolean) {
    if (show) {
        this?.visibility = View.VISIBLE
    }else{
        this?.visibility = View.GONE
    }
}