package paba.meet2.implisitintent

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.CalendarContract
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val messageBtn = findViewById<Button>(R.id.btnMsg)
        messageBtn.setOnClickListener {
            val _sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra("address", "08112345")
                putExtra("sms_body", "ISI SMS")
                type = "text/plain"
            }

            if (_sendIntent.resolveActivity(packageManager) != null) {
//                startActivity(_sendIntent)
                startActivity(Intent.createChooser(_sendIntent, "Choose Your App"))
            }
        }

        val alarmBtn = findViewById<Button>(R.id.btnAlarm)
        alarmBtn.setOnClickListener {
            val _alarmIntent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
                putExtra(AlarmClock.EXTRA_MESSAGE, "Coba Alarm")
                putExtra(AlarmClock.EXTRA_HOUR, 11) //Ngatur di jam berapa
                putExtra(AlarmClock.EXTRA_MINUTES, 40) //Ngatur di menit berapa
                putExtra(AlarmClock.EXTRA_SKIP_UI, true)

            }
            startActivity(_alarmIntent)
        }

        val timerBtn = findViewById<Button>(R.id.btnTimer)
        timerBtn.setOnClickListener {
            val _timerIntent = Intent(AlarmClock.ACTION_SET_TIMER).apply {
                putExtra(AlarmClock.EXTRA_MESSAGE, "Coba Timer")
                putExtra(AlarmClock.EXTRA_LENGTH, 40)
                putExtra(AlarmClock.EXTRA_SKIP_UI, true)
            }
            startActivity(_timerIntent)
        }

        val tvAlamat = findViewById<EditText>(R.id.tvAlamat)
        val websiteBtn = findViewById<Button>(R.id.btnWebsite)
        websiteBtn.setOnClickListener {
            val _websiteIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://" + tvAlamat.text.toString()))
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(_websiteIntent)
            }
            else {
                Toast.makeText(this, "No Browser App Found", Toast.LENGTH_LONG).show()
            }
        }

        val eventBtn = findViewById<Button>(R.id.btnEvent)
        eventBtn.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val datePickerDialog = DatePickerDialog(this, {_,selectedYear, selectedMonth, selectedDay ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDay)

                val timePickerDialog = TimePickerDialog(
                    this, {_, selectedHour, selectedMinute ->
                        val selectedDateTime = Calendar.getInstance()
                        selectedDateTime.set(selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute)

                        val _eventIntent = Intent(Intent.ACTION_INSERT).apply {
                            data = CalendarContract.Events.CONTENT_URI
                            putExtra(CalendarContract.Events.TITLE, "Meeting")
                            putExtra(CalendarContract.Events.EVENT_LOCATION, "Office")
                            putExtra(CalendarContract.Events.DESCRIPTION, "Meeting Description")
                            putExtra(CalendarContract.Events.ALL_DAY, false)

                            val startTime = selectedDateTime.clone() as Calendar
                            val endTime = selectedDateTime.clone() as Calendar
                            endTime.add(Calendar.HOUR_OF_DAY, 1)
                            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime.timeInMillis)
                            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, endTime.timeInMillis)
                        }
                        startActivity(_eventIntent)
                    },
                    hour, minute, true
                )
                timePickerDialog.show()
            },
                year,month, day
            )
            datePickerDialog.show()
        }

        val ivHasil = findViewById<ImageView>(R.id.ivHasil)
        val cameraBtn = findViewById<Button>(R.id.btnPhoto)
        val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            bitmap ->
            if (bitmap != null) {
                ivHasil.setImageBitmap(bitmap)
            }
        }

        cameraBtn.setOnClickListener {
            cameraLauncher.launch(null)
        }
    }
}