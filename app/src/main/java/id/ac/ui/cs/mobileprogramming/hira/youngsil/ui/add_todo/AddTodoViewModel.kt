package id.ac.ui.cs.mobileprogramming.hira.youngsil.ui.add_todo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import id.ac.ui.cs.mobileprogramming.hira.youngsil.entity.Todo
import id.ac.ui.cs.mobileprogramming.hira.youngsil.repository.TodoRepository
import id.ac.ui.cs.mobileprogramming.hira.youngsil.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AddTodoViewModel @ViewModelInject constructor(private val todoRepository: TodoRepository) :
    ViewModel() {
    companion object {
        private const val TAG = "AddTodoViewModel"
    }

    private val _todo = MutableLiveData<State<Todo>>()
    var todo: LiveData<State<Todo>> = _todo

    private val _insert = MutableLiveData<Boolean>()
    val isInsertJobRunning: LiveData<Boolean> = _insert

    private val _pickDeadline = MutableLiveData<Boolean>()
    val alreadyPickDeadline: LiveData<Boolean> = _pickDeadline

    private var job: Job? = null

    init {
        // init state
        _todo.value = State.init()
        _insert.value = false
        _pickDeadline.value = false
    }

    fun addTodo(
        title: String,
        description: String?,
        deadline: Long
    ) {
        _insert.value = true
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            todoRepository.insertTodo(title, description, deadline)
        }
        Log.d(TAG, "Finish adding todo")
    }

    fun alreadyPickDeadline() {
        _pickDeadline.value = true
    }
}