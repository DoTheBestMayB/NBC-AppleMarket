package com.dothebestmayb.nbc_applemarket

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dothebestmayb.nbc_applemarket.databinding.ActivityMainBinding
import com.dothebestmayb.nbc_applemarket.util.PRODUCT_NOTIFICATION_CHANNEL_ID

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val name = getString(R.string.product_alert_channel_name)
        val descriptionText = getString(R.string.product_alert_channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(PRODUCT_NOTIFICATION_CHANNEL_ID, name, importance)
        channel.description = descriptionText
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}