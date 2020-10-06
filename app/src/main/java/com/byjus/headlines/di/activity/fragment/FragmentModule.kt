package com.byjus.headlines.di.activity.fragment

import com.byjus.headlines.headlines.fragments.HeadlinesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    internal abstract fun contributeMainFragment(): HeadlinesFragment
}