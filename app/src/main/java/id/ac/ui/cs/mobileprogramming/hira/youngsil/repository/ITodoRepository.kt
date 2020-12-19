package id.ac.ui.cs.mobileprogramming.hira.youngsil.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import id.ac.ui.cs.mobileprogramming.hira.youngsil.entity.Todo

interface ITodoRepository {
    suspend fun insertTodo(title: String, description:String?, deadline:Long)

    fun getAllUndoneTodo() : PagingSource<Int, Todo>

    fun getTodayTodo(): List<Todo?>

    suspend fun updateTodo(tid: String)
}