package id.ac.ui.cs.mobileprogramming.hira.youngsil.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import id.ac.ui.cs.mobileprogramming.hira.youngsil.YoungSilDb
import id.ac.ui.cs.mobileprogramming.hira.youngsil.dao.QuoteDao
import id.ac.ui.cs.mobileprogramming.hira.youngsil.dao.TodoDao
import id.ac.ui.cs.mobileprogramming.hira.youngsil.dao.WeatherDao
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ApplicationModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): YoungSilDb {
        return Room.databaseBuilder(appContext,
                YoungSilDb::class.java,
                "youngsil-db")
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherDao(db: YoungSilDb): WeatherDao {
        return db.weatherDao()
    }

    @Provides
    @Singleton
    fun provideQuoteDao(db: YoungSilDb): QuoteDao {
        return db.quoteDao()
    }

    @Provides
    @Singleton
    fun provideTodoDao(db: YoungSilDb): TodoDao {
        return db.todoDao()
    }
}