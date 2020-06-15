package com.forestmouse.samples.streaming.di

import android.content.Context
import com.forestmouse.samples.streaming.PostApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Singleton
    @Provides
    fun provideContext(application: PostApplication): Context {
        return application.applicationContext
    }
}