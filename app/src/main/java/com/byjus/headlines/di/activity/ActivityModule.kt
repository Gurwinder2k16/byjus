package com.byjus.headlines.di.activity

import com.byjus.headlines.headlines.activity.Headlines
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector()
    abstract fun contributeHomeActivity(): Headlines

}
