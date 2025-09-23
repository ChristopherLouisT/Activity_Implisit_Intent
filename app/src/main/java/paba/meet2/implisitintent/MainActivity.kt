package paba.meet2.implisitintent

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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
    }
}