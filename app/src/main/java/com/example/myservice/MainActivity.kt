package com.example.myservice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myservice.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val serviceIntent= Intent(this, MyBackgroundService::class.java)
        binding.btnStartBackgroundService.setOnClickListener {
            startService(serviceIntent)
        }

        binding.btnStopBackgroundService.setOnClickListener {
            stopService(serviceIntent)
            Toast.makeText(this, "Service dihentikan", Toast.LENGTH_SHORT).show()
        }
    }
}