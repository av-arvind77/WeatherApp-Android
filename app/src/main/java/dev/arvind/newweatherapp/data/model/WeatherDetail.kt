package dev.arvind.newweatherapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.arvind.newweatherapp.data.model.WeatherDetail.Companion.TABLE_NAME

/**
 * Data class for Database entity and Serialization.
 */
@Entity(tableName = TABLE_NAME)
data class WeatherDetail(

    @PrimaryKey
    var id: Int? = 0,
    var temp: Double? = null,
    var tempTuesday: Double? = null,
    var tempWednesday: Double? = null,
    var tempThursday: Double? = null,
    var tempFriday: Double? = null,
    var latitude: String? = null,
    var longitude: String? = null,
    var cityName: String? = null,
    var dateTime: String? = null
) {
    companion object {
        const val TABLE_NAME = "weather_detail"
    }
}