package id.ac.ui.cs.mobileprogramming.hira.youngsil.di

import android.content.Context
import androidx.room.Room
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import id.ac.ui.cs.mobileprogramming.hira.youngsil.BuildConfig
import id.ac.ui.cs.mobileprogramming.hira.youngsil.YoungSilDb
import id.ac.ui.cs.mobileprogramming.hira.youngsil.api.ApiHelper
import id.ac.ui.cs.mobileprogramming.hira.youngsil.api.ApiService
import id.ac.ui.cs.mobileprogramming.hira.youngsil.api.IApiHelper
import id.ac.ui.cs.mobileprogramming.hira.youngsil.dao.QuoteDao
import id.ac.ui.cs.mobileprogramming.hira.youngsil.dao.TodoDao
import id.ac.ui.cs.mobileprogramming.hira.youngsil.dao.WeatherDao
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object ApplicationModule {

    @Provides
    fun provideWeatherBaseUrl() = "https://api.openweathermap.org/data/2.5/"

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .setPrettyPrinting()
                    .create()
            )
        )
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelper): IApiHelper = apiHelper

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): YoungSilDb {
        return Room.databaseBuilder(
            appContext,
            YoungSilDb::class.java,
            "youngsil-db"
        )
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