package dev.arvind.newweatherapp.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class WeatherDataResponse(
    @SerializedName("cod")
    val cod: String,
    @SerializedName("message")
    val message: Int,
    @SerializedName("cnt")
    val cnt: Int,
    @SerializedName("list")
    val list: List<WeatherData>,
    @SerializedName("city")
    val city: City
) {
    @Keep
    data class City(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("coord")
        val coord: Coord,
        @SerializedName("country")
        val country: String,
        @SerializedName("population")
        val population: Int,
        @SerializedName("timezone")
        val timezone: Double,
        @SerializedName("sunrise")
        val sunrise: Double,
        @SerializedName("sunset")
        val sunset: Double
    )

    @Keep
    data class Coord(
        @SerializedName("lat")
        val lat: Double,
        @SerializedName("lon")
        val lon: Double
    )

    @Keep
    data class WeatherData(
        @SerializedName("dt")
        val dt: String,
        @SerializedName("main")
        val main: Main,
        @SerializedName("weather")
        val weather: List<Weather>,
        @SerializedName("clouds")
        val clouds: Clouds,
        @SerializedName("wind")
        val wind: Wind,
        @SerializedName("visibility")
        val visibility: String,
        @SerializedName("pop")
        val pop: String,
        @SerializedName("rain")
        val rain: Rain,
        @SerializedName("sys")
        val sys: Sys,
        @SerializedName("dt_txt")
        val dt_txt: String
    )

    @Keep
    data class Main(
        @SerializedName("temp")
        val temp: Double,
        @SerializedName("feels_like")
        val feels_like: String,
        @SerializedName("temp_min")
        val temp_min: String,
        @SerializedName("temp_max")
        val temp_max: String,
        @SerializedName("pressure")
        val pressure: String,
        @SerializedName("sea_level")
        val sea_level: String,
        @SerializedName("grnd_level")
        val grnd_level: String,
        @SerializedName("humidity")
        val humidity: String,
        @SerializedName("temp_kf")
        val temp_kf: String
    )

    @Keep
    data class Weather(
        @SerializedName("id")
        val id: Int,
        @SerializedName("main")
        val main: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("icon")
        val icon: String
    )

    @Keep
    data class Clouds(
        @SerializedName("all")
        val all : String
    )

    @Keep
    data class Wind(
        @SerializedName("speed")
        val speed: Double,
        @SerializedName("deg")
        val deg: Int,
        @SerializedName("gust")
        val gust: Double
    )

    @Keep
    data class Rain(
        @SerializedName("3h")
        val hour: String
    )

    @Keep
    data class Sys(
        @SerializedName("pod")
        val pod: String
    )
}


