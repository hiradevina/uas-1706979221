package id.ac.ui.cs.mobileprogramming.hira.youngsil.ui.display_todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.ac.ui.cs.mobileprogramming.hira.youngsil.R
import id.ac.ui.cs.mobileprogramming.hira.youngsil.entity.Todo
import id.ac.ui.cs.mobileprogramming.hira.youngsil.ui.weather.WeatherViewModel
import id.ac.ui.cs.mobileprogramming.hira.youngsil.utils.RecyclerViewOnItemDone
import id.ac.ui.cs.mobileprogramming.hira.youngsil.utils.RecyclerViewOnItemSelected
import id.ac.ui.cs.mobileprogramming.hira.youngsil.utils.State
import kotlinx.android.synthetic.main.fragment_display_todo.*
import kotlinx.android.synthetic.main.weather_fragment.*

@AndroidEntryPoint
class DisplayTodoFragment : Fragment(), RecyclerViewOnItemDone, RecyclerViewOnItemSelected {

    private val displayTodoViewModel: DisplayTodoViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = DisplayTodoAdapter(this, this)
        displayTodoViewModel.allUndoneTodo.observe(viewLifecycleOwner) {
            when (it) {
                is State.Success -> {
                    hideLoading()
                    if (it.data != null) {
                        adapter.setTodos(it.data as List<Todo>)
                    }
                }
                is State.Loading -> showLoading()

                is State.Failed -> {
                    Toast.makeText(context, "Failed", Toast.LENGTH_LONG)
                        .show()
                }

                is State.Initialize -> hideLoading()
            }
        }

        displaytodo_recyclerview.also {
            it.layoutManager = LinearLayoutManager(context)
            it.setHasFixedSize(true)
            it.adapter = adapter
        }
    }

    fun hideLoading() {
        displaytodo_loading.isVisible = false
    }

    fun showLoading() {
        displaytodo_loading.isVisible = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_todo, container, false)
    }

    companion object {
        private const val TAG = "DisplayTodoFragment"
    }

    override fun onItemSelected(todo: Todo) {
        displayTodoViewModel.selectTodo(todo)
    }

    override fun onItemDone(todo: Todo) {
        displayTodoViewModel.checkTodo(todo)
    }
}