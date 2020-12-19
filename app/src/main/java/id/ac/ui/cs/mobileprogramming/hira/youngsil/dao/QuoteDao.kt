package id.ac.ui.cs.mobileprogramming.hira.youngsil.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.ac.ui.cs.mobileprogramming.hira.youngsil.entity.Quote
import kotlinx.coroutines.flow.Flow

@Dao()
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(vararg quote: Quote)

    @Query("SELECT * FROM quote LIMIT 1")
    fun getQuote(): Flow<Quote?>
}