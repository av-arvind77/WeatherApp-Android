package dev.arvind.newweatherapp

import android.app.Application
import androidx.multidex.MultiDexApplication
import dev.arvind.newweatherapp.data.local.WeatherDatabase
import dev.arvind.newweatherapp.data.network.ApiInterface
import dev.arvind.newweatherapp.data.network.NetworkConnectionInterceptor
import dev.arvind.newweatherapp.data.repositories.WeatherRepository
import dev.arvind.newweatherapp.ui.viewModelFactory.WeatherViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class WeatherApplication : MultiDexApplication() , KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@WeatherApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance())}
        bind() from singleton { ApiInterface(instance()) }
        bind() from singleton { WeatherRepository(instance(), instance()) }
        bind() from provider { WeatherViewModelFactory(instance()) }
        bind() from provider { WeatherDatabase(instance()) }
    }
}