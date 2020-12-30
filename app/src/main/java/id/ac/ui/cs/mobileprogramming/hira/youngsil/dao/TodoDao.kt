package id.ac.ui.cs.mobileprogramming.hira.youngsil.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.ac.ui.cs.mobileprogramming.hira.youngsil.entity.Todo

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTodo(vararg todo: Todo)

    @Query("SELECT * FROM todo WHERE is_done=0 AND deadline BETWEEN :startTime AND :endTime")
    fun getTodaysTodo(startTime: Long, endTime: Long): List<Todo?>

    @Query("SELECT * FROM todo WHERE is_done=0")
    fun getAllUndoneTodo(): List<Todo?>

    @Query("UPDATE todo SET is_done=1 WHERE id = :tid")
    suspend fun updateTodo(tid: String)

}