package dev.arvind.newweatherapp.ui.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import dev.arvind.newweatherapp.R
import dev.arvind.newweatherapp.databinding.ActivityMainBinding
import dev.arvind.newweatherapp.ui.viewModel.WeatherViewModel
import dev.arvind.newweatherapp.ui.viewModelFactory.WeatherViewModelFactory
import dev.arvind.newweatherapp.util.EventObserver
import dev.arvind.newweatherapp.util.State
import dev.arvind.newweatherapp.util.showToast
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by closestKodein()
    private lateinit var activityMainBinding: ActivityMainBinding
    private val factory: WeatherViewModelFactory by instance()
    private val viewModel: WeatherViewModel by lazy {
        ViewModelProvider(this,factory).get(WeatherViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_weather)
        setUpUI()
        observeAPICall()
    }

    private fun setUpUI() {
        viewModel.fetchWeatherDetailFromDb("28.6195585", "77.2979777")
    }

    @SuppressLint("SetTextI18n")
    private fun observeAPICall() {
        viewModel.weatherLiveData.observe(this, EventObserver { state ->
            when(state) {
                is State.Loading -> {
                }
                is State.Success -> {
                    state.data.let { weatherDetail ->
                        activityMainBinding.tvTemperature.text = weatherDetail.temp.toString()
                        activityMainBinding.textTemperature.text = weatherDetail.tempTuesday.toString()
                        activityMainBinding.textTemperatureWednesday.text = weatherDetail.tempWednesday.toString()
                        activityMainBinding.textTemperatureThursday.text = weatherDetail.tempThursday.toString()
                        activityMainBinding.textTemperatureFriday.text = weatherDetail.tempFriday.toString()
                        activityMainBinding.tvCityName.text = weatherDetail.cityName.toString()
                    }
                }

                is State.Error -> {
                    showToast(state.message)
                }
            }
        })
    }
}