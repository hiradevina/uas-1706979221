package id.ac.ui.cs.mobileprogramming.hira.youngsil.ui.display_todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import id.ac.ui.cs.mobileprogramming.hira.youngsil.R
import kotlinx.android.synthetic.main.fragment_detail_todo.*
import java.text.SimpleDateFormat
import java.util.*

class DetailTodoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayTodoViewModel.selectedTodo.observe(viewLifecycleOwner) { todo ->
            detailtodo_title.text = todo.title
            detailtodo_description.text = todo.description
            detailtodo_deadline.text =
                SimpleDateFormat.getDateTimeInstance().format(Date(todo.deadline))
        }
    }

    private val displayTodoViewModel: DisplayTodoViewModel by activityViewModels()

    companion object {
        private const val TAG = "DetailTodoFragment"
    }
}