package id.ac.ui.cs.mobileprogramming.hira.youngsil.ui.add_todo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.fragment.app.activityViewModels
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.add_todo_fragment.*
import dagger.hilt.android.AndroidEntryPoint
import id.ac.ui.cs.mobileprogramming.hira.youngsil.R
import java.util.*

@AndroidEntryPoint
class AddTodoFragment : Fragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    private var calendar: Calendar = Calendar.getInstance()
    companion object {
        private const val TAG = "AddTodoFragment"
    }

    private val addTodoViewModel: AddTodoViewModel by activityViewModels() {
        defaultViewModelProviderFactory
    }

    fun submitTodo() {
        val title = addtodo_title.text?.trim().toString()
        val description = addtodo_description.text?.trim().toString()
        val deadline = calendar.timeInMillis
        addTodoViewModel.addTodo(title, description, deadline)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_todo_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addTodoViewModel.alreadyPickDeadline.observe(viewLifecycleOwner, {
            addtodo_submit.isVisible = it
        })
        addtodo_deadline.setOnClickListener {
            showDatePicker(view)
        }

        addtodo_submit.setOnClickListener {
            val title = addtodo_title.text?.trim().toString()
            val description = addtodo_description.text?.trim().toString()
            val deadline = calendar.timeInMillis
            addTodoViewModel.addTodo(title, description, deadline)
        }
    }

     private fun showDatePicker(view: View) {
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        val datePickerDialog =
            DatePickerDialog(view.context, this, year, month, day)
        datePickerDialog.show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        val timePickerDialog = TimePickerDialog(this.context, this, 0, 0,
            DateFormat.is24HourFormat(this.context))
        timePickerDialog.show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, hourOfDay)
        val testing = calendar.timeInMillis
        addTodoViewModel.alreadyPickDeadline()
    }

}