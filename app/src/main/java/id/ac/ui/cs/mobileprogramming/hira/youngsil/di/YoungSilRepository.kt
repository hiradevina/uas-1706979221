package id.ac.ui.cs.mobileprogramming.hira.youngsil.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import id.ac.ui.cs.mobileprogramming.hira.youngsil.repository.*

@Module
@InstallIn(ApplicationComponent::class)
interface YoungSilRepository {

    @Binds
    fun bindQuoteRepository(quoteRepository: QuoteRepository) : IQuoteRepository

    @Binds
    fun bindWeatherRepository(weatherRepository: WeatherRepository) : IWeatherRepository

    @Binds
    fun bindTodoRepository(todoRepository: TodoRepository) : ITodoRepository

}