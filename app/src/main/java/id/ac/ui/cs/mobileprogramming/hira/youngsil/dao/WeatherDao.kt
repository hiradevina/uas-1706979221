package id.ac.ui.cs.mobileprogramming.hira.youngsil.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import id.ac.ui.cs.mobileprogramming.hira.youngsil.entity.Weather

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWeather(vararg weather: Weather)
}