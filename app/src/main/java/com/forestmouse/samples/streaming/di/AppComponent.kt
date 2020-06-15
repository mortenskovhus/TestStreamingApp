package com.forestmouse.samples.streaming.di

import com.forestmouse.samples.streaming.PostApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [NetworkModule::class,
            AppModule::class,

            AndroidSupportInjectionModule::class,
            FragmentBuilder::class]
)
interface AppComponent : AndroidInjector<PostApplication> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<PostApplication>()
}