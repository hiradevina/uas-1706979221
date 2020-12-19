package id.ac.ui.cs.mobileprogramming.hira.youngsil.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.annotation.CallSuper
import androidx.paging.Pager
import androidx.paging.PagingConfig
import dagger.hilt.android.AndroidEntryPoint
import id.ac.ui.cs.mobileprogramming.hira.youngsil.repository.ITodoRepository
import id.ac.ui.cs.mobileprogramming.hira.youngsil.utils.NotificationCreator
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

abstract class HiltBroadcastReceiver : BroadcastReceiver() {
    @CallSuper
    override fun onReceive(context: Context?, intent: Intent?) {
    }
}

@AndroidEntryPoint
class NotificationReceiver : HiltBroadcastReceiver() {

    @Inject
    lateinit var todoRepository: ITodoRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent != null && context != null) {
            var allTodo: String = ""
            var job = GlobalScope.launch(Dispatchers.IO) {
                todoRepository.getTodayTodo().forEach { element ->
                    if (element != null) {
                        Log.d(TAG, "element")
                        val date = Date(element.deadline)
                        allTodo += "${element.title} - ${
                            SimpleDateFormat.getTimeInstance().format(date)
                        }\n"
                    }
                }
                withContext (Dispatchers.Main) {
                    NotificationCreator(context, allTodo).createNotification()
                }
            }
        }

    }
    companion object {
        private const val TAG = "Notification Receiver"
    }
}

