package id.ac.ui.cs.mobileprogramming.hira.youngsil

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import dagger.hilt.android.AndroidEntryPoint
import id.ac.ui.cs.mobileprogramming.hira.youngsil.receiver.NotificationReceiver
import id.ac.ui.cs.mobileprogramming.hira.youngsil.ui.add_todo.AddTodoActivity
import id.ac.ui.cs.mobileprogramming.hira.youngsil.ui.display_todo.DetailTodoFragment
import id.ac.ui.cs.mobileprogramming.hira.youngsil.ui.display_todo.DisplayTodoViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val displayTodoViewModel: DisplayTodoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainactivity_button.setOnClickListener {
            val addTodoIntent = Intent(this, AddTodoActivity::class.java)
            startActivity(addTodoIntent)
        }
        displayTodoViewModel.selectedTodo.observe(this, {
            if (findViewById<View>(R.id.mainactivity_detailtodofragment) == null) {
                val detailTodoFragment = DetailTodoFragment()
                val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.mainactivity_framelayout, detailTodoFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        })
        setupDailyNotification()
    }

    companion object {
        private const val TAG = "MainActivity"
    }


    // send notification every day at 10 am
    fun setupDailyNotification() {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 11)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        val intent = Intent(applicationContext, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        // testing
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + 2000,
            pendingIntent
        )
        Log.d(TAG, "Alarm manager set")
        // production
//        alarmManager.setRepeating(
//            AlarmManager.RTC_WAKEUP,
//            calendar.timeInMillis,
//            AlarmManager.INTERVAL_DAY,
//            pendingIntent
//        )
    }
}