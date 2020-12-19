package id.ac.ui.cs.mobileprogramming.hira.youngsil.repository

import id.ac.ui.cs.mobileprogramming.hira.youngsil.entity.Quote
import kotlinx.coroutines.flow.Flow

interface IQuoteRepository {
    suspend fun insertQuote(title: String)

    fun getQuote() : Flow<Quote?>
}