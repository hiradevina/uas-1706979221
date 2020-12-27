package id.ac.ui.cs.mobileprogramming.hira.youngsil.ui.add_todo

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.CalendarContract.Calendars
import android.provider.CalendarContract.Events
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import id.ac.ui.cs.mobileprogramming.hira.youngsil.R
import id.ac.ui.cs.mobileprogramming.hira.youngsil.utils.State
import kotlinx.android.synthetic.main.add_todo_fragment.*
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class AddTodoFragment : Fragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    private var calendar: Calendar = Calendar.getInstance()

    companion object {
        private const val TAG = "AddTodoFragment"
        private const val calendarName = "Youngsil"
    }

    private val requestMultiplePermissions =
        this.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (!permissions.containsValue(false)) {
                submitTodo(requireView().context)
            }
        }

    private val addTodoViewModel: AddTodoViewModel by activityViewModels() {
        defaultViewModelProviderFactory
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
            if (it) {
                addtodo_deadline.text =
                    SimpleDateFormat.getDateTimeInstance().format(Date(calendar.timeInMillis))
            }
        })
        addTodoViewModel.todo.observe(viewLifecycleOwner, {
            when (it) {
                is State.Success -> {
                    addtodo_loading.visibility = View.GONE
                    Toast.makeText(context, getString(R.string.addtodo_success), Toast.LENGTH_LONG).show()
                }
                is State.Loading -> showLoading()

                is State.Failed -> hideLoading()

                is State.Initialize -> hideLoading()
            }
        }

        )
        addtodo_deadline.setOnClickListener {
            showDatePicker(view)
        }

        addtodo_submit.setOnClickListener {
            checkCalendarPermissionAndAddEventToCalendar(requireView().context)
        }
    }

    fun hideLoading() {
        addtodo_loading.visibility = View.GONE
    }

    fun showLoading() {
        addtodo_loading.visibility = View.VISIBLE
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
        val timePickerDialog = TimePickerDialog(
            this.context, this, 0, 0,
            DateFormat.is24HourFormat(this.context)
        )
        timePickerDialog.show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, hourOfDay)
        addTodoViewModel.alreadyPickDeadline()
    }

    fun submitTodo(context: Context) {
        val title = addtodo_title.text?.trim().toString()
        val description = addtodo_description.text?.trim()?.toString()
        val deadline = calendar.timeInMillis
        addTodoViewModel.addTodo(title, description, deadline)
        addEventToDeviceCalendar(context, title, description, deadline)
    }

    @SuppressLint("MissingPermission")
    private fun addEventToDeviceCalendar(
        context: Context,
        title: String,
        description: String?,
        deadline: Long
    ): Long? {
        var calId: Long? = getCalendarId(context)
        if (calId == -1L) {
            calId = makeYoungSilCalendar(context)
        }

        val values = ContentValues()
        values.put(Events.DTSTART, deadline)
        values.put(Events.DTEND, deadline)
        values.put(Events.TITLE, title)
        if (description != null) {
            values.put(Events.DESCRIPTION, description)
        }
        values.put(Events.CALENDAR_ID, calId)
        values.put(Events.EVENT_TIMEZONE, "Asia/Jakarta")
        values.put(Events.ORGANIZER, "fakhiradevina@gmail.com")
        val uri: Uri? = context.contentResolver.insert(Events.CONTENT_URI, values)
        addTodoViewModel.finishAddingToLocalCalendar()
        return uri?.lastPathSegment?.toLong()
    }

    private fun makeYoungSilCalendar(context: Context): Long? {
        val values = ContentValues()
        values.put(
            Calendars.ACCOUNT_NAME,
            calendarName
        )
        values.put(
            Calendars.ACCOUNT_TYPE,
            CalendarContract.ACCOUNT_TYPE_LOCAL
        )
        values.put(
            Calendars.NAME,
            "YoungSil Calendar"
        )
        values.put(
            Calendars.CALENDAR_DISPLAY_NAME,
            "YoungSil Calendar"
        )
        values.put(
            Calendars.CALENDAR_COLOR,
            -0x10000
        )
        values.put(
            Calendars.CALENDAR_ACCESS_LEVEL,
            Calendars.CAL_ACCESS_OWNER
        )
        values.put(
            Calendars.OWNER_ACCOUNT,
            "fakhiradevina@googlemail.com"
        )
        values.put(
            Calendars.CALENDAR_TIME_ZONE,
            "Asia/Jakarta"
        )
        values.put(
            Calendars.SYNC_EVENTS,
            1
        )
        val builder: Uri.Builder = Calendars.CONTENT_URI.buildUpon()
        builder.appendQueryParameter(
            Calendars.ACCOUNT_NAME,
            calendarName
        )
        builder.appendQueryParameter(
            Calendars.ACCOUNT_TYPE,
            CalendarContract.ACCOUNT_TYPE_LOCAL
        )
        builder.appendQueryParameter(
            CalendarContract.CALLER_IS_SYNCADAPTER,
            "true"
        )
        val uri: Uri? = context.contentResolver.insert(builder.build(), values)
        return uri?.lastPathSegment?.toLong()
    }

    @SuppressLint("MissingPermission")
    private fun getCalendarId(context: Context): Long {
        val projection = arrayOf(CalendarContract.Calendars._ID)
        val selection = CalendarContract.Calendars.ACCOUNT_NAME +
                " = ? AND " +
                CalendarContract.Calendars.ACCOUNT_TYPE +
                " = ? "
        // use the same values as above:
        val selArgs = arrayOf(
            calendarName,
            CalendarContract.ACCOUNT_TYPE_LOCAL
        )
        val cursor: Cursor? = context.contentResolver.query(
            CalendarContract.Calendars.CONTENT_URI,
            projection,
            selection,
            selArgs,
            null
        )
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                return cursor.getLong(0)
            }
        }
        return -1
    }

    private fun checkCalendarPermissionAndAddEventToCalendar(
        context: Context,
    ) {
        when {
            ((checkSelfPermission(
                context,
                Manifest.permission.WRITE_CALENDAR
            ) == PermissionChecker.PERMISSION_GRANTED) || (checkSelfPermission(
                context,
                Manifest.permission.READ_CALENDAR
            ) == PermissionChecker.PERMISSION_GRANTED)
                    ) -> {
                submitTodo(context)
            }
            shouldShowRequestPermissionRationale("To add Todo to local calendar") -> {

            }
            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                val calendarPermission =
                    arrayOf(Manifest.permission.WRITE_CALENDAR, Manifest.permission.READ_CALENDAR)
                requestMultiplePermissions.launch(
                    calendarPermission
                )
            }
        }
    }

}