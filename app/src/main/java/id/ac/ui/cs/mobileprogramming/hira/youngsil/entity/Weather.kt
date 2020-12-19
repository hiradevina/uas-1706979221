package id.ac.ui.cs.mobileprogramming.hira.youngsil.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class Weather(
    @PrimaryKey() @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "lat") val lat: Double,
    @ColumnInfo(name = "lon") val lon: Double,
    @ColumnInfo(name = "temp") val temp: Double,
    @ColumnInfo(name = "name") val location: String?,
) {
}