package id.ac.ui.cs.mobileprogramming.hira.youngsil

import androidx.room.Database
import androidx.room.RoomDatabase
import id.ac.ui.cs.mobileprogramming.hira.youngsil.dao.QuoteDao
import id.ac.ui.cs.mobileprogramming.hira.youngsil.dao.TodoDao
import id.ac.ui.cs.mobileprogramming.hira.youngsil.dao.WeatherDao
import id.ac.ui.cs.mobileprogramming.hira.youngsil.entity.Quote
import id.ac.ui.cs.mobileprogramming.hira.youngsil.entity.Todo
import id.ac.ui.cs.mobileprogramming.hira.youngsil.entity.Weather

@Database(entities = [Quote::class, Todo::class, Weather::class], version = 1)
abstract class YoungSilDb : RoomDatabase() {
    abstract fun quoteDao(): QuoteDao

    abstract fun weatherDao(): WeatherDao

    abstract fun todoDao(): TodoDao
}