package com.forestmouse.samples.streaming

import com.forestmouse.samples.streaming.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

open class PostApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out PostApplication> {
        return DaggerAppComponent.builder().create(this)
    }
}