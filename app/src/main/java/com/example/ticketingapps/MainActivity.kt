package com.example.ticketingapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.Toast
import com.example.ticketingapps.custom_view.SeatsView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        val seatsView=findViewById<SeatsView>(R.id.seatsView)
        val finishButton=findViewById<Button>(R.id.finishButton)



        finishButton.setOnClickListener {
            seatsView.seat?.let {
                Toast.makeText(this,"Kursi Anda nomor ${it.name}",Toast.LENGTH_SHORT).show()
            }?:run{
                Toast.makeText(this,"Silahkan pilih kursi terlebih dahulu",Toast.LENGTH_SHORT).show()
            }
        }

    }
}