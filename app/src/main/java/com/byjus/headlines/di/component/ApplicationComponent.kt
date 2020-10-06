package com.byjus.headlines.di.component

import android.app.Application
import com.byjus.headlines.core.application.ByjusApplications
import com.byjus.headlines.di.NetworkConfig
import com.byjus.headlines.di.activity.ActivityModule
import com.byjus.headlines.di.activity.fragment.FragmentModule
import com.byjus.headlines.di.activity.viewmodel.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule

@Component(
    modules = [
        ActivityModule::class,
        FragmentModule::class,
        AndroidSupportInjectionModule::class,
        ViewModelModule::class,
        NetworkConfig::class
    ]
)
interface ApplicationComponent {
    /*
     * We will call this builder interface from our custom Application class.
     * This will set our application object to the TripComponent.
     * So inside the TripComponent the application instance is available.
     * So this application instance can be accessed by our modules
     * such as ApiModule when needed
     *
     * */
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    /*
     * This is our custom Application class
     * */
    fun inject(appController: ByjusApplications)
}