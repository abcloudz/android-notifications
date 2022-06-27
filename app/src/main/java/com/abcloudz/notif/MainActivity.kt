package com.abcloudz.notif

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.abcloudz.notif.extensions.randomInt
import com.abcloudz.notif.extensions.scheduleExact
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private var ids = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textIds = findViewById<TextView>(R.id.textIds)

        val timePicker = findViewById<TimePicker>(R.id.picker).apply {
            setIs24HourView(true)
        }

        findViewById<View>(R.id.buttonSchedule).setOnClickListener {
            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, timePicker.hour)
                set(Calendar.MINUTE, timePicker.minute)
                set(Calendar.SECOND, 0)
            }
            val notificationId = randomInt
            scheduleExact(notificationId, calendar, 0)
            Toast.makeText(this, "Scheduled $notificationId", Toast.LENGTH_LONG).show()
            ids = "$notificationId\n$ids"
            textIds.text = ids
        }

    }
}