package dev.arvind.newweatherapp.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.arvind.newweatherapp.data.model.WeatherDataResponse
import dev.arvind.newweatherapp.data.model.WeatherDetail
import dev.arvind.newweatherapp.data.repositories.WeatherRepository
import dev.arvind.newweatherapp.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class WeatherViewModel(private val repository: WeatherRepository) :
    ViewModel() {

    private val _weatherLiveData = MutableLiveData<Event<State<WeatherDetail>>>()

    val weatherLiveData: LiveData<Event<State<WeatherDetail>>>
        get() = _weatherLiveData


    private val _weatherDetailListLiveData = MutableLiveData<Event<State<List<WeatherDetail>>>>()
    val weatherListLiveData: LiveData<Event<State<List<WeatherDetail>>>>
        get() = _weatherDetailListLiveData

    private lateinit var weatherResponse: WeatherDataResponse

    private fun findCurrentWeather(lat: String, lon: String) {
        _weatherLiveData.postValue(Event(State.loading()))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                weatherResponse =
                    repository.findWeatherData(lat, lon)
                addWeatherDetailIntoDb(weatherResponse)
                withContext(Dispatchers.Main) {
                    val weatherDetail = WeatherDetail()
                    weatherDetail.temp = weatherResponse.list[0].main.temp
                    weatherDetail.tempTuesday = weatherResponse.list[5].main.temp
                    weatherDetail.tempWednesday = weatherResponse.list[10].main.temp
                    weatherDetail.tempThursday = weatherResponse.list[15].main.temp
                    weatherDetail.tempFriday = weatherResponse.list[20].main.temp
                    weatherDetail.cityName = weatherResponse.city.name
                    _weatherLiveData.postValue(
                        Event(
                            State.success(weatherDetail)
                        )
                    )
                }
            } catch (e: ApiException) {
                withContext(Dispatchers.Main) {
                    _weatherLiveData.postValue(Event(State.error(e.message ?: "")))
                }
            } catch (e: NoInternetException) {
                withContext(Dispatchers.Main) {
                    _weatherLiveData.postValue(Event(State.error(e.message ?: "")))
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _weatherLiveData.postValue(Event(State.error(e.message ?: "")))
                }
            }
        }
    }

    private suspend fun addWeatherDetailIntoDb(weatherDataResponse: WeatherDataResponse) {
        val weatherDetail = WeatherDetail()
        weatherDetail.id = weatherResponse.cod.toInt()
        weatherDetail.latitude = weatherResponse.message.toString()
        repository.addWeather(weatherDetail)
    }

    fun fetchWeatherDetailFromDb(lat: String, lon: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val weatherDetail = repository.fetchWeatherDetail(lat, lon)
            withContext(Dispatchers.Main) {
                if (weatherDetail != null) {
                    if (AppUtils.isTimeExpired(weatherDetail.dateTime)) {
                        findCurrentWeather(lat, lon)
                    } else {
                        _weatherLiveData.postValue(
                            Event(State.success(weatherDetail))
                        )
                    }
                } else {
                    findCurrentWeather(lat, lon)
                }
            }
        }
    }
}