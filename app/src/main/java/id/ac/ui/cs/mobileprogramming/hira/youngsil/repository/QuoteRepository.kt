package id.ac.ui.cs.mobileprogramming.hira.youngsil.repository

import id.ac.ui.cs.mobileprogramming.hira.youngsil.dao.QuoteDao
import id.ac.ui.cs.mobileprogramming.hira.youngsil.entity.Quote
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

class QuoteRepository @Inject constructor(private val quoteDao: QuoteDao) : IQuoteRepository {
    override suspend fun insertQuote(title: String) {
        quoteDao.insertQuote(Quote(UUID.randomUUID().toString(), title))
    }

    override fun getQuote(): Flow<Quote?> {
        return quoteDao.getQuote()
    }
}