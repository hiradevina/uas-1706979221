package id.ac.ui.cs.mobileprogramming.hira.youngsil.ui.display_todo

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ac.ui.cs.mobileprogramming.hira.youngsil.entity.Todo
import id.ac.ui.cs.mobileprogramming.hira.youngsil.repository.TodoRepository
import id.ac.ui.cs.mobileprogramming.hira.youngsil.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DisplayTodoViewModel @ViewModelInject constructor(private val todoRepository: TodoRepository) :
    ViewModel() {
    private val _selectedTodo = MutableLiveData<Todo>()
    val selectedTodo: LiveData<Todo>
        get() = _selectedTodo

    private val _allUndoneTodo = MutableLiveData<State<List<Todo?>>>()
    val allUndoneTodo: LiveData<State<List<Todo?>>>
        get() = _allUndoneTodo

    private var checkTodoJob: Job? = null

    init {
        _allUndoneTodo.postValue(State.init())
        getAllUndoneTodo()
    }

    private fun getAllUndoneTodo() {
        viewModelScope.launch(Dispatchers.IO) {
            _allUndoneTodo.postValue(State.loading())
            val undoneTodos = todoRepository.getAllUndoneTodo()
            _allUndoneTodo.postValue(State.success(undoneTodos))
        }
    }

    fun selectTodo(selected: Todo) {
        _selectedTodo.postValue(selected)
    }

    fun checkTodo(selected: Todo) {
        checkTodoJob?.cancel()
        checkTodoJob = viewModelScope.launch(Dispatchers.IO) {
            todoRepository.updateTodo(selected.id)
        }
    }

    companion object {
        private const val TAG = "DisplayTodoViewModel"
    }
}