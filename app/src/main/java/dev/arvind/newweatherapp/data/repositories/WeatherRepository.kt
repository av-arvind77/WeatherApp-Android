package dev.arvind.newweatherapp.data.repositories

import dev.arvind.newweatherapp.data.local.WeatherDatabase
import dev.arvind.newweatherapp.data.model.WeatherDataResponse
import dev.arvind.newweatherapp.data.model.WeatherDetail
import dev.arvind.newweatherapp.data.network.ApiInterface
import dev.arvind.newweatherapp.data.network.SafeApiRequest

class WeatherRepository(private val apiInterface: ApiInterface, private val db: WeatherDatabase) :
    SafeApiRequest() {

    suspend fun findWeatherData(lat: String, lon: String): WeatherDataResponse = apiRequest {
        apiInterface.findCityWeatherData(lat, lon)
    }

    suspend fun addWeather(weatherDetail: WeatherDetail) {
        db.getWeatherDao().addWeather(weatherDetail)
    }

    suspend fun fetchWeatherDetail(lat: String,lon: String) : WeatherDetail? =
        db.getWeatherDao().fetchWeatherByCity(lat, lon)

    //suspend fun fetchAllWeatherDetails
}