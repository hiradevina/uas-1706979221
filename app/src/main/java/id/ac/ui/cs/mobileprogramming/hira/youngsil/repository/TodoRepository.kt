package id.ac.ui.cs.mobileprogramming.hira.youngsil.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import id.ac.ui.cs.mobileprogramming.hira.youngsil.dao.TodoDao
import id.ac.ui.cs.mobileprogramming.hira.youngsil.entity.Todo
import java.util.*
import javax.inject.Inject

class TodoRepository @Inject constructor(private val todoDao: TodoDao) : ITodoRepository {
    override suspend fun insertTodo(title: String, description: String?, deadline: Long) {
        Log.d("TODO REPOSITORY", "inserting")
        todoDao.insertTodo(
            Todo(
                UUID.randomUUID().toString(),
                title,
                description,
                false,
                deadline
            )
        )
        Log.d("TODO REPOSITORY", "done")
    }

    override fun getAllUndoneTodo(): PagingSource<Int, Todo> {
        return todoDao.getAllUndoneTodo()
    }

    override fun getTodayTodo(): List<Todo?> {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        calendar.set(year, month, day, 0,0,0)
        val todayEpoch = calendar.timeInMillis

        calendar.add(Calendar.MILLISECOND, 1000 * 60 * 60 * 24)
        val tommorowEpoch = calendar.timeInMillis
        return todoDao.getTodaysTodo(todayEpoch, tommorowEpoch)
    }

    override suspend fun updateTodo(tid: String) {
        todoDao.updateTodo(tid)
    }
}