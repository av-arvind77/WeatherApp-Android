package dev.arvind.newweatherapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.arvind.newweatherapp.data.local.dao.WeatherDetailDao
import dev.arvind.newweatherapp.data.model.WeatherDetail

@Database(
    entities = [WeatherDetail::class],
    version = 2
)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun getWeatherDao(): WeatherDetailDao

    companion object {
        const val db_name = "weather_database"

        @Volatile
        private var instance: WeatherDatabase? = null

        operator fun invoke(context: Context) = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, WeatherDatabase::class.java, db_name)
                .fallbackToDestructiveMigration()
                .build()
    }
}