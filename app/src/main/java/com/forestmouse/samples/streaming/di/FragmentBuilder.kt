package com.forestmouse.samples.streaming.di

import com.forestmouse.samples.streaming.ui.DetailsFragment
import com.forestmouse.samples.streaming.ui.ScrollingPostFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {

    @ContributesAndroidInjector
    abstract fun contributeScrollingPostFragment(): ScrollingPostFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailsFragment(): DetailsFragment
}