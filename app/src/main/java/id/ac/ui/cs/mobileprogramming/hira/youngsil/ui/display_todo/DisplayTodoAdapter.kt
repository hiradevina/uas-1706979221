package id.ac.ui.cs.mobileprogramming.hira.youngsil.ui.display_todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.hira.youngsil.R
import id.ac.ui.cs.mobileprogramming.hira.youngsil.entity.Todo
import id.ac.ui.cs.mobileprogramming.hira.youngsil.utils.RecyclerViewOnItemDone
import id.ac.ui.cs.mobileprogramming.hira.youngsil.utils.RecyclerViewOnItemSelected
import kotlinx.android.synthetic.main.todo_viewholder_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class DisplayTodoAdapter(
    private val onItemSelected: RecyclerViewOnItemSelected,
    private val onItemDone: RecyclerViewOnItemDone
) : RecyclerView.Adapter<DisplayTodoAdapter.DisplayTodoViewHolder>() {
    private var todos = emptyList<Todo>()

    inner class DisplayTodoViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bindTodo(todo: Todo) {
            view.displaytodo_viewholder_title.text = todo.title
            view.displaytodo_viewholder_deadline.text =
                SimpleDateFormat.getDateTimeInstance().format(Date(todo.deadline))
            view.displaytodo_viewholder_checkbox.isChecked = todo.isDone
            view.displaytodo_viewholder_checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    onItemDone.onItemDone(todo)
                }
            }
        }
    }

    fun setTodos(newTodos: List<Todo>) {
        todos = newTodos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DisplayTodoAdapter.DisplayTodoViewHolder {
        return DisplayTodoViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.todo_viewholder_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DisplayTodoAdapter.DisplayTodoViewHolder, position: Int) {
        holder.bindTodo(todos[position])
        holder.itemView.setOnClickListener { onItemSelected.onItemSelected(todos[position]) }
    }

    override fun getItemCount(): Int {
        return todos.size
    }
}